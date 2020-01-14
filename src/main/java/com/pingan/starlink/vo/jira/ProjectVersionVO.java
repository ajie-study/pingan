package com.pingan.starlink.vo.jira;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class ProjectVersionVO {

    @ApiModelProperty(name = "projectName",value = "项目名称")
    private String projectName;
    @ApiModelProperty(name = "projectKey",value = "项目Key")
    private String projectKey;
    @ApiModelProperty(name = "projectVersionName",value = "项目版本名称")
    private String projectVersionName;
    @ApiModelProperty(name = "releaseDate",value = "发版日期")
    private String releaseDate;
    @ApiModelProperty(name = "released",value = "是否发版")
    private boolean released;

    public ProjectVersionVO(){}

    public ProjectVersionVO(String projectName,String projectKey,String projectVersionName,String releaseDate,boolean released){
        this.projectName = projectName;
        this.projectKey = projectKey;
        this.projectVersionName = projectVersionName;
        this.releaseDate = releaseDate;
        this.released = released;
    }

}
