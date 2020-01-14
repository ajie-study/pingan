package com.pingan.starlink.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pingan.starlink.model.GitPullRequests;
import com.pingan.starlink.model.git.GitPullrequestsCreateData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class GitpullreqeustsDTO {
    @ApiModelProperty(name = "gitProjectId", value = "代码仓库唯一标识", required = true)
    protected Integer gitProjectId;
    @ApiModelProperty(name = "sourceBranch", value = "源分支", required = true)
    protected String sourceBranch;
    @ApiModelProperty(name = "targetBranch", value = "目标分支", required = true)
    protected String targetBranch;
    @ApiModelProperty(name = "title", value = "pr标题", required = true)
    protected String title;
    @ApiModelProperty(name = "jiraProjectKey", value = "jira项目唯一标识", required = true)
    protected String jiraProjectKey;
    @ApiModelProperty(name = "gitPrDescribe", value = "pr的描述", required = false)
    protected String gitPrDescribe;
    @ApiModelProperty(name = "gitPrAssignee", value = "pr审阅者", required = false)
    protected String gitPrAssignee;
    @ApiModelProperty(name = "jiraIssue", value = "jira的issue", required = false)
    protected String jiraIssue;
    @ApiModelProperty(name = "gitPrCreatedBy", value = "pr的创建人", required = true)
    protected String gitPrCreatedBy;

    @JsonIgnore
    private String uuid;
    @JsonIgnore
    private String gitPrStatus;
    @JsonIgnore
    private Integer gitPrId;
    @JsonIgnore
    private Date gitPrCreatedAt;
    @JsonIgnore
    private Integer gitPrIid;

    @JsonIgnore
    public GitPullrequestsCreateData getGitPullrequestsCreateData() {

        GitPullrequestsCreateData gitPullrequestsData = new GitPullrequestsCreateData();

        gitPullrequestsData.setProjectId(gitProjectId);
        gitPullrequestsData.setSourceBranch(sourceBranch);
        gitPullrequestsData.setTargetBranch(targetBranch);
        gitPullrequestsData.setTitle(title);
        gitPullrequestsData.setDescription(gitPrDescribe);

        return gitPullrequestsData;
    }

    @JsonIgnore
    public GitPullRequests getGitpullrequestsDB() {

        GitPullRequests gitPullRequests = new GitPullRequests();

        gitPullRequests.setGitPrCreateat(gitPrCreatedAt);
        gitPullRequests.setGitPrCreateby(gitPrCreatedBy);
        gitPullRequests.setGitPrId(gitPrId);
        gitPullRequests.setGitPrIid(gitPrIid);
        gitPullRequests.setGitPrStatus(gitPrStatus);
        gitPullRequests.setGitPrTitle(title);
        gitPullRequests.setUuid(uuid);
        gitPullRequests.setJiraProjectKey(jiraProjectKey);
        gitPullRequests.setGitProjectId(gitProjectId);
        gitPullRequests.setGitPrAssignee(gitPrAssignee);
        gitPullRequests.setJiraIssue(jiraIssue);
        gitPullRequests.setGitPrDescribe(gitPrDescribe);
        gitPullRequests.setGitPrTarget(targetBranch);
        gitPullRequests.setGitPrSource(sourceBranch);
        return gitPullRequests;
    }
}
