package com.github.srminister.stores.misc.plugin;

import lombok.Getter;
import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewFrame;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import me.saiintbrisson.minecraft.command.message.MessageHolder;
import me.saiintbrisson.minecraft.command.message.MessageType;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public abstract class CustomPlugin extends JavaPlugin {

    private final ViewFrame viewFrame = ViewFrame.create(this);

    public void registerListener(Listener... listeners) {
        final PluginManager pluginManager = Bukkit.getPluginManager();

        for (Listener listener : listeners) {
            pluginManager.registerEvents(listener, this);
        }
    }

    public void registerCommands(Object... objects) {
        final BukkitFrame frame = new BukkitFrame(this);
        final MessageHolder messageHolder = frame.getMessageHolder();

        messageHolder.setMessage(MessageType.ERROR, "§cAFF! Ocorreu um erro ao executar este comando.");
        messageHolder.setMessage(MessageType.INCORRECT_TARGET, "§cAFF! Este comando não está disponível para '{target}'.");
        messageHolder.setMessage(MessageType.INCORRECT_USAGE, "§cYAY! Use: /{usage}");
        messageHolder.setMessage(MessageType.NO_PERMISSION, "§cAFF! Você não tem permissão para executar este comando.");
        frame.registerCommands(objects);
    }

    public void registerViews(View... views) {
        viewFrame.with(views).register();
    }
}
