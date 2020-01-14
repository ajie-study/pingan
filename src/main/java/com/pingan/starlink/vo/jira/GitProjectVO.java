package com.pingan.starlink.vo.jira;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pingan.starlink.model.GitProject;
import lombok.Data;
import org.gitlab4j.api.models.Project;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GitProjectVO extends GitProject {

    private Project Project;

    public GitProjectVO(GitProject gitProject) {

        this.gitProjectId = gitProject.getGitProjectId();
        this.gitNamespaceId = gitProject.getGitNamespaceId();
        this.gitProjectName = gitProject.getGitProjectName();
        this.gitProjectUrl = gitProject.getGitProjectUrl();
        this.gitProjectDescription = gitProject.getGitProjectDescription();
        this.gitProjectCreatedAt = gitProject.getGitProjectCreatedAt();
        this.uuid = gitProject.getUuid();
    }
}
