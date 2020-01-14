package com.pingan.starlink.model.git;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class GitPrWebhookBody implements Serializable {

    @JsonProperty("event_type")
    private String eventType;
    @JsonProperty("object_attributes")
    private PrWebhookAttributes objectAttributes;
    @JsonProperty("project")
    private PrWebhookProject project;
    @JsonProperty("user")
    private PrWebhokkUser user;


    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Data
    public static class PrWebhookAttributes implements Serializable {
        @JsonFormat(locale = "zh",timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
        @JsonProperty("created_at")
        private Date createdAt;

        @JsonProperty("id")
        private Integer prId;
        @JsonProperty("iid")
        private Integer prIid;
        @JsonProperty("last_commit")
        private PrLastCommit lastCommit;
        @JsonProperty("merge_commit_sha")
        private String mergeCommitSha;
        @JsonProperty("merge_status")
        private String mergeStatus;
        @JsonProperty("source_branch")
        private  String sourceBranchName;
        @JsonProperty("source_project_id")
        private Integer sourceProjectId;
        @JsonProperty("target_branch")
        private  String targetBranchName;
        @JsonProperty("target_project_id")
        private Integer targetProjectId;
        @JsonProperty("state")
        private String state;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Data
    public static class PrLastCommit implements Serializable {
        @JsonProperty("id")
        private String lastCommitId;
        private String message;

        @JsonFormat(locale = "zh",timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
        @JsonProperty("timestamp")
        private String timestamp;
        private String url;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Data
    public static class PrWebhookProject implements Serializable{
        @JsonProperty("id")
        private Integer gitProjectId;
        @JsonProperty("name")
        private String gitProjectName;
        @JsonProperty("http_url")
        private String gitProjectHttpUrl;

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Data
    public static class PrWebhokkUser implements  Serializable {
        @JsonProperty("avatar_url")
        private String avatarUrl;
        @JsonProperty("name")
        private String name;
        @JsonProperty("username")
        private String username;
    }

}
