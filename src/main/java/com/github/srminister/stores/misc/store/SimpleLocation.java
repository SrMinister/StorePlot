package com.github.srminister.stores.misc.store;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

@Getter
@Setter
@AllArgsConstructor
public class SimpleLocation {

    private String world;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    public static SimpleLocation fromString(String input) {
        String[] split = input.split(",");

        final String world = split[0];

        final double x = Double.parseDouble(split[1]);
        final double y = Double.parseDouble(split[2]);
        final double z = Double.parseDouble(split[3]);

        final float yaw = Float.parseFloat(split[4]);
        final float pitch = Float.parseFloat(split[5]);

        return new SimpleLocation(world, x, y, z, yaw, pitch);
    }

    public static SimpleLocation fromLocation(Location location) {
        return new SimpleLocation(
                location.getWorld().getName(),
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getYaw(),
                location.getPitch()
        );
    }

    public Location toLocation() {
        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }

    public String toString() {
        return world + "," + x + "," + y + "," + z + "," + yaw + "," + pitch;
    }
}
