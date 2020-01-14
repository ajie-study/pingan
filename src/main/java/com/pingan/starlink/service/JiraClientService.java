package com.pingan.starlink.service;

import com.pingan.starlink.dto.ProjectCreateDataDTO;
import com.pingan.starlink.dto.VersionCreateDataDTO;
import com.pingan.starlink.dto.VersionReleaseDTO;
import com.pingan.starlink.exception.InternalServerErrorException;
import com.pingan.starlink.exception.NotFoundException;
import com.pingan.starlink.httpclient.PolarisHttpResult;
import com.pingan.starlink.model.Project;
import com.pingan.starlink.model.VersionManagement;
import com.pingan.starlink.model.jira.*;
import com.pingan.starlink.model.polaris.ProjectTemplates;
import com.pingan.starlink.thread.JiraCreateProjectThread;
import com.pingan.starlink.util.ConstantUtil;
import com.pingan.starlink.util.EireneUtil;
import com.pingan.starlink.vo.jira.DepartmentCountVO;
import com.pingan.starlink.vo.jira.ProjectVO;
import com.pingan.starlink.vo.jira.VersionVO;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class JiraClientService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${jira.url}")
    private String jiraUrl;

    @Autowired
    @Setter
    @Getter
    private JiraHttpClient jiraHttpClient;

    @Autowired
    private JiraProjectDBService jiraProjectDBService;

    @Autowired
    private JiraVersionDBService jiraVersionDBService;

    @Autowired
    private DepartmentService departmentService;

    /**
     * 查找单个HTTP JIRA项目
     *
     * @param projectKey
     * @return
     * @throws Exception
     */
    public ProjectDetail getProject(String projectKey) throws Exception {
        ProjectDetail project = jiraHttpClient.getProject(projectKey);
        return project;
    }

    /**
     * 查询单个项目，如果detail == false查询DB，否则查询DB+HTTP
     *
     * @param projectKey
     * @param detail
     * @return
     * @throws Exception
     */
    public ProjectVO getProject(String projectKey, Boolean detail) throws Exception {
        //如果detail == false 查询数据库
        ProjectVO projectVO = null;
        try {
            Project project = jiraProjectDBService.selectProject(projectKey);
            projectVO = new ProjectVO(project);
        } catch (Exception e) {
            logger.info(String.format("no data"));
        }
        //如果detail == true 查询数据库+HTTP
        if (detail) {
            projectVO.setProjectDetail(jiraHttpClient.getProject(projectKey));

        }
        return projectVO;
    }

    /**
     * 根据问题的KEY查询问题类型
     *
     * @param issueKey
     * @return
     * @throws Exception
     */
    public Issue getIssue(String issueKey) throws Exception {
        return jiraHttpClient.getIssue(issueKey);
    }

    public List<IssueQueryResponseData> queryIssues(IssueJqlCondition issueJqlCondition) throws Exception {

        List<IssueQueryResponseData> issueQueryResponseData = jiraHttpClient.queryIssues(issueJqlCondition);

        for (IssueQueryResponseData issueQueryResponseDatum : issueQueryResponseData) {

            issueQueryResponseDatum.setSelf(jiraUrl + "/browse/" + issueQueryResponseDatum.getKey());
        }
        return issueQueryResponseData;
    }

    /**
     * 查询所有的HTTP JIRA 项目 (不是详情)
     *
     * @return
     * @throws Exception
     */
    public List<ProjectAbstract> getAllProjects() throws Exception {
        return jiraHttpClient.getAllProjects();
    }

    /**
     * 添加Http jira项目
     *
     * @param
     * @return
     * @throws Exception
     */
    public ProjectVO createProject(ProjectCreateDataDTO projectCreateDataDTO) throws Exception {
        String projectKey = departmentService.generateProjectKeyAndUpdate(projectCreateDataDTO.getDepartment());
        projectCreateDataDTO.setProjectKey(projectKey);
        String uuid = EireneUtil.randomUUID();

        Project projectDB = projectCreateDataDTO.ProjectDB();
        projectDB.setUuid(uuid);
        //向DB添加 如果报错抛出异常
        int i = jiraProjectDBService.insertProject(projectDB);
        if (i == 0) {
            throw new InternalServerErrorException("Sql insert fail");
        }
        ProjectVO project = null;
        jiraHttpClient.createIfNotExist(projectCreateDataDTO.getLead());

        JiraCreateProjectThread jiraCreateProjectThread = new JiraCreateProjectThread(JiraClientService.this);
        jiraCreateProjectThread.setProjectCreateDataDTO(projectCreateDataDTO);
        jiraCreateProjectThread.start();
        project = getProject(projectCreateDataDTO.getProjectKey(), false);

        return project;
    }

    /**
     * 重建项目
     *
     * @param projectCreateDataDTO
     * @return
     * @throws Exception
     */
    public ProjectVO reCreateProject(ProjectCreateDataDTO projectCreateDataDTO) throws Exception {
        ProjectVO project = null;
        String projectKey = projectCreateDataDTO.getProjectKey();
        ProjectVO project2 = getProject(projectKey, false);
        if (project2 != null) {
            String projectStatus = project2.getProjectStatus();
            if (projectStatus.equals(ConstantUtil.FAILED)) {
                Project project1 = projectCreateDataDTO.ProjectDB();
                project1.setUuid(project2.getUuid());
                project1.setProjectStatus(ConstantUtil.CREATING);
                int i = updateProjectByUuid(project1);
                logger.info(String.format("reCreate project update .%d",i));
                jiraHttpClient.createIfNotExist(projectCreateDataDTO.getLead());
                JiraCreateProjectThread jiraCreateProjectThread = new JiraCreateProjectThread(JiraClientService.this);
                jiraCreateProjectThread.setProjectCreateDataDTO(projectCreateDataDTO);
                jiraCreateProjectThread.start();
                logger.info("asynchronous create project .");
                project = getProject(projectCreateDataDTO.getProjectKey(), false);
            }
        }

        return project;
    }

    /**
     * 创建版本
     *
     * @param versionCreateDataDTO
     * @return
     * @throws Exception
     */
    public VersionVO createVersion(VersionCreateDataDTO versionCreateDataDTO) throws Exception {

        String projectKey = versionCreateDataDTO.getProjectKey();
        ProjectVO project = getProject(projectKey, false);
        if (project == null) {
            logger.info(" 'create project' not found jira project ");
            throw new NotFoundException("找不到jira的项目");
        }
        if (project.getProjectStatus().equals(ConstantUtil.FAILED) || project.getProjectStatus().equals(ConstantUtil.CREATING)) {
            logger.info(" this status don't create version ");
            throw new NotFoundException("此状态的项目不能创建版本");
        }
        String uuid = EireneUtil.randomUUID();
        VersionManagement versionManagement1 = versionCreateDataDTO.versionManagement();
        versionManagement1.setUuid(uuid);
        int version1 = jiraVersionDBService.createVersion(versionManagement1);
        if (version1 == 0) {
            logger.info("Sql insert fail");
            throw new InternalServerErrorException("Sql insert fail");
        }
        VersionVO version = null;
        try {
            logger.info("create HTTP jiraVersion start");
            ProjectVersion version2 = jiraHttpClient.createVersion(versionCreateDataDTO.versionCreateData());
            logger.info("create HTTP jiraVersion end");
            if (version2 != null) {
                VersionManagement versionManagement = new VersionManagement();
                versionManagement.setUuid(uuid);
                versionManagement.setVersionId(version2.getId());
                int i = jiraVersionDBService.updateVersion(versionManagement);
                if (i != 0) {
                    version = getVersion(version2.getId(), true);
                }
            }
        } catch (Exception e) {
            jiraVersionDBService.deleteVersion(uuid);
            throw e;
        }
        return version;
    }

    /**
     * 修改版本管理
     *
     * @param
     * @return
     * @throws IOException
     */
    public VersionManagement updateVersion(String versionUuid, VersionCreateDataDTO versionCreateDataDTO) throws IOException {
        VersionManagement versionManagement1 = selectByVersionUuid(versionUuid);
        VersionManagement versionManagement = versionCreateDataDTO.versionManagement();
        versionManagement.setUuid(versionUuid);
        versionManagement.setProjectKey(versionManagement1.getProjectKey());
        versionManagement.setReleaseDate(versionManagement1.getReleaseDate());
        versionManagement.setVersionId(versionManagement1.getVersionId());
        versionManagement.setIssueNum(versionManagement1.getIssueNum());
        versionManagement.setVersionName(versionManagement1.getVersionName());
        int i = jiraVersionDBService.updateVersion(versionManagement);
        VersionManagement projectVersion = null;
        if (i > 0) {
            projectVersion = selectByVersionUuid(versionUuid);
        }
        return projectVersion;
    }

    /**
     * 获取版本详情
     *
     * @param issueJqlCondition
     * @return
     * @throws Exception
     */
    public List<IssueQueryResponseData> queryIssuesDetail(IssueJqlCondition issueJqlCondition) throws Exception {

        List<IssueQueryResponseData> issueQueryResponseData = jiraHttpClient.queryIssuesDetail(issueJqlCondition);
        for (IssueQueryResponseData issueQueryResponseDatum : issueQueryResponseData) {
            issueQueryResponseDatum.setSelf(jiraUrl + "/browse/" + issueQueryResponseDatum.getKey());
        }
        return issueQueryResponseData;
    }

    /**
     * 版本的操作
     *
     * @param uuid
     * @param operation
     * @param releaseIssues
     * @return
     * @throws Exception
     */
    public VersionVO versionOperation(String uuid, String operation, String releaseIssues) throws Exception {

        VersionManagement versionManagement = jiraVersionDBService.selectProjectVersion(uuid);

        //1. 如果 operation = 发布 接收选中的需求以及需求下面的故事和缺陷（入库） 并改变状态为预发布
        if (operation.equals(ConstantUtil.RELEASE)) {
            versionManagement.setReleaseIssues(releaseIssues);
            versionManagement.setVersionStatus(ConstantUtil.PRE_RELEASE);
            jiraVersionDBService.updateVersion(versionManagement);
            VersionVO version = getVersion(versionManagement.getVersionId(), false);
            return version;
        }
        //2. 如果 operation = 确认 把数据库中的需求以及的选中故事和缺陷传给jira插件接口 并改变状态为已发布
        else if (operation.equals(ConstantUtil.CONFIRM)) {
            VersionReleaseDTO versionReleaseDTO = new VersionReleaseDTO();
            versionReleaseDTO.setProjectKey(versionManagement.getProjectKey());
            versionReleaseDTO.setVersionName(versionManagement.getVersionName());
            versionReleaseDTO.setIssueKeys(versionManagement.getReleaseIssues());
            logger.info("ready release version start");
            PolarisHttpResult polarisHttpResult = jiraHttpClient.releaseVersion(versionReleaseDTO);
            logger.info("ready release version end");
            boolean status = polarisHttpResult.isStatus();
            if (status) {
                versionManagement.setVersionStatus(ConstantUtil.READY_RELEASE);
                jiraVersionDBService.updateVersion(versionManagement);
                VersionVO version = getVersion(versionManagement.getVersionId(), false);
                return version;
            } else {
                logger.info("issueKey is not exits or not null");
                throw new NotFoundException("issueKey is not exits or not null");
            }
        }
        //3. 如果 operation = 回滚 删除数据库中发布选中需求下面的故事和缺陷 并改变状态为未发布
        else if (operation.equals(ConstantUtil.ROLL_BACK)) {
            versionManagement.setReleaseIssues("");
            versionManagement.setVersionStatus(ConstantUtil.NOT_RELEASE);
            int i = jiraVersionDBService.updateVersion(versionManagement);
            VersionVO version = getVersion(versionManagement.getVersionId(), false);
            return version;
        }
        return getVersion(versionManagement.getVersionId(), false);
    }

    /**
     * 根据版本的id找到版本号
     * 如果detail == false查询DB，否则查询HTTP
     *
     * @param versionId
     * @return
     * @throws Exception
     */
    public VersionVO getVersion(String versionId, Boolean detail) throws Exception {
        VersionVO versionVO = null;
        VersionManagement versionManagement = jiraVersionDBService.selectVersionById(versionId);
        if (versionManagement == null) {
            throw new NotFoundException("not found DB");
        }
        versionVO = new VersionVO(versionManagement);
        if (detail) {
            versionVO.setProjectVersion(jiraHttpClient.getVersion(versionId));
        }
        return versionVO;
    }

    /**
     * 查询版本 根据项目的key查询版本 如果key为空查询全部版本
     *
     * @param projectKey
     * @return
     */
    public List<VersionManagement> selectVersionByKey(String projectKey) throws Exception {
        if (projectKey != null) {
            List<VersionManagement> versionManagements = null;
            versionManagements = jiraVersionDBService.selectVersionByProjectKey(projectKey);
            for (VersionManagement versionManagements1 : versionManagements) {
                String versionId = versionManagements1.getVersionName();
                int issueNum = queryIssuesNum(versionManagements1.getProjectKey(), versionId);
                VersionManagement versionManagement = new VersionManagement();
                versionManagement.setUuid(versionManagements1.getUuid());
                versionManagement.setIssueNum(issueNum);
                int i = jiraVersionDBService.updateVersion(versionManagement);
                if (i > 0) {
                    versionManagements = jiraVersionDBService.selectVersionByProjectKey(projectKey);
                }
            }
            return versionManagements;
        }
        List<VersionManagement> versionManagements = jiraVersionDBService.selectVersionAll();
        return versionManagements;
    }

    /**
     * 查找项目下有几个版本
     *
     * @param projectKey
     * @return
     * @throws Exception
     */
    public PolarisHttpResult getVersionCountByProjectKey(String projectKey) throws Exception {
        PolarisHttpResult versionCountByProjectKey = jiraHttpClient.getVersionCountByProjectKey(projectKey);
        return versionCountByProjectKey;
    }

    /**
     * 查询项目、版本下有几个需求（查询需求数）
     *
     * @param projectKey
     * @param fixVersion
     * @return
     */
    public int queryIssuesNum(String projectKey, String fixVersion) throws Exception {
        IssueJqlCondition issueJqlCondition = new IssueJqlCondition(IssueJqlRelation.AND.getRelation());
        if (projectKey != null && !projectKey.isEmpty()) {
            issueJqlCondition.addItemProjectEqual(projectKey);
        }

        if (fixVersion != null && !"".equals(fixVersion)) {
            issueJqlCondition.addItemDueFixVersion(fixVersion);
        }
        List<IssueQueryResponseData> issueQueryResponseDataList = queryIssues(issueJqlCondition);
        int size = issueQueryResponseDataList.size();
        return size;

    }


    /**
     * 删除版本
     *
     * @param uuid
     * @return
     */
    public int deleteVersion(String uuid) {
        return jiraVersionDBService.deleteVersion(uuid);
    }

    /**
     * 查询版本 根据uuid
     *
     * @param uuid
     * @return
     */
    public VersionManagement selectByVersionUuid(String uuid) throws IOException {
        VersionManagement versionManagement = jiraVersionDBService.selectProjectVersion(uuid);
        return versionManagement;
    }


    /**
     * 修改Http project项目
     *
     * @param projectKey
     * @param projectCreateData
     * @return
     * @throws Exception
     */
    public ProjectDetail updateProject(String projectKey, ProjectCreateData projectCreateData) throws Exception {
        return jiraHttpClient.updateProject(projectKey, projectCreateData);
    }

    /**
     * 修改DB Project
     *
     * @param
     * @param project
     * @return
     */
    public Project updateProject(String projectKey, Project project) throws IOException {
        project.setProjectKey(projectKey);
        int i = jiraProjectDBService.updateProject(project);
        Project project1 = null;
        if (i > 0) {
            project1 = jiraProjectDBService.selectProject(projectKey);
        }
        return project1;
    }

    /**
     * 根据uuid修改project
     * @param project
     * @return
     * @throws IOException
     */
    public int updateProjectByUuid(Project project) throws IOException {

        return jiraProjectDBService.updateProjectByUuid(project);

    }

    /**
     * 修改DB Project Status
     *
     * @param
     * @param project
     * @return
     */
    public int updateProject(Project project) throws IOException {

        int i = jiraProjectDBService.updateProject(project);

        return i;
    }

    /**
     * 删除数据库项目
     *
     * @param projectKey
     * @return
     */
    public boolean deleteProject(String projectKey) throws Exception {
        //删除JIRA项目
        boolean res = jiraHttpClient.deleteProject(projectKey);
        if (res == true) {
            //删除DB项目
            try {
                int i = jiraProjectDBService.deleteProject(projectKey);
            } catch (Exception e) {
                throw new InternalServerErrorException("Sql insert fail");
            }

        }
        return res;
    }

    public List<ProjectVO> getProjectByKeyAndDepartment(List<Project> projects) {
        List<ProjectVO> projectVOs = new ArrayList<>();
        for (Project project : projects) {
            ProjectVO projectVO = new ProjectVO(project);
            projectVOs.add(projectVO);
        }
        return projectVOs;
    }

    /**
     * 查询多个项目，如果detail == false查询DB，否则查询DB+HTTP
     *
     * @param projectKeyList
     * @param detail
     * @return
     */
    public List<ProjectVO> selectProjects(List<String> projectKeyList, List<String> departmentList, Boolean detail) throws Exception {
        List<ProjectVO> projectVOs = new ArrayList<>();
        ProjectVO projectVO = null;
        List<Project> projects = jiraProjectDBService.selectByProjectKeyAndDepartment(projectKeyList, departmentList);
        if (detail == false) {
            // fast response;
            for (Project project : projects) {
                projectVO = new ProjectVO(project);
                projectVOs.add(projectVO);
            }
            return projectVOs;
        }
        projectKeyList = getProjectKey(projects);
        for (String projectKey : projectKeyList) {
            try {
                projectVOs.add(getProject(projectKey, detail));
            } catch (Exception e) {
                logger.info(String.format("The Project %s is not found or the user does not have permission to view it", projectKey));
            }

        }
        return projectVOs;
    }


    public List<String> getProjectKeys() throws IOException {
        List<Project> projects = jiraProjectDBService.selectProjects();
        return getProjectKey(projects);
    }

    private List<String> getProjectKey(List<Project> projects) {
        ArrayList<String> projectKeys = new ArrayList<>();
        for (Project project : projects) {
            projectKeys.add(project.getProjectKey());
        }
        return projectKeys;
    }

    public List<ProjectVersion> getVersionsFilter(ProjectDetail projectDetail, int year, int month) throws Exception {

        List<ProjectVersion> projectVersions = new ArrayList<>();

        List<ProjectVersion> projectVersionList = projectDetail.getVersions();
        for (ProjectVersion projectVersion : projectVersionList) {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            //将String转换成Date类型
            if (projectVersion.getReleaseDate() != null) {
                Date releaseDate = simpleDateFormat1.parse(projectVersion.getReleaseDate());
                //把发布版本日期
                calendar.setTime(releaseDate);
                int versionYear = calendar.get(Calendar.YEAR);
                int versionMonth = calendar.get(Calendar.MONTH) + 1;
                if (versionYear == year && versionMonth == month) {
                    projectVersions.add(projectVersion);
                }
            }
        }
        return projectVersions;
    }

    public List<ProjectTemplates> getProjectTemplates() throws Exception {

        PolarisHttpResult projectTemplates = jiraHttpClient.getProjectTemplates();
        Object data = projectTemplates.getData();

        List<ProjectTemplates> projectTemplates1 = null;
        if (projectTemplates.isStatus() == true) {
            projectTemplates1 = (List<ProjectTemplates>) data;
        }
        return projectTemplates1;
    }

    /**
     * 根据用户名称查询用户
     *
     * @param userName
     * @return
     * @throws Exception
     */
    public UserDetail getUser(String userName) throws Exception {
        return jiraHttpClient.getUser(userName);
    }

    /**
     * 创建用户
     *
     * @param userCreateData
     * @return
     * @throws Exception
     */
    public UserDetail createUser(UserCreateData userCreateData) throws Exception {
        return jiraHttpClient.createUser(userCreateData);
    }

    /**
     * 删除用户
     *
     * @param userName
     * @return
     * @throws Exception
     */
    public boolean deleteUser(String userName) throws Exception {
        return jiraHttpClient.deleteUser(userName);
    }

    /**
     * 查询项目总数
     *
     * @return
     * @throws IOException
     */
    public List<DepartmentCountVO> departmentCountVOList() throws IOException {
        return jiraProjectDBService.departmentCountVOList();
    }

}
