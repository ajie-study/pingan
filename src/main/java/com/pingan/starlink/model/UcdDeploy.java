package com.pingan.starlink.model;

import lombok.*;

import javax.persistence.Table;
import java.util.Date;

/**
 * UCD发布记录表
 */
@Table(name = "ucd_deploy")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UcdDeploy {
    /**
     * UUID唯一标识符
     */
    protected String uuid;

    /**
     * jira项目的唯一标识key
     */
    protected String projectKey;

    /**
     * 计划发布时间
     */
    protected Date releaseDate;

    /**
     * 发布仓库
     */
    protected String releaseRepository;

    /**
     * 发布制品
     */
    protected String releaseProduct;

    /**
     * 发布版本
     */
    protected String releaseVersion;

    /**
     * 描述
     */
    protected String description;

    /**
     * 环境
     */
    protected String environment;

    /**
     * 模块
     */
    protected String module;

    /**
     * ip地址和端口
     */
    private String address;

    /**
     * 状态
     */
    private String status;


}