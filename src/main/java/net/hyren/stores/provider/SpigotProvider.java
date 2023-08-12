package net.hyren.stores.provider;

import net.hyren.stores.StoresPlugin;
import net.hyren.stores.data.UserCache;
import net.hyren.stores.data.UserController;
import net.hyren.stores.data.UserStorage;
import org.bukkit.event.Listener;

public class SpigotProvider implements Listener {

    protected StoresPlugin plugin = StoresPlugin.getInstance();
    protected UserCache userCache = plugin.getUserCache();

    protected UserController userController = plugin.getUserController();
    protected UserStorage userStorage = plugin.getUserStorage();
}
