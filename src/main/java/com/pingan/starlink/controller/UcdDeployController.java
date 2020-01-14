package com.pingan.starlink.controller;

import com.pingan.starlink.dto.UcdDeployDTO;
import com.pingan.starlink.exception.NotFoundException;
import com.pingan.starlink.logger.LoggerBuilder;
import com.pingan.starlink.logger.MonitorLogger;
import com.pingan.starlink.service.UcdDeployService;
import com.pingan.starlink.util.JacksonUtil;
import com.pingan.starlink.vo.jira.UcdDeployVo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("v1/eirene/ucd")
@CrossOrigin
public class UcdDeployController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MonitorLogger monitorLogger;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UcdDeployService ucdDeployService;

    @ApiOperation(value = "新增ucdDeploy")
    @RequestMapping(value = "ucds", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public UcdDeployVo createRelease(@RequestBody UcdDeployDTO ucdDeployDTO) throws Exception {

        LoggerBuilder loggerBuilder = null;

        UcdDeployVo ucdDeployVo = null;

        try {
            //设置日志的打印格式
            loggerBuilder = new LoggerBuilder().setInterfaceId("ucd.ucds.createRelease").setArgs(
                    JacksonUtil.bean2Json(ucdDeployDTO)).setRequest(request);

            ucdDeployVo = ucdDeployService.createRelease(ucdDeployDTO);
        } catch (Exception e) {
            //判断是否是业务异常
            if (e instanceof NotFoundException || e instanceof NullPointerException) {
                loggerBuilder.setBusResult("false").setBusReason(e.getMessage());
            } else {
                loggerBuilder.setSysResult("false").setSysReason(e.getMessage()).setBusResult("false");
            }
            throw e;
        } finally {
            monitorLogger.info(loggerBuilder.buildLog());
        }


        return ucdDeployVo;

    }

    @ApiOperation(value = "删除ucdDeploy")
    @RequestMapping(value = "ucds", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    public ModelMap deleteUcdDeployByUuid(@RequestParam("uuid") String uuid) throws Exception{

        ModelMap result = new ModelMap();
        LoggerBuilder loggerBuilder = null;
        int res = 0;
        try {
            loggerBuilder = new LoggerBuilder().setInterfaceId("ucd.ucds.deleteUcdDeployByUuid").
                    setArgs(String.format("{\"uuid\":\"%s\"}", uuid)).setRequest(request);

            res = ucdDeployService.deleteUcdDeployByUuid(uuid);

        } catch (Exception e) {
            //判断是否是业务异常
            if (e instanceof NotFoundException || e instanceof NullPointerException) {
                loggerBuilder.setBusResult("false").setBusReason(e.getMessage());
            } else {
                loggerBuilder.setSysResult("false").setSysReason(e.getMessage()).setBusResult("false");
            }
            throw e;
        } finally {
            monitorLogger.info(loggerBuilder.buildLog());
        }

        result.put("msg", "删除成功!");
        result.put("result", res);
        return result;
    }

    @ApiOperation(value = "修改ucdDeploy")
    @RequestMapping(value = "ucds", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    public UcdDeployVo updateUcdDeploy(@RequestBody UcdDeployDTO ucdDeployDTO) throws Exception {

        UcdDeployVo ucdDeployVo = null;

        LoggerBuilder loggerBuilder = null;

        try {
            loggerBuilder = new LoggerBuilder().setInterfaceId("ucd.ucds.updateUcdDeployByUuid").
                    setArgs(JacksonUtil.bean2Json(ucdDeployDTO)).setRequest(request);

            ucdDeployVo = ucdDeployService.updateUcdDeploy(ucdDeployDTO);
        }catch (Exception e){

            if (e instanceof NotFoundException || e instanceof NullPointerException) {
                loggerBuilder.setBusResult("false").setBusReason(e.getMessage());
            } else {
                loggerBuilder.setSysResult("false").setSysReason(e.getMessage()).setBusResult("false");
            }
            throw e;
        }finally {
            monitorLogger.info(loggerBuilder.buildLog());
        }

        return ucdDeployVo;
    }

    @ApiOperation(value = "查询ucdDeploy", notes = "当uuid和projectKey不存在时查询所有")
    @RequestMapping(value = "ucds",method = RequestMethod.GET)
    public List<UcdDeployVo> selectUcdDeploys(@RequestParam(value = "uuid",required = false) String uuid,
                                              @RequestParam(value = "projectKey",required = false) String projectKey) throws Exception {

        List<UcdDeployVo> ucdDeployVos = null;

        LoggerBuilder loggerBuilder = null;

        try {
            loggerBuilder = new LoggerBuilder().setInterfaceId("ucd.ucds.selectUcdDeploys").
                    setArgs(String.format("{\"uuid\":\"%s\", \"projectKey\":\"%s\"}", uuid, projectKey)).setRequest(request);

            ucdDeployVos = ucdDeployService.selectUcdDeploys(uuid,projectKey);

        }catch (Exception e){

            if (e instanceof NotFoundException || e instanceof NullPointerException) {
                loggerBuilder.setBusResult("false").setBusReason(e.getMessage());
            } else {
                loggerBuilder.setSysResult("false").setSysReason(e.getMessage()).setBusResult("false");
            }
            throw e;
        }finally {
            monitorLogger.info(loggerBuilder.buildLog());
        }

        return ucdDeployVos;
    }

}
