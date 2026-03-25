Imgui Integration mod allows you to use imgui on any cosmic reach GameState

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

