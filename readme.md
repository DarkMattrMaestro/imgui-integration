The Imgui Integration mod allows you to use Dear Imgui on any Cosmic Reach GameState.

## Usage

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

### Building

Run `./gradlew shadowJar` (optionally with the `--info` flag) to build the `jar`.
