package com.pingan.starlink.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserProjectRelationDTO {

    private String projectKey;

    private List<UserRolesDTO> users;

}
