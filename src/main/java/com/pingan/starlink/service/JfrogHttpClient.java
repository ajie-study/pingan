package com.pingan.starlink.service;

import com.pingan.starlink.httpclient.HttpResult;
import com.pingan.starlink.model.jfrog.ArtifactsQueryResponseData;
import com.pingan.starlink.util.JacksonUtil;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JfrogHttpClient extends BaseHttpClient {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public ArtifactsQueryResponseData queryArtifacts(String aqlString, String url, String token) throws Exception {
        HttpResult res = doPost(url + "/artifactory/api/search/aql", aqlString, token);
        if (res.getCode() == 200) {
            ArtifactsQueryResponseData response = JacksonUtil.json2Bean(res.getBody(), ArtifactsQueryResponseData.class);
            return response;
        } else {
            throw new Exception("Unkown Error.");
        }
    }

    protected HttpResult doPost(String url, String postData, String token) throws Exception {
        // 声明httpPost请求
        HttpPost httpPost = new HttpPost(url);
        // 加入配置信息
        httpPost.setConfig(config);
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        httpPost.setHeader("Content-type", "text/plain");

        // 判断map是否为空，不为空则进行遍历，封装from表单对象
        if (postData != null && !postData.isEmpty()) {
            // 构造from表单对象
            StringEntity stringEntity = new StringEntity(postData, "UTF-8");
            // 把表单放到post里
            httpPost.setEntity(stringEntity);
        }

        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpPost);
        logger.info(httpPost.toString());
        logger.info(String.format("request data is: %s", postData));

        String responseBody = getResponse(response);
        Integer responseCode = response.getStatusLine().getStatusCode();
        logger.info(String.format("response code: %d. body: %s", responseCode, responseBody));

        return new HttpResult(responseCode, responseBody);
    }

    protected String getAuthHeader() {
        return null;
    }
}
