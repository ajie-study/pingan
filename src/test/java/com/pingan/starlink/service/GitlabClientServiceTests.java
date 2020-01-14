package com.pingan.starlink.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class GitlabClientServiceTests {

    @Resource
    private GitDBService gitDBService;

    @Test
    public void Test1() throws Exception {



    }
}
