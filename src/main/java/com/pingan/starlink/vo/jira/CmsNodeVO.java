package com.pingan.starlink.vo.jira;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class CmsNodeVO {

    private String id;
    @JsonProperty("os-type")
    private String os_type;
    private String memory;
    private String ip;
    private String idc;
    private String cpu;
    @JsonProperty("os-name")
    private String os_name;
    private String type;
    private String env;
    @JsonProperty("os-version")
    private String os_version;
    private String subenv;
    private String hostname;
    private String cicode;
    private String port;
    private String zone;
    private String appid;
    @JsonProperty("creation-date")
    private String creation_date;
    @JsonProperty("last-modified-date")
    private String last_modified_date;
    private Tag tag;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Data
    public static class Tag implements Serializable {
        private String nodePoolId;
        private String appPoolId;
        private String nodePoolCode;
        private String appPoolType;
        private String nodePoolName;
        private String nodePoolType;
        private String appPoolCode;
        private String status;

        private String appType;

        @JsonProperty("admin-tag-org")
        private String admin_tag_org;
    }
}
