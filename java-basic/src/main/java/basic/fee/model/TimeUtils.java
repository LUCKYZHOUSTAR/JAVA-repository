package basic.fee.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/12/27 12:23
 * @Description:
 */
public class TimeUtils {

    public static final String yyyyMMddHHmm = "yyyyMMddHHmm";


    public static int minuteBetween(String startTime, String endTime) {

        if (startTime.length() < 4) {
            startTime = "0" + startTime;
        }

        if (endTime.length() < 4) {
            endTime = "0" + endTime;
        }
        Date smdate = parse(yyyyMMddHHmm, "20190203" + startTime);

        Date bdate = parse(yyyyMMddHHmm, "20190203" + endTime);

        long time1 = smdate.getTime();
        long time2 = bdate.getTime();
        long between_days = (time2 - time1) / (1000 * 60);

        return Integer.parseInt(String.valueOf(between_days));
    }


    public static Date parse(String format, String timeStr) {
        SimpleDateFormat formatSim = new SimpleDateFormat(format);
        Date d = null;
        try {
            d = formatSim.parse(timeStr);
        } catch (ParseException e) {
        }
        return d;
    }


    public static long until(LocalDateTime startDate, LocalDateTime endDate) {
        Duration duration = Duration.between(startDate, endDate);
        return duration.toDays();
    }





}
