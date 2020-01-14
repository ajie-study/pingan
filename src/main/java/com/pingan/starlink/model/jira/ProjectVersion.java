package com.pingan.starlink.model.jira;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class ProjectVersion implements Serializable {
    private String self;
    private String id;
    private String description;
    private String name;
    private boolean archived;
    private boolean released;
    private String startDate;
    private String releaseDate;
    private boolean overdue;
    private String userStartDate;
    private String userReleaseDate;
    private int projectId;
}
