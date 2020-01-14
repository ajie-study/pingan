package com.pingan.starlink.model.jira;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pingan.starlink.model.UserInfo4A;
import lombok.Data;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class UserCreateData {
    private String name;
    private String password;
    private String emailAddress;
    private String displayName;

    public UserCreateData() {

    }

    public UserCreateData(UserInfo4A user4A) {
        this.name = user4A.getUsername();
        this.displayName = user4A.getDisplayName();
        this.emailAddress = user4A.getEmail();
    }
}
