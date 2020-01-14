package com.pingan.starlink.service;

import com.pingan.starlink.exception.NotFoundException;
import com.pingan.starlink.model.JiraGitprojectRelation;
import com.pingan.starlink.model.git.GitBranchCreateData;
import com.pingan.starlink.model.git.GitProjectCreateData;
import com.pingan.starlink.model.git.GitPullrequestsCreateData;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;


public class GitlabAPIClient {

    @Value("${git.url}")
    private String gitUrl;


    private GitLabApi gitLabApi;

    @Autowired
    private GitDBService gitDBService;
    @Autowired
    private GitService gitService;
    @Autowired
    private GitLabApiFactory gitLabApiFactory;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public Project getGitProject(Integer gitProjectId) throws Exception {

        Project gitProject = getGitLabApiToProjectId(gitProjectId).getProjectApi().getProject(gitProjectId);
        return gitProject;
    }

    public Project createGitProject(GitProjectCreateData gitProjectCreateData) throws Exception {

        Project gitProjectModel = new Project();

        gitProjectModel.setName(gitProjectCreateData.getGitProjectName());
        Namespace namespace = new Namespace();
        namespace.setId(gitProjectCreateData.getNamespaceId());
        gitProjectModel.setNamespace(namespace);
        gitProjectModel.setDescription(gitProjectCreateData.getDescription());

        Project createGitProject = getGitLabApiToGroupId(gitProjectCreateData.getNamespaceId()).getProjectApi().createProject(gitProjectModel);

        return createGitProject;
    }

    public void deleteGitpullrequests(Integer projectId, Integer iid) throws Exception {
        getGitLabApiToProjectId(projectId).getMergeRequestApi().deleteMergeRequest(projectId, iid);
    }

    /*public Branch getBranch(Object projectIdOrPath, String branchName) throws GitLabApiException {
        return getGitLabApi().getRepositoryApi().getBranch(projectIdOrPath, branchName);
    }*/

    public List<Branch> getBranches(Integer gitProjectId) throws Exception {
        List<Branch> branches = getGitLabApiToProjectId(gitProjectId).getRepositoryApi().getBranches(gitProjectId);
        return branches;
    }

    //protects a single project repository branch;
    public Branch createGitBranch(GitBranchCreateData gitBranchCreateData) throws Exception {

        Branch newGitBranch = getGitLabApiToProjectId(gitBranchCreateData.getGitProjectId()).getRepositoryApi().createBranch(gitBranchCreateData.getGitProjectId(),
                gitBranchCreateData.getBranchName(), gitBranchCreateData.getRef());
        return newGitBranch;
    }

    /*Unprotects a single project repository branch;
    public Branch protectBranch(Object projectIdOrPath, String branchName) throws GitLabApiException {
        return getGitLabApi().getRepositoryApi().protectBranch(projectIdOrPath, branchName);
    }*/

    /*
    public Branch unprotectBranch(Object projectIdOrPath, String branchName) throws GitLabApiException {
        return getGitLabApi().getRepositoryApi().unprotectBranch(projectIdOrPath, branchName);
    }*/

    /*get git users
    public List<User> getUsers() throws GitLabApiException {
        return getGitLabApi().getUserApi().getUsers();
    }*/

    /*get git groups
    public List<Group> getGroups() throws GitLabApiException {
        return getGitLabApi().getGroupApi().getGroups();
    }*/

    /**
     * select group by grupId
     *
     * @param gitgroupId
     * @return group
     * @throws Exception
     */
    public Group getGroup(Integer gitgroupId) throws Exception {
        return getGitLabApiToGroupId(gitgroupId).getGroupApi().getGroup(gitgroupId);
    }

    /**
     * 向Git DB中插入数据
     *
     * @param gitProjectId
     * @param projectKey
     * @return
     * @throws Exception
     */
    public boolean relateGitProject(Integer gitProjectId, String projectKey) throws Exception {

        //update DB
        JiraGitprojectRelation jiraGitprojectRelation = new JiraGitprojectRelation();
        jiraGitprojectRelation.setGitProjectId(gitProjectId);
        jiraGitprojectRelation.setJiraProjectKey(projectKey);
        gitDBService.updateJiraGitprojectRealtaion(jiraGitprojectRelation);
        return true;
    }

    /**
     * 遍历 relateProjects 中的gitProject
     *
     * @param relateProjects
     * @param projectKey
     * @return
     * @throws Exception
     */
    public boolean relateGitProject(List<Integer> relateProjects, String projectKey) throws Exception {
        for (Integer gitProject : relateProjects) {
            relateGitProject(gitProject, projectKey);
        }
        return true;
    }

    /**
     * 判断codeSpace 是否为空
     *
     * @param codeSpace
     * @param projectKey
     * @return
     * @throws Exception
     */
//    public boolean createAndRelate(ProjectCreateDataDTO.CodeSpace codeSpace, String projectKey) throws Exception {
//        String projectName = codeSpace.getProjectName();
//        if (projectName != null && !projectName.isEmpty()) {
//            this.createGitProject(codeSpace, projectKey);
//        }
//        return this.relateGitProject(codeSpace.getRelateGitProjectId(), projectKey);
//    }

    /**
     * 添加http Git项目
     *
     * @param codeSpace
     * @return
     * @throws Exception
     */
//    private Project createGitProject(ProjectCreateDataDTO.CodeSpace codeSpace, String projectKey) throws Exception {
//        //        logger.info("createGitProject code_space:{}", JSON.toJSONString(code_space));
//        String projectName = codeSpace.getProjectName();
//        Integer groupId = codeSpace.getGroupId();
//        String description = codeSpace.getDescription();
//        //   Integer namespaceId = Integer.parseInt(groupId);
//
//
//        GitProjectCreateDataDTO gitProjectCreateDataDTO = new GitProjectCreateDataDTO();
//
//        gitProjectCreateDataDTO.setGitProjectName(projectName);
//        gitProjectCreateDataDTO.setNamespaceId(groupId);
//        gitProjectCreateDataDTO.setDescription(description);
//        gitProjectCreateDataDTO.setVisibility(ConstantUtil.GIT_PROJECT_VISIBILITY_DEFAULT);
//        gitProjectCreateDataDTO.setInitializeWithReadme(ConstantUtil.GIT_PROJECT_initializeWithReadme_DEFAULT);
//        gitProjectCreateDataDTO.setJiraProjectKey(projectKey);
//
//        return this.gitService.createGitProject(gitProjectCreateDataDTO);
//    }

    /*    *//**
     * 修改Http Git项目
     * @param project
     * @return
     * @throws GitLabApiException
     */
    public Project updateProject(Project project) throws Exception {

        Project gitProject = getGitLabApiToProjectId(project.getId()).getProjectApi().updateProject(project);

        return gitProject;

    }

    /**
     * 删除项目Http 根据项目的id 或者path
     *
     * @param projectId
     * @throws GitLabApiException
     */
    public void deleteProject(Integer projectId)  {

        try {
            getGitLabApiToProjectId(projectId).getProjectApi().deleteProject(projectId);
        } catch (Exception e) {
            logger.info("delete gitProject error ,errorMessage: {} " , e.getMessage());
        }
    }

    /**
     * 删除分支Http
     *
     * @param projectId
     * @param branchName
     * @throws GitLabApiException
     */
    public void deleteBranch(Integer projectId, String branchName) throws Exception {

        getGitLabApiToProjectId(projectId).getRepositoryApi().deleteBranch(projectId, branchName);


    }

    /**
     * 查询HTTP 单个项目 根据projectId
     *
     * @param projectId
     * @return
     * @throws GitLabApiException
     */
    public Project getProject(Integer projectId) throws Exception {

        Project project = getGitLabApiToProjectId(projectId).getProjectApi().getProject(projectId);

        return project;
    }

    /**
     * 查询HTTP 单个分支
     *
     * @param projectId
     * @param branchName
     * @return
     * @throws GitLabApiException
     */
    public Branch getBranch(Integer projectId, String branchName) throws Exception {

        Branch branch = getGitLabApiToProjectId(projectId).getRepositoryApi().getBranch(projectId, branchName);

        return branch;
    }

    /**
     * 创建pr
     *
     * @param
     * @return
     * @throws GitLabApiException
     */
    public MergeRequest createGitPullrequests(GitPullrequestsCreateData gitPullrequestsCreateData) throws Exception {

        MergeRequest mergeRequest = new MergeRequest();

        mergeRequest.setProjectId(gitPullrequestsCreateData.getProjectId());
        mergeRequest.setSourceBranch(gitPullrequestsCreateData.getSourceBranch());
        mergeRequest.setTargetBranch(gitPullrequestsCreateData.getTargetBranch());
        mergeRequest.setTitle(gitPullrequestsCreateData.getTitle());

        MergeRequest newMegeRequest = getGitLabApiToProjectId(gitPullrequestsCreateData.getProjectId()).getMergeRequestApi().createMergeRequest(
                gitPullrequestsCreateData.getProjectId(),
                gitPullrequestsCreateData.getSourceBranch(),
                gitPullrequestsCreateData.getTargetBranch(),
                gitPullrequestsCreateData.getTitle(),
                gitPullrequestsCreateData.getDescription(),
                gitPullrequestsCreateData.getAssigneeId(),
                gitPullrequestsCreateData.getTargetProjectId(),
                gitPullrequestsCreateData.getLabels(),
                gitPullrequestsCreateData.getMilestoneId(),
                gitPullrequestsCreateData.getRemoveSourceBranch(),
                gitPullrequestsCreateData.getSquash()
        );

        return newMegeRequest;
    }

    /**
     * 根据projectid查询pr
     *
     * @param
     * @return
     * @throws GitLabApiException
     */
    public List<MergeRequest> getGitpullrequest(Integer gitprojectId) throws Exception {
        List<MergeRequest> mergeRequestList = getGitLabApiToProjectId(gitprojectId).getMergeRequestApi().getMergeRequests(gitprojectId);
        return mergeRequestList;
    }

    public MergeRequest getMergequest(Integer gitProjectId ,Integer gitPrIid) throws Exception {
        MergeRequest mergeRequest = getGitLabApiToProjectId(gitProjectId).getMergeRequestApi().getMergeRequest(gitProjectId,gitPrIid);
        return mergeRequest;
    }

    public Project getProjectByPath(String projectPath,Integer projectId) throws Exception {

        Project project = getGitLabApiToProjectId(projectId).getProjectApi().getProject(projectPath);

        return project;
    }

    public String getGitUrl() {
        return gitUrl;
    }

    /**
     * 根据gitProjectId获取GitLabApi
     * @return
     */
    public GitLabApi getGitLabApiToProjectId(Integer gitProjectId) throws NotFoundException {
        return gitLabApiFactory.getGitLabApiByGitProjectId(gitProjectId);
    }

    /**
     * 根据gitGroupId获取GitLabApi
     * @return
     */
    public GitLabApi getGitLabApiToGroupId(Integer gitGroupId) throws NotFoundException {
        return gitLabApiFactory.getGitLabApiByGroupId(gitGroupId);
    }

    public Commit getCommit(Integer gitProjectId, String sha) throws Exception{
        Commit commit = getGitLabApiToProjectId(gitProjectId).getCommitsApi().getCommit(gitProjectId,sha);
        return commit;
    }

    //Initialize the new git project
    public Commit initialize(Integer id,Integer GroupId) throws Exception{

        List<CommitAction> actions = new ArrayList<>();

        CommitAction commitAction = new CommitAction();

        commitAction.setAction(CommitAction.Action.valueOf("CREATE"));
        commitAction.setFilePath("README.md");
        commitAction.setContent("hello world!!");

        actions.add(commitAction);

        Commit commit = gitLabApiFactory.getGitLabApiByGroupId(GroupId).getCommitsApi().createCommit(id, "master",
                "Initialize",null,null,null,actions);

        return commit;
    }
}
















