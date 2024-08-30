package dev.jolkert.deselect.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerInventory;
import org.lwjgl.glfw.GLFW;

public class DeselectClient implements ClientModInitializer
{
	private static final KeyBinding DESELECT_BIND = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"key.deselect.deselect",
			InputUtil.Type.KEYSYM,
			GLFW.GLFW_KEY_Z,
			"category.deselect.test"
	));
	private static final int DESLECTED_SLOT_NUMBER = -15;
	private static Integer previousSelection = 0;

	@Override
	public void onInitializeClient()
	{
		// well that was a lot easier than expected. certainly a very jank way to do that but
		// i suppose it works??
		// - morgan 2024-08-30
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (DESELECT_BIND.wasPressed()) {
				assert client.player != null;
				PlayerInventory inventory = client.player.getInventory();
				if (inventory.selectedSlot == DESLECTED_SLOT_NUMBER)
					inventory.selectedSlot = previousSelection;
				else
				{
					previousSelection = inventory.selectedSlot;
					inventory.selectedSlot = DESLECTED_SLOT_NUMBER;
				}
			}
		});
	}
}
