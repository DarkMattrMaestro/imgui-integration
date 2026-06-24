The Imgui Integration mod allows you to use Dear Imgui on any Cosmic Reach GameState.

## Usage

See the [example mod](example/readme.md)

```java
public class TestWindow extends ImGuiWindow {
    public final ImBoolean SHOW_WINDOW = new ImBoolean(true);

    int[] test = new int[1];

    @Override
    public void init() {
        this.renderIn(ChatMenu.class);
        this.renderIn(InGame.class);
        this.renderIn(PauseMenu.class);
    }

    @Override
    public void render() {
        if (SHOW_WINDOW.get()) {
            ImGui.begin("Example Window", SHOW_WINDOW);
            ImGui.text("Hello!");
            ImGui.sliderInt("test int slider", test, 0, 100);
            ImGui.end();
        } else {
            ImGuiManager.INSTANCE.closeWindow(this);
        }
    }

    @Override
    public void tick() { }

    @Override
    public void dispose() {
        Constants.LOGGER.info("dispose window");
    }
}
```

```java
// Adding a window
ImGuiManager.INSTANCE.windows.add(new TestWindow());
```

## Testing & Building

Run the `gradle cleanOldJigsawLocal` and `gradle cleanOldJigsawGlobal` tasks to remove outdated Jigsaw directories from the local
and global environments.

Run the `gradle transformJars` task to update the game jars.

### Testing

Run `./gradlew runModdedClient` (optionally with `--warning-mode all`) to test the client and
`./gradlew runModdedServer` to test the server.

Run the `./gradlew -p example runModdedClient --warning-mode all` task to run the example mod.

### Building

Run `./gradlew clean -Pgroup='com.github.DarkMattrMaestro' -xtest assemble publishToMavenLocal --console=plain --info`
to build the `jar` and publish it to MavenLocal. This is similar to the command used by Jitpack. To only build the `jar`
without publishing it locally, run `./gradlew buildMergedJar`.
