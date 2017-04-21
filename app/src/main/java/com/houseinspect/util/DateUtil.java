package com.houseinspect.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Lalit on 10/15/2016.
 */
public class DateUtil {

    public static String DATE_FORMAT_1 = "yyyy-MM-dd";
    public static String DATE_FORMAT_2 = "dd-MM-yyyy";

    public static String getDateStrConverter(String dateStr, String currentFormat, String desiredFormat) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(currentFormat);
            Date date = format.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat(desiredFormat);
            return fmtOut.format(date);
        } catch (ParseException e) {
            return "NA";
        }
    }

    public static String getDateStr(Calendar calendar, String format){
        SimpleDateFormat fmtOut = new SimpleDateFormat(format);
        return fmtOut.format(new Date(calendar.getTimeInMillis()));
    }

    public static String getDateStrFromTimeStamp(String timeStampStr){
        long timeStamp =  Long.parseLong(timeStampStr)*1000L;
        Calendar calendar =  Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);
        return getDateStr(calendar, DATE_FORMAT_2);
    }

    public static String convertDateString(String dateStr, String oldFormat, String desiredFormat) {
        DateFormat format = new SimpleDateFormat(oldFormat, Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat(desiredFormat);
            return fmtOut.format(date);
        } catch (ParseException e) {
            return dateStr;
        }
    }
}
