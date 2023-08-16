package com.github.srminister.stores.configuration.helper;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ListHelper {
    public static List<String> colorize(List<String> list) {
        return list.stream()
          .map($ -> ChatColor.translateAlternateColorCodes('&', $))
          .collect(Collectors.toList());
    }

    public static List<String> replace(Map<String, String> map, List<String> list) {
        final List<String> replacedList = new ArrayList<>();

        for (String value : list) {
            for (final Map.Entry<String, String> entry : map.entrySet()) {
                value = value.replace(entry.getKey(), entry.getValue());
            }

            replacedList.add(value);
        }

        return colorize(replacedList);
    }
}