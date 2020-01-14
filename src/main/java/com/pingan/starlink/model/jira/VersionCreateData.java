package com.pingan.starlink.model.jira;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class VersionCreateData {

    private String description;
    private String name;
    private boolean archived;
    private boolean released;
    private String releaseDate;
    private String userReleaseDate;
    private String project;
    @JsonIgnore
    private int projectId;

}
