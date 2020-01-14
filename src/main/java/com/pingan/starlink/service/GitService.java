package com.pingan.starlink.service;

import com.pingan.starlink.dto.GitBranchCreateDataDTO;
import com.pingan.starlink.dto.GitProjectCreateDataDTO;
import com.pingan.starlink.dto.GitpullreqeustsDTO;
import com.pingan.starlink.dto.JiraGitprojectRelationDTO;
import com.pingan.starlink.enums.StatusEnum;
import com.pingan.starlink.exception.InternalServerErrorException;
import com.pingan.starlink.exception.NotFoundException;
import com.pingan.starlink.model.GitBranch;
import com.pingan.starlink.model.GitProject;
import com.pingan.starlink.model.GitPullRequests;
import com.pingan.starlink.model.JiraGitprojectRelation;
import com.pingan.starlink.model.git.GitBranchCreateData;
import com.pingan.starlink.model.git.GitProjectCreateData;
import com.pingan.starlink.model.git.GitPullrequestsCreateData;
import com.pingan.starlink.util.ConstantUtil;
import com.pingan.starlink.util.EireneUtil;
import com.pingan.starlink.util.JacksonUtil;
import com.pingan.starlink.vo.jira.GitBranchVO;
import com.pingan.starlink.vo.jira.GitProjectVO;
import com.pingan.starlink.vo.jira.GitpullrequestsVO;
import com.pingan.starlink.vo.jira.JiraGitprojectRelationVo;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.*;
import org.gitlab4j.api.systemhooks.PushSystemHookEvent;
import org.gitlab4j.api.webhook.EventCommit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GitService {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private GitlabAPIClient gitlabAPIClient;

    @Autowired
    private GitDBService gitDBService;

    @Autowired
    private GitgroupDepartmenRelationDBService gitgroupDepartmenRelationDBService;

    @Autowired
    private JiraProjectDBService jiraProjectDBService;

    private static String GIT = ".git";

    private static String RELEASE = "release";

    /**
     * 查询单个项目，如果detail == true查询DB，否则查询DB+HTTP
     *
     * @param uuid
     * @param detail
     * @return
     * @throws Exception
     */
    public GitProjectVO getGitProject(String uuid, Boolean detail) throws Exception {
        //如果detail == false 查询数据库
        GitProjectVO gitProjectVO = null;
        try {
            GitProject gitProject = gitDBService.getGitProjectByUuid(uuid);
            gitProjectVO = new GitProjectVO(gitProject);
        } catch (Exception e) {
            logger.info(String.format("no data"));
        }

        //如果detail == true 查询数据库+Client
        if (detail) {
            gitProjectVO.setProject(gitlabAPIClient.getGitProject(gitProjectVO.getGitProjectId()));
        }
        return gitProjectVO;
    }


    /**
     * 查询多个项目，如果detail == true查询DB，否则查询DB+HTTP
     *
     * @param gitProjectIdList
     * @param detail
     * @return
     */
    public List<GitProjectVO> getGitProjects(List<Integer> gitProjectIdList, List<Integer> gitGroupIdList, Boolean detail) throws Exception {

        List<GitProjectVO> gitProjectVOs = new ArrayList<>();
        GitProjectVO gitProjectVO = null;

        List<GitProject> gitProjects = gitDBService.getGitProjectsBypIdAndgId(gitProjectIdList, gitGroupIdList);

        if (gitProjects == null || gitProjects.size() < 1) {
            throw new NotFoundException("not found gitProject in db");
        }

        getGitProjectsFromGit(detail, gitProjectVOs, gitProjects);

        return gitProjectVOs;
    }

    private void getGitProjectsFromGit(Boolean detail, List<GitProjectVO> gitProjectVOs, List<GitProject> gitProjects) throws Exception {
        GitProjectVO gitProjectVO;
        for (GitProject gitProject : gitProjects) {
            gitProjectVO = new GitProjectVO(gitProject);
            gitProjectVOs.add(gitProjectVO);
        }

        if (detail) {
            for (GitProjectVO gitProjectVO1 : gitProjectVOs) {
                Project gitProject = gitlabAPIClient.getGitProject(gitProjectVO1.getGitProjectId());
                gitProjectVO1.setProject(gitProject);
            }
        }
    }

    private List<Integer> getGitProject(List<GitProject> gitProjects) {
        ArrayList<Integer> gitProjectIds = new ArrayList<>();
        for (GitProject gitProject : gitProjects) {
            gitProjectIds.add(gitProject.getGitProjectId());
        }
        return gitProjectIds;
    }

    /**
     * 添加git项目
     *
     * @param
     * @return
     * @throws Exception
     */

    public Project createGitProject(GitProjectCreateDataDTO gitProjectCreateDataDTO) throws Exception {

        GitProjectCreateData gitProjectCreateData = gitProjectCreateDataDTO.getGitProjectCreateData();
        Project gitProject = null;
        try {
            //向gitlab上添加项目
          gitProject = gitlabAPIClient.createGitProject(gitProjectCreateData);
        } catch (Exception e) {
            logger.info("error---->{}",e.getMessage());
            throw new InternalServerErrorException(String.format("GitProject create error message: %s", e.getMessage()));
        }
        if (gitProject.getId() != null){
            gitlabAPIClient.initialize(gitProject.getId(),gitProjectCreateDataDTO.getGitgroupId());
        }

        String uuid = EireneUtil.randomUUID();
        gitProjectCreateDataDTO.setUuid(uuid);
        gitProjectCreateDataDTO.setGitProjectUrl(gitProject.getHttpUrlToRepo());
        gitProjectCreateDataDTO.setGitProjectId(gitProject.getId());
        gitProjectCreateDataDTO.setGitProjectCreatedAt(EireneUtil.addDate(gitProject.getCreatedAt()));

        GitProject gitProjectDB = gitProjectCreateDataDTO.getGitProjectDB();
        // 封装关系表数据
        String uuid2 = EireneUtil.randomUUID();
        JiraGitprojectRelation jiraGitprojectRelation = new JiraGitprojectRelation();
        jiraGitprojectRelation.setGitProjectId(gitProjectCreateDataDTO.getGitProjectId());
        jiraGitprojectRelation.setJiraProjectKey(gitProjectCreateDataDTO.getJiraProjectKey());
        jiraGitprojectRelation.setUuid(uuid2);

        try {
           this.gitDBService.insertProjectAndBranch(gitProjectDB,jiraGitprojectRelation);
        } catch (Exception e) {
            gitlabAPIClient.deleteProject(gitProject.getId());
            logger.info("error---->{}",e.getMessage());
            throw new InternalServerErrorException(String.format("GitProject insert DB error message:%s", e.getMessage()));
        }

        return gitProject;
    }




    /**
     * 添加git分支
     *
     * @param
     * @return
     * @throws Exception
     */
    public Branch createGitBranch(GitBranchCreateDataDTO gitBranchCreateDataDTO) throws Exception {
        String uuid = EireneUtil.randomUUID();
        gitBranchCreateDataDTO.setUuid(uuid);

        GitBranchCreateData gitBranchCreateData = gitBranchCreateDataDTO.getGitBranchCreateData();

        Branch gitBranch = null;
        try {
            gitBranch = gitlabAPIClient.createGitBranch(gitBranchCreateData);
        } catch (Exception e) {
            throw new InternalServerErrorException(String.format("GitBranch create error message:", e.getMessage()));
        }
        gitBranchCreateDataDTO.setCreateAt(EireneUtil.addDate(gitBranch.getCommit().getCreatedAt()));

        GitBranch gitBranchDB = gitBranchCreateDataDTO.getGitBranchDB();
        gitBranchDB.setBranchStatus(StatusEnum.OPEN_STATUS.getStatus());
        try {
            int i = gitDBService.insertGitBranch(gitBranchDB);
            logger.info("git branch db insert i = %d", i);
        } catch (Exception e) {
            gitlabAPIClient.deleteBranch(gitBranchCreateData.getGitProjectId(), gitBranchCreateData.getBranchName());
            throw new InternalServerErrorException(String.format("GitProject insert DB error message: %s", e.getMessage()));
        }

        return gitBranch;
    }

    /**
     * 查询gitlab项目下的所有分支
     *
     * @param gitProjectId
     * @return
     * @throws
     */
    public List<GitBranchVO> getBranches(Integer gitProjectId, Boolean detail) throws Exception {
        //detail == false 查询数据库
        List<GitBranchVO> gitBranchVOs = new ArrayList<>();
        //DB数据
        List<GitBranch> gitBranchs = gitDBService.getGitBranch(gitProjectId, StatusEnum.OPEN_STATUS.getStatus());

        if (gitBranchs == null || gitBranchs.size() < 1) {
            throw new NotFoundException ("not found gitBranchs in db");
        }

        if(gitProjectId == null ){
            return getGitBranchVos(gitBranchVOs, gitBranchs);
        }
        //git远端数据
        List<Branch> branchList = gitlabAPIClient.getBranches(gitProjectId);

        //非空判断
        if(CollectionUtils.isEmpty(gitBranchs) || CollectionUtils.isEmpty(branchList)){
            return gitBranchVOs;
        }
        //比较远端跟DB中分支的状态，将状态为open的进行返回
        this.gitDBService.comparatorStatus(gitProjectId,gitBranchs,branchList);
        //获取修改后的branch
        List<GitBranch> gitBranchList = gitDBService.getGitBranch(gitProjectId, StatusEnum.OPEN_STATUS.getStatus());

        gitBranchVOs = getGitBranchVos(gitBranchVOs, gitBranchList);

        if (detail) {
            for (GitBranchVO gitBranchVO1 : gitBranchVOs) {
                for (Branch branch : branchList) {
                    if (branch.getName().equals(gitBranchVO1.getGitBranchName())) {
                        gitBranchVO1.setBranch(branch);
                    }
                }
            }
        }
        return gitBranchVOs;
    }

    private List<GitBranchVO>  getGitBranchVos(List<GitBranchVO> gitBranchVOs, List<GitBranch> gitBranchs) {
        if(CollectionUtils.isEmpty(gitBranchs)){
            return gitBranchVOs;
        }

        for (GitBranch gitBranch : gitBranchs) {
            GitBranchVO gitBranchVO = new GitBranchVO(gitBranch);
            gitBranchVOs.add(gitBranchVO);
        }

        return gitBranchVOs;
    }


    /**
     * 修改项目
     *
     * @param projectId、gitProjectCreateDataDTO
     * @return
     * @throws Exception
     */
    public Project updateProject(Integer projectId,GitProjectCreateDataDTO gitProjectCreateDataDTO) throws Exception {
        Project newGitproject = null;
        try {
            Project gitProject = new Project();
            gitProject.setId(projectId);
            gitProject.setDescription(gitProjectCreateDataDTO.getDescription());
            //gitProject.setVisibility(gitProjectCreateDataDTO.getVisibility());
            gitProject.setName(gitProjectCreateDataDTO.getGitProjectName());
            newGitproject = gitlabAPIClient.updateProject(gitProject);
        } catch (Exception e) {
            throw new InternalServerErrorException(String.format("gitlab update project error: %s", e.getMessage()));

        }

        GitProject gitProjectDB = gitProjectCreateDataDTO.getGitProjectDB();
        gitProjectDB.setGitProjectId(projectId);
        int i = gitDBService.updateProject(gitProjectDB);

        return newGitproject;
    }

    /**
     * 删除DB项目
     *
     * @param uuid
     * @return
     */
    public int deleteProject(String uuid) {
        int i = gitDBService.deleteProjectByUuid(uuid);
        return i;
    }


    /**
     * 删除DB分支
     *
     * @param uuid
     * @return
     */
    public int deleteBranch(String uuid) {
        int i = gitDBService.deleteByGitBranch(uuid);
        return i;
    }

    /**
     * set up jira and gitproject relations in db
     *
     * @param jiraGitprojectRelationDTO
     * @return
     */
    @Transactional
    public JiraGitprojectRelation setRelateGitProject(JiraGitprojectRelationDTO jiraGitprojectRelationDTO) throws Exception {

        String gitProjectUrl = jiraGitprojectRelationDTO.getGitProjectUrl();

        String gitUrl = this.gitlabAPIClient.getGitUrl();

        //判断地址是否正确
        if(!gitProjectUrl.startsWith(gitUrl)){
            throw new InternalServerErrorException(String.format("code space address: %s is not support.", gitProjectUrl));
        }

        //获取项目路径
        String path = gitProjectUrl.replaceAll(gitUrl,"").replaceAll(GIT,"").substring(1);

        //查询git
        Project project = this.gitlabAPIClient.getProjectByPath(path,jiraGitprojectRelationDTO.getGitProjectId());

        //查询mysql
        GitProject gitProject = this.gitDBService.getGitProjectById(project.getId());

        //当数据库没有时，新增记录
        if(gitProject == null){
            gitProject =  new GitProject(project);
            String uuid = EireneUtil.randomUUID();
            gitProject.setUuid(uuid);
            this.gitDBService.insertProject(gitProject);
        }
        JiraGitprojectRelation jiraGitprojectRelation = jiraGitprojectRelationDTO.getJiraGitprojectRelation();
        String uuid = EireneUtil.randomUUID();
        jiraGitprojectRelation.setUuid(uuid);
        jiraGitprojectRelation.setGitProjectId(gitProject.getGitProjectId());
        int res = gitDBService.insertJiraGitprojectRealtaion(jiraGitprojectRelation);
        if (res == 1) {
            return jiraGitprojectRelation;
        } else {
            throw new InternalServerErrorException("jira_gitproject_relation insert DB error ");
        }


    }


    /**
     * 根据jira的项目key查询git仓库
     * @param projectKey
     * @return
     * @throws IOException
     */
    public List<GitProject> gitProjects(String projectKey) throws IOException {
        List<GitProject> gitProjects = gitDBService.selectCodeSpaceByProjectKey(projectKey);
        return gitProjects;
    }

    /**
     * 创建pr
     *
     * @param
     * @return
     * @throws IOException
     */
    public MergeRequest createPullrequests(GitpullreqeustsDTO gitpullreqeustsDTO) throws Exception {
        String uuid = EireneUtil.randomUUID();
        gitpullreqeustsDTO.setUuid(uuid);

        GitPullrequestsCreateData gitPullrequestsCreateData = gitpullreqeustsDTO.getGitPullrequestsCreateData();

        MergeRequest gitPullRequsts = null;

        try {
            gitPullRequsts = gitlabAPIClient.createGitPullrequests(gitPullrequestsCreateData);
        } catch (Exception e) {

            throw new GitLabApiException("gitpullrequests create error in gitlab server: " + e.getMessage());
        }
        gitpullreqeustsDTO.setGitPrCreatedAt(gitPullRequsts.getCreatedAt());
        gitpullreqeustsDTO.setGitPrId(gitPullRequsts.getId());
        gitpullreqeustsDTO.setGitPrIid(gitPullRequsts.getIid());
        gitpullreqeustsDTO.setGitPrStatus(gitPullRequsts.getState());
        GitPullRequests gitPullRequests = gitpullreqeustsDTO.getGitpullrequestsDB();
        try {
            int i = gitDBService.insertGitPullrequests(gitPullRequests);
            logger.info("gitpullrequests db insert i = %d", i);
        } catch (Exception e) {
            gitlabAPIClient.deleteGitpullrequests(gitPullRequsts.getProjectId(), gitPullRequsts.getIid());
            gitDBService.deleteGitpullrequests(uuid);
            throw e;
        }

        return gitPullRequsts;
    }


    /**
     * 根据gitlab的项目id查询pr
     * @param gitProjectId
     * @return
     * @throws IOException
     */
    public List<GitpullrequestsVO> getGitpullrequests(Integer gitProjectId,String status) throws Exception {

        List<GitpullrequestsVO> gitpullrequestsVOS = new ArrayList<>();

        //get all pr from db in one gitproject
        List<GitPullRequests> gitPullRequests = gitDBService.getGitpullrequests(gitProjectId);

        if (gitPullRequests == null || gitPullRequests.size() < 1) {
            throw new NotFoundException("no gitPr in db");
        }
        //get all pr from gitlab server in one gitproject
        List<MergeRequest> mergeRequests = gitlabAPIClient.getGitpullrequest(gitProjectId);


        if(CollectionUtils.isEmpty(gitPullRequests) || CollectionUtils.isEmpty(mergeRequests)){
            return gitpullrequestsVOS;
        }

        //compare gitpr status in db to gitlab server and set status depend on gitlab server;
        this.gitDBService.comparaPrStatus(gitPullRequests,mergeRequests);

        //update Merged pr when code review;
        if (status.equals("merged")) {
            updateMergedPr(gitProjectId);
        }

        //get new status in db
        List<GitPullRequests> gitPullRequestsList = gitDBService.getGitprByStatus(gitProjectId,status);

        //return VO to front;
        gitPullRequestsList.forEach( item ->{
            GitpullrequestsVO gitpullrequestsVO = new GitpullrequestsVO();
            gitpullrequestsVO.setGitPrId(item.getGitPrId());
            gitpullrequestsVO.setGitPrTitle(item.getGitPrTitle());
            gitpullrequestsVO.setGitPrCreateby(item.getGitPrCreateby());
            gitpullrequestsVO.setGitPrCreateat(item.getGitPrCreateat());
            gitpullrequestsVO.setJiraProjectKey(item.getJiraProjectKey());
            gitpullrequestsVO.setGitProjectId(item.getGitProjectId());
            gitpullrequestsVO.setGitPrStatus(item.getGitPrStatus());
            gitpullrequestsVO.setGitPrSource(item.getGitPrSource());
            gitpullrequestsVO.setGitPrTarget(item.getGitPrTarget());

            //codereview info when opened or closed are null
            gitpullrequestsVO.setMgCommitat(item.getMgCommitat());
            gitpullrequestsVO.setMgCommitby(item.getMgCommitby());
            gitpullrequestsVO.setMgCommitId(item.getMgCommitId());
            gitpullrequestsVO.setMgCommitInfo(item.getMgCommitInfo());
            gitpullrequestsVO.setMgBranchName(item.getMgBranchName());
            gitpullrequestsVO.setJiraIssue(item.getJiraIssue());
            gitpullrequestsVOS.add(gitpullrequestsVO);
        });

        return gitpullrequestsVOS;
    }

    private void updateMergedPr(Integer gitProjectId) throws Exception {
        List<GitPullRequests> gitMergedPrs = gitDBService.getGitprByStatus(gitProjectId, StatusEnum.PR_STATUS_MREGED.getStatus());

        for (GitPullRequests gitMergedPr : gitMergedPrs) {

            MergeRequest mergeRequest = gitlabAPIClient.getMergequest(gitMergedPr.getGitProjectId(), gitMergedPr.getGitPrIid());

            Commit commit = gitlabAPIClient.getCommit(gitMergedPr.getGitProjectId(),mergeRequest.getMergeCommitSha());

            GitPullRequests gitPrModel = new GitPullRequests();
            gitPrModel.setMgBranchName(mergeRequest.getTargetBranch());
            gitPrModel.setGitPrId(gitMergedPr.getGitPrId());
            gitPrModel.setMgCommitat(commit.getCommittedDate());
            gitPrModel.setMgCommitby(commit.getAuthorName());
            gitPrModel.setMgCommitId(commit.getShortId());
            gitPrModel.setMgCommitInfo(commit.getMessage());
            gitDBService.updateGitpullrequests(gitPrModel);
        }
    }

    /**
     * 删除 pr in db
     * @param uuid
     * @return
     * @throws
     */
    public int deleteGitpullrequests(String uuid) {
        int i = gitDBService.deleteGitpullrequests(uuid);
        return i;
    }

    /**
     * 修改 pr in db
     * @param uuid
     * @return
     * @throws
     */
    public GitPullRequests updateGitpullrequests(String uuid, GitpullreqeustsDTO gitpullreqeustsDTO) throws IOException {
        gitpullreqeustsDTO.setUuid(uuid);
        int i = gitDBService.updateGitpullrequests(gitpullreqeustsDTO.getGitpullrequestsDB());
        GitPullRequests gitPullRequests = null;
        if (i > 0) {
            gitPullRequests = gitDBService.selectGitpullrequest(uuid);
        }
        return gitPullRequests;



    }

     /* 查询gitProjects，如果detail == false，否则查询DB+HTTP
     * 查询gitProjects，如果detail == false，否则查询DB+HTTP
     * 当groupId存在时，根据groupId查询
     * @param groupId
     * @return
     */
    public List<GitProjectVO> selectProjectByGroupId(Integer groupId,Boolean detail) throws Exception {

        List<GitProject> gitProjects = this.gitDBService.selectProjectByGroupId(groupId);

        List<GitProjectVO> gitProjectVOs = new ArrayList<>();

        getGitProjectsFromGit(detail,gitProjectVOs,gitProjects);

        return gitProjectVOs;
    }

    /**
     * 通过uuid修改gitProject，仅修改DB数据
     * @param uuid
     * @return
     */
    public GitProjectVO updateProjectByUuid(String uuid,GitProjectCreateDataDTO gitProjectCreateDataDTO) throws Exception {

        int num = this.gitDBService.updateProjectByUuid(uuid, gitProjectCreateDataDTO);

        if (num > 0) {
            logger.info("修改成功,data:{}",JacksonUtil.bean2Json(gitProjectCreateDataDTO));
            GitProject gitProject = this.gitDBService.getGitProjectByUuid(uuid);
            GitProjectVO gitProjectVO = new GitProjectVO(gitProject);
            return  gitProjectVO;

        } else {
            throw new Exception("修改失败");
        }
    }

    /**
     * 通过uuid获取gitBranch
     * @param uuid
     * @param detail
     * @return
     */
    public GitBranchVO selectBranchsByUuid(String uuid, Boolean detail) throws Exception {
        GitBranchVO gitBranchVO = null;
        try {
            GitBranch gitBranch = gitDBService.selectBranchsByUuid(uuid);
            gitBranchVO = new GitBranchVO(gitBranch);
        } catch (Exception e) {
            logger.info(String.format("no data"));
        }

        //如果detail == true 查询数据库+Client  getGitProject(gitProjectVO.getGitProjectId())
        if (detail) {
            gitBranchVO.setBranch(gitlabAPIClient.getBranch(gitBranchVO.getGitProjectId(),gitBranchVO.getGitBranchName()));
        }
        return gitBranchVO;
    }

    /**
     * 根据uuid修改gitBranch
     * @param uuid
     * @param gitBranchCreateDataDTO
     * @return
     */
    public GitBranchVO updateBranchsByUuid(String uuid, GitBranchCreateDataDTO gitBranchCreateDataDTO) throws Exception {
        GitBranch gitBranchDB = gitBranchCreateDataDTO.getGitBranchDB();
        gitBranchDB.setUuid(uuid);
        int num = this.gitDBService.updateBranchsByUuid(gitBranchDB);

        if (num >  0) {
            logger.info("修改成功,data{}",JacksonUtil.bean2Json(gitBranchCreateDataDTO));
            GitBranch gitBranch = this.gitDBService.selectBranchsByUuid(uuid);
            GitBranchVO gitBranchVO = new GitBranchVO(gitBranch);
            return  gitBranchVO;
        }else{
            throw new Exception("修改失败");
        }

    }

    /**
     * 当projectKey存在时，根据projectKey查询，否则查询所有
     * @param projectKey
     * @return
     */
    public List<JiraGitprojectRelationVo> getJiraGitprojectRelation(String projectKey) throws IOException {
        List<JiraGitprojectRelation> jiraGitprojectRelations = this.gitDBService.getJiraGitprojectRelation(projectKey);

        ArrayList<JiraGitprojectRelationVo> jiraGitprojectRelationVos = new ArrayList<>();

        jiraGitprojectRelations.forEach(item ->jiraGitprojectRelationVos.add(new JiraGitprojectRelationVo(item)));
        return jiraGitprojectRelationVos;
    }

    /**
     * 根据uuid进行查询JiraGitprojectRelation表
     * @param uuid
     * @return
     */
    public JiraGitprojectRelationVo getJiraGitprojectRelationByUuid(String uuid) throws IOException {
        JiraGitprojectRelation jiraGitprojectRelation = this.gitDBService.getJiraGitprojectRelationByUuid(uuid);
        JiraGitprojectRelationVo jiraGitprojectRelationVo = new JiraGitprojectRelationVo(jiraGitprojectRelation);
        return jiraGitprojectRelationVo;
    }

    /**
     * 根据uuid进行修改JiraGitprojectRelation表
     * @param uuid
     * @return
     */
    public JiraGitprojectRelationVo modifyJiraGitprojectRelationByUuid(String uuid,JiraGitprojectRelationDTO jiraGitprojectRelationDTO) throws Exception {
        int num = this.gitDBService.modifyJiraGitprojectRelationByUuid(uuid,jiraGitprojectRelationDTO);

        if(num > 0){
            logger.info("修改成功，data:{}",JacksonUtil.bean2Json(jiraGitprojectRelationDTO));
            JiraGitprojectRelation JiraGitprojectRelation = this.gitDBService.getJiraGitprojectRelationByUuid(uuid);
            JiraGitprojectRelationVo jiraGitprojectRelationVo = new JiraGitprojectRelationVo(JiraGitprojectRelation);
            return jiraGitprojectRelationVo;
        }else{
            throw new Exception("修改失败！");
        }
    }

    /**
     * 根据uuid进行删除JiraGitprojectRelation表
     * @param uuid
     * @return
     */
    public int deleteJiraGitprojectRelationByUuid(String uuid) {
       return  this.gitDBService.deleteJiraGitprojectRelationByUuid(uuid);
    }

    /**
     * 新增JiraGitprojectRelation表记录
     * @param jiraGitprojectRelationDTO
     * @return
     */
    public JiraGitprojectRelation addJiraGitprojectRelation(JiraGitprojectRelationDTO jiraGitprojectRelationDTO) throws Exception {
        String projectKey = jiraGitprojectRelationDTO.getJiraProjectKey();
        com.pingan.starlink.model.Project project = jiraProjectDBService.selectProject(projectKey);
        if (project == null){
            logger.info(" 'create insertGitPullrequests' not found jira project ");
            throw new NotFoundException("找不到jira的项目");
        }
        if (project.getProjectStatus().equals(ConstantUtil.FAILED) || project.getProjectStatus().equals(ConstantUtil.CREATING)){
            logger.info(" this status don't create version ");
            throw new NotFoundException("此状态的项目不能创建版本");
        }

        JiraGitprojectRelation jiraGitprojectRelation = jiraGitprojectRelationDTO.getJiraGitprojectRelation();
        String uuid = EireneUtil.randomUUID();
        jiraGitprojectRelation.setUuid(uuid);
        int res = this.gitDBService.insertJiraGitprojectRealtaion(jiraGitprojectRelation);
        if (res == 1) {
            return jiraGitprojectRelation;
        } else {
            throw new InternalServerErrorException("jira_gitproject_relation insert DB error ");
        }
    }

    /**
     * 根据git_group_id 同步 project  branch
     * @param groupId
     */
    public List gitLabSyncDBByGroupId(Integer groupId) throws Exception {

        //获取远端组下的所有项目
        Group group = this.gitlabAPIClient.getGroup(groupId);

        List<Project> projects = group.getProjects();

        //获取DB组下所有的项目
        List<GitProject> DBProjects = this.gitDBService.getGitProjectsByGroup(groupId);
        //非空判断
        if(CollectionUtils.isEmpty(projects) ){
            throw  new NotFoundException("根据组的id查询，远端的查询结果为空");
        }

        //取出远端和DB中的projectId
        List<Integer> projectIds =  new ArrayList<>();
        projects.forEach( item -> projectIds.add(item.getId()));

        logger.info("projectList is {}", JacksonUtil.bean2Json(projectIds));

        /*List<Integer> projectIdDBs =  new ArrayList<>();
        DBProjects.forEach( item -> projectIdDBs.add(item.getGitProjectId()));

        //取出不存在于DB的projectId
        projectIds.removeAll(projectIdDBs);

        if(CollectionUtils.isEmpty(projectIds) || projectIds.size() == 0){
            //远端的所有项目都存在于DB,开始更新DB数据
            updateProjectFromClient(groupId);
        }*/

        //默认是首次初始化
        gitProjectAndBranchInit(projects);

        return projectIds;
    }

    public void gitProjectAndBranchInit(List<Project> projects) throws Exception{

        for (Project project : projects) {

            GitProject gitProject = new GitProject();
            gitProject.setUuid(EireneUtil.randomUUID());
            gitProject.setGitProjectId(project.getId());
            gitProject.setGitNamespaceId(project.getNamespace().getId());
            gitProject.setGitProjectName(project.getName());
            gitProject.setGitProjectUrl(project.getHttpUrlToRepo());
            gitProject.setGitProjectDescription(project.getDescription());
            gitProject.setGitProjectCreatedAt(EireneUtil.addDate(project.getCreatedAt()));

            gitDBService.insertProject(gitProject);
            //获取该project下面的branch
            List<Branch> branches = this.gitlabAPIClient.getBranches(project.getId());


            for (Branch branch : branches) {

                GitBranch gitBranch = GitBranch.builder().uuid(EireneUtil.randomUUID()).gitBranchName(branch.getName()).
                        gitProjectId(project.getId()).description(branch.getCommit().getMessage()).createdBy(branch.getCommit().getAuthorName()).
                        createdAt(EireneUtil.addDate(branch.getCommit().getCreatedAt())).createdBy(branch.getCommit().getCommitterName()).
                        commitAt(EireneUtil.addDate(branch.getCommit().getCommittedDate())).release(false).branchStatus(StatusEnum.OPEN_STATUS.getStatus()).build();

                if(gitBranch.getGitBranchName().equalsIgnoreCase(RELEASE)) {
                    gitBranch.setRelease(true);
                }

                this.gitDBService.insertGitBranch(gitBranch);
            }
        }
    }

    /**
     * 根据groupId 更新DB数据
     * @param groupId
     */
    private void updateProjectFromClient(Integer groupId) throws Exception {
        try {

            Group  group = this.gitlabAPIClient.getGroup(groupId);
            List<Project> projects = group.getProjects();

        } catch (Exception e) {

            throw new Exception("远端的所有项目都存在于DB,更新DB数据出错,错误信息：" + e.getMessage());
        }
    }


    /**
     * 根据分支名修改最近提交人和时间
     * @param pushBody
     */
    public void updateBranchCommitAndTime(PushSystemHookEvent pushBody) {
        //获取项目id和分支名
        Integer projectId = pushBody.getProject().getId();

        String[] split = pushBody.getRef().split("/");
        String branchName = split[split.length - 1 ];

        logger.info("[projectId  is {}] ,[branchName is {}]",projectId ,branchName);

        //获取最近提交人和时间
        List<EventCommit> commits = pushBody.getCommits();
        EventCommit commit = commits.get(commits.size() - 1);
        String commitName = commit.getAuthor().getName();
        Date commitDate = EireneUtil.addDate(commit.getTimestamp());


        logger.info("[commitName is {} ], [commitDate is {}]",commitName,commit.getTimestamp());

        this.gitDBService.updateBranchCommitAndTime(projectId,branchName,commitName,commitDate);

    }

}
