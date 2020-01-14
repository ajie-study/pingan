package com.pingan.starlink.vo.jira;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pingan.starlink.model.VersionManagement;
import com.pingan.starlink.model.jira.ProjectVersion;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VersionVO {

    @ApiModelProperty(name = "uuid", value = "唯一标识，不填", required = false)
    private String uuid;
    @ApiModelProperty(name = "versionName", value = "版本名称", required = true)
    private String versionName;
    @ApiModelProperty(name = "versionId", value = "版本号,不填", required = false)
    private String versionId;
    @ApiModelProperty(name = "versionType", value = "版本类型", required = false)
    private String versionType;
    @ApiModelProperty(name = "riskLevel", value = "风险等级", required = false)
    private String riskLevel;
    @ApiModelProperty(name = "testStatus", value = "测试状态", required = false)
    private String testStatus;
    @ApiModelProperty(name = "startDate", value = "开始日期", required = false)
    private Date startDate;
    @ApiModelProperty(name = "releaseDate", value = "发布日期", required = false)
    private String releaseDate;
    @ApiModelProperty(name = "createUser", value = "创建人", required = false)
    private String createUser;
    @ApiModelProperty(name = "createTime", value = "创建时间", required = false)
    private Date createTime;
    @ApiModelProperty(name = "developContacts", value = "开发联系人", required = false)
    private String developContacts;
    @ApiModelProperty(name = "dependentVersion", value = "依赖版本", required = false)
    private String dependentVersion;
    @ApiModelProperty(name = "testPlan", value = "测试计划", required = false)
    private String testPlan;
    @ApiModelProperty(name = "versionDescribe", value = "版本描述", required = false)
    private String versionDescribe;
    @ApiModelProperty(name = "projectKey", value = "所属项目", required = true)
    private String projectKey;
    @ApiModelProperty(name = "versionStatus", value = "版本状态", required = false)
    private String versionStatus;
    @ApiModelProperty(name = "issueNum", value = "问题的总数", required = false)
    private Integer issueNum;
    @ApiModelProperty(name = "releaseIssues", value = "选择要发布问题类型", required = false)
    private String releaseIssues;
    @ApiModelProperty(name = "versionManager", value = "版本负责人", required = false)
    private String versionManager;


    private ProjectVersion projectVersion;

    // versionNum项目在http创建成功的时候修改进去
    public VersionVO(VersionManagement versionManagement) {
        this.uuid = versionManagement.getUuid();
        this.createTime = versionManagement.getCreateTime();
        this.versionName = versionManagement.getVersionName();
        this.createUser = versionManagement.getCreateUser();
        this.developContacts = versionManagement.getDevelopContacts();
        this.dependentVersion = versionManagement.getDependentVersion();
        this.versionType = versionManagement.getVersionType();
        this.riskLevel = versionManagement.getRiskLevel();
        this.projectKey = versionManagement.getProjectKey();
        this.versionStatus = versionManagement.getVersionStatus();
        this.releaseDate = versionManagement.getReleaseDate();
        this.testStatus = versionManagement.getTestStatus();
        this.testPlan = versionManagement.getTestPlan();
        this.startDate = versionManagement.getStartDate();
        this.versionDescribe = versionManagement.getVersionDescribe();
        this.versionId = versionManagement.getVersionId();
        this.issueNum = versionManagement.getIssueNum();
        this.releaseIssues = versionManagement.getReleaseIssues();
        this.versionManager = versionManagement.getVersionManager();
    }

}
