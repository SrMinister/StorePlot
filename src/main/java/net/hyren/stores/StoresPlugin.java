package net.hyren.stores;

import lombok.Getter;
import net.hyren.core.misc.plugin.CustomPlugin;
import net.hyren.core.misc.registry.DefaultRegistry;
import net.hyren.stores.commands.StoresCommand;
import net.hyren.stores.configuration.impl.MessageConfiguration;
import net.hyren.stores.data.User;
import net.hyren.stores.data.UserCache;
import net.hyren.stores.data.UserController;
import net.hyren.stores.data.UserStorage;
import net.hyren.stores.database.MySQLProvider;
import net.hyren.stores.listener.GeneralListener;
import net.hyren.stores.view.StoresView;
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
