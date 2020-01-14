package com.pingan.starlink.service;

import com.pingan.starlink.httpclient.HttpResult;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public abstract class BaseHttpClient {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected CloseableHttpClient httpClient;

    @Autowired
    protected RequestConfig config;

    protected HttpResult doGet(String url) throws Exception {
        // 声明 http get 请求
        HttpGet httpGet = new HttpGet(url);
        // 装载配置信息
        httpGet.setConfig(config);
        httpGet.setHeader(HttpHeaders.AUTHORIZATION, getAuthHeader());
        httpGet.setHeader("Content-type", "application/json");
        logger.info(httpGet.toString());

        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpGet);
        String responseBody = getResponse(response);
        Integer responseCode = response.getStatusLine().getStatusCode();
        logger.info(String.format("response code: %d. body: %s", responseCode, responseBody));

        return new HttpResult(responseCode, responseBody);
    }

    protected HttpResult doGet(String url, Map<String, Object> params) throws Exception {
        return this.doGet(getUrlWithParam(url, params));
    }

    protected HttpResult doPost(String url, String postData) throws Exception {
        // 声明httpPost请求
        HttpPost httpPost = new HttpPost(url);
        // 加入配置信息
        httpPost.setConfig(config);
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, getAuthHeader());
        httpPost.setHeader("Content-type", "application/json");

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

    protected HttpResult doPost(String url) throws Exception {
        // 声明httpPost请求
        HttpPost httpPost = new HttpPost(url);
        // 加入配置信息
        httpPost.setConfig(config);
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, getAuthHeader());
        httpPost.setHeader("Content-type", "application/json");

        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpPost);
        logger.info(httpPost.toString());

        String responseBody = getResponse(response);
        Integer responseCode = response.getStatusLine().getStatusCode();
        logger.info(String.format("response code: %d. body: %s", responseCode, responseBody));

        return new HttpResult(responseCode, responseBody);
    }

    protected HttpResult doPut(String url, String putData) throws Exception {
        // 声明httpPut请求
        HttpPut httpPut = new HttpPut(url);
        // 加入配置信息
        httpPut.setConfig(config);
        httpPut.setHeader(HttpHeaders.AUTHORIZATION, getAuthHeader());
        httpPut.setHeader("Content-type", "application/json");

        // 判断map是否为空，不为空则进行遍历，封装from表单对象
        if (putData != null && !putData.isEmpty()) {
            // 构造from表单对象
            StringEntity stringEntity = new StringEntity(putData, "UTF-8");
            // 把表单放到post里
            httpPut.setEntity(stringEntity);
        }

        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpPut);
        logger.info(httpPut.toString());
        logger.info(String.format("request data is: %s", putData));

        String responseBody = getResponse(response);
        Integer responseCode = response.getStatusLine().getStatusCode();
        logger.info(String.format("response code: %d. body: %s", responseCode, responseBody));

        return new HttpResult(responseCode, responseBody);
    }

    protected HttpResult doDelete(String url, Map<String, Object> params) throws Exception {
        return this.doDelete(getUrlWithParam(url, params));
    }

    protected HttpResult doDelete(String url) throws Exception {
        // 声明httpPut请求
        HttpDelete httpDelete = new HttpDelete(url);
        // 加入配置信息
        httpDelete.setConfig(config);
        httpDelete.setHeader(HttpHeaders.AUTHORIZATION, getAuthHeader());
        httpDelete.setHeader("Content-type", "application/json");
        logger.info(httpDelete.toString());

        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpDelete);

        String responseBody = getResponse(response);
        Integer responseCode = response.getStatusLine().getStatusCode();
        logger.info(String.format("response code: %d. body: %s", responseCode, responseBody));

        return new HttpResult(responseCode, responseBody);
    }

    protected String getResponse(CloseableHttpResponse response) throws Exception {
        HttpEntity httpEntity = response.getEntity();
        if (httpEntity != null) {
            return EntityUtils.toString(httpEntity, "UTF-8");
        } else {
            return null;
        }
    }

    protected String getUrlWithParam(String url, Map<String, Object> params) throws Exception {
        URIBuilder uriBuilder = new URIBuilder(url);
        if (params != null) {
            // 遍历map,拼接请求参数
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
            }
        }
        return uriBuilder.build().toString();
    }

    protected abstract String getAuthHeader();
}
