package com.pingan.starlink.util;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class EireneUtil {

    public static String randomUUID(){

        return UUID.randomUUID().toString();
    }

    public static Date addDate(Date date){

        Calendar instance = Calendar.getInstance();

        instance.setTime(date);

        //添加八小时
        instance.add(Calendar.HOUR_OF_DAY,8);

        Date time = instance.getTime();

        return time;

    }
}
