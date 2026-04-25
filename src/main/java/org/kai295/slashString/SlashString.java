package org.kai295.slashString;

import org.bukkit.plugin.java.JavaPlugin;

public final class SlashString extends JavaPlugin {

    private StringCommand stringCommand;
    private PluginMessages pluginMessages;

    public PluginMessages getMessages() {
        return pluginMessages;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        pluginMessages = new PluginMessages(this);
        pluginMessages.reload();
        stringCommand = new StringCommand(this);
        var cmd = getCommand("string");
        if (cmd == null) {
            getLogger().severe("Command 'string' is missing from plugin.yml. Disabling SlashString.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        cmd.setExecutor(stringCommand);
    }

    @Override
    public void onDisable() {
        stringCommand = null;
        pluginMessages = null;
    }
}
