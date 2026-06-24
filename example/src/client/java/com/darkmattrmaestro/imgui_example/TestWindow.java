package com.darkmattrmaestro.imgui_example;

import finalforeach.cosmicreach.gamestates.ChatMenu;
import finalforeach.cosmicreach.gamestates.InGame;
import finalforeach.cosmicreach.gamestates.PauseMenu;
import imgui.ImGui;
import imgui.type.ImBoolean;
import imgui.type.ImString;
import org.tympanic.imgui_integration.imgui.ImGuiManager;
import org.tympanic.imgui_integration.imgui.ImGuiWindow;

public class TestWindow extends ImGuiWindow {
    private final ImBoolean SHOW = new ImBoolean(true);

    private final ImString TEST_STR = new ImString();

    @Override
    public void init() {
        this.renderIn(ChatMenu.class);
        this.renderIn(InGame.class);
        this.renderIn(PauseMenu.class);
    }

    @Override
    public void render() {
        if (SHOW.get()) {
            if (ImGui.begin("Test Window", SHOW)) {
                ImGui.text("Test menu stuff...");
                ImGui.inputText("aaaaaa", TEST_STR);
            }
            ImGui.end();
        } else {
            ImGuiManager.INSTANCE.closeWindow(this);
        }
    }

    @Override
    public void tick() {

    }

    @Override
    public void dispose() {

    }
}