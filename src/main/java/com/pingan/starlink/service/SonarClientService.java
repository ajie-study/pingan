package com.pingan.starlink.service;

import com.pingan.starlink.model.sonar.SonarProjectDetailResponseData;
import com.pingan.starlink.model.sonar.SonarProjectQueryResponseData;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SonarClientService {

    @Autowired
    @Setter
    private SonarHttpClient sonarHttpClient;

    public SonarProjectQueryResponseData getProjects(Map<String, Object> params) throws Exception {
        return sonarHttpClient.getProjects(params);
    }

    public SonarProjectDetailResponseData getProjectDetail(Map<String, Object> params) throws Exception {
        return sonarHttpClient.getProjectDetail(params);
    }
}
