package com.pingan.starlink.model.jira;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class ProjectCreateData implements Serializable {
    private String key;
    private String name;
    private String projectTypeKey;
    private String projectTemplateKey;
    private String description;
    private String lead;
    private String url;
    private String assigneeType;
    @JsonIgnore
    private int avatarId;
    @JsonIgnore
    private int issueSecurityScheme;
    @JsonIgnore
    private int permissionScheme;
    @JsonIgnore
    private int notificationScheme;
    @JsonIgnore
    private int categoryId;
}
