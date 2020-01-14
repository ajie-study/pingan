package com.pingan.starlink.model.git;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class GitBranchCreateData {

    private Integer gitProjectId;
    private String branchName;
    private String ref;


}