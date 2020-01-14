package com.pingan.starlink.logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Author: Sunzh
 * @Date: 2019/4/12 17:14
 * @Description:
 * @Version: 1.0
 */
public class Timer4Logger {

    private long createTimeStamp;

    public Timer4Logger() {
        this.createTimeStamp = new Date().getTime();
    }

    public long getTime() {
        return new Date().getTime() - createTimeStamp;
    }

    public String now() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
