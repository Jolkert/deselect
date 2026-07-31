package dev.jolkert.deselect.fabric.client;

import com.mojang.blaze3d.platform.InputConstants;
import dev.jolkert.deselect.client.DeselectClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class DeselectClientFabricLatest implements ClientModInitializer
{

	@Override
	public void onInitializeClient()
	{
		DeselectClient.init(
			new KeyMapping(
				"key.deselect.deselect",
				InputConstants.Type.KEYSYM,
				GLFW.GLFW_KEY_Z,
				KeyMapping.Category.INVENTORY
			)
		);
		KeyMappingHelper.registerKeyMapping(DeselectClient.DESELECT_KEY);
		ClientTickEvents.END_CLIENT_TICK.register(DeselectClient::deselectAction);
	}
}
