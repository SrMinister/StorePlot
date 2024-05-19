package com.github.srminister.stores.view;

import com.github.srminister.stores.StoresPlugin;
import com.github.srminister.stores.configuration.provider.MessageProvider;
import com.github.srminister.stores.misc.store.Store;
import com.github.srminister.stores.misc.store.StoreCache;
import com.github.srminister.stores.utils.ItemBuilder;
import com.github.srminister.stores.utils.NumberFormatter;
import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import me.devnatan.inventoryframework.component.Pagination;
import me.devnatan.inventoryframework.context.RenderContext;
import me.devnatan.inventoryframework.state.State;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.github.srminister.stores.configuration.provider.ItemProvider.getItemSlot;
import static com.github.srminister.stores.configuration.provider.ItemProvider.provideItem;

@RequiredArgsConstructor
public class StoresView extends View {

    private final StoresPlugin plugin;
    private final State<StoreCache> userState = initialState("userCache");

    private final State<Pagination> paginationState = lazyPaginationState(
            (context) -> stores(),
            (context, item, index, store) -> item.withItem(
                    new ItemBuilder(provideItem("stores",
                            ImmutableMap.of(
                                    "<ranked>", Integer.toString(index),
                                    "<name>", store.getName(),
                                    "<stars>", NumberFormatter.format(store.getStars()),
                                    "<visits>", NumberFormatter.format(store.getVisits()))))
                            .skullOwner(store.getName())
                            .build()
            ).onClick(click -> {
                final StoreCache storeCache = userState.get(click);
                final Store user = storeCache.getByUser(click.getPlayer().getName());

                if (click.isLeftClick()) {
                    click.getPlayer().chat("/lojas ir " + store.getName());
                    return;
                }

                if (store.getName().equalsIgnoreCase(click.getPlayer().getName())) {
                    MessageProvider.provide(click.getPlayer(), "self-evaluation");
                    return;
                }

                if (user.isExpired()) {
                    store.addStars(1);
                    user.setTime(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1));

                    MessageProvider.provide(click.getPlayer(), "evaluation",
                            ImmutableMap.of(
                            "<user>", store.getName(),
                            "<stars>", NumberFormatter.format(store.getStars()))
                    );
                    return;
                }

                MessageProvider.provide(click.getPlayer(), "evaluation-time",
                        ImmutableMap.of(
                        "<time>", user.getTimeFormat())
                );
            })
    );

    @Override
    public void onInit(@NotNull ViewConfigBuilder config) {
        config.title("Lojas")
                .size(6)
                .layout(
                        "         ",
                        "  OOOOO  ",
                        "  OOOOO  ",
                        "  OOOOO  ",
                        "         ",
                        "         "
                )
                .cancelOnDrag()
                .cancelOnPickup()
                .cancelOnClick()
                .cancelOnDrop();
    }

    @Override
    public void onFirstRender(@NotNull RenderContext render) {
        final Pagination pagination = paginationState.get(render);

        render.slot(
                getItemSlot("information"),
                new ItemBuilder(
                        provideItem("information", ImmutableMap.of()))
                        .build());

        render.slot(18, new ItemBuilder(Material.ARROW)
                        .name("§aPágina anterior")
                        .build())
                .displayIf(pagination::canBack)
                .onClick(pagination::back);

        render.slot(26, new ItemBuilder(Material.ARROW)
                        .name("§aPróxima página")
                        .build())
                .displayIf(pagination::canAdvance)
                .onClick(pagination::advance);
    }


    public List<Store> stores() {
        return plugin.getStoreCache().getCachedElements()
                .stream()
                .filter(user -> user.getLocation() != null)
                .sorted((a, b) -> Integer.compare(b.getStars(), a.getStars()))
                .collect(Collectors.toList());
    }
}
