package gui;

import java.util.concurrent.TimeUnit;

public final class FormatIntervalToString {
    /**
     * @author https://stackoverflow.com/questions/266825/how-to-format-a-duration-in-java-e-g-format-hmmss
     */
    public static String formatInterval(final long interval) {
        final long hr = TimeUnit.MILLISECONDS.toHours(interval);
        final long min = TimeUnit.MILLISECONDS.toMinutes(interval) % 60;
        final long sec = TimeUnit.MILLISECONDS.toSeconds(interval) % 60;
        return String.format("%02d:%02d:%02d", hr, min, sec);
        //doesn't return ms
    }
}