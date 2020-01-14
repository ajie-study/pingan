package com.pingan.starlink.controller;

import com.pingan.starlink.logger.LoggerBuilder;
import com.pingan.starlink.logger.MonitorLogger;
import com.pingan.starlink.model.sonar.SonarProjectDetailResponseData;
import com.pingan.starlink.model.sonar.SonarProjectQueryResponseData;
import com.pingan.starlink.service.SonarClientService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/eirene/sonar")
@CrossOrigin
public class SonarQubeController {

    @Autowired
    private MonitorLogger monitorLogger;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SonarClientService sonarClientService;

    @ApiOperation(value = "查询已扫描项目列表", notes = "查询多个项目")
    @GetMapping(value = "/projects")
    public SonarProjectQueryResponseData getProjects(@RequestParam(required = false, defaultValue = "1") long page,
                                                     @RequestParam(required = false, defaultValue = "10") long pageSize,
                                                     @RequestParam(required = false) String projectKeys) throws Exception {
        LoggerBuilder loggerBuilder = new LoggerBuilder().setInterfaceId("sonar.projects.queryProjectList").setArgs(String.format("{\"projectKeys\":\"%s\"}", projectKeys)).setRequest(request);
        Map<String, Object> params = new HashMap<>();
        SonarProjectQueryResponseData sonarProjectResponse = null;
        try {
            params.put("p", page);
            params.put("ps", pageSize);
            if (!StringUtils.isEmpty(projectKeys)) {
                params.put("projects", projectKeys);
            }
            sonarProjectResponse = sonarClientService.getProjects(params);
        } catch (Exception e) {
            loggerBuilder.setSysResult("false").setSysReason(e.getMessage()).setBusResult("false");
            throw e;
        } finally {
            monitorLogger.info(loggerBuilder.buildLog());
        }
        return sonarProjectResponse;
    }

    @ApiOperation(value = "查询指定项目扫描详情", notes = "查询指定项目扫描详情")
    @GetMapping(value = "/projects/{projectKey}")
    public SonarProjectDetailResponseData getProjectDetail(@PathVariable String projectKey) {
        LoggerBuilder loggerBuilder = new LoggerBuilder().setInterfaceId("sonar.projects.queryProjectDetail").setArgs(String.format("{\"projectKey\":\"%s\"}", projectKey)).setRequest(request);
        Map<String, Object> params = new HashMap<>();
        params.put("metricKeys", "alert_status,bugs,reliability_rating,vulnerabilities,security_rating,code_smells,sqale_rating,duplicated_lines_density,coverage,ncloc,ncloc_language_distribution");
        params.put("component", projectKey);
        SonarProjectDetailResponseData sonarProjectDetailResponse = null;
        try {
            sonarProjectDetailResponse = sonarClientService.getProjectDetail(params);
        } catch (Exception e) {
            sonarProjectDetailResponse = new SonarProjectDetailResponseData();
            loggerBuilder.setSysResult("false").setSysReason(e.getMessage()).setBusResult("false");
        }
        monitorLogger.info(loggerBuilder.buildLog());
        return sonarProjectDetailResponse;
    }
}
