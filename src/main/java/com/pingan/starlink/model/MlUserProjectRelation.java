package com.pingan.starlink.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "ml_user_project_relation")
public class MlUserProjectRelation {
    /**
     * UUID唯一标识符
     */
    @Id
    private String uuid;

    /**
     * 用户名
     */
    private String username;

    /**
     * protal项目的key
     */
    @Column(name = "project_key")
    private String projectKey;

    /**
     * 用户在项目中的角色
     */
    private String role;

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
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取protal项目的key
     *
     * @return project_key - protal项目的key
     */
    public String getProjectKey() {
        return projectKey;
    }

    /**
     * 设置protal项目的key
     *
     * @param projectKey protal项目的key
     */
    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    /**
     * 获取用户在项目中的角色
     *
     * @return role - 用户在项目中的角色
     */
    public String getRole() {
        return role;
    }

    /**
     * 设置用户在项目中的角色
     *
     * @param role 用户在项目中的角色
     */
    public void setRole(String role) {
        this.role = role;
    }
}