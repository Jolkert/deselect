package dev.jolkert.deselect.mixin;

import com.simibubi.create.content.equipment.toolbox.ToolboxBlockEntity;
import dev.jolkert.deselect.access.DeselectAccess;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ToolboxBlockEntity.class)
public class CreateToolboxFixMixin
{
	@Inject(method = "connectPlayer", at = @At("HEAD"))
	void fixHotbarSlot(int slot, Player player, int hotbarSlot, CallbackInfo ci)
	{
		Inventory inventory = player.getInventory();
		if (((DeselectAccess)inventory).deselect$isDeselected())
		{
			inventory.selected = ((DeselectAccess)inventory).deselect$getPreviousSlot();
		}
	}
}
