package com.github.srminister.stores.provider;

import com.github.srminister.stores.StoresPlugin;
import com.github.srminister.stores.data.UserCache;
import com.github.srminister.stores.data.UserController;
import com.github.srminister.stores.data.UserStorage;
import org.bukkit.event.Listener;

public class SpigotProvider implements Listener {

    protected StoresPlugin plugin = StoresPlugin.getInstance();
    protected UserCache userCache = plugin.getUserCache();

    protected UserController userController = plugin.getUserController();
    protected UserStorage userStorage = plugin.getUserStorage();
}
