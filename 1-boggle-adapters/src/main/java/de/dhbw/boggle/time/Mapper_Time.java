package de.dhbw.boggle.time;

import java.time.Duration;

public class Mapper_Time {
    public static String durationToTimeString(javafx.util.Duration time) {

        int minutes = ((int)time.toSeconds() / 60);
        int seconds = ((int)time.toSeconds() % 60);

        String str_minutes = Integer.toString(minutes);
        String str_seconds = Integer.toString(seconds);

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
