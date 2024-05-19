package com.github.srminister.stores.commands;

import com.github.srminister.stores.StoresPlugin;
import com.github.srminister.stores.configuration.provider.MessageProvider;
import com.github.srminister.stores.misc.store.Store;
import com.github.srminister.stores.misc.store.StoreCache;
import com.github.srminister.stores.misc.store.SimpleLocation;
import com.github.srminister.stores.view.StoresView;
import com.google.common.collect.ImmutableMap;
import com.intellectualcrafters.plot.object.PlotPlayer;
import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class StoresCommand {

    private final StoresPlugin plugin;
    private final StoreCache storeCache;

    @Command(
            name = "loja",
            aliases = {"lojas"}
    )

    public void execute(Context<CommandSender> context) {
        final Player player = (Player) context.getSender();

        plugin.getViewFrame().open(
                StoresView.class,
                player,
                ImmutableMap.of(
                        "userCache",
                        storeCache
                )
        );
    }

    @Command(
            name = "loja.setloc"
    )

    public void executeSetLoc(Context<CommandSender> context) {
        final Player player = (Player) context.getSender();
        final Store store = storeCache.getByUser(player.getName());
        final PlotPlayer plotPlayer = PlotPlayer.wrap(player.getPlayer());

        if (store.getLocation() != null) {
            MessageProvider.provide(player, "already-set");
            return;
        }

        if (plotPlayer.getLocation().isPlotArea() && plotPlayer.getCurrentPlot() != null) {
            if (plotPlayer.getCurrentPlot().isOwner(player.getPlayer().getUniqueId())) {
                MessageProvider.provide(player, "set");

                store.setLocation(SimpleLocation.fromLocation(player.getLocation())
                        .toString());
                return;
            }
            MessageProvider.provide(player, "not-owner");
            return;
        }
        MessageProvider.provide(player, "not-owner");
    }


    @Command(
            name = "loja.removeloc"
    )

    public void executeRemove(Context<CommandSender> context) {
        final Player player = (Player) context.getSender();

        final Store store = storeCache.getByUser(player.getName());
        if (store.getLocation() != null) {
            store.setLocation(null);
            MessageProvider.provide(player, "remove");
            return;
        }
        MessageProvider.provide(player, "no-store-remove");
    }

    @Command(
            name = "loja.ir"
    )

    public void executeLoc(Context<CommandSender> context, OfflinePlayer target) {
        final Player player = (Player) context.getSender();
        final Store store = storeCache.getByUser(target.getName());

        if (store.getLocation() == null) {
            MessageProvider.provide(player, "no-store");
            return;
        }

        if (store.getName().equalsIgnoreCase(player.getName())) {
            MessageProvider.provide(player, "own-store");

            final SimpleLocation location = SimpleLocation.fromString(store.getLocation());
            player.teleport(location.toLocation());
            return;
        }

        MessageProvider.provide(player, "visit", ImmutableMap.of("<user>", store.getName()));

        store.addVisits(1);

        final SimpleLocation location = SimpleLocation.fromString(store.getLocation());
        player.teleport(location.toLocation());
    }
}