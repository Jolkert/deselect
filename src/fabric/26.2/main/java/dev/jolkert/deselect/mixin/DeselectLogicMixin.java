package dev.jolkert.deselect.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.jolkert.deselect.Deselect;
import dev.jolkert.deselect.access.DeselectAccess;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Inventory.class)
public class DeselectLogicMixin implements DeselectAccess
{
	@Shadow
	private int selected;
	@Unique
	int deselect$previousSlot;

	@Override
	public int deselect$getPreviousSlot()
	{
		return this.deselect$previousSlot;
	}

	@Override
	public void deselect$setPreviousSlot(int slot)
	{
		this.deselect$previousSlot = slot;
	}

	@Override
	public boolean deselect$isDeselected()
	{
		return this.selected == Deselect.DESELECT_SLOT_ID;
	}

	@WrapOperation(method = "setSelectedSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;isHotbarSlot(I)Z"))
	boolean modifyHotbarCheck(int i, Operation<Boolean> original)
	{
		return original.call(i) || i == Deselect.DESELECT_SLOT_ID;
	}

	// i have no idea why the mixin gets upset when i try to set the return type as `ItemStack`, but it sure does
	// *really* want the generic. its always an `ItemStack`, I promise -morgan 2026-07-30
	@SuppressWarnings("unchecked")
	@WrapOperation(
		method = "getSelectedItem",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/core/NonNullList;get(I)Ljava/lang/Object;")
	)
	<E> E fixSelectedItemGet(NonNullList<ItemStack> instance, int index, Operation<ItemStack> original)
	{
		Inventory self = (Inventory) (Object) this;
		if (this.deselect$isDeselected())
		{
			return (E) ItemStack.EMPTY;
		}
		else
		{
			return (E) original.call(instance, index);
		}
	}

	@Inject(method = "getSuitableHotbarSlot", at = @At("HEAD"))
	void reselectBeforeGetSuitableHotbarSlot(CallbackInfoReturnable<Integer> cir)
	{
		Inventory self = (Inventory) (Object) this;
		if (this.deselect$isDeselected())
		{
			self.setSelectedSlot(this.deselect$getPreviousSlot());
		}
	}

	@WrapOperation(method = "getSlotWithRemainingSpace", at = @At(
		value = "INVOKE",
		ordinal = 0,
		target = "Lnet/minecraft/world/entity/player/Inventory;getItem(I)Lnet/minecraft/world/item/ItemStack;")
	)
	ItemStack fixRemainingSpaceCheck(Inventory instance, int i, Operation<ItemStack> original)
	{
		return !this.deselect$isDeselected() ?
			original.call(instance, i) :
			original.call(instance, this.deselect$getPreviousSlot());
	}

	@Inject(method = "getItem", at = @At("HEAD"), cancellable = true)
	void returnEmptyWhenDeselected(int slot, CallbackInfoReturnable<ItemStack> cir)
	{
		if (slot == Deselect.DESELECT_SLOT_ID)
		{
			cir.setReturnValue(ItemStack.EMPTY);
		}
	}
}
