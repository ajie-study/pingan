package com.pingan.starlink.vo.jira;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pingan.starlink.model.Project;
import com.pingan.starlink.model.UserInfo4A;
import com.pingan.starlink.model.jira.ProjectDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProjectVO {

    @ApiModelProperty(name = "uuid", value = "项目的唯一标识", required = false)
    private String uuid;
    @ApiModelProperty(name = "projectKey", value = "项目的Key", required = false)
    private String projectKey;
    @ApiModelProperty(name = "projectName", value = "项目的名称", required = false)
    private String projectName;
    @ApiModelProperty(name = "projectTemplateKey", value = "调用模板接口,不写系统有默认模板", required = false)
    private String projectTemplateKey;
    @ApiModelProperty(name = "description", value = "项目的描述", required = false)
    private String description;
    @ApiModelProperty(name = "lead", value = "项目经理", required = false)
    private UserInfo4A lead = new UserInfo4A();
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
    private UserInfo4A owner = new UserInfo4A();
    @ApiModelProperty(name = "planStartDate", value = "计划开始日期", required = false)
    private Date planStartDate;
    @ApiModelProperty(name = "artifactoryUrl", value = "仓库地址", required = false)
    private String artifactoryUrl;
    @ApiModelProperty(name = "department", value = "所属部门", required = false)
    private String department;
    @ApiModelProperty(name = "projectStatus", value = "创建项目时的状态", required = false)
    private String projectStatus;

    private ProjectDetail projectDetail;

    private void valid() {
        if (assigneetype == null || assigneetype.isEmpty()) {
            assigneetype = "PROJECT_LEAD";
        }
    }

    public ProjectVO(Project project) {
        this.valid();
        this.uuid = project.getUuid();
        this.projectKey = project.getProjectKey();
        this.projectName = project.getProjectName();
        this.projectTemplateKey = project.getProjectTemplateKey();
        this.description = project.getDescription();
        this.lead.setUsername(project.getLead());
        this.assigneetype = project.getAssigneetype();
        this.projectType = project.getProjectType();
        this.system = project.getSystem();
        this.subSystem = project.getSubSystem();
        this.devMode = project.getDevMode();
        this.hostSystem = project.getHostSystem();
        this.auxiliarySystem = project.getAuxiliarySystem();
        this.devGroup = project.getDevGroup();
        this.pmo = project.getPmo();
        this.status = project.getStatus();
        this.endDate = project.getEndDate();
        this.planEndDate = project.getPlanEndDate();
        this.createDate = project.getCreateDate();
        this.owner.setUsername(project.getOwner());
        this.planStartDate = project.getPlanStartDate();
        this.artifactoryUrl = project.getArtifactoryUrl();
        this.department = project.getDepartment();
        this.projectStatus = project.getProjectStatus();
    }
}
