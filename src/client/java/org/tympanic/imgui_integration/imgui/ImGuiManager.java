package org.tympanic.imgui_integration.imgui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import finalforeach.cosmicreach.gamestates.GameState;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class ImGuiManager {
    private ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();
    private ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
    private InputProcessor tmpProcessor;
    public boolean hasBeenInitialized = false;
    public List<ImGuiWindow> windows = new ArrayList<>();
    public static ImGuiManager INSTANCE = new ImGuiManager();

    private String glslVersion;

    private void decideGlGlslVersions() {
        final boolean isMac = System.getProperty("os.name").toLowerCase().contains("mac");
        if (isMac) {
            glslVersion = "#version 150";
            GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
            GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
            GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);  // 3.2+ only
            GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);          // Required on Mac
        } else {
            glslVersion = "#version 130";
            GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
            GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 0);
        }
    }


    public void init() {
        if (!hasBeenInitialized) {
            decideGlGlslVersions();
            long windowHandle = ((Lwjgl3Graphics) Gdx.graphics).getWindow().getWindowHandle();
            ImGui.createContext();
            ImGuiIO io = ImGui.getIO();
            io.setIniFilename(null);

            // Set flags
            io.addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);
//            io.addConfigFlags(ImGuiConfigFlags.NavEnableGamepad);
            io.addConfigFlags(ImGuiConfigFlags.DockingEnable);
            io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable);
            io.setConfigWindowsResizeFromEdges(false);

            io.getFonts().addFontDefault();
            io.getFonts().build();
            imGuiGlfw.init(windowHandle, true);
            imGuiGl3.init(glslVersion);
            hasBeenInitialized = true;
        }
    }

    public void start() {
        if (tmpProcessor != null) {
            Gdx.input.setInputProcessor(tmpProcessor);
            tmpProcessor = null;
        }

        imGuiGl3.newFrame();
        imGuiGlfw.newFrame();
        ImGui.newFrame();
    }

    public void render() {
        start();
        for (ImGuiWindow window : windows) {
            if (!window.hasBeenInitialized) {
                window.init();
                window.hasBeenInitialized = true;
            }
            if (window.rendersIn(GameState.currentGameState.getClass()) || window.rendersIn(GameState.class)) {
                window.render();
            }
        }
        end();
    }

    public void tick() {
        for (ImGuiWindow window : windows) {
            if (!window.hasBeenInitialized) {
                window.init();
                window.hasBeenInitialized = true;
            }
            if (window.rendersIn(GameState.currentGameState.getClass()) || window.rendersIn(GameState.class)) {
                window.tick();
            }
        }
    }

    public void end() {
        ImGui.render();
        imGuiGl3.renderDrawData(ImGui.getDrawData());

        // Update and Render additional Platform Windows
        if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            final long backupCurrentContext = GLFW.glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            GLFW.glfwMakeContextCurrent(backupCurrentContext);
        }

        if (ImGui.getIO().getWantCaptureKeyboard() || ImGui.getIO().getWantCaptureMouse()) {
            tmpProcessor = Gdx.input.getInputProcessor();
            Gdx.input.setInputProcessor(null);
        }
    }

    public void dispose() {
        imGuiGl3.shutdown();
        imGuiGl3 = null;
        imGuiGlfw.shutdown();
        imGuiGlfw = null;
        ImGui.destroyContext();

        for (ImGuiWindow window : windows) {
            if (window.hasBeenInitialized) {
                window.dispose();
            }
        }
    }
}