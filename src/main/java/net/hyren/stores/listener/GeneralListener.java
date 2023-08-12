package net.hyren.stores.listener;

import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.var;
import net.hyren.stores.StoresPlugin;
import net.hyren.stores.data.User;
import net.hyren.stores.data.store.Stores;
import net.hyren.stores.provider.SpigotProvider;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class GeneralListener extends SpigotProvider {


    @EventHandler
    public void on(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        userController.create(player);

    }

    @EventHandler
    public void on(PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        userController.remove(player);
    }
}
