package com.pingan.starlink.controller;

import com.pingan.starlink.dto.ProjectCreateDataDTO;
import com.pingan.starlink.dto.VersionCreateDataDTO;
import com.pingan.starlink.exception.NotFoundException;
import com.pingan.starlink.exception.BadRequestException;
import com.pingan.starlink.model.IssueField;
import com.pingan.starlink.model.Project;
import com.pingan.starlink.model.VersionManagement;
import com.pingan.starlink.model.jira.*;
import com.pingan.starlink.model.polaris.ProjectTemplates;
import com.pingan.starlink.service.GitlabAPIClient;
import com.pingan.starlink.service.JiraClientService;
import com.pingan.starlink.service.JiraIssueFieldDBService;
import com.pingan.starlink.util.EireneUtil;
import com.pingan.starlink.util.JiraIssueFieldMap;
import com.pingan.starlink.vo.jira.*;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/eirene/jira")
@CrossOrigin
public class JiraController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${jira.url}")
    private String jiraUrl;

    @Resource
    private JiraClientService jiraClientService;

    @Autowired
    private JiraIssueFieldDBService jiraIssueFieldDBService;

    @Autowired
    private GitlabAPIClient gitlabAPIClient;

    @ApiOperation(value = "查询项目", notes = "根据项目的Key查询单个项目，如果detail == false查询DB，否则查询DB+HTTP")
    @RequestMapping(value = "/projects/{projectKey}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ProjectVO getProject(@PathVariable(value = "projectKey") String projectKey,
                                @RequestParam(value = "detail", required = false, defaultValue = "false") Boolean detail) throws Exception {

        ProjectVO projectVO = jiraClientService.getProject(projectKey, detail);
        if (projectVO == null) {
            throw new NotFoundException("not found db");
        }
        return projectVO;
    }

//    @ApiOperation(value = "查询项目", notes = "查询所有项目")
//    @RequestMapping(value = "/projects_all", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
//    @ResponseBody
//    public List<ProjectAbstract> getAllProjects() throws Exception {
//
//        List<ProjectAbstract> projects = jiraClientService.getAllProjects();
//
//        return projects;
//    }

    @ApiOperation(value = "查询项目模板", notes = "查询所有的项目模板,系统默认的6个模板和自定义模板")
    @RequestMapping(value = "project_templates", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<ProjectTemplates> getProjectTemplates() throws Exception {

        List<ProjectTemplates> projectTemplates = jiraClientService.getProjectTemplates();

        return projectTemplates;
    }


    @ApiOperation(value = "查询项目", notes = "根据项目的key和department查询指定项目，如果参数为空,detail == false查询DB，detail == true否则查询DB+HTTP，如果参数有值，查询指定的项目")
    @RequestMapping(value = "/projects", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<ProjectVO> getProjects(@RequestParam(value = "project_list", required = false) List<String> projectKeyList,
                                       @RequestParam(value = "department_list", required = false) List<String> departmentList,
                                       @RequestParam(value = "detail", required = false, defaultValue = "false") Boolean detail) throws Exception {

        List<ProjectVO> projectVOS = jiraClientService.selectProjects(projectKeyList, departmentList, detail);

        return projectVOS;
    }

    @ApiOperation(value = "创建项目", notes = "projectKey(不能重复，大写、至少两个英文，不能超过10个英文)、projectName(不能重复、至少2个字符，不能大于80个字符)、projectTemplateKey、lead对应其中的username、department必须存在")
    @RequestMapping(value = "/projects", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ProjectVO createProjects(@RequestBody ProjectCreateDataDTO projectCreateDateDTO,
                                    @RequestParam(value = "status",defaultValue = "false") boolean status) throws Exception {
        ProjectVO project = null;
        if (status == false) {
            project = jiraClientService.createProject(projectCreateDateDTO);
        }else if (status){
            project = jiraClientService.reCreateProject(projectCreateDateDTO);
        }


        return project;
    }

    @ApiOperation(value = "创建版本" ,notes = "projectKey(必填)、versionName(必填)输入版本的名称, 它必须少于 255 个字符。releaseDate(选填) 必须是日期格式（例yyyy-MM-dd）")
    @RequestMapping(value = "/versions", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public VersionVO createVersion(@RequestBody VersionCreateDataDTO versionCreateDataDTO) throws Exception {

        VersionVO project = jiraClientService.createVersion(versionCreateDataDTO);

        return project;
    }

    @ApiOperation(value = "查找版本", notes = "根据版本的projectKey查找版本,如果projectKey为空的话查询全部版本")
    @RequestMapping(value = "/versions", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<VersionManagement> getVersionsByKey(@RequestParam(value = "projectKey",required = false) String projectKey) throws Exception {

        List<VersionManagement> versionManagements = jiraClientService.selectVersionByKey(projectKey);

        return versionManagements;
    }

    @ApiOperation(value = "查询版本", notes = "根据版本的uuid查询")
    @RequestMapping(value = "/versions/{versionUuid}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public VersionManagement selectVersionByUuid(@PathVariable("versionUuid") String versionUuid) throws IOException {

        VersionManagement versionManagement = jiraClientService.selectByVersionUuid(versionUuid);

        return versionManagement;
    }
    @ApiOperation(value = "查询版本详情", notes = "根据条件查询问题")
    @RequestMapping(value = "/versions/details", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<IssueQueryResponseData> queryIssuesDetail(@RequestParam(value = "projectKey") String projectKey,
                                                          @RequestParam(value = "fixVersion") String fixVersion) throws Exception {
        IssueJqlCondition issueJqlCondition = new IssueJqlCondition(IssueJqlRelation.AND.getRelation());
        if (projectKey != null && !projectKey.isEmpty()) {
            issueJqlCondition.addItemProjectEqual(projectKey);
        }
        if (fixVersion != null && !fixVersion.isEmpty()) {
            issueJqlCondition.addItemDueFixVersion(fixVersion);
        }
        JiraIssueFieldMap jiraIssueFieldMap = new JiraIssueFieldMap(jiraIssueFieldDBService.getAll());

        issueJqlCondition.setFields(jiraIssueFieldMap.getFieldIds());

        List<IssueQueryResponseData> issueQueryResponseDataList = jiraClientService.queryIssuesDetail(issueJqlCondition);
        for (IssueQueryResponseData responseData : issueQueryResponseDataList) {
            responseData.transfFields(jiraIssueFieldMap);
            List<IssueQueryResponseData> children = responseData.getChildren();
            for (IssueQueryResponseData childrenFields:children){
                childrenFields.transfFields(jiraIssueFieldMap);
            }
        }

        return issueQueryResponseDataList;
    }


    @ApiOperation(value = "版本操作", notes = "操作")
    @RequestMapping(value = "/versions/operation", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public VersionVO versionOperation(@RequestParam("uuid") String uuid,
                                      @RequestParam("operation") String operation,
                                      @RequestParam(value = "releaseIssues",required = false) String releaseIssues) throws Exception {

        VersionVO versionVO = jiraClientService.versionOperation(uuid, operation, releaseIssues);

        return versionVO;
    }

    @ApiOperation(value = "修改版本", notes = "uuid、versionName、versionNum、releastDate、createTime不能修改")
    @RequestMapping(value = "/versions/{versionUuid}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public VersionManagement updateVersion(@PathVariable("versionUuid") String versionUuid, @RequestBody VersionCreateDataDTO versionCreateDataDTO) throws IOException {

        VersionManagement versionManagement = jiraClientService.updateVersion(versionUuid, versionCreateDataDTO);

        return versionManagement;
    }

    @ApiOperation(value = "删除版本", notes = "根据项目的uuid删除项目")
    @RequestMapping(value = "/versions/{versionUuid}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ModelMap deleteVersions(@PathVariable("versionUuid") String versionUuid) throws Exception {
        int i = jiraClientService.deleteVersion(versionUuid);
        ModelMap result = new ModelMap();
        result.put("code", i);
        result.put("msg", "删除成功!");
        return result;
    }

    @ApiOperation(value = "修改项目", notes = "uuid、projectKey、projectName、projectTemplateKey、PROJECT_LEAD、codeSpace[]不能修改")
    @RequestMapping(value = "/projects/{projectKey}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Project updateProject(@PathVariable("projectKey") String projectKey, @RequestBody ProjectCreateDataDTO projectCreateDataDTO) throws IOException {

        Project project = jiraClientService.updateProject(projectKey, projectCreateDataDTO.ProjectDB());

        return project;
    }

    @ApiOperation(value = "查询部门总数",notes = "查询部门的总数")
    @RequestMapping(value = "/department_count", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<DepartmentCountVO> getDepartmentCounts() throws IOException {

        List<DepartmentCountVO> departmentCountVOS = jiraClientService.departmentCountVOList();

        return departmentCountVOS;
    }

    @ApiOperation(value = "删除项目", notes = "根据项目的Key删除项目")
    @RequestMapping(value = "/projects/{projectKey}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ModelMap deleteProject(@PathVariable("projectKey") String projectKey) throws Exception {
        jiraClientService.deleteProject(projectKey);
        ModelMap result = new ModelMap();
        result.put("msg", "删除成功!");
        return result;
    }

    @ApiOperation(value = "根据日期查询项目", notes = "根据年份和月份查询每个月的项目版本")
    @RequestMapping(value = "/projects_version", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<ProjectVersionVO> getProjectVersions(@RequestParam(value = "project_list", required = false) List<String> projectKeyList,
                                                     @RequestParam("year") int year, @RequestParam("month") int month) throws Exception {
//        if (projectKeyList == null || projectKeyList.isEmpty()) {
//            projectKeyList = jiraClientService.getProjectKeys();
//        }
        List<ProjectVersionVO> projectVersions = new ArrayList<>();
        for (String projectkey : projectKeyList) {
            try {
                List<ProjectVersionVO> versions = getProjectVersions(projectkey, year, month);
                projectVersions.addAll(versions);
            } catch (NotFoundException e) {
                logger.warn(String.format("The Project %s is not found or the user does not have permission to view it.", projectkey));
            }
        }
        return projectVersions;
    }

    private List<ProjectVersionVO> getProjectVersions(String projectKey, int year, int month) throws Exception {

        List<ProjectVersionVO> projectVersionVOS = new ArrayList<>();

        ProjectDetail projectDetail = jiraClientService.getProject(projectKey);

        List<ProjectVersion> versionsFilter = jiraClientService.getVersionsFilter(projectDetail, year, month);
        for (ProjectVersion projectVersion : versionsFilter) {
            ProjectVersionVO projectVersionVO = new ProjectVersionVO(projectDetail.getName(), projectDetail.getKey(), projectVersion.getName(), projectVersion.getReleaseDate(), projectVersion.isReleased());
            projectVersionVOS.add(projectVersionVO);
        }
        return projectVersionVOS;
    }

    @ApiOperation(value = "查询问题", notes = "根据问题的Key查询问题")
    @RequestMapping(value = "/issues/{issueKey}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Issue getIssue(@PathVariable("issueKey") String issueKey) throws Exception {

        Issue issue = jiraClientService.getIssue(issueKey);

        return issue;
    }

    @ApiOperation(value = "查询问题", notes = "根据条件查询问题")
    @RequestMapping(value = "/issues", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<IssueQueryResponseData> queryIssues(@RequestParam(value = "issueTypes", required = false) List<String> issueTypes,
                                                    @RequestParam(value = "issueKeyList", required = false) List<String> issueKeyList,
                                                    @RequestParam(value = "projects", required = false) List<String> projectKeyList,
                                                    @RequestParam(value = "fixVersions", required = false) List<String> fixVersions,
                                                    @RequestParam(required = false) String creator,
                                                    @RequestParam(required = false) String assignee,
                                                    @RequestParam(value = "status", required = false) List<String> status,
                                                    @RequestParam(required = false) String dueDateStart,
                                                    @RequestParam(required = false) String dueDateEnd) throws Exception {
        IssueJqlCondition issueJqlCondition = new IssueJqlCondition(IssueJqlRelation.AND.getRelation());
        if (projectKeyList != null && !projectKeyList.isEmpty()) {
            IssueJqlCondition subProjectKeyJqlCondition = new IssueJqlCondition(IssueJqlRelation.OR.getRelation());
            for (String projectKey : projectKeyList) {
                subProjectKeyJqlCondition.addItemProjectEqual(projectKey);
            }
            issueJqlCondition.addIssueJqlCondition(subProjectKeyJqlCondition);
        }
        if (issueTypes != null && !issueTypes.isEmpty()) {
            IssueJqlCondition subIssueTypeJqlCondition = new IssueJqlCondition(IssueJqlRelation.OR.getRelation());
            for (String issueType : issueTypes) {
                subIssueTypeJqlCondition.addItemIssueTypeEqual(issueType);
            }
            issueJqlCondition.addIssueJqlCondition(subIssueTypeJqlCondition);
        }
        if (issueKeyList != null && !issueKeyList.isEmpty()) {
            IssueJqlCondition subIssueTypeJqlCondition = new IssueJqlCondition(IssueJqlRelation.OR.getRelation());
            for (String issueKey : issueKeyList) {
                subIssueTypeJqlCondition.addItemIssueKeyEqual(issueKey);
            }
            issueJqlCondition.addIssueJqlCondition(subIssueTypeJqlCondition);
        }
        if (fixVersions != null && !fixVersions.isEmpty()) {
            IssueJqlCondition subIssueTypeJqlCondition = new IssueJqlCondition(IssueJqlRelation.OR.getRelation());
            for (String fixVersion : fixVersions) {
                subIssueTypeJqlCondition.addItemDueFixVersion(fixVersion);
            }
            issueJqlCondition.addIssueJqlCondition(subIssueTypeJqlCondition);
        }
        if (creator != null && !"".equals(creator)) {
            issueJqlCondition.addItemCreatorEqual(creator);
        }
        if (assignee != null && !"".equals(assignee)) {
            issueJqlCondition.addItemAssigneeEqual(assignee);
        }
        if (status != null && !status.isEmpty()) {
            IssueJqlCondition subStatusJqlCondition = new IssueJqlCondition(IssueJqlRelation.OR.getRelation());
            for (String statue : status) {
                subStatusJqlCondition.addItemStatusEqual(statue);
            }
            issueJqlCondition.addIssueJqlCondition(subStatusJqlCondition);
        }
        if (dueDateStart != null && !"".equals(dueDateStart)) {
            issueJqlCondition.addItemDueDateStart(dueDateStart);
        }
        if (dueDateEnd != null && !"".equals(dueDateEnd)) {
            issueJqlCondition.addItemDueDateEnd(dueDateEnd);
        }

        JiraIssueFieldMap jiraIssueFieldMap = new JiraIssueFieldMap(jiraIssueFieldDBService.getAll());

        issueJqlCondition.setFields(jiraIssueFieldMap.getFieldIds());

        List<IssueQueryResponseData> issueQueryResponseDataList = jiraClientService.queryIssues(issueJqlCondition);
        for (IssueQueryResponseData responseData : issueQueryResponseDataList) {
            responseData.transfFields(jiraIssueFieldMap);
        }

        return issueQueryResponseDataList;
    }

    @ApiOperation(value = "查询用户", notes = "根据用户名称查询")
    @RequestMapping(value = "/users/{userName}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public UserDetail getUser(@PathVariable("userName") String userName) throws Exception {

        UserDetail userDetail = jiraClientService.getUser(userName);

        return userDetail;
    }

    @ApiOperation(value = "创建用户")
    @RequestMapping(value = "/users", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public UserDetail createUser(@RequestBody UserCreateData UserCreateData) throws Exception {

        UserDetail res = jiraClientService.createUser(UserCreateData);

        return res;
    }

    @ApiOperation(value = "删除用户", notes = "根据用户名删除用户")
    @RequestMapping(value = "/users/{userName}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ModelMap deleteUser(@PathVariable("userName") String userName) throws Exception {

        jiraClientService.deleteUser(userName);
        ModelMap result = new ModelMap();
        result.put("msg", "删除成功!");

        return result;
    }

    @ApiOperation(value = "获取所有字段")
    @RequestMapping(value = "/issue_fields", method = RequestMethod.GET)
    @ResponseBody
    public List<IssueField> getAllIssueFields() {
        return jiraIssueFieldDBService.getAll();
    }

    @ApiOperation(value = "通过id获取字段")
    @RequestMapping(value = "/issue_fields/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public IssueField getIssueFieldById(@PathVariable String uuid) throws Exception {
        IssueField issueField = jiraIssueFieldDBService.getById(uuid);
        if (issueField == null) {
            throw new NotFoundException("not found in db");
        }
        return issueField;
    }

    @ApiOperation(value = "通过id删除字段")
    @RequestMapping(value = "/issue_fields/{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public ModelMap deleteIssueFieldById(@PathVariable String uuid) throws Exception {
        ModelMap result = new ModelMap();
        int res = jiraIssueFieldDBService.deleteById(uuid);
        if (res < 1) {
            throw new NotFoundException("not found in db ");
        }
        result.put("msg", "删除成功!");
        result.put("result", res);
        return result;
    }

    @ApiOperation(value = "新增或修改字段")
    @RequestMapping(value = "/issue_fields", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public IssueField saveIssueField(@RequestBody IssueField issueField) throws Exception {
        if (issueField == null || issueField.getFieldId() == null || "".equals(issueField.getFieldId())
                || issueField.getFieldKey() == null || "".equals(issueField.getFieldKey())
                || issueField.getFieldName() == null || "".equals(issueField.getFieldName())) {
            throw new BadRequestException("传入参数有误");
        }

        if (issueField.getUuid() == null || "".equals(issueField.getUuid())) {
            issueField.setUuid(EireneUtil.randomUUID());
            issueField = jiraIssueFieldDBService.saveIssueField(issueField);
        } else {
            issueField = jiraIssueFieldDBService.updateIssueField(issueField);
            if (issueField == null) {
                throw new NotFoundException("not found in db");
            }
        }
        return issueField;
    }

}
