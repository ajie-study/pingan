package com.pingan.starlink.vo.jira;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UserVO {

    private String uuid;
    private String username;
    @JsonProperty("createdAt")
    private Date created_at;
    @JsonProperty("updatedAt")
    private Date updated_at;
    private String permission;
    private String type;
    private String realname;
    private String department;
    private String position;
    private String mobile;
    private String email;
    @JsonProperty("officeAddress")
    private String office_address;
    @JsonProperty("landlinePhone")
    private String landline_phone;
    private String extra_info;
    private String role;
    @JsonProperty("projectKey")
    private String project_key;



}
