package com.pingan.starlink.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "git_branch")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GitBranch {
    /**
     * UUID唯一标识符
     */
    @Id
    protected String uuid;

    /**
     * 分支名称
     */
    @Column(name = "git_branch_name")
    protected String gitBranchName;

    /**
     * 分支所属的git项目id(gitlab生成的值)
     */
    @Column(name = "git_project_id")
    protected Integer gitProjectId;

    /**
     * 分支描述
     */
    protected String description;

    /**
     * 分支创建者
     */
    @Column(name = "created_by")
    protected String createdBy;

    /**
     * 分支创建时间
     */
    @Column(name = "created_at")
    protected Date createdAt;

    /**
     * 分支是否为发布版本
     */
    protected Boolean release;

    /**
     * 最近提交人
     */
    @Column(name = "commit_by")
    protected String commitBy;


    /**
     * 最近提交时间
     */
    @Column(name = "commit_at")
    protected Date commitAt;

    /**
     * 分支状态
     * @return
     */
    @Column(name = "branch_status")
    protected String branchStatus;
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
     * 获取分支名称
     *
     * @return git_branch_name - 分支名称
     */
    public String getGitBranchName() {
        return gitBranchName;
    }

    /**
     * 设置分支名称
     *
     * @param gitBranchName 分支名称
     */
    public void setGitBranchName(String gitBranchName) {
        this.gitBranchName = gitBranchName;
    }

    /**
     * 获取分支所属的git项目id(gitlab生成的值)
     *
     * @return git_project_id - 分支所属的git项目id(gitlab生成的值)
     */
    public Integer getGitProjectId() {
        return gitProjectId;
    }

    /**
     * 设置分支所属的git项目id(gitlab生成的值)
     *
     * @param gitProjectId 分支所属的git项目id(gitlab生成的值)
     */
    public void setGitProjectId(Integer gitProjectId) {
        this.gitProjectId = gitProjectId;
    }

    /**
     * 获取分支描述
     *
     * @return description - 分支描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置分支描述
     *
     * @param description 分支描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取分支创建者
     *
     * @return created_by - 分支创建者
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * 设置分支创建者
     *
     * @param createdBy 分支创建者
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 获取分支创建时间
     *
     * @return created_at - 分支创建时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置分支创建时间
     *
     * @param createdAt 分支创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 获取分支是否为发布版本
     *
     * @return release - 分支是否为发布版本
     */
    public Boolean getRelease() {
        return release;
    }

    /**
     * 设置分支是否为发布版本
     *
     * @param release 分支是否为发布版本
     */
    public void setRelease(Boolean release) {
        this.release = release;
    }

    public String getCommitBy() {
        return commitBy;
    }

    public void setCommitBy(String commitBy) {
        this.commitBy = commitBy;
    }

    public Date getCommitAt() {
        return commitAt;
    }

    public void setCommitAt(Date commitAt) {

        this.commitAt = commitAt;
    }

    public String getBranchStatus() {
        return branchStatus;
    }

    public void setBranchStatus(String branchStatus) {
        this.branchStatus = branchStatus;
    }
}