package com.pingan.starlink.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "ml_department")
public class MlDepartment {
    /**
     * UUID唯一标识符
     */
    @Id
    private String uuid;

    /**
     * 部门名称
     */
    @Column(name = "department_name")
    private String departmentName;

    /**
     * 部门KEY
     */
    @Column(name = "department_key")
    private String departmentKey;

    /**
     * 部门下创建新项目时的项目索引
     */
    @Column(name = "next_project_index")
    private Integer nextProjectIndex;

    /**
     * 部门角色
     */
    private String roles;

    /**
     * 上级部门名称
     */
    @Column(name = "superior_department")
    private String superiorDepartment;

    /**
     * 下级部门名称
     */
    @Column(name = "inferior_department")
    private String inferiorDepartment;

    /**
     * 描述
     */
    private String describes;

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
     * 获取部门名称
     *
     * @return department_name - 部门名称
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * 设置部门名称
     *
     * @param departmentName 部门名称
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * 获取部门KEY
     *
     * @return department_key - 部门KEY
     */
    public String getDepartmentKey() {
        return departmentKey;
    }

    /**
     * 设置部门KEY
     *
     * @param departmentKey 部门KEY
     */
    public void setDepartmentKey(String departmentKey) {
        this.departmentKey = departmentKey;
    }

    /**
     * 获取部门下创建新项目时的项目索引
     *
     * @return next_project_index - 部门下创建新项目时的项目索引
     */
    public Integer getNextProjectIndex() {
        return nextProjectIndex;
    }

    /**
     * 设置部门下创建新项目时的项目索引
     *
     * @param nextProjectIndex 部门下创建新项目时的项目索引
     */
    public void setNextProjectIndex(Integer nextProjectIndex) {
        this.nextProjectIndex = nextProjectIndex;
    }

    /**
     * 获取部门角色
     *
     * @return roles - 部门角色
     */
    public String getRoles() {
        return roles;
    }

    /**
     * 设置部门角色
     *
     * @param roles 部门角色
     */
    public void setRoles(String roles) {
        this.roles = roles;
    }

    /**
     * 获取上级部门名称
     *
     * @return superior_department - 上级部门名称
     */
    public String getSuperiorDepartment() {
        return superiorDepartment;
    }

    /**
     * 设置上级部门名称
     *
     * @param superiorDepartment 上级部门名称
     */
    public void setSuperiorDepartment(String superiorDepartment) {
        this.superiorDepartment = superiorDepartment;
    }

    /**
     * 获取下级部门名称
     *
     * @return inferior_department - 下级部门名称
     */
    public String getInferiorDepartment() {
        return inferiorDepartment;
    }

    /**
     * 设置下级部门名称
     *
     * @param inferiorDepartment 下级部门名称
     */
    public void setInferiorDepartment(String inferiorDepartment) {
        this.inferiorDepartment = inferiorDepartment;
    }

    /**
     * 获取描述
     *
     * @return describes - 描述
     */
    public String getDescribes() {
        return describes;
    }

    /**
     * 设置描述
     *
     * @param describes 描述
     */
    public void setDescribes(String describes) {
        this.describes = describes;
    }
}