package com.pingan.starlink.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pingan.starlink.model.Project;
import com.pingan.starlink.model.UserInfo4A;
import com.pingan.starlink.model.jira.ProjectCreateData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class ProjectCreateDataDTO {

    @ApiModelProperty(name = "projectKey", value = "项目的Key", required = true)
    private String projectKey;
    @ApiModelProperty(name = "projectName", value = "项目的名称", required = true)
    private String projectName;
    @ApiModelProperty(name = "projectTemplateKey", value = "调用模板接口,不写系统有默认模板", required = true)
    private String projectTemplateKey;
    //@ApiModelProperty(name = "description",value = "项目的描述",required = false)
    @JsonIgnore
    private String description;
    @ApiModelProperty(name = "lead", value = "项目经理", required = true)
    private UserInfo4A lead;
    @ApiModelProperty(name = "assigneetype", value = "枚举被分配的", example = "PROJECT_LEAD")
    private String assigneetype;
    @ApiModelProperty(name = "projectType", value = "项目类型", required = false)
    private String projectType;
    @ApiModelProperty(name = "system", value = "系统", required = false)
    private String system;
    @ApiModelProperty(name = "subSystem", value = "子系统", required = false)
    private String subSystem;
    @ApiModelProperty(name = "devMode", value = "开发模式", required = false)
    private String devMode;
    @ApiModelProperty(name = "hostSystem", value = "主办系统", required = false)
    private String hostSystem;
    @ApiModelProperty(name = "auxiliarySystem", value = "辅办系统", required = false)
    private String auxiliarySystem;
    @ApiModelProperty(name = "devGroup", value = "开发组", required = false)
    private String devGroup;
    @ApiModelProperty(name = "pmo", value = "pmo", required = false)
    private String pmo;
    @ApiModelProperty(name = "status", value = "空间状态", required = false)
    private String status;
    @ApiModelProperty(name = "endDate", value = "结项日期", required = false)
    private Date endDate;
    @ApiModelProperty(name = "planEndDate", value = "计划结束日期", required = false)
    private Date planEndDate;
    @ApiModelProperty(name = "createDate", value = "立项日期", required = false)
    private Date createDate;
    @ApiModelProperty(name = "owner", value = "项目owner", required = false)
    private UserInfo4A owner;
    @ApiModelProperty(name = "planStartDate", value = "计划开始日期", required = false)
    private Date planStartDate;
    @ApiModelProperty(name = "artifactoryUrl", value = "仓库地址", required = false)
    private String artifactoryUrl;
    @ApiModelProperty(name = "department", value = "所属部门", required = false)
    private String department;


    //    @ApiIgnore //整个方法都被忽略了 对应的ApiModel都不显示了
//    @JsonIgnore
    public ProjectCreateData projectCreateData() {
        this.valid();
        ProjectCreateData projectCreateData = new ProjectCreateData();
        projectCreateData.setKey(projectKey);
        projectCreateData.setName(projectName);
        projectCreateData.setProjectTemplateKey(projectTemplateKey);
        projectCreateData.setDescription(description);
        projectCreateData.setLead(lead.getUsername());
        projectCreateData.setAssigneeType(assigneetype);
        return projectCreateData;
    }

    private void valid() {
        if (assigneetype == null || assigneetype.isEmpty()) {
            assigneetype = "PROJECT_LEAD";
        }
    }

    //    @JsonIgnore //不展示这个字段
    public Project ProjectDB() {
        this.valid();
        Project project = new Project();
        project.setProjectKey(projectKey);
        project.setProjectName(projectName);
        project.setProjectTemplateKey(projectTemplateKey);
        project.setDescription(description);
        project.setLead(lead.getUsername());
        project.setAssigneetype(assigneetype);
        project.setProjectType(projectType);
        project.setSystem(system);
        project.setSubSystem(subSystem);
        project.setDevMode(devMode);
        project.setHostSystem(hostSystem);
        project.setAuxiliarySystem(auxiliarySystem);
        project.setDevGroup(devGroup);
        project.setPmo(pmo);
        project.setStatus(status);
        project.setEndDate(endDate);
        project.setPlanEndDate(planEndDate);
        project.setCreateDate(createDate);
        project.setOwner(owner.getUsername());
        project.setPlanStartDate(planStartDate);
        project.setDepartment(department);
        project.setArtifactoryUrl(artifactoryUrl);
        return project;
    }

}
