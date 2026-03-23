package org.tympanic.imgui_integration;

import dev.puzzleshq.puzzleloader.loader.mod.entrypoint.client.ClientModInit;

public class ClientInitializer implements ClientModInit {

    @Override
    public void onClientInit() {
        Constants.LOGGER.info("{} init", Constants.MOD_ID);
    }
}
