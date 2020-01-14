package com.pingan.starlink.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "gitgroup_department_relation")
public class GitgroupDepartmentRelation {
    /**
     * UUID唯一标识符
     */
    @Id
    protected String uuid;

    /**
     * git组的id
     */
    @Column(name = "gitgroup_id")
    protected Integer gitgroupId;

    /**
     * 所属部门
     */
    protected String department;

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
}