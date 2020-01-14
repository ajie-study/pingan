package com.pingan.starlink.controller;

import com.pingan.starlink.exception.BadRequestException;
import com.pingan.starlink.exception.NotFoundException;
import com.pingan.starlink.logger.LoggerBuilder;
import com.pingan.starlink.logger.MonitorLogger;
import com.pingan.starlink.model.ArtifactoryRepo;
import com.pingan.starlink.model.jfrog.ArtifactsQueryResponseData;
import com.pingan.starlink.service.ArtifactoryRepoDBService;
import com.pingan.starlink.service.JfrogClientService;
import com.pingan.starlink.util.EireneUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/v1/eirene/jfrog")
@CrossOrigin
public class JfrogController {

    @Autowired
    private MonitorLogger monitorLogger;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JfrogClientService jfrogClientService;

    @Autowired
    private ArtifactoryRepoDBService artifactoryRepoDBService;

    @ApiOperation(value = "查询项目对应的制品仓库", notes = "制品仓库列表查询")
    @GetMapping(value = "/repos/{uuid}/artifacts")
    public ArtifactsQueryResponseData queryArtifacts(@RequestParam(required = false, defaultValue = "1") int page,
                                                     @RequestParam(required = false, defaultValue = "10") int pageSize,
                                                     @RequestParam(required = false, defaultValue = "created") String sort,
                                                     @RequestParam String projectKey,
                                                     @PathVariable String uuid) throws Exception {
        LoggerBuilder loggerBuilder = new LoggerBuilder().setInterfaceId("jfrog.repos.queryArtifacts").setArgs(String.format("{\"uuid\":\"%s\", \"projectKey\":\"%s\"}", uuid, projectKey)).setRequest(request);
        ArtifactsQueryResponseData artifactsQueryResponseData = null;
        try {
            ArtifactoryRepo artifactoryRepo = artifactoryRepoDBService.getArtifactoryRepo(uuid);
            if (artifactoryRepo == null) {
                throw new NotFoundException("not found in db");
            }

            String findStr = String.format("items.find({\"$and\" : [{\"property.key\" : \"project_name\"},{\"property.value\" : \"%s\"},{\"repo\" : \"%s\"}]})", projectKey.toLowerCase(), artifactoryRepo.getRepoName());
            StringBuffer aqlString = new StringBuffer(findStr);
            String orderStr = String.format(".sort({\"$desc\" : [\"%s\"]})", sort);
            aqlString.append(orderStr);
            int start = (page - 1) * pageSize;
            String pageStr = String.format(".offset(%d).limit(%d)", start, pageSize);
            aqlString.append(pageStr);
            artifactsQueryResponseData = jfrogClientService.queryArtifacts(aqlString.toString(), artifactoryRepo.getRepoUrl(), artifactoryRepo.getToken());
        } catch (Exception e) {
            if (e instanceof NotFoundException) {
                loggerBuilder.setBusResult("false").setBusReason(e.getMessage());
            } else {
                loggerBuilder.setSysResult("false").setSysReason(e.getMessage()).setBusResult("false");
            }
            throw e;
        } finally {
            monitorLogger.info(loggerBuilder.buildLog());
        }
        return artifactsQueryResponseData;
    }

    @ApiOperation(value = "获取指定部门下的仓库列表")
    @RequestMapping(value = "/repos", method = RequestMethod.GET)
    @ResponseBody
    public List<ArtifactoryRepo> getAllRepos(@RequestParam String department) throws Exception {
        List<ArtifactoryRepo> artifactoryRepos = artifactoryRepoDBService.getAllRepos(department);
        if (artifactoryRepos == null || artifactoryRepos.size() < 1) {
            throw new NotFoundException("not found in db");
        }
        return artifactoryRepos;
    }

    @ApiOperation(value = "通过主键id查询仓库信息")
    @RequestMapping(value = "/repos/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public ArtifactoryRepo getArtifactoryRepo(@PathVariable String uuid) throws Exception {
        ArtifactoryRepo artifactoryRepo = artifactoryRepoDBService.getArtifactoryRepo(uuid);
        if (artifactoryRepo == null) {
            throw new NotFoundException("not found in db");
        }
        return artifactoryRepo;
    }

    @ApiOperation(value = "通过主键id删除仓库信息")
    @RequestMapping(value = "/repos/{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ModelMap deleteArtiDeptRepoById(@PathVariable String uuid) throws Exception {
        ModelMap result = new ModelMap();
        int res = artifactoryRepoDBService.deleteById(uuid);
        if (res < 1) {
            throw new NotFoundException("not found in db");
        }
        result.put("msg", "删除成功!");
        result.put("result", res);
        return result;
    }

    @ApiOperation(value = "新增或修改仓库信息")
    @RequestMapping(value = "/repos", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ArtifactoryRepo saveOrUpdateArtifactoryRepo(@RequestBody ArtifactoryRepo artifactoryRepo) throws Exception {
        if (artifactoryRepo == null || StringUtils.isEmpty(artifactoryRepo.getDepartment())
                || StringUtils.isEmpty(artifactoryRepo.getRepoName())
                || StringUtils.isEmpty(artifactoryRepo.getRepoType())
                || StringUtils.isEmpty(artifactoryRepo.getRepoUrl())) {
            throw new BadRequestException("传入参数有误");
        }

        if (StringUtils.isEmpty(artifactoryRepo.getUuid())) {
            artifactoryRepo.setUuid(EireneUtil.randomUUID());
            artifactoryRepo = artifactoryRepoDBService.saveArtifactoryRepo(artifactoryRepo);
        } else {
            artifactoryRepo = artifactoryRepoDBService.updateArtifactoryRepo(artifactoryRepo);
        }

        if (artifactoryRepo == null) {
            throw new NotFoundException("not found in db");
        }
        return artifactoryRepo;
    }
}
