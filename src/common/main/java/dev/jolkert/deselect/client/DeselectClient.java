package dev.jolkert.deselect.client;

import dev.jolkert.deselect.Deselect;
import dev.jolkert.deselect.access.DeselectAccess;
import dev.jolkert.deselect.mixin.InventorySelectedDuck;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.world.entity.player.Inventory;

public class DeselectClient
{
	public static KeyMapping DESELECT_KEY;

	public static void init(KeyMapping key)
	{
		DeselectClient.DESELECT_KEY = key;
	}

	public static void deselectAction(Minecraft client)
	{
		if (client.player == null)
		{
			return;
		}

		while (DESELECT_KEY.consumeClick())
		{
			Inventory inventory = client.player.getInventory();

			if (((DeselectAccess) inventory).deselect$isDeselected())
			{
				((InventorySelectedDuck) inventory)
					.deselect$setSelected(((DeselectAccess) inventory).deselect$getPreviousSlot());
			}
			else
			{
				Deselect.LOGGER.info("deselecting");
				((DeselectAccess) inventory)
					.deselect$setPreviousSlot(((InventorySelectedDuck) inventory).deselect$getSelected());
				((InventorySelectedDuck) inventory).deselect$setSelected(Deselect.DESELECT_SLOT_ID);
			}

			if (client.getConnection() != null)
			{
				client.getConnection().send(new ServerboundSetCarriedItemPacket(((InventorySelectedDuck) inventory).deselect$getSelected()));
			}
		}
	}
}
