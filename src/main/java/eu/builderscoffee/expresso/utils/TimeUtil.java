package eu.builderscoffee.expresso.utils;

import java.util.concurrent.TimeUnit;

public class TimeUtil {

    public static String calculateTime(long seconds) {
        String time = null;
        long hours = TimeUnit.SECONDS.toHours(seconds) - TimeUnit.SECONDS.toHours(TimeUnit.SECONDS.toDays(seconds));
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.SECONDS.toMinutes(TimeUnit.SECONDS.toHours(seconds));
        long second = TimeUnit.SECONDS.toSeconds(seconds) - TimeUnit.SECONDS.toSeconds(TimeUnit.SECONDS.toMinutes(seconds));
        return null;
        //return (hours >= 1 ? hours + "h" : "") + minute >=1 ? minute + "minutes" ;
        //System.out.println(" Hour " + hours + " Minute " + minute + " Seconds " + second);

    }
}
