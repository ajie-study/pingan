package com.pingan.starlink.model.jira;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class ProjectDetail extends ProjectAbstract {
    private String description;
    private Lead lead;
    private List<Object>  components;
    private List<IssueType> issueTypes;
    private String assigneeType;
    private List<ProjectVersion> versions;
    private boolean arrchived;
    private Map<String, Object> roles;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Data
    public static class Lead implements Serializable {
        private String self;
        private String key;
        private String name;
        private Map<String, String> avatarUrls;
        private String displayName;
        private boolean active;
    }
}
