package com.pingan.starlink.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pingan.starlink.enums.StatusEnum;
import com.pingan.starlink.model.GitBranch;
import com.pingan.starlink.model.git.GitBranchCreateData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class GitBranchCreateDataDTO {

    @JsonIgnore
    private String uuid;
    @ApiModelProperty(name = "gitProjectId", value = "gitlab项目Id", required = true)
    protected Integer gitProjectId;
    @ApiModelProperty(name = "gitBranchName", value = "gitlab分支名称", required = true)
    protected String gitBranchName;
    @ApiModelProperty(name = "ref", value = "Source to create the branch from", required = true)
    protected String ref;
    @ApiModelProperty(name = "description", value = "分支描述", required = false)
    protected String description;
    @ApiModelProperty(name = "createdBy", value = "分支创建人", required = false)
    protected String createdBy;
    @ApiModelProperty(name = "commitBy", value = "最近提交人", required = false)
    protected String commitBy;
    @ApiModelProperty(name = "commitAt", value = "最近提交时间", required = false)
    protected Date commitAt;


    @JsonIgnore
    @ApiModelProperty(name = "createAt", value = "分支创建时间", required = false)
    protected Date createAt;

    @ApiModelProperty(name = "release", value = "是否发版", required = true)
    protected Boolean release;

    @JsonIgnore
    public GitBranchCreateData getGitBranchCreateData() {

        GitBranchCreateData gitBranchCreateData = new GitBranchCreateData();
        gitBranchCreateData.setBranchName(gitBranchName);
        gitBranchCreateData.setGitProjectId(gitProjectId);
        gitBranchCreateData.setRef(ref);

        return gitBranchCreateData;
    }

    @JsonIgnore
    public GitBranch getGitBranchDB() {
        GitBranch gitBranch = new GitBranch();

        gitBranch.setUuid(uuid);
        gitBranch.setGitBranchName(gitBranchName);
        gitBranch.setGitProjectId(gitProjectId);
        gitBranch.setDescription(description);
        gitBranch.setCreatedBy(createdBy);
        gitBranch.setCreatedAt(createAt);
        gitBranch.setRelease(release);
        gitBranch.setCommitBy(commitBy);
        gitBranch.setCommitAt(commitAt);
        gitBranch.setBranchStatus(StatusEnum.OPEN_STATUS.getStatus());

        return gitBranch;
    }


}
