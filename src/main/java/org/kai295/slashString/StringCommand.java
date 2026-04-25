package org.kai295.slashString;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class StringCommand implements CommandExecutor {

    private final SlashString plugin;
    private final Map<UUID, Long> lastUseMillis = new ConcurrentHashMap<>();

    StringCommand(SlashString plugin) {
        this.plugin = plugin;
    }

    private static String formatWaitDuration(long seconds) {
        if (seconds <= 0) {
            return "a moment";
        }
        if (seconds == 1) {
            return "1 second";
        }
        return seconds + " seconds";
    }

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args
    ) {
        if (args.length >= 1 && "reload".equalsIgnoreCase(args[0])) {
            if (args.length > 1) {
                plugin.getMessages().send(sender, "reload.too-many-args");
                return true;
            }
            if (sender instanceof Player player && !player.isOp()) {
                plugin.getMessages().send(sender, "reload.no-permission");
                return true;
            }
            plugin.reloadConfig();
            plugin.getMessages().reload();
            plugin.getMessages().send(sender, "reload.success");
            return true;
        }

        if (args.length > 0) {
            plugin.getMessages().send(sender, "general.unknown-subcommand");
            return true;
        }

        if (!(sender instanceof Player player)) {
            plugin.getMessages().send(sender, "general.console-not-player");
            return true;
        }

        int cooldownSeconds = Math.max(0, plugin.getConfig().getInt("cooldown-seconds"));
        if (cooldownSeconds > 0) {
            Long last = lastUseMillis.get(player.getUniqueId());
            long now = System.currentTimeMillis();
            if (last != null) {
                long elapsedSeconds = (now - last) / 1000L;
                if (elapsedSeconds < cooldownSeconds) {
                    long remaining = cooldownSeconds - elapsedSeconds;
                    plugin.getMessages().send(player, "cooldown.active", "time", formatWaitDuration(remaining));
                    return true;
                }
            }
        }

        int amount = Math.max(1, plugin.getConfig().getInt("amount"));
        boolean dropIfFull = plugin.getConfig().getBoolean("drop-if-full", true);
        ItemStack stack = new ItemStack(Material.STRING, amount);
        Map<Integer, ItemStack> overflow = player.getInventory().addItem(stack);
        int notStored = overflow.values().stream().mapToInt(ItemStack::getAmount).sum();

        if (notStored > 0 && dropIfFull) {
            overflow.values().forEach(leftover ->
                    player.getWorld().dropItemNaturally(player.getLocation(), leftover));
        }

        lastUseMillis.put(player.getUniqueId(), System.currentTimeMillis());
        int stored = amount - notStored;
        if (notStored > 0 && !dropIfFull) {
            plugin.getMessages().send(player, "claim.partial-no-drop",
                    "stored", String.valueOf(stored),
                    "not_stored", String.valueOf(notStored));
        } else if (notStored > 0) {
            plugin.getMessages().send(player, "claim.received-with-drop", "amount", String.valueOf(amount));
        } else {
            plugin.getMessages().send(player, "claim.received-full", "amount", String.valueOf(amount));
        }
        return true;
    }
}
