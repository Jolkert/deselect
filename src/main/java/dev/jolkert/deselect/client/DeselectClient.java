package dev.jolkert.deselect.client;

import dev.jolkert.deselect.Deselect;
import dev.jolkert.deselect.access.PreviousSelectionAccess;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class DeselectClient implements ClientModInitializer
{
	private static final KeyBinding DESELECT_BIND = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"key.deselect.deselect",
			InputUtil.Type.KEYSYM,
			GLFW.GLFW_KEY_Z,
			"category.deselect.test"
	));

	@Override
	public void onInitializeClient()
	{
		// ok so it turns out that was a smidge more involved than i thought. seems to be working now though? we'll see
		// - morgan 2024-08-30
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (DESELECT_BIND.wasPressed()) {
				assert client.player != null;
				PlayerInventory inventory = client.player.getInventory();

				if (inventory.selectedSlot == Deselect.DESELECTED_SLOT_NUMBER)
				{
					inventory.selectedSlot = ((PreviousSelectionAccess)inventory).deselect$getPreviousSlot();
				}
				else
				{
					((PreviousSelectionAccess)inventory).deselect$setPreviousSlot(inventory.selectedSlot);
					inventory.selectedSlot = Deselect.DESELECTED_SLOT_NUMBER;
				}
			}
		});
	}
}
