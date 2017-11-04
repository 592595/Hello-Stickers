package com.iheshulin.yourname.util;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
/**
 * Created by LC on 2017/5/18.
 */
public class GetDatetime {

    /**ljn*
        获取当前服务器时间
        yyyy-MM-dd HH:mm:ss格式
     */
    public static String getNowString(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }
    /**ljn*
     获取当前服务器时间
     format格式
     */
    public static String getNowString(String format){
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }
    /**ljn*
     获取当前服务器时间
     Date类型
     */
    public static Date getNow(){
        return new Date();
    }
    /**ljn*
     Date类型时间转yyyy-MM-dd HH:mm:ss格式的字符串
     */
    public static String dateToString(Date datetime){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(datetime);
    }
    /**ljn*
     Date类型时间转format格式的字符串
     */
    public static String dateToString(Date datetime, String format){
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(datetime);
    }

    /**ljn*
     yyyy-MM-dd HH:mm:ss格式的字符串转Date
     */
    public static Date stringToDate(String datetime){
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(datetime);
            return date;
        }
        catch (ParseException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**ljn*
     format格式的字符串转Date
     */
    public static Date stringToDate(String datetime, String format){
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = sdf.parse(datetime);
            return date;
        }
        catch (ParseException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
