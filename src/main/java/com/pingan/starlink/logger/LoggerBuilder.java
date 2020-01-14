package com.pingan.starlink.logger;

import com.pingan.starlink.util.ConstantUtil;
import com.pingan.starlink.util.IpUtil;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Sunzh
 * @Date: 2019/4/15 14:04
 * @Description:
 * @Version: 1.0
 */
public class LoggerBuilder {

    private String interfaceId;

    private HttpServletRequest request;

    private String sysResult = "success";

    private String sysReason = "";

    private String busResult = "success";

    private String busReason = "";

    private String args = "";

    private Timer4Logger timer;

    public LoggerBuilder() {
        timer = new Timer4Logger();
    }

    public String buildLog() {

        if (StringUtils.isEmpty(interfaceId)) {
            throw new IllegalArgumentException("Illegal Argument: interfaceId is empty");
        }

        StringBuffer sb = new StringBuffer(ConstantUtil.TOPIC_ID + "|");
        sb.append(interfaceId + "|");
        sb.append(IpUtil.getIpAddr(request) + "|");
        sb.append(sysResult + "|" + sysReason + "|");
        sb.append(busResult + "|" + busReason + "|");
        sb.append(timer.now() + "|");
        sb.append(timer.getTime() + "|");
        sb.append(args);
        return sb.toString();
    }

    public String buildLog(HttpServletRequest request, String interfaceId, String result,
                           String reason, String b_result, String b_reason, String args) {

        if (StringUtils.isEmpty(interfaceId) || StringUtils.isEmpty(result) || StringUtils.isEmpty(b_result)) {
            throw new IllegalArgumentException("Illegal Argument: interfaceId or result or b_result is empty");
        }

        // String classname = new Exception().getStackTrace()[1].getClassName(); //获取调用者的类名
        // String method_name = new Exception().getStackTrace()[1].getMethodName(); //获取调用者的方法名
        StringBuffer sb = new StringBuffer(ConstantUtil.TOPIC_ID + "|");
        sb.append(interfaceId + "|");
        sb.append(IpUtil.getIpAddr(request) + "|");
        sb.append(result + "|" + reason + "|");
        sb.append(b_result + "|" + b_reason + "|");
        sb.append(timer.now() + "|");
        sb.append(timer.getTime() + "|");
        sb.append(args);
        return sb.toString();
    }

    public LoggerBuilder setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId;
        return this;
    }

    public LoggerBuilder setRequest(HttpServletRequest request) {
        this.request = request;
        return this;
    }

    public LoggerBuilder setSysResult(String sysResult) {
        this.sysResult = sysResult;
        return this;
    }

    public LoggerBuilder setSysReason(String sysReason) {
        this.sysReason = sysReason;
        return this;
    }

    public LoggerBuilder setBusResult(String busResult) {
        this.busResult = busResult;
        return this;
    }

    public LoggerBuilder setBusReason(String busReason) {
        this.busReason = busReason;
        return this;
    }

    public LoggerBuilder setArgs(String args) {
        this.args = args;
        return this;
    }
}
