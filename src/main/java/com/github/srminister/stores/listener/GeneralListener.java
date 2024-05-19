package com.github.srminister.stores.listener;

import com.github.srminister.stores.misc.store.Store;
import com.github.srminister.stores.misc.store.StoreCache;
import com.github.srminister.stores.misc.store.StoreStorage;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class GeneralListener implements Listener {

    private final StoreCache storeCache;
    private final StoreStorage storeStorage;

    @EventHandler
    void onJoin(PlayerJoinEvent event) {
        storeStorage.load(event.getPlayer().getName());

    }

    @EventHandler
    void onQuit(PlayerQuitEvent event) {
        final Store store = storeCache.getByUser(event.getPlayer().getName());

        if (store == null) return;

        storeStorage.upsert(store);
    }
}
