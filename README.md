# CommandLogger

A Minecraft Paper plugin that logs player commands to Discord via webhooks.

## What it does

When a player executes a command, the plugin sends a notification to a configured Discord webhook. This allows server administrators to monitor command usage in real-time without being in-game.

The logging runs asynchronously to prevent server performance impact.

## Commands

### `/commandlogger reload`
Reloads the configuration file.

### `/commandlogger <player>`
Shows all commands executed by the specified player during the current session.

## Configuration

```yaml
# config.yml

# Discord webhook URL for sending notifications
# Must be a valid webhook URL from a Discord channel
discord-webhook-url: ""

# Commands that should not trigger notifications
bypass:
  - "tpa"
  - "tpaccept"
  - "spawn"
  - "home"

# LuckPerms integration (optional)
luckperms-log:
  enabled: true
  discord-webhook-url: ""  # Override webhook for LuckPerms-specific logs
```

## Permissions

| Permission | Description |
|------------|-------------|
| `commandlogger.bypass` | Players with this permission are not logged |

## Installation

1. Download the JAR from the releases page
2. Place the JAR file in your Paper server's `plugins/` folder
3. Restart or reload the server
4. Edit `plugins/CommandLogger/config.yml` to set your Discord webhook URL
5. Restart or use `/commandlogger reload` to apply changes

## Requirements

- Minecraft Paper 1.21 or higher
- Java 21

## Notes

- LuckPerms is optional and will be detected automatically if present
- Commands are stored in memory only; they are cleared when the server stops
- The plugin uses compile-only dependencies for Paper API and LuckPerms