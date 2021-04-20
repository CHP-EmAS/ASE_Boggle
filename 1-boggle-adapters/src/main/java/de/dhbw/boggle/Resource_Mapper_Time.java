package de.dhbw.boggle;

import java.time.Duration;

public class Resource_Mapper_Time {
    public static String durationToTimeString(javafx.util.Duration time) {

        Integer minutes = ((int)time.toSeconds() / 60);
        Integer seconds = ((int)time.toSeconds() % 60);

        String str_minutes = minutes.toString();
        String str_seconds = seconds.toString();

        if(minutes < 10)
            str_minutes = "0" + str_minutes;

        if(seconds < 10)
            str_seconds = "0" + str_seconds;

        return (str_minutes + ":" + str_seconds);
    }

    public static Duration convert(javafx.util.Duration javaFxDuration) {
        return Duration.ofMillis((long)javaFxDuration.toMillis());
    }

    public static javafx.util.Duration convert(Duration javaDuration) {
        return javafx.util.Duration.millis(javaDuration.toMillis());
    }
}
