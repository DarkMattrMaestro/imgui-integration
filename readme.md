Imgui Integration mod allows you to use imgui on any cosmic reach GameState

## Usage

```java
public class TestWindow extends ImGuiWindow {
    int[] test = new int[1];
    @Override
    public void init() {
        LOGGER.info("init window");
        this.renderIn(ChatMenu.class);
        this.renderIn(InGame.class);
        this.renderIn(PauseMenu.class);
    }
    @Override
    public void render() {
        ImGui.begin("testing");
        ImGui.text("test text");
        ImGui.sliderInt("test int slider", test, 0, 100);
        ImGui.end();
    }
    @Override
    public void dispose() {
        LOGGER.info("dispose window");
    }
}
```

```java
// Adding a window
ImGuiManager.INSTANCE.windows.add(new TestWindow());
```

