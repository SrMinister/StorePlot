package com.github.srminister.stores.configuration.adapter;

import com.github.srminister.stores.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ItemAdapter {
    public ItemStack read(ConfigurationSection section) {
        String[] strippedMaterial = section.getString("material").split(":");
        if(strippedMaterial.length != 2) return null;

        String rawMaterial = strippedMaterial[0];

        Material material;

        try {
            material = Material.getMaterial(Integer.parseInt(rawMaterial));
        } catch (NumberFormatException e) {
            material = Material.valueOf(rawMaterial);
        }

         short data = Short.parseShort(strippedMaterial[1]);

        String headTexture = section.getString("head-texture");
        String displayName = convert(section.getString("display-name"));

          List<String> lore = section.getStringList("lore")
                .stream().map(this::convert)
                .collect(Collectors.toList());

          int amount = (section.contains("amount") ? section.getInt("amount") : 1);

        if(material != Material.SKULL_ITEM && data != 3) {
            return new ItemBuilder(material, amount, data)
                    .name(displayName)
                    .lore(lore)
                    .acceptItemMeta(itemMeta -> {
                        if(section.getBoolean("glow")) {
                            itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 3, true);
                            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        }
                    }).build();
        }

        return new ItemBuilder(Material.SKULL_ITEM)
                .texture(headTexture)
                .name(displayName)
                .lore(lore)
                .amount(amount)
                .build();
    }

    public static ItemStack provide(ItemStack itemStack, Map<String, String> replace) {
        return replace(itemStack, replace);
    }

    private static ItemStack replace(ItemStack itemStack, Map<String, String> replace) {
        if(replace == null) return itemStack;

        ItemStack clone = itemStack.clone();
        ItemMeta itemMeta = clone.getItemMeta();

        for (Map.Entry<String, String> entry : replace.entrySet()) {
            itemMeta.setDisplayName(
                    itemMeta.getDisplayName().replace(entry.getKey(), entry.getValue())
            );
        }

          List<String> lore = new ArrayList<>();
        for (String datum : itemMeta.getLore()) {
            for (Map.Entry<String, String> entry : replace.entrySet()) datum = datum
                    .replace(entry.getKey(), entry.getValue());

            lore.add(datum);
        }

        itemMeta.setLore(lore);
        clone.setItemMeta(itemMeta);

        return clone;
    }

    private String convert(String data) {
        return ChatColor.translateAlternateColorCodes(
                '&', data
        );
    }
}