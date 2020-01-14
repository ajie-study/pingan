package com.pingan.starlink.httpclient;


import com.pingan.starlink.vo.jira.CmsNodeVO;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CmsHttpResult {

    // 响应状态
    private String status;

    // 响应消息
    private String message;

    // 响应数据
    private Map<String,List<CmsNodeVO>> data;

}
