package cn.appoa.doudoufriend.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.appoa.aframework.utils.AtyUtils;


public class TimeDifferencesUtils {

    /**
     * 获取时间差
     */
    public static int getTimeDifferences(String time1, String time2) {
        if(DateUtils.getTimeCompareSize(time1,time2)){
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date d1 = df.parse(time1);
                Date d2 = df.parse(time2);
                //long diff = d1.getTime() - d2.getTime();//两时间差,精确到毫秒
                long diff = d2.getTime() - d1.getTime();//两时间差,精确到毫秒
                long day = diff / 86400000;                         //以天数为单位取整
                long hour = diff % 86400000 / 3600000;               //以小时为单位取整
                long min = diff % 86400000 % 3600000 / 60000;       //以分钟为单位取整
                long seconds = diff % 86400000 % 3600000 % 60000 / 1000;   //以秒为单位取整
                AtyUtils.i("两天相差时间", day + "天" + hour + "小时" + min + "分" + seconds + "秒");
                return (int)day;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}
