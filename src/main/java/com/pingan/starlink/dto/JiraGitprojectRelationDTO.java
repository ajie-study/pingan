package com.pingan.starlink.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pingan.starlink.model.JiraGitprojectRelation;
import com.pingan.starlink.vo.jira.JiraGitprojectRelationVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class JiraGitprojectRelationDTO {


    @ApiModelProperty(name = "jiraProjectKey", value = "jira项目的唯一标识key", required = true)
    protected String jiraProjectKey;
    @ApiModelProperty(name = "gitProjectUrl", value = "project地址")
    protected String gitProjectUrl;
    @ApiModelProperty(name = "gitProjectId", value = "gitlab生成的id")
    protected Integer gitProjectId;

    @JsonIgnore
    public JiraGitprojectRelation getJiraGitprojectRelation() {

        JiraGitprojectRelation jiraGitprojectRelation = new JiraGitprojectRelation();
        jiraGitprojectRelation.setJiraProjectKey(jiraProjectKey);
        jiraGitprojectRelation.setGitProjectId(gitProjectId);
        return jiraGitprojectRelation;
    }

}
