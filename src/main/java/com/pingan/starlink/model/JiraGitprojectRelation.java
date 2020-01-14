package com.pingan.starlink.model;

import javax.persistence.*;

@Table(name = "jira_gitproject_relation")
public class JiraGitprojectRelation {
    /**
     * UUID唯一标识符
     */
    @Id
    protected String uuid;

    /**
     * gitlab生成的id
     */
    @Column(name = "git_project_id")
    protected Integer gitProjectId;

    /**
     * jira项目的唯一标识key
     */
    @Column(name = "jira_project_key")
    protected String jiraProjectKey;

    /**
     * 获取UUID唯一标识符
     *
     * @return uuid - UUID唯一标识符
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * 设置UUID唯一标识符
     *
     * @param uuid UUID唯一标识符
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * 获取gitlab生成的id
     *
     * @return git_project_id - gitlab生成的id
     */
    public Integer getGitProjectId() {
        return gitProjectId;
    }

    /**
     * 设置gitlab生成的id
     *
     * @param gitProjectId gitlab生成的id
     */
    public void setGitProjectId(Integer gitProjectId) {
        this.gitProjectId = gitProjectId;
    }

    /**
     * 获取jira项目的唯一标识key
     *
     * @return jira_project_key - jira项目的唯一标识key
     */
    public String getJiraProjectKey() {
        return jiraProjectKey;
    }

    /**
     * 设置jira项目的唯一标识key
     *
     * @param jiraProjectKey jira项目的唯一标识key
     */
    public void setJiraProjectKey(String jiraProjectKey) {
        this.jiraProjectKey = jiraProjectKey;
    }

    @Override
    public String toString() {
        return "JiraGitprojectRelation{" +
                "uuid='" + uuid + '\'' +
                ", gitProjectId=" + gitProjectId +
                ", jiraProjectKey='" + jiraProjectKey + '\'' +
                '}';
    }
}