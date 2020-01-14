package com.pingan.starlink.service;


import com.pingan.starlink.dto.GitProjectCreateDataDTO;
import com.pingan.starlink.dto.JiraGitprojectRelationDTO;
import com.pingan.starlink.enums.StatusEnum;
import com.pingan.starlink.exception.InternalServerErrorException;
import com.pingan.starlink.exception.NotFoundException;
import com.pingan.starlink.mapper.*;
import com.pingan.starlink.model.*;
import com.pingan.starlink.model.git.GitPrWebhookBody;
import com.pingan.starlink.util.ConstantUtil;
import com.pingan.starlink.util.EireneUtil;
import com.pingan.starlink.util.JacksonUtil;
import org.gitlab4j.api.models.Branch;
import org.gitlab4j.api.models.MergeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GitDBService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GitProjectMapper gitProjectMapper;
    @Autowired
    private GitBranchMapper gitBranchMapper;
    @Autowired
    private JiraGitprojectRelationMapper jiraGitprojectRelationMapper;
    @Autowired
    private GitPullRequestsMapper gitPullRequestsMapper;
    @Autowired
    private GitGroupMapper gitGroupMapper;
    @Autowired
    private GitgroupDepartmentRelationMapper gitgroupDepartmentRelationMapper;
    @Autowired
    private JiraProjectDBService jiraProjectDBService;

    private static String MASTER = "master";

    private static String  MASTER_BRANCH = "master_branch";

    private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public List<GitProject> getGitProjects() throws IOException {
        logger.info("Select gitProject All");
        List<GitProject> selectAllGitProject = gitProjectMapper.selectAll();
        logger.info("Result is：%s,", JacksonUtil.bean2Json(selectAllGitProject));
        return selectAllGitProject;
    }



    public GitProject getGitProjectByUuid(String uuid) throws IOException {
        logger.info(String.format("select by GitProjectName: %s.", uuid));
        return gitProjectMapper.getGitProjectByUuid(uuid);

    }


    public GitProject getGitProjectById(Integer gitProjectId) throws IOException {
        logger.info(String.format("select gitProjectById: %d", gitProjectId));
        GitProject gitProject = gitProjectMapper.selectByProjectId(gitProjectId);

        logger.info(String.format("result is: %s", JacksonUtil.bean2Json(gitProject)));
        return gitProject;
    }


    public List<GitProject> getGitProjectsBypIdAndgId(List<Integer> gitProjectIdList, List<Integer> gitGroupIdList) throws IOException {
        logger.info("Select gitProject by projectIds and groupIds");
        List<GitProject> allgitProjects = gitProjectMapper.selectByProjectIdAndGroupId(gitProjectIdList, gitGroupIdList);
        logger.info("Result is：%s,", JacksonUtil.bean2Json(allgitProjects));
        return allgitProjects;
    }

    public int deleteGitProject(String uuid) {
        logger.info(String.format("delete gitProject id: %s", uuid));
        int res = gitProjectMapper.deleteByPrimaryKey(uuid);
        logger.info(String.format("result is: %d", res));
        return res;
    }

    public int insertProject(GitProject gitProject) throws IOException {
        logger.info(String.format("Insert gitProject: %s", JacksonUtil.bean2Json(gitProject)));
        int insert = gitProjectMapper.insert(gitProject);
        logger.info(String.format("Result is: %d", insert));
        return insert;
    }

    public GitProject updateGitProject(GitProject gitProject) throws Exception {

        if (gitProject.getGitProjectName() != null) {
            logger.info(String.format("update gitProject: %s", JacksonUtil.bean2Json(gitProject)));
            gitProjectMapper.updateByPrimaryKey(gitProject);
        } else {
            logger.info(String.format("insert gitProject: %s", JacksonUtil.bean2Json(gitProject)));
            gitProjectMapper.insert(gitProject);
        }
        gitProject = gitProjectMapper.selectByPrimaryKey(gitProject.getGitProjectName());
        logger.info(String.format("save notic: %s", JacksonUtil.bean2Json(gitProject)));

        return gitProject;
    }

    public int insertJiraGitprojectRealtaion(JiraGitprojectRelation jiraGitprojectRelation) throws Exception {
        String projectKey1 = jiraGitprojectRelation.getJiraProjectKey();
        Project project = jiraProjectDBService.selectProject(projectKey1);
        if (project.getProjectStatus().equals(ConstantUtil.FAILED) || project.getProjectStatus().equals(ConstantUtil.CREATING)){
            throw new NotFoundException("此状态的项目不能创建版本");
        }
        logger.info(String.format("Insert jiraGitprojectRelation: , %s", jiraGitprojectRelation.toString()));
        int insert = jiraGitprojectRelationMapper.insert(jiraGitprojectRelation);
        logger.info(String.format("Result is: %d", insert));
        return insert;
    }

    public JiraGitprojectRelation updateJiraGitprojectRealtaion(JiraGitprojectRelation jiraGitprojectRelation) throws Exception {

        if (jiraGitprojectRelation.getGitProjectId() != null) {
            logger.info(String.format("update gitProject: %s", JacksonUtil.bean2Json(jiraGitprojectRelation)));
            jiraGitprojectRelationMapper.updateByPrimaryKey(jiraGitprojectRelation);
        } else {
            logger.info(String.format("insert gitProject: %s", JacksonUtil.bean2Json(jiraGitprojectRelation)));
            jiraGitprojectRelationMapper.insert(jiraGitprojectRelation);
        }
        jiraGitprojectRelation = jiraGitprojectRelationMapper.selectByPrimaryKey(jiraGitprojectRelation);
        logger.info(String.format("save notic: %s", JacksonUtil.bean2Json(jiraGitprojectRelation)));

        return jiraGitprojectRelation;
    }

    public List<GitBranch> getGitBranch(Integer gitProjectId , String branchStatus) throws IOException {
        logger.info("Select gitBranch by gitProjectId: {}",gitProjectId);
        List<GitBranch> selectAllGitBranch = gitBranchMapper.selectBranchByProjectId(gitProjectId ,branchStatus);
        logger.info("Result is：{}", JacksonUtil.bean2Json(selectAllGitBranch));
        return selectAllGitBranch;
    }

    public int insertGitBranch(GitBranch gitBranch) throws IOException {
        logger.info("Insert GitBranch: {}", JacksonUtil.bean2Json(gitBranch));
        int insert = gitBranchMapper.insertGitBranch(gitBranch);
        logger.info("Result is: {}", insert);
        return insert;
    }


    public int deleteByGitBranch(String uuid) {
        logger.info("delete gitBranchName: {}", uuid);
        int res = gitBranchMapper.deleteByPrimaryKey(uuid);
        logger.info("result is: {}", res);
        return res;
    }

    public int updateProject(GitProject gitProject) throws IOException {
        logger.info(String.format("updateProject: %s", JacksonUtil.bean2Json(gitProject)));
        int res = gitProjectMapper.updateGitproject(gitProject);
        logger.info(String.format("result is: %d", res));
        return res;
    }

    public int deleteProjectByUuid(String uuid) {
        logger.info(String.format("delete gitBranch by uuid: %s", uuid));
        int res = gitProjectMapper.deleteByPrimaryKey(uuid);
        logger.info(String.format("result is: %d", res));
        return res;
    }


    public int insertGitPullrequests(GitPullRequests gitPullRequests) throws Exception {
        String projectKey = gitPullRequests.getJiraProjectKey();
        Project project = jiraProjectDBService.selectProject(projectKey);
        if (project == null){
            logger.info(" 'create insertGitPullrequests' not found jira project ");
            throw new NotFoundException("找不到jira的项目");
        }
        if (project.getProjectStatus().equals(ConstantUtil.FAILED) || project.getProjectStatus().equals(ConstantUtil.CREATING)){
            logger.info(" this status don't create version ");
            throw new NotFoundException("此状态的项目不能创建版本");
        }
        logger.info(String.format("Insert gitPullRequests: %s. ", JacksonUtil.bean2Json(gitPullRequests)));
        int insert = gitPullRequestsMapper.insert(gitPullRequests);
        logger.info(String.format("Rlesult is: %d. ", insert));
        return insert;
    }

    public int deleteGitpullrequests(String uuid) {
        logger.info(String.format("delete Gitpullrequests by uuid: %s.", uuid));
        int res = gitProjectMapper.deleteByPrimaryKey(uuid);
        logger.info(String.format("result is: %d. ", res));
        return res;
    }

    public List<GitProject> selectCodeSpaceByProjectKey(String projectKey) throws IOException {
        logger.info("select codeSpace by projectKey: {}. ",projectKey);
        List<GitProject> gitProjects = gitProjectMapper.selectCodeSpaceByProjectKey(projectKey);
        logger.info("result is: {}. ", JacksonUtil.bean2Json(gitProjects));
        return gitProjects;
    }


    public List<GitProject> selectProjectByGroupId(Integer groupId) throws IOException {
        logger.info("select gitProject by groupId: {}",groupId);
        List<GitProject> gitProjects = gitProjectMapper.selectProjectByGroupId(groupId);
        logger.info("result is: {}", JacksonUtil.bean2Json(gitProjects));
        return gitProjects;
    }

    public int updateProjectByUuid(String uuid,GitProjectCreateDataDTO gitProjectCreateDataDTO) {
        GitProject gitProjectDB = gitProjectCreateDataDTO.getGitProjectDB();
        gitProjectDB.setUuid(uuid);
        return gitProjectMapper.updateGitprojectByUUid(gitProjectDB);
    }

    public GitBranch selectBranchsByUuid(String uuid) throws IOException {
        logger.info("select gitBranch by uuid: {}",uuid);
        GitBranch gitBranch = this.gitBranchMapper.selectBranchsByUuid(uuid);
        logger.info("result is: {}", JacksonUtil.bean2Json(gitBranch));
        return gitBranch;
    }

    public int updateBranchsByUuid(GitBranch gitBranchDB) throws IOException {
        logger.info("update gitBranch {}", JacksonUtil.bean2Json(gitBranchDB));
        int res = gitBranchMapper.updateBranchsByUuid(gitBranchDB);
        return res;
    }

    public List<GitPullRequests> getGitpullrequests(Integer gitProjectId) throws IOException {
        logger.info("select gitPullRequests by gitprId: {}. ",gitProjectId);
        List<GitPullRequests> gitPullRequestsList= gitPullRequestsMapper.selectGitprbyId(gitProjectId);
        logger.info("result is: {}. ", JacksonUtil.bean2Json(gitPullRequestsList));
        return gitPullRequestsList;
    }

    public int updateGitpullrequests(GitPullRequests gitPullRequests) throws IOException {
        logger.info(String.format("update gitpr : %s. ", JacksonUtil.bean2Json(gitPullRequests)));
        int updateByPrimaryKey = gitPullRequestsMapper.updateGitpullrequests(gitPullRequests);
        logger.info(String.format("result update gitpr : %d. ", updateByPrimaryKey));
        return updateByPrimaryKey;
    }

    public GitPullRequests selectGitpullrequest(String uuid) throws IOException {
        logger.info(String.format("Select gitpullrequests : %s. ", uuid));
        GitPullRequests gitPullRequests= gitPullRequestsMapper.selectByPrimaryKey(uuid);
        logger.info(String.format("Result gitpullrequests : %s. ", JacksonUtil.bean2Json(gitPullRequests)));
        return gitPullRequests;
    }


    public int insertGitgroup(GitGroup gitGroup) throws IOException {
        logger.info(String.format("Insert gitgroup: , %s.", JacksonUtil.bean2Json(gitGroup))); //%s 字符串类型 %d 整数类型 十进制
        int insert = gitGroupMapper.insert(gitGroup);
        logger.info(String.format("Result is: %d.", insert));
        return  insert;
    }

    public int deleteGitgroup(String uuid) {
        logger.info(String.format("Delete gitgroup: , %s.", uuid)); //%s 字符串类型 %d 整数类型 十进制
        int deleteByPrimaryKey = gitGroupMapper.deleteByPrimaryKey(uuid);
        logger.info(String.format("Result is: %d.", deleteByPrimaryKey));
        return deleteByPrimaryKey;
    }

    public GitGroup updateGitGroup(String uuid, GitGroup gitGroup) throws Exception {
        GitGroup gitGroup1 = null;

        if (gitGroup.getUuid() == null) {
            gitGroup.setUuid(uuid);
        }
        if (uuid != gitGroup.getUuid() ){
            throw new InternalServerErrorException("两个uuid不一致");
        }

        logger.info(String.format("Update gitgroup: , %s.", JacksonUtil.bean2Json(gitGroup))); //%s 字符串类型 %d 整数类型 十进制
        int updateGroup = gitGroupMapper.updateByPrimaryKey(gitGroup);
        logger.info(String.format("Result is: %d.", updateGroup));

        if (updateGroup > 0) {
            gitGroup1 = selectByGitgroupUuid(gitGroup.getUuid());
        }
        return gitGroup1;
    }

    public GitGroup selectByGitgroupUuid(String uuid) throws IOException {
        logger.info(String.format("select gitgroup: %s.", uuid));
        GitGroup gitGroup = gitGroupMapper.selectByPrimaryKey(uuid);
        logger.info(String.format("result is: %s. ", JacksonUtil.bean2Json(gitGroup)));
        return gitGroup;
    }

    public List<GitGroup> selectAllGitgroup() throws IOException {
        logger.info(String.format("select all gitgroup"));
        List<GitGroup> gitGroups = gitGroupMapper.selectAll();
        logger.info(String.format("result is: %s. ", JacksonUtil.bean2Json(gitGroups)));
        return gitGroups;
    }

    public int insertGitgroupDev(GitgroupDepartmentRelation gitgroupDepartmentRelation) throws IOException {

        gitgroupDepartmentRelation.setUuid(EireneUtil.randomUUID());
        logger.info(String.format("Insert gitgroupDepartmentRelation : %s.", JacksonUtil.bean2Json(gitgroupDepartmentRelation)));
        int insert = gitgroupDepartmentRelationMapper.insert(gitgroupDepartmentRelation);
        logger.info(String.format("Result version : %d.", insert));
        return insert;
    }

    public int deleteGitgroupDev(String uuid) {
        logger.info(String.format("delete gitgroupDepartmentRelation : %s. ", uuid));
        int res = gitgroupDepartmentRelationMapper.deleteByPrimaryKey(uuid);
        logger.info(String.format("Result is  : %d. ", res));
        return res;
    }

    public GitgroupDepartmentRelation updateGitgroupDev(String uuid, GitgroupDepartmentRelation gitgroupDepartmentRelation) throws Exception{
        GitgroupDepartmentRelation gitgroupDepartmentRelation1 = null;

        if (gitgroupDepartmentRelation.getUuid() == null) {
            gitgroupDepartmentRelation.setUuid(uuid);
        }
        if (uuid != gitgroupDepartmentRelation.getUuid()) {
            throw new InternalServerErrorException("两个uuid不一致");
        }

        logger.info(String.format("Update gitgroup department relation: , %s.", JacksonUtil.bean2Json(gitgroupDepartmentRelation))); //%s 字符串类型 %d 整数类型 十进制
        int updateGitgroupDev = gitgroupDepartmentRelationMapper.updateByPrimaryKeySelective(gitgroupDepartmentRelation);
        logger.info(String.format("Result is: %d.",updateGitgroupDev));

        if (updateGitgroupDev > 0) {
            gitgroupDepartmentRelation1 = selectGitgroupDevByUuid(gitgroupDepartmentRelation.getUuid());
        }

        return gitgroupDepartmentRelation1;
    }

    private GitgroupDepartmentRelation selectGitgroupDevByUuid(String uuid) throws IOException {

        logger.info(String.format("Select GitgroupDepartmentRelation: , %s.", uuid)); //%s 字符串类型 %d 整数类型 十进制
        GitgroupDepartmentRelation gitgroupDepartmentRelation = gitgroupDepartmentRelationMapper.selectByPrimaryKey(uuid);
        logger.info(String.format("Result is: %s.", JacksonUtil.bean2Json(gitgroupDepartmentRelation)));

        return gitgroupDepartmentRelation;
    }


    public List<GitgroupDepartmentRelation> selectAllGitgroupAndDev(String departmentName) throws IOException {

        logger.info(String.format("Select GitgroupDepartmentRelation: , %s.", departmentName)); //%s 字符串类型 %d 整数类型 十进制

        List<GitgroupDepartmentRelation> GitgroupDepartmentRelation = gitgroupDepartmentRelationMapper.selectAllByDept(departmentName);
        logger.info(String.format("Result is: %s.", JacksonUtil.bean2Json(GitgroupDepartmentRelation)));

        return GitgroupDepartmentRelation;
    }


    public List<JiraGitprojectRelation> getJiraGitprojectRelation(String projectKey) throws IOException {
        logger.info("projectKey is {}",projectKey);
        List<JiraGitprojectRelation> jiraGitprojectRelations =  this.jiraGitprojectRelationMapper.selectRelationByProjectKey(projectKey);
        logger.info("Result is {}",JacksonUtil.bean2Json(jiraGitprojectRelations));
        return jiraGitprojectRelations;
    }

    public JiraGitprojectRelation getJiraGitprojectRelationByUuid(String uuid) throws IOException {
        logger.info("uuid is {}",uuid);
        JiraGitprojectRelation jiraGitprojectRelation = this.jiraGitprojectRelationMapper.selectJiraGitprojectRelationByUuid(uuid);
        logger.info("Result is {}",JacksonUtil.bean2Json(jiraGitprojectRelation));
        return jiraGitprojectRelation;
    }

    public int modifyJiraGitprojectRelationByUuid(String uuid, JiraGitprojectRelationDTO jiraGitprojectRelationDTO) throws IOException {
        logger.info("request data : {}",JacksonUtil.bean2Json(jiraGitprojectRelationDTO));
        JiraGitprojectRelation jiraGitprojectRelation = jiraGitprojectRelationDTO.getJiraGitprojectRelation();
        jiraGitprojectRelation.setUuid(uuid);
        int num = this.jiraGitprojectRelationMapper.updateByPrimaryKey(jiraGitprojectRelation);
        return num;
    }

    public int deleteJiraGitprojectRelationByUuid(String uuid) {
        return this.jiraGitprojectRelationMapper.deleteByPrimaryKey(uuid);
    }

    public String getTokenByGroupId(Integer groupId) {
        logger.info("groupId is {}",groupId);
        String token = this.gitGroupMapper.getTokenByGroupId(groupId);
        logger.info("token is {}",token);
        return token;
    }

    public Integer getGroupIdGitProjectId(Integer projectId) {
        logger.info("projectId is {}",projectId);
        Integer groupId = this.gitProjectMapper.getGroupIdGitProjectId(projectId);
        logger.info("groupId is {}",groupId);
        return groupId;
    }

    public void updateBranchsStatusByName(String name , String branchStatus,Integer gitProjectId) {
        logger.info("name is {}",name);
        this.gitBranchMapper.updateBranchsStatusByName(name,branchStatus,gitProjectId);

    }

    /**
     * 比较远端分支，修改DB状态
     * @param gitProjectId
     * @param gitBranchs
     * @param branchList
     */
    @Transactional
    public void comparatorStatus(Integer gitProjectId ,List<GitBranch> gitBranchs, List<Branch> branchList) {

        // 获取git远端中 projectId下所有的分支名
        List<String> branchNames =  new ArrayList<>();
        branchList.forEach(item -> branchNames.add(item.getName()));
        //获取git远端中 projectId下所有的分支名
        List<String> branchDBNames =  new ArrayList<>();
        gitBranchs.forEach(item -> branchDBNames.add(item.getGitBranchName()));
        //获取不存在于远端的分支
        branchDBNames.removeAll(branchNames);
        //修改数据库
        branchDBNames.forEach(str ->
                updateBranchsStatusByName(str, StatusEnum.CLOSE_STATUS.getStatus(),gitProjectId)
        );

    }

    @Transactional
    public void insertProjectAndBranch (GitProject gitProjectDB, JiraGitprojectRelation
            jiraGitprojectRelation) throws Exception {
        insertProject(gitProjectDB);
        //创建gitProject时，向git_branch表中插入master分支
        GitBranch branch = GitBranch.builder().uuid(EireneUtil.randomUUID()).gitBranchName(MASTER).gitProjectId(gitProjectDB.getGitProjectId())
                .description(MASTER_BRANCH).createdAt(gitProjectDB.getGitProjectCreatedAt()).release(false).branchStatus(StatusEnum.OPEN_STATUS.getStatus()).build();
        insertGitBranch(branch);
        insertJiraGitprojectRealtaion(jiraGitprojectRelation);
    }

    /**
     * 修改pr状态
     *
     */
    @Transactional
    public void comparaPrStatus  (List < GitPullRequests > gitPullRequests, List < MergeRequest > mergeRequests) throws IOException {
        for (GitPullRequests gitPullRequest : gitPullRequests) {
            for (MergeRequest mergeRequest : mergeRequests) {
                if (mergeRequest.getId().equals(gitPullRequest.getGitPrId())) {
                    if (!(mergeRequest.getState().equals(gitPullRequest.getGitPrStatus()))) {
                        this.updateGitprStatusByUuid(gitPullRequest.getUuid(), mergeRequest.getState());
                        break;
                    }

                }
            }
        }
    }

    private void updateGitprStatusByUuid (String uuid, String state){
        logger.info("update gitpr status : {}", uuid, state);
        gitPullRequestsMapper.updateGitprStatusByUuid(uuid, state);

    }

    public List<GitPullRequests> getGitprByStatus (Integer gitProjectId, String status) throws IOException {
        logger.info("select gitpr by ststus : {},{}", gitProjectId, status);
        List<GitPullRequests> gitPullRequestsList = gitPullRequestsMapper.selectGitpr(gitProjectId, status);
        logger.info("result is: {}", JacksonUtil.bean2Json(gitPullRequestsList));
        return gitPullRequestsList;
    }

    public List<GitProject> getGitProjectsByGroup(Integer groupId) throws IOException{
        logger.info("groupId is  : {}", groupId);
        List<GitProject> projects = gitProjectMapper.selectProjectByGroupId(groupId);
        logger.info("result is: {}", JacksonUtil.bean2Json(projects));
        return projects;
    }


    public void updateBranchCommitAndTime(Integer projectId, String branchName, String commitName, Date commitDate) {
        this.gitBranchMapper.updateBranchCommitAndTime(projectId, branchName, commitName, commitDate);
        logger.info("updateBranchCommitAndTime ==>  SUCCESS");

    }

    public void updateGitPrStatus(GitPrWebhookBody prEvents) throws Exception{

        String eventType = prEvents.getEventType();

        if (eventType != null && eventType.equals("merge_request")){

            Integer gitPrId = prEvents.getObjectAttributes().getPrId();

            if (gitPrId == null) {
                return;
            }

            Integer gitProjectId = prEvents.getProject().getGitProjectId();
            logger.info("gitPrId is :{},git project id is : {}", gitPrId,gitProjectId);

            //get pr from db from gitPrId
            GitPullRequests gitPullRequests = this.getGitPrByPrid(gitPrId);

            if (gitPullRequests == null) {
                logger.info("git pr in db is: null");
                return;
            }

            String serverStatus = prEvents.getObjectAttributes().getState();
            String dbStatus =  gitPullRequests.getGitPrStatus();
            logger.info("git pr status in db is: {},git pr status in gitlab server is: {}",dbStatus,serverStatus);

            if (serverStatus == null || dbStatus == null) {
                logger.info("git pr status in db or in gitlab server is null!");
                return ;
            }
            if (!dbStatus.equals(serverStatus)) {

                this.updateGitprStatusByUuid(gitPullRequests.getUuid(),serverStatus);

                if (serverStatus.equals("merged")) {

                    GitPullRequests gitPrModel = new GitPullRequests();

                    String mgBranchName = prEvents.getObjectAttributes().getTargetBranchName();
                    gitPrModel.setMgBranchName(mgBranchName);
                    logger.info("MgBranchName is :{}", mgBranchName);

                    gitPrModel.setGitPrId(gitPrId);

                    String date = prEvents.getObjectAttributes().getLastCommit().getTimestamp();
                    String newDate = date.replaceAll("[TZ]"," ");
                    Date MgCommitat =  format.parse(newDate);

                    gitPrModel.setMgCommitat(MgCommitat);
                    logger.info("MgCommitat is :{}", MgCommitat);


                    String mgCommitby = prEvents.getUser().getUsername();
                    gitPrModel.setMgCommitby(mgCommitby);
                    logger.info("MgCommitby is :{}", mgCommitby);

                    String mgCommitId = prEvents.getObjectAttributes().getMergeCommitSha();
                    gitPrModel.setMgCommitId(mgCommitId);
                    logger.info("mgCommitId is :{}", mgCommitId);

                    String mgCommitInfo = prEvents.getObjectAttributes().getLastCommit().getMessage();
                    gitPrModel.setMgCommitInfo(mgCommitInfo);
                    logger.info("mgCommitInfo is :{}", mgCommitInfo);

                    this.updateGitpullrequests(gitPrModel);
                }
            }
        }
    }

    private GitPullRequests getGitPrByPrid(Integer gitPrId) throws IOException {
        logger.info("gitPrId is  : {}", gitPrId);
        GitPullRequests gitPullRequests= gitPullRequestsMapper.getGitPrByPrid(gitPrId);
        logger.info("result is: {}", JacksonUtil.bean2Json(gitPullRequests));
        return gitPullRequests;

    }
}