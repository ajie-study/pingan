package com.pingan.starlink.model.jira;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pingan.starlink.util.JiraIssueFieldMap;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class IssueQueryResponseData implements Serializable {
    private Long id;
    private Long projectId;
    private Projects project;
    private String self;
    private Long created;
    private String creator;
    private String description;
    private Long dueDate;
    private String issueType;
    private String key;
    private List<ConfigScheme> fixVersions = new ArrayList<>();
    private List<String> labels = new ArrayList<>();
    private String priority;
    private String status;
    private String summary;
    private List<ConfigScheme> affectedVersions = new ArrayList<>();
    private Map<String, String> fields;
    private String subTaskCnt;
    private List<IssueQueryResponseData> children;

    public void transfFields(JiraIssueFieldMap jiraIssueFieldMap) {
        HashMap<String, String> keyFields = new HashMap<>();
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            String fieldKey = jiraIssueFieldMap.getKeyById(entry.getKey(), entry.getKey());
            keyFields.put(fieldKey, entry.getValue());
        }
        this.fields = keyFields;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Data
    public static class ConfigScheme implements Serializable {
        private Long id;
        private String name;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Data
    public static class Projects implements Serializable {
        private long id;
        private String name;
        private String key;
        private String url;
        private String email;
        private String leadUserName;
        private String description;
    }

}
