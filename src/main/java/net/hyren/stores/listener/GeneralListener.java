package net.hyren.stores.listener;

import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.var;
import net.hyren.stores.StoresPlugin;
import net.hyren.stores.data.User;
import net.hyren.stores.data.store.Stores;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@RequiredArgsConstructor
public class GeneralListener implements Listener {

    private final StoresPlugin plugin;

    @EventHandler

    public void on(PlayerJoinEvent event) {
        var player = event.getPlayer();

        plugin.getUserCache().addCachedElement(
                User.builder()
                        .username(player.getName())
                        .stores(Stores.builder()
                                .stars(0)
                                .visits(0)
                                .location(null)
                                .build())
                        .build()
        );
    }
}
