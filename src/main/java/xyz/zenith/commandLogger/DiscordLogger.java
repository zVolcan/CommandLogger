package xyz.zenith.commandLogger;

import com.eduardomcb.discord.webhook.WebhookManager;
import com.eduardomcb.discord.webhook.models.Message;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

public final class DiscordLogger {

    @Getter
    @Setter
    private String WEBHOOK_URL;
    private final CommandLogger plugin;

    public DiscordLogger(CommandLogger plugin) {
        this.plugin = plugin;
        setWEBHOOK_URL(plugin.getConfig().getString("discord-webhook-url"));
    }

    public void sendMessage(Player player, String command) {
        if (WEBHOOK_URL == null || WEBHOOK_URL.isEmpty()) {
            plugin.getLogger().warning("Discord webhook URL is not set! Please set it in the config.yml file.");
            return;
        }

        WebhookManager manager = new WebhookManager()
                .setChannelUrl(WEBHOOK_URL);

        manager.setMessage(
                new Message()
                        .setAvatarUrl("https://render.crafty.gg/2d/head/" +  player.getUniqueId().toString().replace("-", ""))
                        .setContent("**Command Executed:** " + command)
                )
                .exec();
    }
}
