package net.hyren.stores.configuration.impl;

import lombok.Getter;
import net.hyren.stores.StoresPlugin;
import net.hyren.stores.configuration.Configuration;
import net.hyren.stores.configuration.helper.ListHelper;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class MessageConfiguration implements Configuration<String, String[]> {
    private static final Map<String, String[]> CACHE_MAP = new HashMap<>();
    private static final String FILE_NAME = "messages.yml";

    private final StoresPlugin plugin;
    private final FileConfiguration configuration;

    public MessageConfiguration(StoresPlugin plugin) {
        this.plugin = plugin;
        this.configuration = this.setup();

        this.loadValues(configuration.getConfigurationSection("Messages"));
    }

    @Override
    public void loadValues(ConfigurationSection section) {
        section.getKeys(false).forEach(key -> {
            final List<String> list = ListHelper.colorize(section.getStringList(key));
            this.getCache().put(key, list.toArray(new String[0]));
        });
    }

    @Override
    public FileConfiguration setup() {
        final File file = this.get();
        if(!file.exists()) plugin().saveResource(FILE_NAME, false);

        return this.load(file);
    }

    @Override
    public File get() {
        return new File(
          plugin().getDataFolder() + File.separator,
          FILE_NAME
        );
    }

    public Map<String, String[]> getCache() {
        return CACHE_MAP;
    }
}
