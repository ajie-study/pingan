package com.pingan.starlink.model.jira;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class UserAbstract {
    protected String self;
    protected String name;
    protected String key;
    protected String emailAddress;
    protected Map<String, String> avatarUrls;
    protected String displayName;
    protected boolean active;
    protected String timeZone;
}
