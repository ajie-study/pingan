package com.pingan.starlink.model;

import org.gitlab4j.api.models.Project;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "git_project")
public class GitProject {
    /**
     * UUID唯一标识符
     */
    @Id
    protected String uuid;

    /**
     * gitlab生成的值
     */
    @Column(name = "git_project_id")
    protected Integer gitProjectId;

    /**
     * gitlab组的id
     */
    @Column(name = "git_namespace_id")
    protected Integer gitNamespaceId;

    /**
     * gitproject名称
     */
    @Column(name = "git_project_name")
    protected String gitProjectName;

    /**
     * git仓库地址
     */
    @Column(name = "git_project_url")
    protected String gitProjectUrl;

    /**
     * gitProject描述
     */
    @Column(name = "git_project_description")
    protected String gitProjectDescription;

    /**
     * gitproject创建时间
     */
    @Column(name = "git_project_created_at")
    protected Date gitProjectCreatedAt;


    public GitProject() {
    }

    public GitProject(Project project) {
        this.gitProjectId = project.getId();
        this.gitNamespaceId = project.getNamespace().getId();
        this.gitProjectName = project.getName();
        this.gitProjectUrl = project.getSshUrlToRepo();
        this.gitProjectDescription = project.getDescription();
        this.gitProjectCreatedAt = project.getCreatedAt();
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
     * 获取gitlab生成的值
     *
     * @return git_project_id - gitlab生成的值
     */
    public Integer getGitProjectId() {
        return gitProjectId;
    }

    /**
     * 设置gitlab生成的值
     *
     * @param gitProjectId gitlab生成的值
     */
    public void setGitProjectId(Integer gitProjectId) {
        this.gitProjectId = gitProjectId;
    }

    /**
     * 获取gitlab组的id
     *
     * @return git_namespace_id - gitlab组的id
     */
    public Integer getGitNamespaceId() {
        return gitNamespaceId;
    }

    /**
     * 设置gitlab组的id
     *
     * @param gitNamespaceId gitlab组的id
     */
    public void setGitNamespaceId(Integer gitNamespaceId) {
        this.gitNamespaceId = gitNamespaceId;
    }

    /**
     * 获取gitproject名称
     *
     * @return git_project_name - gitproject名称
     */
    public String getGitProjectName() {
        return gitProjectName;
    }

    /**
     * 设置gitproject名称
     *
     * @param gitProjectName gitproject名称
     */
    public void setGitProjectName(String gitProjectName) {
        this.gitProjectName = gitProjectName;
    }

    /**
     * 获取git仓库地址
     *
     * @return git_project_url - git仓库地址
     */
    public String getGitProjectUrl() {
        return gitProjectUrl;
    }

    /**
     * 设置git仓库地址
     *
     * @param gitProjectUrl git仓库地址
     */
    public void setGitProjectUrl(String gitProjectUrl) {
        this.gitProjectUrl = gitProjectUrl;
    }

    /**
     * 获取gitProject描述
     *
     * @return git_project_description - gitProject描述
     */
    public String getGitProjectDescription() {
        return gitProjectDescription;
    }

    /**
     * 设置gitProject描述
     *
     * @param gitProjectDescription gitProject描述
     */
    public void setGitProjectDescription(String gitProjectDescription) {
        this.gitProjectDescription = gitProjectDescription;
    }

    /**
     * 获取gitproject创建时间
     *
     * @return git_project_created_at - gitproject创建时间
     */
    public Date getGitProjectCreatedAt() {
        return gitProjectCreatedAt;
    }

    /**
     * 设置gitproject创建时间
     *
     * @param gitProjectCreatedAt gitproject创建时间
     */
    public void setGitProjectCreatedAt(Date gitProjectCreatedAt) {
        this.gitProjectCreatedAt = gitProjectCreatedAt;
    }
}