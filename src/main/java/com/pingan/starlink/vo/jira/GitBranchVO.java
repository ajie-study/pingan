package com.pingan.starlink.vo.jira;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pingan.starlink.model.GitBranch;
import lombok.Data;
import org.gitlab4j.api.models.Branch;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GitBranchVO extends GitBranch {

    private Branch branch;

    public GitBranchVO(GitBranch gitBranch) {

        this.gitProjectId = gitBranch.getGitProjectId();
        this.createdAt = gitBranch.getCreatedAt();
        this.description = gitBranch.getDescription();
        this.gitBranchName = gitBranch.getGitBranchName();
        this.release = gitBranch.getRelease();
        this.createdBy = gitBranch.getCreatedBy();
        this.uuid = gitBranch.getUuid();
        this.branchStatus = gitBranch.getBranchStatus();
    }
}
