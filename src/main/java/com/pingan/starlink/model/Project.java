package com.pingan.starlink.model;

import java.util.Date;
import javax.persistence.*;

public class Project {
    /**
     * UUID唯一标识符
     */
    @Id
    private String uuid;

    /**
     * 项目Key
     */
    @Column(name = "project_key")
    private String projectKey;

    /**
     * 项目名称
     */
    @Column(name = "project_name")
    private String projectName;

    /**
     * 项目模板
     */
    @Column(name = "project_template_key")
    private String projectTemplateKey;

    /**
     * 描述
     */
    private String description;

    /**
     * 项目领导
     */
    private String lead;

    /**
     * 被分配的
     */
    @Column(name = "assigneeType")
    private String assigneetype;

    /**
     * 项目类型
     */
    @Column(name = "project_type")
    private String projectType;

    /**
     * 系统
     */
    private String system;

    /**
     * 子系统
     */
    @Column(name = "sub_system")
    private String subSystem;

    /**
     * 开发模式
     */
    @Column(name = "dev_mode")
    private String devMode;

    /**
     * 主办系统
     */
    @Column(name = "host_system")
    private String hostSystem;

    /**
     * 辅办系统
     */
    @Column(name = "auxiliary_system")
    private String auxiliarySystem;

    /**
     * 开发组
     */
    @Column(name = "dev_group")
    private String devGroup;

    /**
     * PMO
     */
    private String pmo;

    /**
     * 空间状态
     */
    private String status;

    /**
     * 结项日期
     */
    @Column(name = "end_date")
    private Date endDate;

    /**
     * 计划结束日期
     */
    @Column(name = "plan_end_date")
    private Date planEndDate;

    /**
     * 立项日期
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 项目owner
     */
    private String owner;

    /**
     * 计划开始日期
     */
    @Column(name = "plan_start_date")
    private Date planStartDate;

    /**
     * 仓库地址
     */
    @Column(name = "artifactory_url")
    private String artifactoryUrl;

    /**
     * 所属部门
     */
    private String department;

    /**
     * 项目创建状态
     */
    @Column(name = "project_status")
    private String projectStatus;

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
     * 获取项目Key
     *
     * @return project_key - 项目Key
     */
    public String getProjectKey() {
        return projectKey;
    }

    /**
     * 设置项目Key
     *
     * @param projectKey 项目Key
     */
    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    /**
     * 获取项目名称
     *
     * @return project_name - 项目名称
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * 设置项目名称
     *
     * @param projectName 项目名称
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * 获取项目模板
     *
     * @return project_template_key - 项目模板
     */
    public String getProjectTemplateKey() {
        return projectTemplateKey;
    }

    /**
     * 设置项目模板
     *
     * @param projectTemplateKey 项目模板
     */
    public void setProjectTemplateKey(String projectTemplateKey) {
        this.projectTemplateKey = projectTemplateKey;
    }

    /**
     * 获取描述
     *
     * @return description - 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取项目领导
     *
     * @return lead - 项目领导
     */
    public String getLead() {
        return lead;
    }

    /**
     * 设置项目领导
     *
     * @param lead 项目领导
     */
    public void setLead(String lead) {
        this.lead = lead;
    }

    /**
     * 获取被分配的
     *
     * @return assigneeType - 被分配的
     */
    public String getAssigneetype() {
        return assigneetype;
    }

    /**
     * 设置被分配的
     *
     * @param assigneetype 被分配的
     */
    public void setAssigneetype(String assigneetype) {
        this.assigneetype = assigneetype;
    }

    /**
     * 获取项目类型
     *
     * @return project_type - 项目类型
     */
    public String getProjectType() {
        return projectType;
    }

    /**
     * 设置项目类型
     *
     * @param projectType 项目类型
     */
    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    /**
     * 获取系统
     *
     * @return system - 系统
     */
    public String getSystem() {
        return system;
    }

    /**
     * 设置系统
     *
     * @param system 系统
     */
    public void setSystem(String system) {
        this.system = system;
    }

    /**
     * 获取子系统
     *
     * @return sub_system - 子系统
     */
    public String getSubSystem() {
        return subSystem;
    }

    /**
     * 设置子系统
     *
     * @param subSystem 子系统
     */
    public void setSubSystem(String subSystem) {
        this.subSystem = subSystem;
    }

    /**
     * 获取开发模式
     *
     * @return dev_mode - 开发模式
     */
    public String getDevMode() {
        return devMode;
    }

    /**
     * 设置开发模式
     *
     * @param devMode 开发模式
     */
    public void setDevMode(String devMode) {
        this.devMode = devMode;
    }

    /**
     * 获取主办系统
     *
     * @return host_system - 主办系统
     */
    public String getHostSystem() {
        return hostSystem;
    }

    /**
     * 设置主办系统
     *
     * @param hostSystem 主办系统
     */
    public void setHostSystem(String hostSystem) {
        this.hostSystem = hostSystem;
    }

    /**
     * 获取辅办系统
     *
     * @return auxiliary_system - 辅办系统
     */
    public String getAuxiliarySystem() {
        return auxiliarySystem;
    }

    /**
     * 设置辅办系统
     *
     * @param auxiliarySystem 辅办系统
     */
    public void setAuxiliarySystem(String auxiliarySystem) {
        this.auxiliarySystem = auxiliarySystem;
    }

    /**
     * 获取开发组
     *
     * @return dev_group - 开发组
     */
    public String getDevGroup() {
        return devGroup;
    }

    /**
     * 设置开发组
     *
     * @param devGroup 开发组
     */
    public void setDevGroup(String devGroup) {
        this.devGroup = devGroup;
    }

    /**
     * 获取PMO
     *
     * @return pmo - PMO
     */
    public String getPmo() {
        return pmo;
    }

    /**
     * 设置PMO
     *
     * @param pmo PMO
     */
    public void setPmo(String pmo) {
        this.pmo = pmo;
    }

    /**
     * 获取空间状态
     *
     * @return status - 空间状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置空间状态
     *
     * @param status 空间状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取结项日期
     *
     * @return end_date - 结项日期
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 设置结项日期
     *
     * @param endDate 结项日期
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 获取计划结束日期
     *
     * @return plan_end_date - 计划结束日期
     */
    public Date getPlanEndDate() {
        return planEndDate;
    }

    /**
     * 设置计划结束日期
     *
     * @param planEndDate 计划结束日期
     */
    public void setPlanEndDate(Date planEndDate) {
        this.planEndDate = planEndDate;
    }

    /**
     * 获取立项日期
     *
     * @return create_date - 立项日期
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置立项日期
     *
     * @param createDate 立项日期
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取项目owner
     *
     * @return owner - 项目owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * 设置项目owner
     *
     * @param owner 项目owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * 获取计划开始日期
     *
     * @return plan_start_date - 计划开始日期
     */
    public Date getPlanStartDate() {
        return planStartDate;
    }

    /**
     * 设置计划开始日期
     *
     * @param planStartDate 计划开始日期
     */
    public void setPlanStartDate(Date planStartDate) {
        this.planStartDate = planStartDate;
    }

    /**
     * 获取仓库地址
     *
     * @return artifactory_url - 仓库地址
     */
    public String getArtifactoryUrl() {
        return artifactoryUrl;
    }

    /**
     * 设置仓库地址
     *
     * @param artifactoryUrl 仓库地址
     */
    public void setArtifactoryUrl(String artifactoryUrl) {
        this.artifactoryUrl = artifactoryUrl;
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
     * 获取项目创建状态
     *
     * @return project_status - 项目创建状态
     */
    public String getProjectStatus() {
        return projectStatus;
    }

    /**
     * 设置项目创建状态
     *
     * @param projectStatus 项目创建状态
     */
    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }
}