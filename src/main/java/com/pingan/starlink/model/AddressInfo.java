package com.pingan.starlink.model;

import lombok.Data;

@Data
public class AddressInfo {
    /**
     * ip地址
     */
    private String ip;
    /**
     * 端口
     */
    private String port;
    /**
     * 状态
     */
    private String status;
    /**
     * 发布状态
     */
    private String releaseStatus;
}