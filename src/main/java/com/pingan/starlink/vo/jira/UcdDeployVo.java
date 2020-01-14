package com.pingan.starlink.vo.jira;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pingan.starlink.model.AddressInfo;
import com.pingan.starlink.model.UcdDeploy;
import lombok.Data;


import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UcdDeployVo extends UcdDeploy {

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
     * address List
     */
    private List<AddressInfo> addressInfos;

    /**
     * 状态
     */
    private String status;


}
