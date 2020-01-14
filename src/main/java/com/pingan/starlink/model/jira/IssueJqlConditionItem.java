package com.pingan.starlink.model.jira;

import lombok.Data;

@Data
public class IssueJqlConditionItem {

    private String key;

    private Object value;

    private String operator;

    public IssueJqlConditionItem(String key, String operator, Object value) {
        this.key = key;
        this.operator = operator;
        this.value = value;
    }

}
