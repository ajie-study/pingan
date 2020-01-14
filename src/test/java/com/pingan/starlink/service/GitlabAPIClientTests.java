package com.pingan.starlink.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class GitlabAPIClientTests {
    @Autowired
    private GitlabAPIClient gitlabAPIClient;

    @Test
    public void Test1() throws Exception {


    }
}
