package xyz.zenith.commandLogger;

import lombok.Getter;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.zenith.commandLogger.commands.MainCommand;

import java.util.*;

public final class CommandLogger extends JavaPlugin implements Listener {

    @Getter
    private DiscordLogger discordLogger;
    @Getter
    private final Map<String, List<String>> commandMap = new HashMap<>();

    @Override
    public void onEnable() {
        getLogger().info("§7╔══════════════════════════════════════╗§7");
        getLogger().info("§7║   §b  ____                        _     §7║§7");
        getLogger().info("§7║   §b / ___|___  _ __ ___  _ __   | |___ §7║§7");
        getLogger().info("§7║   §b| |   / _ \\| '_ ` _ \\| '_ \\  | / __|§7║§7");
        getLogger().info("§7║   §b| |__| (_) | | | | | | |_) | | \\__ \\§7║§7");
        getLogger().info("§7║   §b \\____\\___/|_| |_| |_| .__/  |_|___/§7║§7");
        getLogger().info("§7║   §e      CommandLogger Loaded          §7║§7");
        getLogger().info("§7╚══════════════════════════════════════╝§7");

        discordLogger = new DiscordLogger(this);
        getServer().getPluginManager().registerEvents(this, this);

        PluginCommand pluginCommand = getCommand("commandlogger");
        Objects.requireNonNull(pluginCommand).setExecutor(new MainCommand(this));
    }

    @Override
    public void onDisable() {
        commandMap.clear();
    }

    @EventHandler
    public void onCommandExecuted(PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();
        final String command = event.getMessage();
        getServer().getScheduler().runTaskAsynchronously(this, () -> {
            if (player.hasPermission("commandlogger.bypass")) {
                return;
            }

            discordLogger.sendMessage(player, command);
            List<String> commands = new ArrayList<>(commandMap.get(player.getName()));
            commands.add(command);

            commandMap.put(player.getName(), commands);
        });
    }

}
