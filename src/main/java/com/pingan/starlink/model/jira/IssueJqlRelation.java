package com.pingan.starlink.model.jira;

public enum IssueJqlRelation {

        AND("and"), OR("or");

    private String relation;

    private IssueJqlRelation(String relation) {
        this.relation = relation;
    }

    public static IssueJqlRelation get(String relation) {

        if (relation == null) return null;

        for (IssueJqlRelation issueJqlRelation : IssueJqlRelation.values()) {
            if (issueJqlRelation.getRelation().equalsIgnoreCase(relation.trim())) {
                return issueJqlRelation;
            }
        }

        return null;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
