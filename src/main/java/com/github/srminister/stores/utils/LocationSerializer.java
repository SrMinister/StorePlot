package com.github.srminister.stores.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.StringJoiner;

public class LocationSerializer {

    public static String toString(Location value) {
        return value.getWorld().getName() + ";" +
                value.getBlockX() + ";" +
                value.getBlockY() + ";" +
                value.getBlockZ() + ";" +
                value.getYaw() + ";" +
                value.getPitch();
    }

    public static Location fromString(String value) {
        if(value.isEmpty()) return null;

        final String[] split = value.split(";");

        return new Location(
                Bukkit.getWorld(split[0]),
                Double.parseDouble(split[1]),
                Double.parseDouble(split[2]),
                Double.parseDouble(split[3]),
                Float.parseFloat(split[4]),
                Float.parseFloat(split[5])
        );
    }


    public static String serialize(Location location, boolean yawAndPitch) {
        if (location.getWorld() == null)
            return null;

        final StringJoiner joiner = new StringJoiner(";");
        joiner.add(location.getWorld().getName());
        joiner.add(String.valueOf(location.getX()));
        joiner.add(String.valueOf(location.getY()));
        joiner.add(String.valueOf(location.getZ()));

        if (yawAndPitch) {
            joiner.add(String.valueOf(location.getYaw()));
            joiner.add(String.valueOf(location.getPitch()));
        }

        return joiner.toString().substring(0, joiner.length() - 1);
    }

    public static String getFormattedText(Location location) {
        String text = String.format("%s, %d, %d, %d", location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
        return ChatColor.GRAY + text;
    }
}