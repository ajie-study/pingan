package com.pingan.starlink.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pingan.starlink.model.VersionManagement;
import com.pingan.starlink.model.jira.VersionCreateData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class VersionCreateDataDTO {

    @ApiModelProperty(name = "versionName", value = "版本名称", required = true)
    private String versionName;
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
    @ApiModelProperty(name = "versionManager", value = "版本负责人", required = false)
    private String versionManager;
//    @ApiModelProperty(name = "issueNum", value = "问题的总数", required = false)
//    private Integer issueNum;
//    @ApiModelProperty(name = "releaseIssues", value = "选择要发布问题类型", required = false)
//    private String releaseIssues;


    public VersionCreateData versionCreateData() {
        VersionCreateData versionCreateData = new VersionCreateData();
        versionCreateData.setName(versionName);
        versionCreateData.setProject(projectKey);
        //发版日期 选填 必须是日期形式
        versionCreateData.setReleaseDate(releaseDate);
        return versionCreateData;
    }

    // versionNum项目在http创建成功的时候修改进去
    public VersionManagement versionManagement() {
        VersionManagement versionManagement = new VersionManagement();
        versionManagement.setCreateTime(createTime);
        versionManagement.setVersionName(versionName);
        versionManagement.setCreateUser(createUser);
        versionManagement.setDevelopContacts(developContacts);
        versionManagement.setDependentVersion(dependentVersion);
        versionManagement.setVersionType(versionType);
        versionManagement.setRiskLevel(riskLevel);
        versionManagement.setProjectKey(projectKey);
        versionManagement.setReleaseDate(releaseDate);
        versionManagement.setTestStatus(testStatus);
        versionManagement.setTestPlan(testPlan);
        versionManagement.setStartDate(startDate);
        versionManagement.setVersionDescribe(versionDescribe);
        versionManagement.setVersionManager(versionManager);
        return versionManagement;
    }

}
