package com.pingan.starlink.util;

import com.pingan.starlink.model.IssueField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JiraIssueFieldMap {

    private Map<String, String> Id_Key;


    public JiraIssueFieldMap(List<IssueField> issueFields) {
        Id_Key = new HashMap<>();
        for (IssueField issueField : issueFields) {
            Id_Key.put(issueField.getFieldId(), issueField.getFieldKey());
        }
    }

    public List<String> getFieldIds() {
        return new ArrayList<String>(Id_Key.keySet());
    }

    public String getKeyById(String Id, String defaultKey) {
        if (Id_Key.containsKey(Id)) {
            return Id_Key.get(Id);
        } else {
            return defaultKey;
        }
    }
}
