package com.pingan.starlink.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "version_management")
public class VersionManagement {
    /**
     * UUID唯一标识符
     */
    @Id
    private String uuid;

    /**
     * 版本号
     */
    @Column(name = "version_name")
    private String versionName;

    /**
     * 版本id
     */
    @Column(name = "version_id")
    private String versionId;

    /**
     * 版本类型
     */
    @Column(name = "version_type")
    private String versionType;

    /**
     * 风险等级
     */
    @Column(name = "risk_level")
    private String riskLevel;

    /**
     * 测试状态
     */
    @Column(name = "test_status")
    private String testStatus;

    /**
     * 开始日期
     */
    @Column(name = "start_date")
    private Date startDate;

    /**
     * 发布日期
     */
    @Column(name = "release_date")
    private String releaseDate;

    /**
     * 创建人
     */
    @Column(name = "create_user")
    private String createUser;

    /**
     * 版本创建时间 默认当前时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 开发联系人
     */
    @Column(name = "develop_contacts")
    private String developContacts;

    /**
     * 依赖版本
     */
    @Column(name = "dependent_version")
    private String dependentVersion;

    /**
     * 测试计划
     */
    @Column(name = "test_plan")
    private String testPlan;

    /**
     * 版本描述
     */
    @Column(name = "version_describe")
    private String versionDescribe;

    /**
     * 所属项目
     */
    @Column(name = "project_key")
    private String projectKey;

    /**
     * 版本状态
     */
    @Column(name = "version_status")
    private String versionStatus;

    /**
     * 问题的总数
     */
    @Column(name = "issue_num")
    private Integer issueNum;

    /**
     * 选择要发布问题类型
     */
    @Column(name = "release_issues")
    private String releaseIssues;

    /**
     * 版本负责人
     */
    @Column(name = "version_manager")
    private String versionManager;

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
     * 获取版本号
     *
     * @return version_name - 版本号
     */
    public String getVersionName() {
        return versionName;
    }

    /**
     * 设置版本号
     *
     * @param versionName 版本号
     */
    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    /**
     * 获取版本id
     *
     * @return version_id - 版本id
     */
    public String getVersionId() {
        return versionId;
    }

    /**
     * 设置版本id
     *
     * @param versionId 版本id
     */
    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    /**
     * 获取版本类型
     *
     * @return version_type - 版本类型
     */
    public String getVersionType() {
        return versionType;
    }

    /**
     * 设置版本类型
     *
     * @param versionType 版本类型
     */
    public void setVersionType(String versionType) {
        this.versionType = versionType;
    }

    /**
     * 获取风险等级
     *
     * @return risk_level - 风险等级
     */
    public String getRiskLevel() {
        return riskLevel;
    }

    /**
     * 设置风险等级
     *
     * @param riskLevel 风险等级
     */
    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    /**
     * 获取测试状态
     *
     * @return test_status - 测试状态
     */
    public String getTestStatus() {
        return testStatus;
    }

    /**
     * 设置测试状态
     *
     * @param testStatus 测试状态
     */
    public void setTestStatus(String testStatus) {
        this.testStatus = testStatus;
    }

    /**
     * 获取开始日期
     *
     * @return start_date - 开始日期
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 设置开始日期
     *
     * @param startDate 开始日期
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 获取发布日期
     *
     * @return release_date - 发布日期
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * 设置发布日期
     *
     * @param releaseDate 发布日期
     */
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * 获取创建人
     *
     * @return create_user - 创建人
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 设置创建人
     *
     * @param createUser 创建人
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 获取版本创建时间 默认当前时间
     *
     * @return create_time - 版本创建时间 默认当前时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置版本创建时间 默认当前时间
     *
     * @param createTime 版本创建时间 默认当前时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取开发联系人
     *
     * @return develop_contacts - 开发联系人
     */
    public String getDevelopContacts() {
        return developContacts;
    }

    /**
     * 设置开发联系人
     *
     * @param developContacts 开发联系人
     */
    public void setDevelopContacts(String developContacts) {
        this.developContacts = developContacts;
    }

    /**
     * 获取依赖版本
     *
     * @return dependent_version - 依赖版本
     */
    public String getDependentVersion() {
        return dependentVersion;
    }

    /**
     * 设置依赖版本
     *
     * @param dependentVersion 依赖版本
     */
    public void setDependentVersion(String dependentVersion) {
        this.dependentVersion = dependentVersion;
    }

    /**
     * 获取测试计划
     *
     * @return test_plan - 测试计划
     */
    public String getTestPlan() {
        return testPlan;
    }

    /**
     * 设置测试计划
     *
     * @param testPlan 测试计划
     */
    public void setTestPlan(String testPlan) {
        this.testPlan = testPlan;
    }

    /**
     * 获取版本描述
     *
     * @return version_describe - 版本描述
     */
    public String getVersionDescribe() {
        return versionDescribe;
    }

    /**
     * 设置版本描述
     *
     * @param versionDescribe 版本描述
     */
    public void setVersionDescribe(String versionDescribe) {
        this.versionDescribe = versionDescribe;
    }

    /**
     * 获取所属项目
     *
     * @return project_key - 所属项目
     */
    public String getProjectKey() {
        return projectKey;
    }

    /**
     * 设置所属项目
     *
     * @param projectKey 所属项目
     */
    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    /**
     * 获取版本状态
     *
     * @return version_status - 版本状态
     */
    public String getVersionStatus() {
        return versionStatus;
    }

    /**
     * 设置版本状态
     *
     * @param versionStatus 版本状态
     */
    public void setVersionStatus(String versionStatus) {
        this.versionStatus = versionStatus;
    }

    /**
     * 获取问题的总数
     *
     * @return issue_num - 问题的总数
     */
    public Integer getIssueNum() {
        return issueNum;
    }

    /**
     * 设置问题的总数
     *
     * @param issueNum 问题的总数
     */
    public void setIssueNum(Integer issueNum) {
        this.issueNum = issueNum;
    }

    /**
     * 获取选择要发布问题类型
     *
     * @return release_issues - 选择要发布问题类型
     */
    public String getReleaseIssues() {
        return releaseIssues;
    }

    /**
     * 设置选择要发布问题类型
     *
     * @param releaseIssues 选择要发布问题类型
     */
    public void setReleaseIssues(String releaseIssues) {
        this.releaseIssues = releaseIssues;
    }

    /**
     * 获取版本负责人
     *
     * @return version_manager - 版本负责人
     */
    public String getVersionManager() {
        return versionManager;
    }

    /**
     * 设置版本负责人
     *
     * @param versionManager 版本负责人
     */
    public void setVersionManager(String versionManager) {
        this.versionManager = versionManager;
    }
}