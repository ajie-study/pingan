package com.pingan.starlink.controller;


import com.pingan.starlink.exception.NotFoundException;
import com.pingan.starlink.model.CmsAppidProjectRelation;
import com.pingan.starlink.service.CmsClientService;
import com.pingan.starlink.vo.jira.CmsNodeVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/eirene/cms")
@CrossOrigin
public class CmsAppidProjectRelationController {

    @Autowired
    private CmsClientService cmsClientService;


    @ApiOperation(value = "新增cms和项目关系", notes = "")
    @RequestMapping(value = "/appid_project_relations", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public CmsAppidProjectRelation insert(@RequestBody CmsAppidProjectRelation cmsAppidProjectRelation) throws Exception {

        CmsAppidProjectRelation cmsAppidProjectRelation1 = cmsClientService.insertCmsAndProjectRelation(cmsAppidProjectRelation);

        return cmsAppidProjectRelation1;

    }
    @ApiOperation(value = "查询部门", notes = "根据uuid查询单个")
    @RequestMapping(value = "/appid_project_relations/{uuid}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public CmsAppidProjectRelation selectByUuid(@PathVariable(value = "uuid") String uuid) throws Exception {

        CmsAppidProjectRelation cmsAppidProjectRelation = cmsClientService.selectAppidProjectRelationByUuid(uuid);

        return cmsAppidProjectRelation;

    }

    @ApiOperation(value = "查询部门", notes = "根据项目查询否则查询全部")
    @RequestMapping(value = "/appid_project_relations", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<CmsAppidProjectRelation> selectAll(@RequestParam(value = "project", required = false) String project) throws Exception {

        List<CmsAppidProjectRelation> cmsAppidProjectRelations = cmsClientService.selectAppidProjectRelationAll();

        return cmsAppidProjectRelations;

    }

    @ApiOperation(value = "删除appid和项目的关系", notes = "")
    @RequestMapping(value = "/appid_project_relations/{uuid}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ModelMap delete(@PathVariable("uuid") String uuid) throws Exception {
        ModelMap result = new ModelMap();
        int cmsAppidProjectRelation = cmsClientService.deleteAppidProjectRelationByUuid(uuid);

        if (cmsAppidProjectRelation < 1) {
            throw new NotFoundException("not found in db");
        }
        result.put("msg", "删除成功!");
        result.put("result", cmsAppidProjectRelation);

        return result;

    }

    @ApiOperation(value = "查询部门", notes = "根据项目查询否则查询全部")
    @RequestMapping(value = "/appid_project_relations_appid", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String,List<CmsNodeVO>> selectCmsNodes(@RequestParam(value = "appId") String appId) throws Exception {

        Map<String, List<CmsNodeVO>> cmsByAppId = cmsClientService.getCmsByAppId(appId);

        return cmsByAppId;

    }

}
