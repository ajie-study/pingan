package com.pingan.starlink.httpclient;

import lombok.Data;

import java.util.List;

@Data
public class PolarisHttpResult {

    // 响应状态
    private boolean status;

    // 响应消息
    private String msg;

    // 响应数据
    private Object data;

}

