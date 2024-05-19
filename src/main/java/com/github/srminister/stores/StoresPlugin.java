package com.github.srminister.stores;

import com.github.srminister.stores.commands.StoresCommand;
import com.github.srminister.stores.configuration.impl.MessageConfiguration;
import com.github.srminister.stores.misc.store.StoreCache;
import com.github.srminister.stores.database.MySQLProvider;
import com.github.srminister.stores.listener.GeneralListener;
import com.github.srminister.stores.misc.plugin.CustomPlugin;
import com.github.srminister.stores.misc.store.StoreStorage;
import com.github.srminister.stores.view.StoresView;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

@Getter
public class StoresPlugin extends CustomPlugin {

    private MySQLProvider mySQLProvider;
    private StoreCache storeCache;
    private StoreStorage storeStorage;
    private MessageConfiguration messageConfiguration;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        mySQLProvider = new MySQLProvider(
                getConfig().getString("mysql.host"),
                getConfig().getInt("mysql.port"),
                getConfig().getString("mysql.database"),
                getConfig().getString("mysql.username"),
                getConfig().getString("mysql.password")
        );
        storeCache = new StoreCache();
        storeStorage = new StoreStorage();
        messageConfiguration = new MessageConfiguration(this);

        mySQLProvider.init();
        loadRegistry();
    }

    @Override
    public void onDisable() {
        storeCache.getCachedElements().forEach(storeStorage::upsert);
        mySQLProvider.closeConnection();
    }

    public void loadRegistry() {
        registerCommands(
                new StoresCommand(this, storeCache)
        );
        registerViews(
                new StoresView(this)
        );

        registerListener(
                new GeneralListener(storeCache, storeStorage)
        );
    }

    public static StoresPlugin getInstance() {
        return getPlugin(StoresPlugin.class);
    }

    public ConfigurationSection getSection(String value) {
        return getConfig().getConfigurationSection(value);
    }
}
