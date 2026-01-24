package dev.jolkert.deselect.client;

import com.mojang.blaze3d.platform.InputConstants;
import dev.jolkert.deselect.Deselect;
import dev.jolkert.deselect.access.DeselectAccess;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Inventory;
import org.lwjgl.glfw.GLFW;

public class DeselectClient
{
	public static KeyMapping DESELECT_KEY = new KeyMapping(
			"key.deselect.deselect",
			InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_Z,
			KeyMapping.CATEGORY_INVENTORY
	);

	public static void deselectAction(Minecraft client)
	{
		while (DESELECT_KEY.consumeClick())
		{
			assert client.player != null;
			Inventory inventory = client.player.getInventory();

			if (inventory.selected == Deselect.DESELECTED_VALUE)
			{
				inventory.selected = ((DeselectAccess)inventory).deselect$getPreviousSlot();
			}
			else
			{
				((DeselectAccess)inventory).deselect$setPreviousSlot(inventory.selected);
				inventory.selected = Deselect.DESELECTED_VALUE;
			}
		}
	}
}
