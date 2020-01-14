package com.pingan.starlink.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "git_group")
public class GitGroup {
    /**
     * UUID唯一标识符
     */
    @Id
    private String uuid;

    /**
     * git组的id
     */
    @Column(name = "gitgroup_id")
    private Integer gitgroupId;

    /**
     * gitgroup全名唯一标识
     */
    @Column(name = "gitgroup_fullname")
    private String gitgroupFullname;

    /**
     * gitgroup路径
     */
    @Column(name = "gitgroup_fullpath")
    private String gitgroupFullpath;

    /**
     * gitgroup内master用户access token
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
     * 获取git组的id
     *
     * @return gitgroup_id - git组的id
     */
    public Integer getGitgroupId() {
        return gitgroupId;
    }

    /**
     * 设置git组的id
     *
     * @param gitgroupId git组的id
     */
    public void setGitgroupId(Integer gitgroupId) {
        this.gitgroupId = gitgroupId;
    }

    /**
     * 获取gitgroup全名唯一标识
     *
     * @return gitgroup_fullname - gitgroup全名唯一标识
     */
    public String getGitgroupFullname() {
        return gitgroupFullname;
    }

    /**
     * 设置gitgroup全名唯一标识
     *
     * @param gitgroupFullname gitgroup全名唯一标识
     */
    public void setGitgroupFullname(String gitgroupFullname) {
        this.gitgroupFullname = gitgroupFullname;
    }

    /**
     * 获取gitgroup路径
     *
     * @return gitgroup_fullpath - gitgroup路径
     */
    public String getGitgroupFullpath() {
        return gitgroupFullpath;
    }

    /**
     * 设置gitgroup路径
     *
     * @param gitgroupFullpath gitgroup路径
     */
    public void setGitgroupFullpath(String gitgroupFullpath) {
        this.gitgroupFullpath = gitgroupFullpath;
    }

    /**
     * 获取gitgroup内master用户access token
     *
     * @return token - gitgroup内master用户access token
     */
    public String getToken() {
        return token;
    }

    /**
     * 设置gitgroup内master用户access token
     *
     * @param token gitgroup内master用户access token
     */
    public void setToken(String token) {
        this.token = token;
    }
}