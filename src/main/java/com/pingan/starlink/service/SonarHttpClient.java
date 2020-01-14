package com.pingan.starlink.service;

import com.pingan.starlink.httpclient.HttpResult;
import com.pingan.starlink.model.sonar.SonarProjectDetailResponseData;
import com.pingan.starlink.model.sonar.SonarProjectQueryResponseData;
import com.pingan.starlink.util.JacksonUtil;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.Charset;
import java.util.Map;

public class SonarHttpClient extends BaseHttpClient {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${sonar.url}")
    private String sonarUrl;

    @Value("${sonar.user}")
    private String user;

    @Value("${sonar.password}")
    private String password;

    public SonarProjectQueryResponseData getProjects(Map<String, Object> params) throws Exception {
        HttpResult res = doGet(sonarUrl + "/api/projects/search", params);
        if (res.getCode() == 200) {
            SonarProjectQueryResponseData sonarResponse = JacksonUtil.json2Bean(res.getBody(), SonarProjectQueryResponseData.class);
            return sonarResponse;
        } else {
            throw new Exception("Unkown Error.");
        }
    }

    public SonarProjectDetailResponseData getProjectDetail(Map<String, Object> params) throws Exception {
        HttpResult res = doGet(sonarUrl + "/api/measures/component", params);
        if (res.getCode() == 200) {
            SonarProjectDetailResponseData sonarProjectDetailResponse = JacksonUtil.json2Bean(res.getBody(), SonarProjectDetailResponseData.class);
            return sonarProjectDetailResponse;
        } else {
            throw new Exception("Unkown Error.");
        }
    }

    protected String getAuthHeader() {
        String auth = user + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(Charset.forName("ISO-8859-1")));
        String authHeader = "Basic " + new String(encodedAuth);
        return authHeader;
    }
}
