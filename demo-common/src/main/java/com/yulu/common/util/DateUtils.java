package com.yulu.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Yulu on 2020/4/24.
 */
public class DateUtils {

    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    public static Date getCurDate() {
        return new Date();
    }

    public static Long getTimeStamp() {
        return getCurDate().getTime();
    }

    public static Date getDateResultByStr(String pattern, String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date result = null;
        try {
            result = sdf.parse(dateStr);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return result;
    }

    public static String getStrResultByDate(String pattern, Date date) {
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            result = format.format(date);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return result;
    }
}
