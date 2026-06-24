package com.darkmattrmaestro.imgui_example;

import dev.puzzleshq.puzzleloader.loader.mod.entrypoint.client.ClientModInit;
import org.tympanic.imgui_integration.imgui.ImGuiManager;

public class ClientInitializer implements ClientModInit {

    @Override
    public void onClientInit() {
        Constants.LOGGER.info("{} init", Constants.MOD_ID);
        ImGuiManager.INSTANCE.windows.add(new TestWindow());
    }
}
