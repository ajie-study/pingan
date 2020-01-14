package com.pingan.starlink.service;

import com.pingan.starlink.httpclient.CmsHttpResult;
import com.pingan.starlink.httpclient.HttpResult;
import com.pingan.starlink.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class CmsHttpClient extends BaseHttpClient {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${cms.url}")
    private String cmsUrl;

    public CmsHttpResult getCmsNodes(String appid) throws Exception {

        HttpResult res = doGet(cmsUrl + "/tulip-service/entity/query/node?appid=" + appid);
        if (res.getCode() == 200) {
            CmsHttpResult cmsHttpResult = JacksonUtil.json2Bean(res.getBody(), CmsHttpResult.class);
            return cmsHttpResult;
        }  else {
            throw new Exception("Unkown Error.");
        }

    }



    @Override
    protected String getAuthHeader() {
        return null;
    }
}
