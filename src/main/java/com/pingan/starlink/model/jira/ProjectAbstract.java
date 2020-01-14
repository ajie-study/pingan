package com.pingan.starlink.model.jira;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class ProjectAbstract implements Serializable {
    protected String expand;
    protected String self;
    protected String id;
    protected String key;
    protected String name;
    protected String projectTypeKey;
    protected Map<String, String> avatarUrls;
}
