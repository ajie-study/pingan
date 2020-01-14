package com.pingan.starlink.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User extends MlUser {

    private Integer id;

    private String username;

    private String password;

    private char sex;

    private String address;
}
