package net.hyren.stores;

import lombok.Getter;
import net.hyren.core.misc.plugin.CustomPlugin;
import net.hyren.core.misc.registry.DefaultRegistry;
import net.hyren.stores.commands.StoresCommand;
import net.hyren.stores.configuration.impl.MessageConfiguration;
import net.hyren.stores.data.UserCache;
import net.hyren.stores.listener.GeneralListener;
import net.hyren.stores.view.StoresView;
import org.bukkit.configuration.ConfigurationSection;

@Getter
public class StoresPlugin extends CustomPlugin {

    private DefaultRegistry defaultRegistry;
    private UserCache userCache;
    private MessageConfiguration messageConfiguration;

    @Override
    public void onLoading() {
       defaultRegistry = new DefaultRegistry(this);
       userCache = new UserCache();
       messageConfiguration = new MessageConfiguration(this);
    }

    @Override
    public void onStart() {
        saveDefaultConfig();
        loadRegistry();

    }

    @Override
    public void onEnd() {

    }

    @Override
    public void loadRegistry() {
        defaultRegistry.registerCommands(
                new StoresCommand(this)
        );

        defaultRegistry.registerViews(
                new StoresView(this)
        );

        defaultRegistry.registerListener(
                new GeneralListener(this)
        );
    }

    public static StoresPlugin getInstance() {
        return getPlugin(StoresPlugin.class);
    }

    public ConfigurationSection getSection(String value) {
        return getConfig().getConfigurationSection(value);
    }
}
