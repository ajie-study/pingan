package com.pingan.starlink.service;

import com.pingan.starlink.model.jfrog.ArtifactsQueryResponseData;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JfrogClientService {

    @Autowired
    @Setter
    private JfrogHttpClient jfrogHttpClient;

    public ArtifactsQueryResponseData queryArtifacts(String aqlString, String url, String token) throws Exception {
        return jfrogHttpClient.queryArtifacts(aqlString, url, token);
    }
}
