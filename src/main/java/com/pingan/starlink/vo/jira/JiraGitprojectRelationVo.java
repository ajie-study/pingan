package com.pingan.starlink.vo.jira;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pingan.starlink.model.JiraGitprojectRelation;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class JiraGitprojectRelationVo extends JiraGitprojectRelation{

    public JiraGitprojectRelationVo(){

    }

    public JiraGitprojectRelationVo(JiraGitprojectRelation jiraGitprojectRelation){
        this.uuid = jiraGitprojectRelation.getUuid();
        this.gitProjectId = jiraGitprojectRelation.getGitProjectId();
        this.jiraProjectKey = jiraGitprojectRelation.getJiraProjectKey();
    }

}
