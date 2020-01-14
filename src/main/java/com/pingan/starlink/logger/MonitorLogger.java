package com.pingan.starlink.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: Sunzh
 * @Date: 2019/4/12 15:26
 * @Description: ${description}
 * @Version: 1.0
 */
@Component
public class MonitorLogger {

    @Value("${monitor.enable}")
    private boolean enable;

    @Value("${monitor.env}")
    private String env;

    private Logger logger;

    public Logger getLogger() {
        if (logger == null) {
            logger = LogManager.getLogger(env);
        }
        return logger;
    }

    public void info(Object object) {
        if (enable) {
            getLogger().info(object);
        }
    }

}
