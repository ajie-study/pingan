package com.pingan.starlink.service;

import com.pingan.starlink.dto.VersionReleaseDTO;
import com.pingan.starlink.exception.BadRequestException;
import com.pingan.starlink.exception.ForbiddenException;
import com.pingan.starlink.exception.NotFoundException;
import com.pingan.starlink.exception.UnauthorizedException;
import com.pingan.starlink.httpclient.HttpResult;
import com.pingan.starlink.httpclient.JiraIssueHttpResult;
import com.pingan.starlink.httpclient.PolarisHttpResult;
import com.pingan.starlink.model.UserInfo4A;
import com.pingan.starlink.model.jira.*;
import com.pingan.starlink.model.polaris.ConfigScheme1;
import com.pingan.starlink.model.polaris.Schemes;
import com.pingan.starlink.util.ConstantUtil;
import com.pingan.starlink.util.JacksonUtil;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JiraHttpClient extends BaseHttpClient {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${jira.url}")
    private String jiraUrl;

    @Value("${jira.user}")
    private String user;

    @Value("${jira.password}")
    private String password;

    public ProjectDetail getProject(String projectKey) throws Exception {

        HttpResult res = doGet(jiraUrl + "/rest/api/2/project/" + projectKey);
        if (res.getCode() == 200) {
            ProjectDetail projectDetail = JacksonUtil.json2Bean(res.getBody(), ProjectDetail.class);
            return projectDetail;
        } else if (res.getCode() == 401) {
            throw new UnauthorizedException("Authentication credentials are incorrect or missing.");
        } else if (res.getCode() == 404) {
            throw new NotFoundException(String.format("The Project %s is not found or the user does not have permission to view it.", projectKey));
        } else {
            throw new Exception("Unkown Error.");
        }
    }

    public Issue getIssue(String issueKey) throws Exception {

        HttpResult res = doGet(jiraUrl + "/rest/api/2/issue/" + issueKey);

        if (res.getCode() == 200) {
            Issue issue = JacksonUtil.json2Bean(res.getBody(), Issue.class);
            return issue;
        } else if (res.getCode() == 401) {
            throw new UnauthorizedException("Authentication credentials are incorrect or missing.");
        } else if (res.getCode() == 404) {
            throw new NotFoundException(String.format("The Issue %s is not found or the user does not have permission to view it.", issueKey));
        } else {
            throw new Exception("Unkown Error.");
        }
    }

    public List<IssueQueryResponseData> queryIssues(IssueJqlCondition issueJqlCondition) throws Exception {

        HttpResult res = doPost(jiraUrl + "/rest/polaris/1/issue/issues", JacksonUtil.bean2Json(issueJqlCondition));

        if (res.getCode() == 200) {
            JiraIssueHttpResult jiraIssueHttpResult = JacksonUtil.json2Bean(res.getBody(), JiraIssueHttpResult.class);
            if (jiraIssueHttpResult.isStatus()) {
                return jiraIssueHttpResult.getData();
            } else {
                throw new BadRequestException(jiraIssueHttpResult.getMsg());
            }
        } else if (res.getCode() == 400) {
            throw new BadRequestException(res.getBody());
        } else if (res.getCode() == 401) {
            throw new UnauthorizedException("Authentication credentials are incorrect or missing.");
        } else if (res.getCode() == 403) {
            throw new ForbiddenException("The user does not have permission to create projects.");
        } else {
            throw new Exception("Unkown Error.");
        }
    }

    public List<ProjectAbstract> getAllProjects() throws Exception {
        HttpResult res = doGet(String.format("%s/rest/api/2/project", jiraUrl));
        if (res.getCode() == 200) {
            List<ProjectAbstract> projects = JacksonUtil.json2Bean(res.getBody(), List.class, ProjectAbstract.class);
            return projects;
        } else {
            throw new Exception("Unkown Error.");
        }
    }

    public List<ProjectDetail> getAllProject() throws Exception {
        HttpResult res = doGet(String.format("%s/rest/api/2/project", jiraUrl));
        if (res.getCode() == 200) {
            List<ProjectDetail> projects = JacksonUtil.json2Bean(res.getBody(), List.class, ProjectDetail.class);
            return projects;
        } else {
            throw new Exception("Unkown Error.");
        }
    }

    public PolarisHttpResult getProjectTemplates() throws Exception {
        HttpResult res = doGet(String.format("%s/rest/polaris/1/projectContext/projectTemplates", jiraUrl));
        if (res.getCode() == 200) {
            PolarisHttpResult projectTemplates = JacksonUtil.json2Bean(res.getBody(), PolarisHttpResult.class);
            return projectTemplates;
        } else {
            throw new Exception("Unkown Error.");
        }
    }

    public PolarisHttpResult getIssueTypeSchemes() throws Exception {
        HttpResult res = doGet(String.format("%s/rest/polaris/1/projectContext/issueTypeSchemes", jiraUrl));
        if (res.getCode() == 200) {
            PolarisHttpResult configScheme = JacksonUtil.json2Bean(res.getBody(), PolarisHttpResult.class);
            return configScheme;
        } else {
            throw new Exception("Unkown Error.");
        }
    }

    public PolarisHttpResult getWorkflowSchemes() throws Exception {
        HttpResult res = doGet(String.format("%s/rest/polaris/1/projectContext/workflowSchemes", jiraUrl));
        if (res.getCode() == 200) {
            PolarisHttpResult configScheme = JacksonUtil.json2Bean(res.getBody(), PolarisHttpResult.class);
            return configScheme;
        } else {
            throw new Exception("Unkown Error.");
        }
    }

    public PolarisHttpResult getScreensSchemes() throws Exception {
        HttpResult res = doGet(String.format("%s/rest/polaris/1/projectContext/screensSchemes", jiraUrl));
        if (res.getCode() == 200) {
            PolarisHttpResult configScheme = JacksonUtil.json2Bean(res.getBody(), PolarisHttpResult.class);
            return configScheme;
        } else {
            throw new Exception("Unkown Error.");
        }
    }

    public PolarisHttpResult getFieldsSchemes() throws Exception {
        HttpResult res = doGet(String.format("%s/rest/polaris/1/projectContext/fieldsSchemes", jiraUrl));
        if (res.getCode() == 200) {
            PolarisHttpResult configScheme = JacksonUtil.json2Bean(res.getBody(), PolarisHttpResult.class);
            return configScheme;
        } else {
            throw new Exception("Unkown Error.");
        }
    }

    public Map<String, List<ConfigScheme1>> schemes() throws Exception {
        HttpResult res = doGet(String.format("%s/rest/polaris/1/projectContext/schemes", jiraUrl));
        if (res.getCode() == 200) {
            Map map = JacksonUtil.json2Bean(res.getBody(), Map.class);
            return map;
        } else {
            throw new Exception("Unkown Error.");
        }
    }

    public PolarisHttpResult associationIssueTypeScheme(Schemes schemes) throws Exception {
        HttpResult res = doPost(String.format("%s/rest/polaris/1/projectContext/associationIssueTypeScheme?projectKey=%s&schemeId=%s", jiraUrl, schemes.getProjectKey(), schemes.getIssueTypeSchemeId()));
        if (res.getCode() == 200) {

            PolarisHttpResult projectTemplates = JacksonUtil.json2Bean(res.getBody(), PolarisHttpResult.class);
            return projectTemplates;
        } else {
            throw new Exception("Unkown Error.");
        }
    }

    public PolarisHttpResult associationWorkflowScheme(Schemes schemes) throws Exception {
        HttpResult res = doPost(String.format("%s/rest/polaris/1/projectContext/associationWorkflowScheme?projectKey=%s&schemeId=%s", jiraUrl, schemes.getProjectKey(), schemes.getWorkflowSchemeId()));
        if (res.getCode() == 200) {
            PolarisHttpResult polarisHttpResult = JacksonUtil.json2Bean(res.getBody(), PolarisHttpResult.class);
            return polarisHttpResult;
        } else {
            throw new Exception("Unkown Error.");
        }
    }

    public PolarisHttpResult associationScreensScheme(Schemes schemes) throws Exception {
        HttpResult res = doPost(String.format("%s/rest/polaris/1/projectContext/associationScreensScheme?projectKey=%s&schemeId=%s", jiraUrl, schemes.getProjectKey(), schemes.getScreensSchemeId()));
        if (res.getCode() == 200) {
            PolarisHttpResult polarisHttpResult = JacksonUtil.json2Bean(res.getBody(), PolarisHttpResult.class);
            return polarisHttpResult;
        } else {
            throw new Exception("Unkown Error.");
        }
    }

    public PolarisHttpResult associationFieldsScheme(Schemes schemes) throws Exception {
        HttpResult res = doPost(String.format("%s/rest/polaris/1/projectContext/associationFieldsScheme?projectKey=%s&schemeId=%s", jiraUrl, schemes.getProjectKey(), schemes.getFieldsSchemeId()));
        if (res.getCode() == 200) {
            PolarisHttpResult polarisHttpResult = JacksonUtil.json2Bean(res.getBody(), PolarisHttpResult.class);
            return polarisHttpResult;
        } else {
            throw new Exception("Unkown Error.");
        }
    }

    public PolarisHttpResult associationSchemes(Schemes schemes) throws Exception {
        HttpResult res = doPost(String.format("%s/rest/polaris/1/projectContext/associationSchemes?projectKey=%s&issueTypeSchemeId=%s&workflowSchemeId=%s&screensSchemeId=%s&fieldsSchemeId=%s", jiraUrl, schemes.getProjectKey(), schemes.getIssueTypeSchemeId(), schemes.getWorkflowSchemeId(), schemes.getScreensSchemeId(), schemes.getFieldsSchemeId()));
        if (res.getCode() == 200) {
            PolarisHttpResult polarisHttpResult = JacksonUtil.json2Bean(res.getBody(), PolarisHttpResult.class);
            return polarisHttpResult;
        } else {
            throw new Exception("Unkown Error.");
        }
    }


    public List<ProjectDetail> listProjects(List<String> projectKeyList) throws Exception {
        List<ProjectDetail> projectDetails = new ArrayList<>();
        for (String projectKey : projectKeyList) {
            try {
                //弱list，无效的key跳过，不中止查询
                projectDetails.add(getProject(projectKey.toUpperCase()));
            } catch (NotFoundException e) {
                logger.warn(String.format("The Project %s is not found or the user does not have permission to view it.", projectKey));
            }
        }
        return projectDetails;
    }

    public PolarisHttpResult associationProjectSchemes(String projectKey, String templateKey) throws Exception {
        HttpResult res = doPost(String.format("%s/rest/polaris/1/projectContext/associationProjectSchemes?projectKey=%s&templateKey=%s", jiraUrl, projectKey, templateKey));
        if (res.getCode() == 200) {
            PolarisHttpResult polarisHttpResult = JacksonUtil.json2Bean(res.getBody(), PolarisHttpResult.class);
            return polarisHttpResult;
        } else {
            throw new Exception("Unkown Error.");
        }
    }


    public ProjectCreateResponseData createProject(ProjectCreateData projectCreateData) throws Exception {
        HttpResult res = null;
        boolean contains = ConstantUtil.projectTemplates.contains(projectCreateData.getProjectTemplateKey());
        ProjectCreateResponseData projectCreateResponseData = null;
        if (contains) {
            res = doPost(jiraUrl + "/rest/api/2/project", JacksonUtil.bean2Json(projectCreateData));
            projectCreateResponseData = JacksonUtil.json2Bean(res.getBody(), ProjectCreateResponseData.class);
        } else {
            projectCreateData.setProjectTypeKey(ConstantUtil.projectTypeKey);
            String customTemplate = projectCreateData.getProjectTemplateKey();
            projectCreateData.setProjectTemplateKey(null);
            res = doPost(jiraUrl + "/rest/api/2/project", JacksonUtil.bean2Json(projectCreateData));
            projectCreateResponseData = JacksonUtil.json2Bean(res.getBody(), ProjectCreateResponseData.class);
            associationProjectSchemes(projectCreateData.getKey(), customTemplate);
        }
        if (res.getCode() == 201) {
            return projectCreateResponseData;
        } else if (res.getCode() == 400) {
            throw new BadRequestException(res.getBody());
        } else if (res.getCode() == 401) {
            throw new UnauthorizedException("Authentication credentials are incorrect or missing .");
        } else if (res.getCode() == 403) {
            throw new ForbiddenException("The user does not have permission to create projects .");
        } else {
            throw new Exception("Unkown Error.");
        }
    }

    public boolean deleteProject(String projectKey) throws Exception {
        Map params = new HashMap<String, Object>();
        params.put("projectKey", projectKey);
        HttpResult res = doDelete(jiraUrl + "/rest/api/2/project/" + projectKey);
        if (res.getCode() == 204) {
            return true;
        } else if (res.getCode() == 401) {
            throw new UnauthorizedException("Returned if the user is not logged in.");
        } else if (res.getCode() == 403) {
            throw new ForbiddenException("Returned if the currently authenticated user does not have permission to delete the project.");
        } else if (res.getCode() == 404) {
            throw new NotFoundException("Returned if the project does not exist.");
        } else {
            throw new Exception("Unkown Error.");
        }
    }

    public ProjectDetail updateProject(String projectKey, ProjectCreateData projectCreateData) throws Exception {
        HttpResult res = doPut(jiraUrl + "/rest/api/2/project/" + projectKey, JacksonUtil.bean2Json(projectCreateData));
        if (res.getCode() == 200 || res.getCode() == 201) {
            ProjectDetail projectDetail = JacksonUtil.json2Bean(res.getBody(), ProjectDetail.class);
            return projectDetail;
        } else if (res.getCode() == 400) {
            throw new BadRequestException("Returned if the request is not valid and the project could not be updated.");
        } else if (res.getCode() == 401) {
            throw new UnauthorizedException("Returned if the user is not logged in.");
        } else if (res.getCode() == 403) {
            throw new ForbiddenException("Returned if the user does not have rights to update projects.");
        } else if (res.getCode() == 404) {
            throw new NotFoundException("Returned if the project does not exist.");
        } else {
            throw new Exception("Unkown Error.");
        }
    }

    public ProjectVersion createVersion(VersionCreateData versionCreateData) throws Exception {
        HttpResult res = doPost(jiraUrl + "/rest/api/2/version", JacksonUtil.bean2Json(versionCreateData));
        if (res.getCode() == 201) {
            ProjectVersion projectVersion = JacksonUtil.json2Bean(res.getBody(), ProjectVersion.class);
            return projectVersion;
        } else if (res.getCode() == 403) {
            throw new ForbiddenException("Returned if the currently authenticated user does not have permission to edit the version.");
        } else if (res.getCode() == 404) {
            throw new NotFoundException("Returned if the version does not exist or the currently authenticated user does not have permission to view it.");
        } else {
            throw new Exception("Unkown Error.");
        }
    }
    public PolarisHttpResult getVersionCountByProjectKey(String projectKey) throws Exception {
        HttpResult res = doPost(String.format("%s/rest/polaris/1/projectContext/associationProjectSchemes?projectKey=%s",jiraUrl,projectKey));
        if (res.getCode() == 200) {
            PolarisHttpResult polarisHttpResult = JacksonUtil.json2Bean(res.getBody(), PolarisHttpResult.class);
            return polarisHttpResult;
        } else {
            throw new Exception("Unkown Error.");
        }
    }

    public ProjectVersion getVersion(String id) throws Exception {
        HttpResult res = doGet(jiraUrl + "/rest/api/2/version/" + id);
        if (res.getCode() == 200) {
            ProjectVersion projectVersion = JacksonUtil.json2Bean(res.getBody(), ProjectVersion.class);
            return projectVersion;
        } else if (res.getCode() == 404) {
            throw new NotFoundException("Returned if the version does not exist or the currently authenticated user does not have permission to view it.");
        } else {
            throw new Exception("Unkown Error.");
        }
    }

    public List<IssueQueryResponseData> queryIssuesDetail(IssueJqlCondition issueJqlCondition) throws Exception {

        HttpResult res = doPost(jiraUrl + "/rest/polaris/1/issue/tree", JacksonUtil.bean2Json(issueJqlCondition));

        if (res.getCode() == 200) {
            JiraIssueHttpResult jiraIssueHttpResult = JacksonUtil.json2Bean(res.getBody(), JiraIssueHttpResult.class);
            if (jiraIssueHttpResult.isStatus()) {
                return jiraIssueHttpResult.getData();
            } else {
                throw new BadRequestException(jiraIssueHttpResult.getMsg());
            }
        } else {
            throw new Exception("Unkown Error.");
        }
    }

    //发布版本，传入相应的需求问题
    public PolarisHttpResult releaseVersion(VersionReleaseDTO versionReleaseDTO) throws Exception {
        HttpResult res = doPost(jiraUrl + "/rest/polaris/1/version/release", JacksonUtil.bean2Json(versionReleaseDTO));
        if (res.getCode() == 200) {
            PolarisHttpResult polarisHttpResult = JacksonUtil.json2Bean(res.getBody(), PolarisHttpResult.class);
            return polarisHttpResult;
        } else {
            throw new Exception("Unkown Error.");
        }
    }

    public UserDetail getUser(String userName) throws Exception {
        Map params = new HashMap<String, Object>();
        params.put("username", userName);
        HttpResult res = doGet(jiraUrl + "/rest/api/2/user", params);
        if (res.getCode() == 200) {
            UserDetail userDetail = JacksonUtil.json2Bean(res.getBody(), UserDetail.class);
            return userDetail;
        } else if (res.getCode() == 400) {
            throw new BadRequestException("More than one of accountId, username, and key are provided.");
        } else if (res.getCode() == 401) {
            throw new UnauthorizedException("Authentication credentials are incorrect or missing.");
        } else if (res.getCode() == 404) {
            throw new NotFoundException(String.format("The userName %s is not found or the user does not have permission to view it.", userName));
        } else {
            throw new Exception("Unkown Error.");
        }
    }

    public UserDetail createUser(UserCreateData userCreateData) throws Exception {
        HttpResult res = doPost(jiraUrl + "/rest/api/2/user", JacksonUtil.bean2Json(userCreateData));

        if (res.getCode() == 201) {
            UserDetail userDetail = JacksonUtil.json2Bean(res.getBody(), UserDetail.class);
            return userDetail;
        } else if (res.getCode() == 400) {
            throw new BadRequestException(res.getBody());
        } else if (res.getCode() == 401) {
            throw new UnauthorizedException("Authentication credentials are incorrect or missing.");
        } else if (res.getCode() == 403) {
            throw new ForbiddenException("The user does not have permission to create user.");
        } else {
            throw new Exception("Unkown Error.");
        }
    }

    public boolean deleteUser(String userName) throws Exception {
        Map params = new HashMap<String, Object>();
        params.put("username", userName);
        HttpResult res = doDelete(jiraUrl + "/rest/api/2/user", params);
        if (res.getCode() == 204) {
            return true;
        } else if (res.getCode() == 400) {
            throw new BadRequestException("More than one of accountId, username, and key are provided.");
        } else if (res.getCode() == 401) {
            throw new UnauthorizedException("Authentication credentials are incorrect or missing.");
        } else if (res.getCode() == 403) {
            throw new ForbiddenException("the calling user does not have the required permissions..");
        } else if (res.getCode() == 404) {
            throw new NotFoundException(String.format("The userName: %s is not found or accountId, username, and key are all missing.", userName));
        } else {
            throw new Exception("Unkown Error.");
        }
    }

    protected String getAuthHeader() {
        String auth = user + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(Charset.forName("ISO-8859-1")));
        String authHeader = "Basic " + new String(encodedAuth);
        return authHeader;
    }

    /**
     * 判断jira中是否有对应的lead，没有的话就创建
     *
     * @param user4A
     * @throws Exception
     */
    public void createIfNotExist(UserInfo4A user4A) throws Exception {

        UserCreateData userCreateData = null;
        try {
            userCreateData = new UserCreateData(user4A);

            UserDetail user = getUser(user4A.getUsername());

            logger.info("用户{}已存在", user4A.getUsername());
        } catch (NotFoundException e) {
            //当找不到用户时抛出异常
            logger.info("用户{}不存在", user4A.getUsername());
            createUser(userCreateData);
        }
    }

}
