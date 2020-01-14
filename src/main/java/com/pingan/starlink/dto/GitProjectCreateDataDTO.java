package com.pingan.starlink.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pingan.starlink.model.GitProject;
import com.pingan.starlink.model.git.GitProjectCreateData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class GitProjectCreateDataDTO {

    @JsonIgnore
    private String uuid;

    @ApiModelProperty(name = "gitgroupId", value = "gitlab当前用户下组的ID", required = true)
    protected Integer gitgroupId;
    @ApiModelProperty(name = "gitProjectName", value = "gitlab项目名称", required = true)
    protected String gitProjectName;
    @ApiModelProperty(name = "description", value = "gitlab项目描述", required = true)
    protected String description;

    @ApiModelProperty(name = "jiraProjectKey", value = "与jira关联", required = true)
    protected String jiraProjectKey;

    @JsonIgnore
    private String gitProjectUrl;
    @JsonIgnore
    private Integer gitProjectId;
    @JsonIgnore
    private Date gitProjectCreatedAt;


    @JsonIgnore
    public GitProjectCreateData getGitProjectCreateData() {

        GitProjectCreateData gitProjectCreateData = new GitProjectCreateData();


        gitProjectCreateData.setGitProjectName(gitProjectName);
        gitProjectCreateData.setNamespaceId(gitgroupId);
        gitProjectCreateData.setDescription(description);

        return gitProjectCreateData;
    }

    @JsonIgnore
    public GitProject getGitProjectDB() {

        GitProject gitProject = new GitProject();

        gitProject.setUuid(uuid);
        gitProject.setGitProjectId(gitProjectId);
        gitProject.setGitNamespaceId(gitgroupId);
        gitProject.setGitProjectName(gitProjectName);
        gitProject.setGitProjectUrl(gitProjectUrl);
        gitProject.setGitProjectDescription(description);
        gitProject.setGitProjectCreatedAt(gitProjectCreatedAt);

        return gitProject;
    }
}
