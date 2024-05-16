package HCMUTE.SocialMedia.Utils;

import android.util.Log;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProcessTime {
    public static long getDurationWithNowInSecond(String time){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime startTime = LocalDateTime.parse(time, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            LocalDateTime endTime = LocalDateTime.now();

            ZoneId zone = ZoneId.of("+0700");
            ZonedDateTime endTimeWithZone = ZonedDateTime.of(endTime, zone);

            // Tính khoảng cách thời gian
            Duration duration = Duration.between(startTime, endTimeWithZone.toLocalDateTime());
            return duration.toMinutes();
        }

        return -1;
    }

    public static List<String> getTimeFromString(String time){
        String[] timeSplit = time.split("T");
        String[] yyyymmdd = timeSplit[0].split("-");
        String[] hhmmss = timeSplit[1].split(":");

        List<String> timeResult = new ArrayList<>();
        Collections.addAll(timeResult, yyyymmdd);
        Collections.addAll(timeResult, hhmmss);
        return timeResult;
    }
}
