package xyz.zenith.commandLogger.luckperms;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.node.NodeAddEvent;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.types.InheritanceNode;
import xyz.zenith.commandLogger.CommandLogger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class LuckPermsManager {

    private final Map<String, List<String>> cache = new HashMap<>();
    private CommandLogger plugin;
    private LuckPerms luckPerms;

    public LuckPermsManager(final CommandLogger plugin) {
        if (plugin.getServer().getPluginManager().getPlugin("LuckPerms") == null) {
            plugin.getLogger().warning("LuckPerms is not installed! CommandLogger will not be able to fetch player prefixes and suffixes.");
            return;
        }

        this.plugin = plugin;
        this.luckPerms = plugin.getServer().getServicesManager().load(LuckPerms.class);
        registerEventLog();
    }

    public List<String> getPlayerCache(String playerName) {
        return cache.getOrDefault(playerName, new ArrayList<>());
    }

    public void registerEventLog() {
        luckPerms.getEventBus().subscribe(plugin,
                NodeAddEvent.class
        , event -> {
            User user = (User) event.getTarget();
            Node node = event.getNode();

            if (node instanceof InheritanceNode nodoGroup) {
                final String groupName = nodoGroup.getGroupName();
                List<String> groups = getPlayerCache(user.getUsername());
                groups.add(groupName);
                cache.put(user.getUsername(), groups);
            }
        }).close();
    }
}
