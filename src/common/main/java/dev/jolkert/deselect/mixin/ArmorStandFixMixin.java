package dev.jolkert.deselect.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.jolkert.deselect.access.DeselectAccess;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ArmorStand.class)
public class ArmorStandFixMixin
{
	@WrapOperation(method = "swapItem", at = @At(
		value = "INVOKE",
		target = "Lnet/minecraft/world/entity/player/Player;setItemInHand(Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/item/ItemStack;)V")
	)
	void fixArmorStandVoidingItems(Player instance, InteractionHand interactionHand, ItemStack itemStack, Operation<Void> original)
	{
		Inventory inventory = instance.getInventory();
		if (!((DeselectAccess) inventory).deselect$isDeselected())
		{
			original.call(instance, interactionHand, itemStack);
			return;
		}

		int firstEmptySlot = inventory.getFreeSlot();
		if (firstEmptySlot != -1)
		{
			inventory.setItem(firstEmptySlot, itemStack);
			return;
		}

		instance.drop(itemStack, false);
	}
}
