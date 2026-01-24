package dev.jolkert.deselect.mixin;

import dev.jolkert.deselect.Deselect;
import dev.jolkert.deselect.access.DeselectAccess;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Inventory.class)
public class DeselectLogicMixin implements DeselectAccess
{
	@Shadow
	public int selected;
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

	// ok who the hell called that function `swapPaint`? that might be the worst name of all time
	// -morgan 2026-01-24
	@Inject(method = "swapPaint", at = @At("HEAD"))
	void resetSelectedStateOnScroll(double scrollAmount, CallbackInfo ci)
	{
		if (this.deselect$isDeselected())
		{
			this.selected = this.deselect$previousSlot;
		}
	}

	@Inject(method = "getSuitableHotbarSlot", at = @At("HEAD"))
	void resetSelectedStateOnPick(CallbackInfoReturnable<Integer> cir)
	{
		if (this.deselect$isDeselected())
		{
			this.selected = this.deselect$previousSlot;
		}
	}

	// idk why i did it the way i did before? it just seems like it would allow you to mine with op pickaxe speed
	// without actually using the durability of said pickaxe? unless im missing something that just seems woefully
	// obvious and like i absolutely should not have done that
	// -morgan 2026-01-24
	@Inject(
			method = "getDestroySpeed",
			at = @At("HEAD"),
			cancellable = true
	)
	void fixBlockBreakingSpeed(BlockState state, CallbackInfoReturnable<Float> cir)
	{
		if (this.deselect$isDeselected())
		{
			cir.setReturnValue(1.0f);
		}
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
