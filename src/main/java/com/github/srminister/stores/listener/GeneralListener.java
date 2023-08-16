package com.github.srminister.stores.listener;

import lombok.RequiredArgsConstructor;
import com.github.srminister.stores.provider.SpigotProvider;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
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
