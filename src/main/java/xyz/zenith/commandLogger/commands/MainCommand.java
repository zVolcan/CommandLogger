package xyz.zenith.commandLogger.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.zenith.commandLogger.CommandLogger;

import java.util.List;
import java.util.Map;

public final class MainCommand implements CommandExecutor {

    private final CommandLogger plugin;

    public MainCommand(CommandLogger plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Usage: /cl <reload>");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            plugin.getDiscordLogger().setWEBHOOK_URL(plugin.getConfig().getString("discord-webhook-url"));
            sender.sendMessage("Configuration reloaded!");
            return true;
        }

        Player player = plugin.getServer().getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>Player not found!"));
            return true;
        }

        Map<String, List<String>> commandMap = plugin.getCommandMap();
        if (!commandMap.containsKey(player.getName())) {
            sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>No command found for player " + player.getName() + "!"));
            return true;
        }

        List<String> commandsExecuted = commandMap.get(player.getName());

        sender.sendMessage(MiniMessage.miniMessage().deserialize("<green>Commands executed by " + player.getName() + ":"));
        for (String cmd : commandsExecuted) {
            sender.sendMessage(MiniMessage.miniMessage().deserialize("<yellow>" + cmd));
        }

        return true;
    }
}
