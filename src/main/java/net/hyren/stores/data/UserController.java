package net.hyren.stores.data;

import lombok.RequiredArgsConstructor;
import net.hyren.stores.StoresPlugin;
import net.hyren.stores.data.store.Stores;
import org.bukkit.entity.Player;


@RequiredArgsConstructor
public class UserController {

    private final StoresPlugin plugin;

    public void create(Player player) {
        final UserStorage userStorage = plugin.getUserStorage();
        final UserCache userCache = plugin.getUserCache();

        final User find = userStorage.find(player.getName());
        if (find != null) {
            userCache.addCachedElement(find);
            return;
        }

        User user = new User(
                player.getName(),
                0,
                new Stores(
                        0,
                        0,
                        null
                )
        );

        userStorage.insert(user);
        userCache.addCachedElement(user);
    }

    public void remove(Player player) {
        final UserStorage userStorage = plugin.getUserStorage();
        ;
        final UserCache userCache = plugin.getUserCache();

        final User find = userStorage.find(player.getName());
        if (find == null) return;

        userStorage.update(find);
        userCache.removeCachedElement(find);
    }
}