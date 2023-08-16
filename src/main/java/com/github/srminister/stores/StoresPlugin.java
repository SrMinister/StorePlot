package com.github.srminister.stores;

import com.github.srminister.stores.commands.StoresCommand;
import com.github.srminister.stores.configuration.impl.MessageConfiguration;
import com.github.srminister.stores.data.User;
import com.github.srminister.stores.data.UserCache;
import com.github.srminister.stores.database.MySQLProvider;
import com.github.srminister.stores.listener.GeneralListener;
import com.github.srminister.stores.view.StoresView;
import lombok.Getter;
import net.hyren.core.misc.plugin.CustomPlugin;
import net.hyren.core.misc.registry.DefaultRegistry;
import com.github.srminister.stores.data.UserController;
import com.github.srminister.stores.data.UserStorage;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

@Getter
public class StoresPlugin extends CustomPlugin {

    private MySQLProvider mySQLProvider;
    private DefaultRegistry defaultRegistry;
    private UserCache userCache;
    private UserStorage userStorage;
    private UserController userController;
    private MessageConfiguration messageConfiguration;

    @Override
    public void onLoading() {
        mySQLProvider = new MySQLProvider(
                getConfig().getString("mysql.host"),
                getConfig().getInt("mysql.port"),
                getConfig().getString("mysql.database"),
                getConfig().getString("mysql.username"),
                getConfig().getString("mysql.password")
        );
        defaultRegistry = new DefaultRegistry(this);
        userCache = new UserCache();
        userStorage = new UserStorage();
        userController = new UserController(this);
        messageConfiguration = new MessageConfiguration(this);
    }

    @Override
    public void onStart() {
        saveDefaultConfig();
        mySQLProvider.init();

        loadRegistry();
    }

    @Override
    public void onEnd() {
        Bukkit.getOnlinePlayers().forEach(players -> {
            final User user = userCache.getByUsername(players.getName());
            if (user == null) return;

                userStorage.update(user);
        });
        mySQLProvider.closeConnection();
    }

    @Override
    public void loadRegistry() {
        defaultRegistry.registerCommands(
                new StoresCommand()
        );

        defaultRegistry.registerViews(
                new StoresView(this)
        );

        defaultRegistry.registerListener(
                new GeneralListener()
        );
    }

    public static StoresPlugin getInstance() {
        return getPlugin(StoresPlugin.class);
    }

    public ConfigurationSection getSection(String value) {
        return getConfig().getConfigurationSection(value);
    }
}
