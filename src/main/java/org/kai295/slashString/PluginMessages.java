package org.kai295.slashString;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class PluginMessages {

    private static final LegacyComponentSerializer LEGACY_AMPERSAND =
            LegacyComponentSerializer.legacyAmpersand();

    private final JavaPlugin plugin;
    private FileConfiguration config;

    PluginMessages(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    void reload() {
        File file = new File(plugin.getDataFolder(), "messages.yml");
        if (!file.exists()) {
            plugin.saveResource("messages.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    void send(CommandSender sender, String path, String... placeholderPairs) {
        if (placeholderPairs.length % 2 != 0) {
            throw new IllegalArgumentException("placeholderPairs must be key, value pairs");
        }
        String template = config.getString(path);
        if (template == null || template.isEmpty()) {
            plugin.getLogger().warning("Missing messages.yml key: " + path);
            sender.sendMessage(Component.text("Missing message: " + path, NamedTextColor.RED));
            return;
        }
        for (int i = 0; i < placeholderPairs.length; i += 2) {
            template = template.replace("{" + placeholderPairs[i] + "}", String.valueOf(placeholderPairs[i + 1]));
        }
        sender.sendMessage(LEGACY_AMPERSAND.deserialize(template));
    }
}
