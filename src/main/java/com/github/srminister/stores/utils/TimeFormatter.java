package com.github.srminister.stores.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Getter
@RequiredArgsConstructor
public enum TimeFormatter {

    YEAR(31104000, "ano", "anos"),
    MONTH(2592000, "mês", "meses"),
    DAY(86400, "d", "d"),
    HOUR(3600, "h", "h"),
    MINUTE(60, "m", "m"),
    SECOND(1, "s", "s");

    private static final TimeFormatter[] values = values();

    private static final Pattern PATTERN = Pattern.compile("^(\\d+\\.?\\d*)(\\D+)");

    public static List<String> A = Arrays.asList("S", "M", "H", "D");

    private final long timeInSeconds;

    private final String singular;
    private final String plural;

    public static String format(long millis) {
        Map<TimeFormatter, Integer> timeUnitMap = new LinkedHashMap<>();

        long leftTime = TimeUnit.MILLISECONDS.toSeconds(millis);
        for (TimeFormatter value : TimeFormatter.values) {
            if (leftTime >= value.getTimeInSeconds()) {
                int durationInCurrentTime = (int) (leftTime / value.getTimeInSeconds());
                leftTime %= value.getTimeInSeconds();

                timeUnitMap.put(value, durationInCurrentTime);
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        Set<Map.Entry<TimeFormatter, Integer>> entries = timeUnitMap.entrySet();
        int size = entries.size();

        int index = 1;
        for (Map.Entry<TimeFormatter, Integer> entry : entries) {
            Integer value = entry.getValue();
            TimeFormatter key = entry.getKey();

            stringBuilder.append(value).append(value == 1 ? key.getSingular() : key.getPlural());
            if (index == size - 1) {
                stringBuilder.append(" e ");
            } else if (index != size) {
                stringBuilder.append(", ");
            }

            index++;
        }

        String string = stringBuilder.toString();
        return string.isEmpty() ? "alguns instantes" : string;
    }
}