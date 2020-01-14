package com.pingan.starlink.httpclient;

import com.pingan.starlink.model.jira.IssueQueryResponseData;
import lombok.Data;

import java.util.List;

@Data
public class JiraIssueHttpResult {

    // 响应状态
    private boolean status;

    // 响应消息
    private String msg;

    // 响应数据
    private List<IssueQueryResponseData> data;

}

