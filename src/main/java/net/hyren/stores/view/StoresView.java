package net.hyren.stores.view;

import com.google.common.collect.ImmutableMap;
import net.hyren.core.utils.ItemBuilder;
import net.hyren.core.utils.NumberFormatter;
import net.hyren.stores.StoresPlugin;
import net.hyren.stores.configuration.provider.MessageProvider;
import net.hyren.stores.data.User;
import network.twisty.core.misc.inventory.*;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static net.hyren.stores.configuration.provider.ItemProvider.getItemSlot;
import static net.hyren.stores.configuration.provider.ItemProvider.provideItem;

public class StoresView extends View {
    private final StoresPlugin plugin;

    private final int[] SLOTS = new int[]{
            11, 12, 13, 14, 15,
            20, 21, 22, 23, 24,
            29, 30, 31, 32, 33
    };

    public StoresView(StoresPlugin plugin) {
        super(6, "Lojas");
        this.plugin = plugin;

        setCancelOnClick(true);
        setCancelOnPickup(true);
    }

    @Override
    protected void onRender(ViewContext context) {
        List<User> users = plugin.getUserCache().getRanked();

        context.slot(
                getItemSlot("information"),
                new ItemBuilder(
                        provideItem("information", ImmutableMap.of()))
                        .build());

        for (int index = 0; index < 15; index++) {
            if ((index + 1) > users.size()) continue;

            final ItemStack userItem = userItem(users.get(index));
            int finalIndex = index;
            context.slot(SLOTS[index]).onRender(item -> item.setItem(userItem)).onClick(click -> {
                final User user = plugin.getUserCache().getByUsername(click.getPlayer().getName());
                click.close();

                if (click.getClickOrigin().isLeftClick()) {
                    click.getPlayer().chat("/lojas ir " + users.get(finalIndex).getName());
                    return;
                }

                if (click.getClickOrigin().isRightClick()) {
                    if (users.get(finalIndex).getName().equalsIgnoreCase(click.getPlayer().getName())) {
                        MessageProvider.provide(click.getPlayer(), "self-evaluation");
                        return;
                    }
                    if (user.isExpired()) {
                        users.get(finalIndex).getStores().addStars(1);
                        user.setTime(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1));

                        MessageProvider.provide(click.getPlayer(), "evaluation", ImmutableMap.of(
                                "<user>", users.get(finalIndex).getName(),
                                "<stars>", NumberFormatter.format(users.get(finalIndex).getStores().getStars())));
                        return;
                    }

                    MessageProvider.provide(click.getPlayer(), "evaluation-time", ImmutableMap.of(
                            "<time>", user.getTimeFormat()));
                }
            });
        }

        for (int slot : SLOTS) {
            final ViewItem item = context.getItem(slot);
            if (item == null) context.slot(slot).onRender(empty -> empty.setItem(emptyItem()));
        }
    }

    private ItemStack userItem(User user) {
        List<User> ranking = plugin.getUserCache().getRanked();

        final String ranked = ranking.get(0) != null && user.equals(ranking.get(0)) ?
                "§e§lPOPULAR!" : "";

        return new ItemBuilder(provideItem("stores",
                ImmutableMap.of(
                        "<ranked>", ranked,
                        "<name>", user.getName(),
                        "<stars>", NumberFormatter.format(user.getStores().getStars()),
                        "<visits>", NumberFormatter.format(user.getStores().getVisits()))))
                .skull(user.getName())
                .build();
    }

    private ItemStack emptyItem() {
        return new ItemBuilder(Material.SKULL_ITEM, 1, 3)
                .name("§8Vazio")
                .build();
    }
}
