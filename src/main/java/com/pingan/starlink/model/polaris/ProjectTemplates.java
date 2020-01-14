package com.pingan.starlink.model.polaris;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class ProjectTemplates implements Serializable {

    private String key;
    private String name;

    public ProjectTemplates(){}

    public ProjectTemplates(String key,String name){
        this.key = key;
        this.name = name;
    }

}
