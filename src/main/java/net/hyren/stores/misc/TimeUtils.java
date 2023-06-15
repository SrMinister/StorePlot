package net.hyren.stores.misc;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public enum TimeUtils {

    DAY(86400000, "days", "day", "d"),
    HOUR(3600000, "hours", "hour", "h");

    private final long millis;
    private final List<String> formats;

    TimeUtils(long millis, String... formats) {
        this.millis = millis;
        this.formats = Arrays.asList(formats);
    }

    public List<String> getFormats() {
        return formats;
    }


    public static String format(long value) {
        if (value <= 0) return "Em instantes";

        long days = TimeUnit.MILLISECONDS.toDays(value);
        long hours = TimeUnit.MILLISECONDS.toHours(value) - (days * 24);

        long[] times = {days, hours};
        String[] names = {"d", "h"};

        List<String> values = new ArrayList<>();
        for (int index = 0; index < times.length; index++) {
            long time = times[index];
            if (time > 0) {
                String name = times[index] + "" + names[index];
                values.add(name);
            }
        }

        if (values.isEmpty()) {
            return "Em instantes";
        }

        if (values.size() == 1) {
            return values.get(0);
        }

        return String.join(" ", values.subList(0, values.size() - 1)) + " " + values.get(values.size() - 1);
    }

}