# AGENTS.md

## Project Overview

CommandLogger is a Minecraft Paper plugin that logs executed commands to Discord via webhooks. It monitors `PlayerCommandPreprocessEvent` and sends command data asynchronously to a configured Discord webhook.

**Key Technologies:**
- Java 21 (toolchain)
- Gradle (Kotlin DSL)
- PaperMC API 1.21
- Lombok (code generation)
- Discord Webhook library (eduardomcb/discord-webhook)
- LuckPerms (optional integration for prefixes/suffixes)

**Architecture:**
- `CommandLogger.java` - Main plugin class implementing `Listener`, registers events and commands
- `commands/MainCommand.java` - `/commandlogger` command executor (reload + player lookup)
- `DiscordLogger.java` - Webhook communication layer
- `luckperms/LuckPermsManager.java` - Optional LuckPerms integration (placeholder)

## Setup Commands

```bash
# Clone and setup
git clone <repo-url>
cd CommandLogger

# Build the plugin (shadow JAR with dependencies bundled)
./gradlew build

# Build only the fat JAR (ready for server)
./gradlew shadowJar

# Clean build artifacts
./gradlew clean
```

## Development Workflow

```bash
# Start a test server (requires 2G RAM)
./gradlew runServer

# Build and hot-reload during development
./gradlew build
# Then copy build/libs/*.jar to your Paper server plugins folder
```

**Note:** The plugin uses `compileOnly` for Paper API and LuckPerms, meaning it must be run on a live Paper server for testing.

## Testing Instructions

No automated tests currently configured. Manual testing requires:
1. Running on a Paper 1.21.11 server
2. Setting `discord-webhook-url` in `config.yml`
3. Executing commands as a player without `commandlogger.bypass` permission

## Code Style Guidelines

- **Language:** Java 21 with Lombok annotations (`@Getter`, `@Setter`)
- **Naming:** Standard Bukkit convention (`onEnable`, `onDisable`, event handlers)
- **Package structure:** `xyz.zenith.commandLogger` -> subpackages per concern
- **Asynchronous:** Command logging runs in async tasks to avoid blocking the main thread

## Build and Deployment

```bash
# Output location
build/libs/CommandLogger-<version>.jar  # (shadowJar produces unclassified JAR)

# Plugin metadata in paper-plugin.yml
# - api-version: 1.21
# - dependencies: LuckPerms (optional, load BEFORE)
```

**Deployment:**
1. Run `./gradlew shadowJar`
2. Copy the JAR from `build/libs/` to your Paper server's `plugins/` folder
3. Restart or reload the server
4. Configure `discord-webhook-url` in the generated `config.yml`

## Configuration

```yaml
# config.yml
discord-webhook-url: ""  # Set your Discord webhook URL here
```

## Permissions

| Permission | Description |
|------------|-------------|
| `commandlogger.bypass` | Bypass command logging (exempts player from webhook notifications) |

## Additional Notes

- LuckPermsManager exists but is not fully integrated (warning logged if LuckPerms not found)
- Discord notifications include player head as avatar and command content
- Commands stored in-memory in `commandMap` (cleared on plugin disable)