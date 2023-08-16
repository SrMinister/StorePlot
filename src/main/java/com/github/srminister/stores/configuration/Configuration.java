package com.github.srminister.stores.configuration;

import com.github.srminister.stores.StoresPlugin;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Map;

public interface Configuration<K, V> {
    void loadValues(ConfigurationSection section);

    FileConfiguration setup();
    File get();

    Map<K, V> getCache();

    default void register(K k, V v) {
        this.getCache().put(k, v);
    }

    default FileConfiguration load(File file) {
        return YamlConfiguration.loadConfiguration(file);
    }

    default StoresPlugin plugin() {
        return StoresPlugin.getInstance();
    }
}
