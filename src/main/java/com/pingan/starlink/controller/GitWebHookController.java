package com.pingan.starlink.controller;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pingan.starlink.model.git.GitPrWebhookBody;
import com.pingan.starlink.service.GitDBService;
import com.pingan.starlink.service.GitService;
import com.pingan.starlink.util.JacksonUtil;
import io.swagger.annotations.ApiOperation;
import org.gitlab4j.api.systemhooks.PushSystemHookEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/v1/git")
@CrossOrigin
public class GitWebHookController {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private GitService gitService;

    private static ObjectMapper mapper = new ObjectMapper();

    private static String  OBJECT_ATTRIBUTES = "object_attributes";

    private static String OBJECTATTRIBUTES = "objectAttributes";


    @Resource
    private GitDBService gitDBService;

    @ApiOperation(value = "pr web hook")

    @RequestMapping(value = "/webhook/merge_request_events", method = RequestMethod.POST, produces = "application/json;charset-UTF-8")
    @ResponseBody
    @JsonIgnore
    public void mergeRequestWebHook(@RequestBody GitPrWebhookBody prEvents) throws Exception {

        logger.info(String.format("webhook merge request events requestBody: %s", JacksonUtil.bean2Json(prEvents)));

        this.gitDBService.updateGitPrStatus(prEvents);

    }

    @ApiOperation(value = "监听pushEvent")
    @RequestMapping(value = "/webhook/push_events", method = RequestMethod.POST, produces = "application/json;charset-UTF-8")
    @ResponseBody
    public void pushWebHook(@RequestBody PushSystemHookEvent pushBody) throws Exception {

        logger.info("webhook push events request  ==> updateBranchCommitAndTime start");

        this.gitService.updateBranchCommitAndTime(pushBody);
    }
}
