package com.pingan.starlink.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pingan.starlink.enums.StatusEnum;
import com.pingan.starlink.model.AddressInfo;
import com.pingan.starlink.model.UcdDeploy;
import com.pingan.starlink.util.EireneUtil;
import com.pingan.starlink.util.JacksonUtil;
import lombok.Builder;
import lombok.Data;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)  //忽略不存在的属性
@JsonInclude(JsonInclude.Include.NON_NULL) //表示,如果值为null,则不返回
@Data
public class UcdDeployDTO {

    /**
     * UUID唯一标识符
     */
    private String  uuid;

    /**
     * jira项目的唯一标识key
     */
    private String projectKey;

    /**
     * 计划发布时间
     */
    private Date releaseDate;

    /**
     * 发布仓库
     */
    private String releaseRepository;

    /**
     * 发布制品
     */
    private String releaseProduct;

    /**
     * 发布版本
     */
    private String releaseVersion;

    /**
     * 描述
     */
    private String description;

    /**
     * 环境
     */
    private String environment;

    /**
     * 模块
     */
    private String module;

    /**
     * ip地址和端口
     */
    private List<AddressInfo> addressInfoList;

    /**
     * 状态
     */
    private String status;


    public UcdDeploy getUcdDeploy(UcdDeployDTO ucdDeployDTO) throws IOException {

        /**
         * 获取UcdDeploy对象
         */
       UcdDeploy ucdDeploy =  UcdDeploy.builder().uuid(ucdDeployDTO.getUuid()).
                projectKey(ucdDeployDTO.getProjectKey()).
                releaseDate(EireneUtil.addDate(ucdDeployDTO.getReleaseDate())).
                releaseRepository(ucdDeployDTO.getReleaseRepository()).
                releaseProduct(ucdDeployDTO.getReleaseProduct()).
                releaseVersion(ucdDeployDTO.getReleaseVersion()).
                description(ucdDeployDTO.getDescription()).
                environment(ucdDeployDTO.getEnvironment()).
                module(ucdDeployDTO.getModule()).
                address(JacksonUtil.bean2Json(ucdDeployDTO.getAddressInfoList())).
                build();

        String status = ucdDeployDTO.getStatus();

        if(status == null || status == "" ){
            status = StatusEnum.UNRELEASE.getStatus();
        }

        ucdDeploy.setStatus(status);

       return ucdDeploy;
    }
}
