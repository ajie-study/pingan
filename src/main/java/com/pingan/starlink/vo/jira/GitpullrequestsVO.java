package com.pingan.starlink.vo.jira;

import lombok.Data;

import java.util.Date;

@Data
public class GitpullrequestsVO {

    private Integer gitPrId;
    private String gitPrTitle;
    private String gitPrCreateby;
    private Date   gitPrCreateat;
    private String gitPrStatus;
    private String jiraProjectKey;
    private Integer gitProjectId;
    private String gitPrTarget;
    private String gitPrSource;

    private String mgCommitId;
    private String mgCommitby;
    private Date mgCommitat;
    private String mgCommitInfo;
    private String mgBranchName;
    private String jiraIssue;
}

