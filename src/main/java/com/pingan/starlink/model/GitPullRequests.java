package com.pingan.starlink.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "git_pull_requests")
public class GitPullRequests {
    /**
     * UUID唯一标识符
     */
    @Id
    protected String uuid;

    /**
     * pr的id(gitlab生成)
     */
    @Column(name = "git_pr_id")
    protected Integer gitPrId;

    /**
     * pr标题
     */
    @Column(name = "git_pr_title")
    protected String gitPrTitle;

    /**
     * pr创建人
     */
    @Column(name = "git_pr_createby")
    protected String gitPrCreateby;

    /**
     * pr创建时间
     */
    @Column(name = "git_pr_createat")
    protected Date gitPrCreateat;

    /**
     * pr的状态
     */
    @Column(name = "git_pr_status")
    protected String gitPrStatus;

    /**
     * 相关联的jira id
     */
    @Column(name = "jira_project_key")
    protected String jiraProjectKey;

    /**
     * gitproject的id
     */
    @Column(name = "git_project_id")
    protected Integer gitProjectId;

    /**
     * git pr describe
     * @return
     */
    @Column(name = "git_pr_describe")
    protected String gitPrDescribe;

    public String getGitPrDescribe() {
        return gitPrDescribe;
    }

    public void setGitPrDescribe(String gitPrDescribe) {
        this.gitPrDescribe = gitPrDescribe;
    }

    public Integer getGitProjectId() {
        return gitProjectId;
    }

    public void setGitProjectId(Integer gitProjectId) {
        this.gitProjectId = gitProjectId;
    }


    /**
     * pr审阅者
     *
     */
    @Column(name = "git_pr_assignee")
    protected String gitPrAssignee;

    /**
     * jira的issue
     */
    @Column(name = "jira_issue")
    protected  String jiraIssue;



    public String getGitPrAssignee() {
        return gitPrAssignee;
    }

    public void setGitPrAssignee(String gitPrAssignee) {
        this.gitPrAssignee = gitPrAssignee;
    }

    public String getJiraIssue() {
        return jiraIssue;
    }

    public void setJiraIssue(String jiraIssue) {
        this.jiraIssue = jiraIssue;
    }

    /**
     * 获取UUID唯一标识符
     *
     * @return uuid - UUID唯一标识符
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * 设置UUID唯一标识符
     *
     * @param uuid UUID唯一标识符
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * 获取pr的id(gitlab生成)
     *
     * @return git_pr_id - pr的id(gitlab生成)
     */
    public Integer getGitPrId() {
        return gitPrId;
    }

    /**
     * 设置pr的id(gitlab生成)
     *
     * @param gitPrId pr的id(gitlab生成)
     */
    public void setGitPrId(Integer gitPrId) {
        this.gitPrId = gitPrId;
    }

    /**
     * 获取pr标题
     *
     * @return git_pr_title - pr标题
     */
    public String getGitPrTitle() {
        return gitPrTitle;
    }

    /**
     * 设置pr标题
     *
     * @param gitPrTitle pr标题
     */
    public void setGitPrTitle(String gitPrTitle) {
        this.gitPrTitle = gitPrTitle;
    }

    /**
     * 获取pr创建人
     *
     * @return git_pr_createby - pr创建人
     */
    public String getGitPrCreateby() {
        return gitPrCreateby;
    }

    /**
     * 设置pr创建人
     *
     * @param gitPrCreateby pr创建人
     */
    public void setGitPrCreateby(String gitPrCreateby) {
        this.gitPrCreateby = gitPrCreateby;
    }

    /**
     * 获取pr创建时间
     *
     * @return git_pr_createat - pr创建时间
     */
    public Date getGitPrCreateat() {
        return gitPrCreateat;
    }

    /**
     * 设置pr创建时间
     *
     * @param gitPrCreateat pr创建时间
     */
    public void setGitPrCreateat(Date gitPrCreateat) {
        this.gitPrCreateat = gitPrCreateat;
    }

    /**
     * 获取pr的状态
     *
     * @return git_pr_status - pr的状态
     */
    public String getGitPrStatus() {
        return gitPrStatus;
    }

    /**
     * 设置pr的状态
     *
     * @param gitPrStatus pr的状态
     */
    public void setGitPrStatus(String gitPrStatus) {
        this.gitPrStatus = gitPrStatus;
    }

    /**
     * 获取相关联的jira id
     *
     * @return jira_project_key - 相关联的jira id
     */
    public String getJiraProjectKey() {
        return jiraProjectKey;
    }

    /**
     * 设置相关联的jira id
     *
     * @param jiraProjectKey 相关联的jira id
     */
    public void setJiraProjectKey(String jiraProjectKey) {
        this.jiraProjectKey = jiraProjectKey;
    }


    @Column(name = "mg_commit_id")
    protected String mgCommitId;

    @Column(name = "mg_commitby")
    protected String mgCommitby;

    @Column(name = "mg_commitat")
    protected Date mgCommitat;

    @Column(name = "mg_commit_info")
    protected String mgCommitInfo;

    @Column(name = "git_pr_iid")
    protected Integer gitPrIid;

    @Column(name = "mg_branch_name")
    protected String mgBranchName;

    @Column(name = "git_pr_source")
    protected  String gitPrSource;

    @Column(name = "git_pr_target")
    protected  String gitPrTarget;

    public String getMgBranchName() {
        return mgBranchName;
    }

    public void setMgBranchName(String mgBranchName) {
        this.mgBranchName = mgBranchName;
    }

    public Integer getGitPrIid() {
        return gitPrIid;
    }

    public void setGitPrIid(Integer gitPrIid) {
        this.gitPrIid = gitPrIid;
    }


    public String getMgCommitby() {
        return mgCommitby;
    }

    public void setMgCommitby(String mgCommitby) {
        this.mgCommitby = mgCommitby;
    }

    public Date getMgCommitat() {
        return mgCommitat;
    }

    public void setMgCommitat(Date mgCommitat) {
        this.mgCommitat = mgCommitat;
    }

    public String getMgCommitInfo() {
        return mgCommitInfo;
    }

    public void setMgCommitInfo(String mgCommitInfo) {
        this.mgCommitInfo = mgCommitInfo;
    }


    public String getMgCommitId() {
        return mgCommitId;
    }

    public void setMgCommitId(String mgCommitId) {
        this.mgCommitId = mgCommitId;
    }

    public String getGitPrSource() {
        return gitPrSource;
    }

    public void setGitPrSource(String gitPrSource) {
        this.gitPrSource = gitPrSource;
    }

    public String getGitPrTarget() {
        return gitPrTarget;
    }

    public void setGitPrTarget(String gitPrTarget) {
        this.gitPrTarget = gitPrTarget;
    }
}