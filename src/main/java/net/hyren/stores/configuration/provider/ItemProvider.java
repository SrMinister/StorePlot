package net.hyren.stores.configuration.provider;

import net.hyren.stores.StoresPlugin;
import net.hyren.stores.configuration.adapter.ItemAdapter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ItemProvider {

    private static final StoresPlugin PLUGIN = StoresPlugin.getInstance();
    public static ItemStack provideItem(String key, Map<String, String> replaceMap) {
        final ItemAdapter adapter = new ItemAdapter();

        final ConfigurationSection section = PLUGIN.getSection(key);
        return ItemAdapter.provide(adapter.read(section), replaceMap);
    }

    public static int getItemSlot(String key) {
        final ConfigurationSection section = PLUGIN.getSection(key);
        return section.getInt("slot");
    }
}
