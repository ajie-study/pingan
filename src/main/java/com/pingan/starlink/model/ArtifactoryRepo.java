package com.pingan.starlink.model;

import javax.persistence.*;

@Table(name = "artifactory_repo")
public class ArtifactoryRepo {
    /**
     * UUID唯一标识符
     */
    @Id
    private String uuid;

    /**
     * 所属部门
     */
    private String department;

    /**
     * 仓库名
     */
    @Column(name = "repo_name")
    private String repoName;

    /**
     * 仓库地址
     */
    @Column(name = "repo_url")
    private String repoUrl;

    /**
     * 仓库类型
     */
    @Column(name = "repo_type")
    private String repoType;

    /**
     * Token
     */
    private String token;

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
     * 获取所属部门
     *
     * @return department - 所属部门
     */
    public String getDepartment() {
        return department;
    }

    /**
     * 设置所属部门
     *
     * @param department 所属部门
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * 获取仓库名
     *
     * @return repo_name - 仓库名
     */
    public String getRepoName() {
        return repoName;
    }

    /**
     * 设置仓库名
     *
     * @param repoName 仓库名
     */
    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    /**
     * 获取仓库地址
     *
     * @return repo_url - 仓库地址
     */
    public String getRepoUrl() {
        return repoUrl;
    }

    /**
     * 设置仓库地址
     *
     * @param repoUrl 仓库地址
     */
    public void setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
    }

    /**
     * 获取仓库类型
     *
     * @return repo_type - 仓库类型
     */
    public String getRepoType() {
        return repoType;
    }

    /**
     * 设置仓库类型
     *
     * @param repoType 仓库类型
     */
    public void setRepoType(String repoType) {
        this.repoType = repoType;
    }

    /**
     * 获取Token
     *
     * @return token - Token
     */
    public String getToken() {
        return token;
    }

    /**
     * 设置Token
     *
     * @param token Token
     */
    public void setToken(String token) {
        this.token = token;
    }
}