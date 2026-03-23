# Fabric Multi-Version Mod Template

An extensible Fabric mod template for Minecraft that serves as a foundation for developing mods that support multiple Minecraft versions. The template demonstrates basic mod structures, client-side commands, and multi-version builds.

This project is configured to provide a consistent development environment across all operating systems (Windows, macOS, Linux) and supports multiple Minecraft versions.

## 🚀 Quick Start

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd fabric-multi-version-mod-template
   ```

2. **Install Java 21 JDK** (if not present)

3. **Build mod for Minecraft 1.21.1:**
   ```bash
   ./gradlew :mc-1.21.1:build
   ```

4. **Test in game:**
   - Find mod in `mc-1.21.1/build/libs/` (file without `-dev` or `-sources`)
   - Install as mod in Minecraft Launcher
   - Type `/hello` in chat

## 📋 Supported Versions

* **Minecraft Versions:**
  - 1.21.1 (fully configured)
  - 1.21.2 (example setup, can be customized)
* **Java:** 21
* **Fabric API:** Current version per Minecraft version

## 🏗️ Project Structure

```
fabric-multi-version-mod-template/
├── core/                          # Shared source code
│   ├── src/main/java/com/multiversion/template/
│   │   ├── ExampleMod.java               # Server mod (empty)
│   │   └── ExampleModClient.java         # Client mod with commands
│   └── src/main/resources/
│       └── fabric.mod.json                # Mod configuration
├── mc-1.21.1/                    # Build configuration for MC 1.21.1
│   ├── build.gradle              # Gradle build script
│   └── gradle.properties         # Versions and settings
├── mc-1.21.2/                    # Example for additional version
├── gradle/wrapper/               # Gradle Wrapper
├── build.gradle                  # Root build script
├── settings.gradle               # Project configuration
└── README.md
```

## ⚙️ Gradle Commands Overview

### Basic Commands

| Command                                    | Description                              | Example |
|--------------------------------------------|------------------------------------------|---------|
| `./gradlew --version`                      | Show Gradle version                      | `./gradlew --version` |
| `./gradlew tasks`                          | List all available tasks                 | `./gradlew tasks` |
| `./gradlew clean`                          | Clear build directories                  | `./gradlew clean` |
| `./gradlew build`                          | Build all versions                       | `./gradlew build` |
| `./gradlew :mc-X.Y.Z:build`                | Build specific version                   | `./gradlew :mc-1.21.1:build` |
| `./gradlew addMcVersion -PmcVersion=X.Y.Z` | Add new MC version                       | `./gradlew addMcVersion -PmcVersion=1.21.3` |
| `./gradlew addMcVersion -PmcVersion=X-Y-Z` | Add new MC version with dashes (windows) | `./gradlew addMcVersion -PmcVersion=1-21-4` |

### Development and Testing

| Command | Description | Usage |
|---------|-------------|-------|
| `./gradlew :mc-1.21.1:runClient` | Start Minecraft client | For development and testing |
| `./gradlew :mc-1.21.1:runServer` | Start Minecraft server | For server testing |
| `./gradlew :mc-1.21.1:genSources` | Generate Minecraft source code | For IDE support |

### Multi-Version Builds

* **Build specific version:**
  ```bash
  ./gradlew :mc-1.21.1:build
  ```

* **Build all versions:**
  ```bash
  ./gradlew build
  ```

* **Parallel builds (faster):**
  ```bash
  ./gradlew build --parallel
  ```

## 1. Setup Development Environment

Make sure you have the **Java 21 JDK** installed.

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd fabric-multi-version-mod-template
   ```

2. **Set up IDE (IntelliJ IDEA recommended):**
   * Open the project directory in IntelliJ IDEA.
   * IntelliJ recognizes the `build.gradle` and configures the project automatically. This may take a few minutes as Gradle downloads Minecraft dependencies.
   * Run the `genSources` task to make the decompiled Minecraft source code available for your IDE. You can find it in the Gradle panel on the right side under `Tasks > fabric > genSources`.

## 2. Build Project

To create a `.jar` file of the mod that you can use in a real Minecraft client or server, run the following command in the terminal:

* **For Windows:**
  ```bash
  .\gradlew.bat build
  ```
* **For macOS/Linux:**
  ```bash
  ./gradlew build
  ```

The finished mod file can be found in the `build/libs/` directory afterwards. The file without `-dev` or `-sources` is the one you can distribute.

## 3. Testing in Development Environment

You can start the game directly from your IDE or via the terminal to test the mod.

* **Start the client:**
  * Run the `runClient` task in the Gradle panel (`Tasks > fabric > runClient`).
  * Or in terminal: `./gradlew runClient`

* **Start the server:**
  * Run the `runServer` task in the Gradle panel (`Tasks > fabric > runServer`).
  * Or in terminal: `./gradlew runServer`
  * On first start, you must accept the `eula.txt`.

## 🎮 Mod Commands Overview

### Available Commands

| Command | Description | Example Output |
|---------|-------------|----------------|
| `/hello` | Simple greeting command | `Hello, world!` |

### How do the commands work?

The commands are registered client-side and work in both singleplayer and multiplayer servers. They use the Fabric Command API v2 for robust command processing.

**Code example for `/hello`:**
```java
dispatcher.register(literal("hello")
    .executes(context -> {
        context.getSource().sendFeedback(() -> Text.literal("Hello, world!"), false);
        return 1; // Signal success
    }));
```

## 🔧 Extending the Template

### Adding New Commands

1. **Create new method in `ExampleModClient.java`:**
   ```java
   private void registerExampleCommand(CommandDispatcher<ServerCommandSource> dispatcher) {
       dispatcher.register(literal("example")
           .executes(context -> {
               context.getSource().sendFeedback(() ->
                   Text.literal("Example command executed!"), false);
               return 1;
           }));
   }
   ```

2. **Register command in `onInitializeClient()`:**
   ```java
   CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
       registerHelloCommand(dispatcher);
       registerExampleCommand(dispatcher); // Add new command
   });
   ```

3. **Rebuild and test mod:**
   ```bash
   ./gradlew :mc-1.21.1:build
   ./gradlew :mc-1.21.1:runClient
   ```

### Further Command Examples

The template already contains a simple `/hello` command as an example. Here are more ideas for commands you can implement:

**Example: Simple Info Command**
```java
private void registerInfoCommand(CommandDispatcher<ServerCommandSource> dispatcher) {
    dispatcher.register(literal("info")
        .executes(context -> {
            context.getSource().sendFeedback(() ->
                Text.literal("This is an example mod!"), false);
            return 1;
        }));
}
```

**Example: Player Position Command**
```java
private void registerPositionCommand(CommandDispatcher<ServerCommandSource> dispatcher) {
    dispatcher.register(literal("position")
        .executes(context -> {
            PlayerEntity player = (PlayerEntity) context.getSource().getEntity();
            if (player != null) {
                BlockPos pos = player.getBlockPos();
                context.getSource().sendFeedback(() ->
                    Text.literal(String.format("Position: %d, %d, %d", pos.getX(), pos.getY(), pos.getZ())), false);
            }
            return 1;
        }));
}
```

**Example: World Time Command**
```java
private void registerTimeCommand(CommandDispatcher<ServerCommandSource> dispatcher) {
    dispatcher.register(literal("worldtime")
        .executes(context -> {
            long time = context.getSource().getWorld().getTime();
            context.getSource().sendFeedback(() ->
                Text.literal("World Time: " + time), false);
            return 1;
        }));
}
```

### Adding New Minecraft Version

**Automatically with Gradle task (recommended):**

1. **Use Gradle task (non-interactive):**
   ```bash
   # Linux/macOS
   ./gradlew addMcVersion -PmcVersion=1.21.11

   # Windows (dashes are automatically converted to dots)
   .\gradlew.bat addMcVersion -PmcVersion=1-21-11
   ```
   **Important:** After adding a version, check the correct Fabric versions at https://fabricmc.net/develop/ and update the `gradle.properties` in the new folder if necessary.

2. **Build new version:**
   ```bash
   ./gradlew :mc-1.21.11:build
   ```

**Manually (alternative):**

1. **Create new directory:**
   ```bash
   mkdir mc-1.21.11
   ```

2. **Copy configuration files:**
   ```bash
   cp mc-1.21.1/build.gradle mc-1.21.11/
   cp mc-1.21.1/gradle.properties mc-1.21.11/
   ```

3. **Update versions in `mc-1.21.11/gradle.properties`:**
   ```properties
    minecraft_version=1.21.11
    yarn_mappings=1.21.11+build.4
    loader_version=0.18.4
    loom_version=1.14-SNAPSHOT

    # Fabric API
    fabric_api_version=0.141.1+1.21.11
   ```

4. **Add project to `settings.gradle`:**
   ```gradle
   include 'mc-1.21.11'
   ```

5. **Build new version:**
   ```bash
   ./gradlew :mc-1.21.11:build
   ```

### Customizing Mod Configuration

**Edit `fabric.mod.json`:**
- Change mod ID, name, description
- Update authors and contact information
- Add new dependencies
- Add entry points for server mod (if needed)

**Example for server mod entry point:**
```json
{
  "entrypoints": {
    "client": ["hub.guzio.minetrix.ExampleModClient"],
    "server": ["hub.guzio.minetrix.ExampleMod"]
  }
}
```

## 🛠️ Advanced Gradle Options

### Build Optimizations

* **Parallel execution:**
  ```bash
  ./gradlew build --parallel
  ```

* **Use build cache:**
  ```bash
  ./gradlew build --build-cache
  ```

* **Run only specific tasks:**
  ```bash
  ./gradlew :mc-1.21.1:compileJava :mc-1.21.1:processResources
  ```

### Debugging and Troubleshooting

* **Verbose output:**
  ```bash
  ./gradlew build --info
  ```

* **Stacktrace on errors:**
  ```bash
  ./gradlew build --stacktrace
  ```

* **Show dependency tree:**
  ```bash
  ./gradlew :mc-1.21.1:dependencies
  ```

* **Clear cache:**
  ```bash
  ./gradlew clean
  rm -rf ~/.gradle/caches/
  ```

## 📜 Credits

This template was created by shyskyfox. If you use this template for your own mods, please give a short mention in your mod description or README. For example:

"Based on the Fabric Multi-Version Mod Template by shyskyfox (https://github.com/shyskyfox/fabric-multi-version-mod-template)"

The MIT license requires retaining the copyright notice in all copies or substantial portions of the software.

## 📚 Further Resources

- [Fabric Modding Wiki](https://fabricmc.net/wiki/start)
- [Fabric Command API Documentation](https://fabricmc.net/wiki/tutorial:commands)
- [Minecraft Forge vs Fabric](https://fabricmc.net/wiki/faq#fabric_vs_forge)
- [Gradle Build Tool](https://gradle.org/documentation/)

---

<div align="center">Thanks for reading :3</div>

---
