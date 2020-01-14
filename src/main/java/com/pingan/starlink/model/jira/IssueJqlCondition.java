package com.pingan.starlink.model.jira;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class IssueJqlCondition {

    //and,or 对应IssueJqlRelation
    private String type;

    private List<IssueJqlConditionItem> items;

    private List<IssueJqlCondition> conds;

    private List<String> fields;

    public IssueJqlCondition(String type) {
        this.type = type;
    }

    public IssueJqlCondition addIssueJqlCondition(IssueJqlCondition issueJqlCondition) {
        if (conds == null) {
            conds = new ArrayList<>();
        }
        conds.add(issueJqlCondition);
        return this;
    }

    public IssueJqlCondition addIssueJqlConditionItem(IssueJqlConditionItem issueJqlConditionItem) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(issueJqlConditionItem);
        return this;
    }

    public IssueJqlCondition addItemProjectEqual(String projectKey) {
        this.addIssueJqlConditionItem(new IssueJqlConditionItem("project", "=", projectKey));
        return this;
    }

    public IssueJqlCondition addItemIssueTypeEqual(String issueType) {
        this.addIssueJqlConditionItem(new IssueJqlConditionItem("issueType", "=", issueType));
        return this;
    }

    public IssueJqlCondition addItemIssueKeyEqual(String issueKey) {
        this.addIssueJqlConditionItem(new IssueJqlConditionItem("issueKey", "=", issueKey));
        return this;
    }

    public IssueJqlCondition addItemCreatorEqual(String creator) {
        this.addIssueJqlConditionItem(new IssueJqlConditionItem("creator", "=", creator));
        return this;
    }

    public IssueJqlCondition addItemAssigneeEqual(String assignee) {
        this.addIssueJqlConditionItem(new IssueJqlConditionItem("assignee", "=", assignee));
        return this;
    }

    public IssueJqlCondition addItemStatusEqual(String status) {
        this.addIssueJqlConditionItem(new IssueJqlConditionItem("status", "=", status));
        return this;
    }

    public IssueJqlCondition addItemDueDateStart(String dueDateStart) {
        this.addIssueJqlConditionItem(new IssueJqlConditionItem("dueDate", ">=", dueDateStart));
        return this;
    }

    public IssueJqlCondition addItemDueDateEnd(String dueDateEnd) {
        this.addIssueJqlConditionItem(new IssueJqlConditionItem("dueDate", "<=", dueDateEnd));
        return this;
    }

    public IssueJqlCondition addItemDueFixVersion(String fixVersion) {
        this.addIssueJqlConditionItem(new IssueJqlConditionItem("fixVersion", "=", fixVersion));
        return this;
    }

}
