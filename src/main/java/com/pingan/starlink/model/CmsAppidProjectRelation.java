package com.pingan.starlink.model;

import javax.persistence.*;

@Table(name = "cms_appid_project_relation")
public class CmsAppidProjectRelation {
    /**
     * UUID唯一标识符
     */
    @Id
    private String uuid;

    /**
     * appid
     */
    private String appid;

    /**
     * 项目
     */
    private String project;

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
     * 获取appid
     *
     * @return appid - appid
     */
    public String getAppid() {
        return appid;
    }

    /**
     * 设置appid
     *
     * @param appid appid
     */
    public void setAppid(String appid) {
        this.appid = appid;
    }

    /**
     * 获取项目
     *
     * @return project - 项目
     */
    public String getProject() {
        return project;
    }

    /**
     * 设置项目
     *
     * @param project 项目
     */
    public void setProject(String project) {
        this.project = project;
    }
}