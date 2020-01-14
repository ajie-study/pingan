package com.pingan.starlink.service;

import com.pingan.starlink.httpclient.PolarisHttpResult;
import com.pingan.starlink.model.Project;
import com.pingan.starlink.model.UserInfo4A;
import com.pingan.starlink.model.VersionManagement;
import com.pingan.starlink.model.jira.ProjectDetail;
import com.pingan.starlink.model.jira.ProjectVersion;
import com.pingan.starlink.model.jira.VersionCreateData;
import com.pingan.starlink.model.polaris.ConfigScheme1;
import com.pingan.starlink.model.polaris.Schemes;
import com.pingan.starlink.util.ConstantUtil;
import com.pingan.starlink.util.JacksonUtil;
import com.pingan.starlink.vo.jira.DepartmentCountVO;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class JiraClientServiceTests {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JiraClientService jiraClientService;

    @Autowired
    private JiraProjectDBService jiraProjectDBService;

    @Autowired
    private JiraVersionDBService jiraVersionDBService;

    @Autowired
    private JiraHttpClient jiraHttpClient;


    @Test
    public void getProject() throws Exception {


        JiraHttpClient mock = mock(JiraHttpClient.class);
        String projectKey = "PRO_TEST";
        // 设置模拟http响应
        when(mock.getProject(projectKey)).thenReturn(buildProjectDetail());
        jiraClientService.setJiraHttpClient(mock);

        ProjectDetail projectDetail = jiraClientService.getProject(projectKey);

        Assert.assertEquals("SCRUMDEV", projectDetail.getKey());
        Assert.assertEquals("10101", projectDetail.getId());
        Assert.assertEquals("scrum开发方法", projectDetail.getName());
    }

    private ProjectDetail buildProjectDetail() throws IOException {
        String project = "{\"expand\":\"description,lead,url,projectKeys\",\n" +
                "\"self\":\"http://119.28.9.84:8080/rest/api/2/project/10101\",\n" +
                "\"id\":\"10101\",\n" +
                "\"key\":\"SCRUMDEV\",\n" +
                "\"name\":\"scrum开发方法\",\n" +
                "\"projectTypeKey\":\"software\"}";
        return JacksonUtil.json2Bean(project, ProjectDetail.class);
    }

    @Test
    public void getProjectVersions() throws Exception {

        int year = 2019;
        int month = 2;
        // 设置模拟http响应

        List<ProjectVersion> versionsFilter = jiraClientService.getVersionsFilter(buildProjectDetail1(), year, month);

        List<ProjectVersion> projectVersionList = buildProjectVersion();

        for (ProjectVersion projectVersion : projectVersionList) {

            for (ProjectVersion projectVersion1 : versionsFilter) {

                if (projectVersion.getReleaseDate().equals(projectVersion1.getReleaseDate()))

                    Assert.assertEquals(projectVersion.getName(), projectVersion1.getName());
            }
        }


    }

    private ProjectDetail buildProjectDetail1() throws Exception {
        String projectVersion = "{\"expand\": \"description,lead,url,projectKeys\",\n" +
                "        \"self\": \"http://119.28.9.84:8080/rest/api/2/project/10103\",\n" +
                "        \"id\": \"10103\",\n" +
                "        \"key\": \"BASEDEV\",\n" +
                "        \"name\": \"基本开发方法\",\n" +
                "        \"projectTypeKey\": \"software\",\n" +
                "        \"assigneeType\": \"UNASSIGNED\",\n" +
                "        \"versions\": [\n" +
                "            {\n" +
                "                \"self\": \"http://119.28.9.84:8080/rest/api/2/version/10002\",\n" +
                "                \"id\": \"10002\",\n" +
                "                \"name\": \"1.0\",\n" +
                "                \"archived\": false,\n" +
                "                \"released\": true,\n" +
                "                \"releaseDate\": \"2019-02-22\",\n" +
                "                \"overdue\": false,\n" +
                "                \"userReleaseDate\": \"22/二月/19\",\n" +
                "                \"projectId\": 10103\n" +
                "            },\n" +
                "            {\n" +
                "                \"self\": \"http://119.28.9.84:8080/rest/api/2/version/10003\",\n" +
                "                \"id\": \"10003\",\n" +
                "                \"description\": \"1.1\",\n" +
                "                \"name\": \"1.1\",\n" +
                "                \"archived\": false,\n" +
                "                \"released\": false,\n" +
                "                \"startDate\": \"2019-02-25\",\n" +
                "                \"releaseDate\": \"2019-02-26\",\n" +
                "                \"overdue\": true,\n" +
                "                \"userStartDate\": \"25/二月/19\",\n" +
                "                \"userReleaseDate\": \"26/二月/19\",\n" +
                "                \"projectId\": 10103\n" +
                "            },\n" +
                "            {\n" +
                "                \"self\": \"http://119.28.9.84:8080/rest/api/2/version/10100\",\n" +
                "                \"id\": \"10100\",\n" +
                "                \"name\": \"1.2\",\n" +
                "                \"archived\": false,\n" +
                "                \"released\": false,\n" +
                "                \"startDate\": \"2019-02-27\",\n" +
                "                \"releaseDate\": \"2019-02-27\",\n" +
                "                \"overdue\": true,\n" +
                "                \"userStartDate\": \"27/二月/19\",\n" +
                "                \"userReleaseDate\": \"27/二月/19\",\n" +
                "                \"projectId\": 10103\n" +
                "            }\n" +
                "        ]}";
        return JacksonUtil.json2Bean(projectVersion, ProjectDetail.class);
    }

    private List<ProjectVersion> buildProjectVersion() throws Exception {
        String projectVersion = "[\n" +
                "        {\n" +
                "            \"self\": \"http://119.28.9.84:8080/rest/api/2/version/10002\",\n" +
                "            \"id\": \"10002\",\n" +
                "            \"name\": \"1.0\",\n" +
                "            \"archived\": false,\n" +
                "            \"released\": true,\n" +
                "            \"releaseDate\": \"2019-02-22\",\n" +
                "            \"overdue\": false,\n" +
                "            \"userReleaseDate\": \"22/二月/19\",\n" +
                "            \"projectId\": 10103\n" +
                "        },\n" +
                "        {\n" +
                "            \"self\": \"http://119.28.9.84:8080/rest/api/2/version/10003\",\n" +
                "            \"id\": \"10003\",\n" +
                "            \"description\": \"1.1\",\n" +
                "            \"name\": \"1.1\",\n" +
                "            \"archived\": false,\n" +
                "            \"released\": false,\n" +
                "            \"startDate\": \"2019-02-25\",\n" +
                "            \"releaseDate\": \"2019-02-26\",\n" +
                "            \"overdue\": true,\n" +
                "            \"userStartDate\": \"25/二月/19\",\n" +
                "            \"userReleaseDate\": \"26/二月/19\",\n" +
                "            \"projectId\": 10103\n" +
                "        }]";
        return JacksonUtil.json2Bean(projectVersion, List.class, ProjectVersion.class);
    }

    @Ignore
    @Test
    public void insertProject() throws IOException {
        Project project = new Project();
        project.setProjectKey("KJSA123");
        project.setProjectName("测试213");
        project.setDescription("描述");
        project.setLead("fanyang");
        project.setAssigneetype("123");
        project.setProjectType("asd");
        project.setDevMode("asd");
        project.setHostSystem("asd");
        project.setDevMode("sda");
        project.setPmo("sada");
        jiraProjectDBService.insertProject(project);
    }

    @Ignore
    @Test
    public void deleteProject() {
        String projectKey = "YFFFF";
        int i = jiraProjectDBService.deleteProject(projectKey);
        System.out.println("-----------------------------" + i);
    }

    @Ignore
    @Test
    public void updateProject() throws IOException {

        Project project = new Project();
        project.setProjectType("asd");
        project.setDevMode("asd");
        project.setHostSystem("asd");
        project.setDevMode("sda");
        project.setPmo("sada");
        int i = jiraProjectDBService.updateProject(project);
        System.out.println("-----------------------------" + i);
    }


    @Ignore
    @Test
    public void selectProject() throws IOException {
        String projectKey = "YF";
        Project project = jiraProjectDBService.selectProject(projectKey);
        System.out.println("-----------------------------" + JacksonUtil.bean2Json(project));
    }

    @Ignore
    @Test
    public void selectProjects() throws IOException {
        List<Project> projects = jiraProjectDBService.selectProjects();
        System.out.println("-----------------------------" + JacksonUtil.bean2Json(projects));
    }

    @Ignore
    @Test
    public void versionCreate() throws IOException {
        VersionManagement versionManagement = new VersionManagement();
        versionManagement.setVersionName("111");
        int version = jiraVersionDBService.createVersion(versionManagement);
        System.out.println("-----------------------------" + version);
    }

    @Ignore
    @Test
    public void versionDelete() throws IOException {
        String uuid = "be34b76e-feeb-4cc0-97e7-30fc6a911261";
        int version = jiraVersionDBService.deleteVersion(uuid);
        System.out.println("-----------------------------" + version);
    }


    @Ignore
    @Test
    public void versionJiraCreat() throws Exception {
        VersionCreateData versionCreateData = new VersionCreateData();
        versionCreateData.setName("New Version 33");
        versionCreateData.setProject("YFFFF");
//        ProjectVersion version = jiraHttpClient.createVersion(versionCreateData);
//        System.out.println("--------------------------"+version);
    }

    @Ignore
    @Test
    public void uuid() {
        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);
    }

    @Ignore
    @Test
    public void getVersion() throws IOException {
        String versionId = "10114";
        VersionManagement projectVersion = jiraVersionDBService.selectVersionById(versionId);
        logger.info(String.format("result : %s", JacksonUtil.bean2Json(projectVersion)));
    }

    @Ignore
    @Test
    public void getDepartmentCount() throws IOException {
        List<DepartmentCountVO> departmentCountVOS = jiraProjectDBService.departmentCountVOList();
        logger.info(String.format("result : %s", JacksonUtil.bean2Json(departmentCountVOS)));
    }

    @Ignore
    @Test
    public void getIssueTypeSchemes() throws Exception {
        PolarisHttpResult issueTypeSchemes = jiraHttpClient.getIssueTypeSchemes();
        PolarisHttpResult workflowSchemes = jiraHttpClient.getWorkflowSchemes();
        PolarisHttpResult fieldsSchemes = jiraHttpClient.getFieldsSchemes();
        PolarisHttpResult screensSchemes = jiraHttpClient.getScreensSchemes();
        Map<String, List<ConfigScheme1>> schemes = jiraHttpClient.schemes();
        logger.info(String.format("result : %s", JacksonUtil.bean2Json(issueTypeSchemes)));
        logger.info(String.format("result : %s", JacksonUtil.bean2Json(workflowSchemes)));
        logger.info(String.format("result : %s", JacksonUtil.bean2Json(fieldsSchemes)));
        logger.info(String.format("result : %s", JacksonUtil.bean2Json(screensSchemes)));
        logger.info(String.format("result : %s", JacksonUtil.bean2Json(schemes)));

    }

    @Ignore
    @Test
    public void associationWorkflowScheme() throws Exception {
        Schemes schemes = new Schemes();
        schemes.setWorkflowSchemeId((long) 10101);
        schemes.setProjectKey("KKKK");
        PolarisHttpResult polarisHttpResult = jiraHttpClient.associationWorkflowScheme(schemes);
        logger.info(String.format("result : %s", JacksonUtil.bean2Json(polarisHttpResult)));
    }

    @Ignore
    @Test
    public void associationFieldsScheme() throws Exception {
        Schemes schemes = new Schemes();
        schemes.setFieldsSchemeId((long) 10000);
        schemes.setProjectKey("KKKK");
        PolarisHttpResult polarisHttpResult = jiraHttpClient.associationFieldsScheme(schemes);
        logger.info(String.format("result : %s", JacksonUtil.bean2Json(polarisHttpResult)));
    }

    @Ignore
    @Test
    public void associationIssueTypeScheme() throws Exception {
        Schemes schemes = new Schemes();
        schemes.setIssueTypeSchemeId((long) 10207);
        schemes.setProjectKey("KKKK");
        PolarisHttpResult polarisHttpResult = jiraHttpClient.associationIssueTypeScheme(schemes);
        logger.info(String.format("result : %s", JacksonUtil.bean2Json(polarisHttpResult)));
    }

    @Ignore
    @Test
    public void associationScreensScheme() throws Exception {
        Schemes schemes = new Schemes();
        schemes.setScreensSchemeId((long) 10001);
        schemes.setProjectKey("KKKK");
        PolarisHttpResult polarisHttpResult = jiraHttpClient.associationScreensScheme(schemes);
        logger.info(String.format("result : %s", JacksonUtil.bean2Json(polarisHttpResult)));
    }

    @Ignore
    @Test
    public void associationSchemes() throws Exception {
        Schemes schemes = new Schemes();
        schemes.setWorkflowSchemeId((long) 10101);
        schemes.setProjectKey("KKKK");
        schemes.setScreensSchemeId((long) 0);
        schemes.setFieldsSchemeId((long) 0);
        schemes.setIssueTypeSchemeId((long) 0);
        PolarisHttpResult polarisHttpResult = jiraHttpClient.associationSchemes(schemes);
        logger.info(String.format("result : %s", JacksonUtil.bean2Json(polarisHttpResult)));
    }

    @Ignore
    @Test
    public void createIfNotExist() throws Exception {
        UserInfo4A user4A = new UserInfo4A();
        user4A.setUsername("zhoumoumou");
        user4A.setEmail("zhou@alauda.io");
        user4A.setDisplayName("周某某");
        this.jiraHttpClient.createIfNotExist(user4A);
    }

    @Ignore
    @Test
    public void hashsetContains() throws Exception {

        String projectTemplate = "com.pyxis.greenhopper.jira:gh-kanban-template";
        boolean contains = ConstantUtil.projectTemplates.contains(projectTemplate);
        System.out.println("contains-------------------" + contains);
    }

    @Ignore
    @Test
    public void versionNum() throws Exception {
        String projectKey = "CS8";
        String fixVersion = "10038";
        int i = jiraClientService.queryIssuesNum(projectKey, fixVersion);
        System.out.println(i);
    }

}