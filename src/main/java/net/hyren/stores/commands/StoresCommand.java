package net.hyren.stores.commands;

import com.google.common.collect.ImmutableMap;
import com.intellectualcrafters.plot.object.PlotPlayer;
import lombok.RequiredArgsConstructor;
import net.hyren.core.misc.serializer.LocationSerializer;
import net.hyren.stores.configuration.provider.MessageProvider;
import net.hyren.stores.data.User;
import net.hyren.stores.provider.SpigotProvider;
import net.hyren.stores.view.StoresView;
import network.twisty.core.misc.commands.annotation.Command;
import network.twisty.core.misc.commands.command.Context;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class StoresCommand extends SpigotProvider {

    @Command(
            name = "stores",
            aliases = {"lojas"}
    )

    public void execute(Context<CommandSender> context) {
        final Player player = (Player) context.getSender();
        plugin.getDefaultRegistry().getViewFrame().open(StoresView.class, player);
    }

    @Command(
            name = "stores.setloc"
    )

    public void executeSetLoc(Context<CommandSender> context) {
        final Player player = (Player) context.getSender();
        final User user = userCache.getByUsername(player.getName());
        final PlotPlayer plotPlayer = PlotPlayer.wrap(player.getPlayer());

        if (user.getStores().getLocation() != null) {
            MessageProvider.provide(player, "already-set");
            return;
        }

        if (plotPlayer.getLocation().isPlotArea() && plotPlayer.getCurrentPlot() != null) {
            if (plotPlayer.getCurrentPlot().isOwner(player.getPlayer().getUniqueId())) {
                MessageProvider.provide(player, "set");

                final String location = LocationSerializer.serialize(player.getLocation(), false);
                user.getStores().setLocation(location);
                return;
            }
            MessageProvider.provide(player, "not-owner");
            return;
        }
        MessageProvider.provide(player, "not-owner");
    }


    @Command(
            name = "stores.removeloc"
    )

    public void executeRemove(Context<CommandSender> context) {
        final Player player = (Player) context.getSender();

        final User user = userCache.getByUsername(player.getName());
        if (user.getStores().getLocation() != null) {
            user.getStores().setLocation(null);
            MessageProvider.provide(player, "remove");
            return;
        }
        MessageProvider.provide(player, "no-store-remove");
    }

    @Command(
            name = "stores.ir"
    )

    public void executeLoc(Context<CommandSender> context, OfflinePlayer target) {
        final Player player = (Player) context.getSender();
        final User user = userCache.getByUsername(target.getName());

        if (user.getStores().getLocation() == null) {
            MessageProvider.provide(player, "no-store");
            return;
        }

        if (user.getName().equalsIgnoreCase(player.getName())) {
            MessageProvider.provide(player, "own-store");
            player.teleport(LocationSerializer.deserialize(user.getStores().getLocation()));
            return;
        }

        MessageProvider.provide(player, "visit", ImmutableMap.of("<user>", user.getName()));

        user.getStores().addVisits(1);
        player.teleport(LocationSerializer.deserialize(user.getStores().getLocation()));
    }
}
