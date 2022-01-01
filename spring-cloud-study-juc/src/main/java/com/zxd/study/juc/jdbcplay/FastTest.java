package com.zxd.study.juc.jdbcplay;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FastTest {
    public static void main(String[] args) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        System.out.println(df.format(new Date()));
        System.out.println(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
    }
}
