package api.handle.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description:
 * @Author:苏晓雨
 * @Date: Created in 18-8-13 下午4:47
 */

public class TimeStampAndLongUtil {
    //Time_long
    public long TimeToLong(String time){
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime()/1000L;
    }

    //long_Time
    public String LongToTime(long data){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date_string = sdf.format(new Date(data * 1000L));
        return date_string;
    }

    public static void main(String[] args) throws Exception {
        TimeStampAndLongUtil timeStampAndLongUtil = new TimeStampAndLongUtil();
        System.out.println(timeStampAndLongUtil.TimeToLong("2018-08-20 15:20:18"));
    }
}
