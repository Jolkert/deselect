package dev.jolkert.deselect.mixin;

import com.simibubi.create.content.equipment.toolbox.ToolboxBlockEntity;
import dev.jolkert.deselect.access.DeselectAccess;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import com.simibubi.create.content.equipment.toolbox.RadialToolboxMenu;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(RadialToolboxMenu.class)
public class CreateToolboxFixMixin
{

	@Inject(method = "<init>", at = @At("HEAD"))
	private static void fixToolbox(List<ToolboxBlockEntity> toolboxes, RadialToolboxMenu.State state, ToolboxBlockEntity selectedBox, CallbackInfo ci)
	{
		if (selectedBox == null)
			return;

		Player player = (Player) Minecraft.getInstance().player;
		var inventory = player.getInventory();
		if (((DeselectAccess)inventory).deselect$isDeselected())
		{
			int previousSlot = ((DeselectAccess) inventory).deselect$getPreviousSlot();
			if (inventory.getItem(previousSlot).isEmpty())
			{
				inventory.selected = previousSlot;
			}
			else
			{
				int firstFreeSlot = inventory.getFreeSlot();
				if (Inventory.isHotbarSlot(firstFreeSlot))
				{
					inventory.selected = firstFreeSlot;
				}
				else
				{
					inventory.selected = previousSlot;
				}
			}
		}
	}
}
