package com.pingan.starlink.controller;

import com.pingan.starlink.dto.GitBranchCreateDataDTO;
import com.pingan.starlink.dto.GitProjectCreateDataDTO;
import com.pingan.starlink.dto.GitpullreqeustsDTO;
import com.pingan.starlink.dto.JiraGitprojectRelationDTO;
import com.pingan.starlink.exception.NotFoundException;
import com.pingan.starlink.logger.LoggerBuilder;
import com.pingan.starlink.logger.MonitorLogger;
import com.pingan.starlink.model.*;
import com.pingan.starlink.service.GitDBService;
import com.pingan.starlink.service.GitService;
import com.pingan.starlink.util.EireneUtil;
import com.pingan.starlink.util.JacksonUtil;
import com.pingan.starlink.vo.jira.GitBranchVO;
import com.pingan.starlink.vo.jira.GitProjectVO;
import com.pingan.starlink.vo.jira.GitpullrequestsVO;
import com.pingan.starlink.vo.jira.JiraGitprojectRelationVo;
import io.swagger.annotations.ApiOperation;
import org.gitlab4j.api.models.Branch;
import org.gitlab4j.api.models.MergeRequest;
import org.gitlab4j.api.models.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1/eirene/git")
@CrossOrigin
public class GitlabController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MonitorLogger monitorLogger;

    @Autowired
    private HttpServletRequest request;
    @Resource
    private GitService gitService;
    @Resource
    private GitDBService gitDBService;

    @ApiOperation(value = "创建git项目")
    @RequestMapping(value = "/projects", method = RequestMethod.POST, produces = "application/json;charset-UTF-8")
    @ResponseBody
    public Project createGitProject(@RequestBody GitProjectCreateDataDTO gitProjectCreateDataDTO
                                    ) throws Exception {

        logger.info("[POST],[eirene/git/projects],[{}]",JacksonUtil.bean2Json(gitProjectCreateDataDTO));

        LoggerBuilder loggerBuilder = null;

        Project gitProject = null;

        try {
            loggerBuilder = new LoggerBuilder().setInterfaceId("git.projects.craeteGitProject").setArgs(
                    JacksonUtil.bean2Json(gitProjectCreateDataDTO)).setRequest(request);
            gitProject = gitService.createGitProject(gitProjectCreateDataDTO);
        } catch (Exception e) {
            if (e instanceof NotFoundException || e instanceof NullPointerException) {
                loggerBuilder.setBusResult("false").setSysReason(e.getMessage());
            } else {
                loggerBuilder.setSysResult("false").setSysReason(e.getMessage()).setSysResult("false");
            }
            throw e;
        } finally {
             monitorLogger.info(loggerBuilder.buildLog());
        }

        return gitProject;
    }

    @ApiOperation(value = "删除git项目")
    @RequestMapping(value = "/projects/{uuid}", method = RequestMethod.DELETE, produces = "application/json;charset-UTF-8")
    @ResponseBody
    public ModelMap deleteProject(@PathVariable("uuid") String projectUuid) {
        logger.info("[DELETE],[eirene/git/projects/{}],[{}]",projectUuid,projectUuid);

        int i = gitService.deleteProject(projectUuid);
        ModelMap result = new ModelMap();
        if (i <= 0) {
            result.put("msg", "删除数据库失败");
        } else {
            result.put("msg", "删除数据库成功!");
        }
        return result;
    }

    @ApiOperation(value = "修改Git项目",notes = "只能修改gitprojectName、visibility项目可见度、description项目描述")
    @RequestMapping(value = "/projects", method = RequestMethod.PUT, produces = "application/json;charset-UTF-8")
    @ResponseBody
    public Project projectUpdate(@RequestParam(value = "projectId") Integer projectId,
                                 @RequestBody GitProjectCreateDataDTO gitProjectCreateDataDTO) throws Exception {
        logger.info("[PUT],[eirene/git/projects?projectId={}],[{}]",projectId,projectId);
        Project project = gitService.updateProject(projectId,gitProjectCreateDataDTO);

        return project;
    }

    @ApiOperation(value = "修改Git项目",notes = "修改gitProject，仅修改DB")
    @RequestMapping(value = "/projects/{uuid}", method = RequestMethod.PUT, produces = "application/json;charset-UTF-8")
    @ResponseBody
    public GitProjectVO projectUpdate(@PathVariable(value = "uuid")String uuid,
                                  @RequestBody GitProjectCreateDataDTO gitProjectCreateDataDTO) throws Exception {
        logger.info("[PUT],[eirene/git/projects/{}],[{}]",uuid,uuid);
        return  gitService.updateProjectByUuid(uuid,gitProjectCreateDataDTO);

    }

    @ApiOperation(value = "查询git项目", notes = "查询单个项目，如果detail == false查询DB，否则查询DB+Client")
    @RequestMapping(value = "/projects/{uuid}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public GitProjectVO getProject(@PathVariable("uuid") String uuid,
                                   @RequestParam("detail") Boolean detail) throws Exception {
        logger.info("[GET],[eirene/git/projects/{}/?detail={}],[{}]",uuid,detail,uuid);

        LoggerBuilder loggerBuilder = new LoggerBuilder().setInterfaceId("git.projects.getProject").setArgs(String.format("{\"uuid\":\"%s\", \"detail\":\"%s\"}",uuid,detail)).setRequest(request);
        GitProjectVO gitProjectVO = null;

        try {
            gitProjectVO = gitService.getGitProject(uuid,detail);
            if (gitProjectVO == null) {
                throw new NotFoundException("not found in db");
            }
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

        return gitProjectVO;
    }

    @ApiOperation(value = "查询git项目", notes = "查询多个项目，如果detail == false查询DB，否则查询DB+Client")
    @RequestMapping(value = "/projects", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<GitProjectVO> getProjects(@RequestParam(value = "git_project_list", required = false) List<Integer> gitProjectIdList,
                                          @RequestParam(value = "git_group_list", required = false) List<Integer> gitGroupIdList,
                                          @RequestParam(value = "detail", required = false, defaultValue = "false") Boolean detail) throws Exception {
        logger.info("[GET],[eirene/git/projects?git_project_list={}&git_group_list={}&detail={}]",
                JacksonUtil.bean2Json(gitProjectIdList),JacksonUtil.bean2Json(gitGroupIdList),detail);

        LoggerBuilder loggerBuilder = null;

        Project gitProject = null;

        List<GitProjectVO> gitProjectVOS = null;

        try {
            loggerBuilder = new LoggerBuilder().setInterfaceId("git.projects.getProjects").setArgs(
                    String.format("{\"git_project_list\":\"%s\", \"git_group_list\":\"%s\", \"detail\":\"%s\"}", JacksonUtil.bean2Json(gitProjectIdList),
                            JacksonUtil.bean2Json(gitGroupIdList),detail)).setRequest(request);

            gitProjectVOS = gitService.getGitProjects(gitProjectIdList,gitGroupIdList,detail);
        } catch (Exception e) {
            if (e instanceof NotFoundException){
                loggerBuilder.setBusResult("false").setSysReason(e.getMessage());
            } else {
                loggerBuilder.setSysResult("false").setSysReason(e.getMessage()).setSysResult("false");
            }
            throw e;
        } finally {
            monitorLogger.info(loggerBuilder.buildLog());
        }

        return gitProjectVOS;
    }

    @ApiOperation(value = "创建git分支")
    @RequestMapping(value = "/branchs", method = RequestMethod.POST, produces = "application/json;charset-UTF-8")
    @ResponseBody
    public Branch createGitProjectBranch(@RequestBody GitBranchCreateDataDTO gitBranchCreateDataDTO) throws Exception {
        logger.info("[POST],[eirene/git/branchs],[{}]",JacksonUtil.bean2Json(gitBranchCreateDataDTO));

        LoggerBuilder loggerBuilder = null;
        Branch gitBranch = null;

        try {
            loggerBuilder = new LoggerBuilder().setInterfaceId("git.branchs.createGitProjectBranch").setArgs(
                    JacksonUtil.bean2Json(gitBranchCreateDataDTO)).setRequest(request);
            gitBranch = gitService.createGitBranch(gitBranchCreateDataDTO);
        } catch (Exception e) {
            if (e instanceof NullPointerException) {
                loggerBuilder.setBusResult("false").setBusReason(e.getMessage());
            } else {
                loggerBuilder.setSysResult("false").setSysReason(e.getMessage()).setSysResult("false");
            }
            throw e;
        } finally {
            monitorLogger.info(loggerBuilder.buildLog());
        }

        logger.info("create git branch：{}", JacksonUtil.bean2Json(gitBranch));

        return gitBranch;
    }

    @ApiOperation(value = "查询git分支", notes = "当gitProjectId参数存在时，根据gitProjectId查询，否则查询所有")
    @RequestMapping(value = "/branchs", method = RequestMethod.GET)
    @ResponseBody
    public List<GitBranchVO> selectAllGitBranchs(@RequestParam(value = "gitProjectId", required = false) Integer gitProjectId,
                                                  @RequestParam(value = "detail", required = false, defaultValue = "false") Boolean detail) throws Exception {
        logger.info("[GET],[eirene/git/branchs?gitProjectId={}&detail={}],[{}]",
                gitProjectId,detail,gitProjectId);

        LoggerBuilder loggerBuilder = null;

        List<GitBranchVO> gitBranchVOS = null;

        List<GitProjectVO> gitProjectVOS = null;

        try {
            loggerBuilder = new LoggerBuilder().setInterfaceId("git.branchs.selectAllGitBranchs").setArgs(
                    String.format("{\"gitProjectId\":\"%d\",\"detail\":\"%s\"}", gitProjectId,detail)).setRequest(request);
             gitBranchVOS = gitService.getBranches(gitProjectId,detail);
        } catch (Exception e) {
            if (e instanceof NotFoundException){
                loggerBuilder.setBusResult("false").setSysReason(e.getMessage());
            } else {
                loggerBuilder.setSysResult("false").setSysReason(e.getMessage()).setSysResult("false");
            }
            throw e;
        } finally {
            monitorLogger.info(loggerBuilder.buildLog());
        }

        logger.info("get git branch：{}", JacksonUtil.bean2Json(gitBranchVOS));
        return gitBranchVOS;
    }

    @ApiOperation(value = "根据uuid查询分支")
    @RequestMapping(value = "/branchs/{uuid}",method = RequestMethod.GET)
    @ResponseBody
    public GitBranchVO selectGitBranch(@PathVariable("uuid") String uuid,
                                       @RequestParam(value = "detail", required = false, defaultValue = "false") Boolean detail) throws Exception {
        logger.info("[GET],[eirene/git/branchs/{}?detail={}],[{}]",
                uuid,detail,uuid);
        return this.gitService.selectBranchsByUuid(uuid,detail);
    }

    @ApiOperation(value = "修改git分支")
    @RequestMapping(value = "/branchs/{uuid}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public GitBranchVO updateBranchsByUuid(@PathVariable("uuid") String uuid,
                                        @RequestBody GitBranchCreateDataDTO gitBranchCreateDataDTO ) throws Exception {

        logger.info("[PUT],[eirene/git/branchs/{}],[{}]",
                uuid,JacksonUtil.bean2Json(gitBranchCreateDataDTO));
        return gitService.updateBranchsByUuid(uuid, gitBranchCreateDataDTO);

    }

    @ApiOperation(value = "删除git分支")
    @RequestMapping(value = "/branchs/{uuid}", method = RequestMethod.DELETE, produces = "application/json;charset-UTF-8")
    @ResponseBody
    public ModelMap deleteBranch(@PathVariable("uuid") String branchUuid) {
        logger.info("[DELETE],[eirene/git/branchs/{}],[{}]", branchUuid,branchUuid);
        int i = gitService.deleteBranch(branchUuid);
        ModelMap result = new ModelMap();
        if (i <= 0) {
            result.put("msg", "删除失败");
        } else {
            result.put("msg", "删除成功!");
        }
        return result;
    }

    //relate gitproject and jiraproject
    @ApiOperation(value = "新增jira、gitproject关联关系表")
    @RequestMapping(value = "/jira_gitproject_relations", method = RequestMethod.POST)
    public JiraGitprojectRelationVo addJiraGitprojectRelation(@RequestBody JiraGitprojectRelationDTO jiraGitprojectRelationDTO) throws Exception {

        logger.info("[POST],[eirene/git/jira_gitproject_relations],[{}]",JacksonUtil.bean2Json(jiraGitprojectRelationDTO));

        JiraGitprojectRelation jiraGitprojectRelation = gitService.addJiraGitprojectRelation(jiraGitprojectRelationDTO);

        JiraGitprojectRelationVo jiraGitprojectRelationVo = new JiraGitprojectRelationVo(jiraGitprojectRelation);

        logger.info("jira-gitproject-relate:{}",JacksonUtil.bean2Json(jiraGitprojectRelationVo));

        return jiraGitprojectRelationVo;
    }

    @ApiOperation(value = "创建pr")
    @RequestMapping(value = "/pullrequests", method = RequestMethod.POST, produces = "application/json;charset-UTF-8")
    @ResponseBody
    public MergeRequest createPullrequests(@RequestBody GitpullreqeustsDTO gitpullrequestsDTO
                                           ) throws Exception {
        logger.info("[POST],[eirene/git/pullrequests],[{}]",JacksonUtil.bean2Json(gitpullrequestsDTO));

        LoggerBuilder loggerBuilder = null;

        MergeRequest gitPullrequests = null;

        try {
            loggerBuilder = new LoggerBuilder().setInterfaceId("git.pullrequests.createPullrequests").setArgs(
                    JacksonUtil.bean2Json(gitpullrequestsDTO)).setRequest(request);
            gitPullrequests  = gitService.createPullrequests(gitpullrequestsDTO);
        } catch (Exception e) {
            if (e instanceof NullPointerException) {
                loggerBuilder.setBusResult("false").setBusReason(e.getMessage());
            } else {
                loggerBuilder.setSysResult("false").setSysReason(e.getMessage()).setSysResult("false");
            }
            throw e;
        } finally {
            monitorLogger.info(loggerBuilder.buildLog());
        }

        logger.info("create gitPullrequests：{}", JacksonUtil.bean2Json(gitPullrequests));
        return gitPullrequests;
    }

    @ApiOperation(value = "查询pr", notes = "展示PR:status=opened、codereview:statue=merged")
    @RequestMapping(value = "/pullrequests", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<GitpullrequestsVO> getGitpullrequests(@RequestParam("gitProjectId") Integer gitProjectId,
                                                      @RequestParam("status") String status) throws Exception {
        logger.info("[GET],[eirene/git/pullrequests?gitProjectId={}&status={}],[{}]",gitProjectId,status,gitProjectId + "," +status);

        LoggerBuilder loggerBuilder = null;

        List<GitpullrequestsVO> gitpullrequestsVOS = null;

        try {
            loggerBuilder = new LoggerBuilder().setInterfaceId("git.pullrequests.getGitpulrequests").setArgs(
                    String.format("{\"gitProjectId\":\"%d\",\"status\":\"%s\"}", gitProjectId,status)).setRequest(request);
             gitpullrequestsVOS = gitService.getGitpullrequests(gitProjectId,status);
        } catch (Exception e) {
            if (e instanceof NotFoundException){
                loggerBuilder.setBusResult("false").setSysReason(e.getMessage());
            } else {
                loggerBuilder.setSysResult("false").setSysReason(e.getMessage()).setSysResult("false");
            }
            throw e;
        } finally {
            monitorLogger.info(loggerBuilder.buildLog());
        }
        logger.info("get gitPullrequests：{}", JacksonUtil.bean2Json(gitpullrequestsVOS));
        return gitpullrequestsVOS;
    }

    @ApiOperation(value = "删除pr")
    @RequestMapping(value = "/pullrequests/{uuid}", method = RequestMethod.DELETE, produces = "application/json;charset-UTF-8")
    @ResponseBody
    public ModelMap deletePr(@PathVariable("uuid") String uuid) throws Exception {
        logger.info("[DELETE],[eirene/git/pullrequests/{}],[{}]",uuid,uuid);

        ModelMap result = new ModelMap();

        int deleteGitpullrequests = gitService.deleteGitpullrequests(uuid);

        if (deleteGitpullrequests < 1) {
            throw new NotFoundException("not found in gitpullrequests db");
        }

        result.put("msg", "删除成功！");
        result.put("result", deleteGitpullrequests);

        return result;
    }

    @ApiOperation(value = "修改pr", notes = "")
    @RequestMapping(value = "/pullrequests/{uuid}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public GitPullRequests updateDepartment(@PathVariable("uuid") String uuid,@RequestBody GitpullreqeustsDTO gitpullrequestsDTO) throws Exception {
        logger.info("[PUT],[eirene/git/pullrequests/{}],[{}]",uuid,uuid);

        GitPullRequests gitPullRequests1 = gitService.updateGitpullrequests(uuid,gitpullrequestsDTO);

        return gitPullRequests1;

    }


    @ApiOperation(value = "新增gitlab组", notes = "")
    @RequestMapping(value = "/groups", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ModelMap insert(@RequestBody GitGroup gitGroup) throws Exception {
        logger.info("[POST],[eirene/git/groups],[{}]",JacksonUtil.bean2Json(gitGroup));

        ModelMap result = new ModelMap();
        gitGroup.setUuid(EireneUtil.randomUUID());
        int res = gitDBService.insertGitgroup(gitGroup);
        result.put("gitGroup",gitGroup);
        result.put("msg",res);
        return result;
    }

    @ApiOperation(value = "删除gitlab组", notes = "")
    @RequestMapping(value = "/groups/{uuid}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ModelMap deleteGitgroup(@PathVariable("uuid") String uuid) throws Exception {
        logger.info("[DELETE],[eirene/git/groups/{}],[{}]",uuid,uuid);

        ModelMap result = new ModelMap();
        int deleteGitgroup = gitDBService.deleteGitgroup(uuid);

        if (deleteGitgroup < 1) {
            throw new NotFoundException("not found in git_group db");
        }

        result.put("msg", "删除成功!");
        result.put("result", deleteGitgroup);

        return result;

    }

    @ApiOperation(value = "修改gitlab组", notes = "")
    @RequestMapping(value = "/groups/{uuid}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public GitGroup updateGitgroup(@PathVariable("uuid") String uuid,@RequestBody GitGroup gitGroup) throws Exception {
        logger.info("[PUT],[eirene/git/groups/{}],[{}]",uuid,JacksonUtil.bean2Json(gitGroup));

        GitGroup gitGroup1 = gitDBService.updateGitGroup(uuid,gitGroup);

        return gitGroup1;

    }

    @ApiOperation(value = "查询gitlab组", notes = "")
    @RequestMapping(value = "/groups/{uuid}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public GitGroup selectGitgroup(@RequestParam( value = "uuid",required = false) String uuid) throws Exception {
        logger.info("[GET],[eirene/git/groups/{}],[{}]",uuid,uuid);

        GitGroup gitGroups = gitDBService.selectByGitgroupUuid(uuid);

        return gitGroups;
    }

    @ApiOperation(value = "查询gitlab组", notes = "")
    @RequestMapping(value = "/groups", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<GitGroup> selectGitgroup() throws Exception {
        logger.info("[GET],[eirene/git/groups]");

        LoggerBuilder loggerBuilder = new LoggerBuilder().setInterfaceId("git.groups.selectGitgroup").setArgs("select all gitlab groups in db").setRequest(request);

        List<GitGroup> gitGroups = null;
        try {
            gitGroups = gitDBService.selectAllGitgroup();
            if (gitGroups == null) {
                throw new NotFoundException("not found in db");
            }
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

        return gitGroups;
    }

    @ApiOperation(value = "新增git组与部门关系表", notes = "")
    @RequestMapping(value = "/group_department_relates", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ModelMap save(@RequestBody GitgroupDepartmentRelation gitgroupDepartmentRelation) throws Exception {
        logger.info("[POST],[eirene/git/group_department_relates],[{}]",JacksonUtil.bean2Json(gitgroupDepartmentRelation));

        ModelMap result = new ModelMap();
        int res = gitDBService.insertGitgroupDev(gitgroupDepartmentRelation);
        result.put("gitgroupDepartmentRelation",gitgroupDepartmentRelation);
        result.put("msg",res);
        return result;
    }

    @ApiOperation(value = "删除git组与部门关系表", notes = "")
    @RequestMapping(value = "/group_department_relates/{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ModelMap delete(@PathVariable String uuid) {
        logger.info("[DELETE],[eirene/git/group_department_relates/{}],[{}]",uuid,uuid);

        ModelMap result = new ModelMap();
        int res = gitDBService.deleteGitgroupDev(uuid);
        result.put("msg", "删除成功!");
        result.put("result", res);
        return result;
    }

    @ApiOperation(value = "修改git组与部门关系表", notes = "uuid不能修改")
    @RequestMapping(value = "/group_department_relates/{uuid}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public GitgroupDepartmentRelation updateGitgroupDepartmentRelation(@PathVariable("uuid") String uuid, @RequestBody GitgroupDepartmentRelation gitgroupDepartmentRelation) throws Exception {
        logger.info("[PUT],[eirene/git/group_department_relates/{}],[{}]",uuid,JacksonUtil.bean2Json(gitgroupDepartmentRelation));

        GitgroupDepartmentRelation gitgroupDepartmentRelation1 = gitDBService.updateGitgroupDev(uuid,gitgroupDepartmentRelation);
        return gitgroupDepartmentRelation1;
    }

    @ApiOperation(value = "查询git组与部门关系表", notes = "departmentName为null查询全部")
    @RequestMapping(value = "/group_department_relates", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<GitgroupDepartmentRelation> selectAll(@RequestParam( value = "department",required = false) String department) throws Exception {

        logger.info("[GET],[eirene/git/group_department_relates?department={}],[{}]",department,department);

        LoggerBuilder loggerBuilder = new LoggerBuilder().setInterfaceId("git.groups.selectAll").setArgs("select all gitlab groups relation in db").setRequest(request);
        List<GitgroupDepartmentRelation> gitgroupDepartmentRelations = null;

        try {
            gitgroupDepartmentRelations = gitDBService.selectAllGitgroupAndDev(department);
            if (gitgroupDepartmentRelations == null) {
                throw new NotFoundException("not found in db");
            }
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
        return gitgroupDepartmentRelations;
    }


    @ApiOperation(value = "查询所有git项目和jira的关系表",notes = "当projectKey存在时，根据key查询")
    @RequestMapping(value = "/jira_gitproject_relations",method = RequestMethod.GET)
    @ResponseBody
    public List<JiraGitprojectRelationVo> selectJiraGitprojectRelation(@RequestParam(value = "projectKey",required = false)String projectKey) throws IOException {
        logger.info("[GET],[eirene/git/jira_gitproject_relations?projectKey={}],[{}]",projectKey,projectKey);

        return this.gitService.getJiraGitprojectRelation(projectKey);
    }

    @ApiOperation(value = "查询git与jira关系表")
    @RequestMapping(value = "/jira_gitproject_relations/{uuid}",method = RequestMethod.GET)
    @ResponseBody
    public JiraGitprojectRelationVo selectJiraGitprojectRelationByUuid(@PathVariable("uuid") String uuid) throws IOException {

        logger.info("[GET],[eirene/git/jira_gitproject_relations/{}],[{}]",uuid,uuid);

        return this.gitService.getJiraGitprojectRelationByUuid(uuid);
    }

    @ApiOperation(value = "修改git与jira关系表")
    @RequestMapping(value = "/jira_gitproject_relations/{uuid}",method = RequestMethod.PUT,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JiraGitprojectRelationVo updateJiraGitprojectRelationByUuid(@PathVariable("uuid") String uuid,
                                                                        @RequestBody JiraGitprojectRelationDTO jiraGitprojectRelationDTO) throws Exception {
        logger.info("[PUT],[eirene/git/jira_gitproject_relations/{}],[{}]",uuid,JacksonUtil.bean2Json(jiraGitprojectRelationDTO));

        return this.gitService.modifyJiraGitprojectRelationByUuid(uuid,jiraGitprojectRelationDTO);
    }

    @ApiOperation(value = "删除git与jira关系表")
    @RequestMapping(value = "/jira_gitproject_relations/{uuid}",method = RequestMethod.DELETE,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ModelMap deleteJiraGitprojectRelationByUuid(@PathVariable("uuid") String uuid){

        logger.info("[DELETE],[eirene/git/jira_gitproject_relations/{}],[{}]",uuid,uuid);

        int num = this.gitService.deleteJiraGitprojectRelationByUuid(uuid);
        ModelMap result = new ModelMap();
        if (num <= 0) {
            result.put("msg", "删除失败");
        } else {
            result.put("msg", "删除成功!");
        }
        return result;
    }

    @ApiOperation(value = "根据jira的项目key查询有关联的git仓库")
    @RequestMapping(value = "/projects_by_key", method = RequestMethod.GET, produces = "application/json;charset-UTF-8")
    @ResponseBody
    public List<GitProject> codeSpacesByKey(@RequestParam String projectKey) throws IOException {

        logger.info("[GET],[eirene/git/projects_by_key?projectKey={}],[{}]",projectKey,projectKey);


        List<GitProject> gitProjects = gitService.gitProjects(projectKey);

        return gitProjects;
    }

    @ApiOperation(value = "同步远端到DB", notes = "根据git组id同步数据")
    @RequestMapping(value = "/groups/sycn", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap gitLabSynchronizeDB(@RequestParam("groupId") Integer groupId) throws Exception {

        logger.info("[POST],[eirene/git/groups/sycn?groupId={}],[{}]",groupId,groupId);

        ModelMap result = new ModelMap();

        List<Integer> projectIdList = this.gitService.gitLabSyncDBByGroupId(groupId);
        result.put("msg", "同步成功");
        result.put("groupId",groupId);
        result.put("projectIdList",projectIdList);

        return result;
    }
}
