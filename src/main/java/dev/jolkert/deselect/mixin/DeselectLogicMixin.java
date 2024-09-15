package dev.jolkert.deselect.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.jolkert.deselect.Deselect;
import dev.jolkert.deselect.access.PreviousSelectionAccess;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerInventory.class)
public class DeselectLogicMixin implements PreviousSelectionAccess
{
	@Shadow public int selectedSlot;
	@Unique
	int previousSelectedSlot;

	@Override
	public int deselect$getPreviousSlot()
	{
		return this.previousSelectedSlot;
	}

	@Override
	public void deselect$setPreviousSlot(int slot)
	{
		this.previousSelectedSlot = slot;
	}

	@Override
	public boolean deselect$hasHotbarDeselected()
	{
		return this.selectedSlot == Deselect.DESELECTED_SLOT_NUMBER;
	}

	@Inject(method = "scrollInHotbar", at = @At("HEAD"))
	void resetSelectedStateOnScroll(double scrollAmount, CallbackInfo ci)
	{
		if (this.deselect$hasHotbarDeselected())
		{
			this.selectedSlot = this.previousSelectedSlot;
		}
	}

	@Inject(method = "getSwappableHotbarSlot", at = @At("HEAD"))
	void resetSelectedStateOnPick(CallbackInfoReturnable<Integer> cir)
	{
		if (this.deselect$hasHotbarDeselected())
		{
			this.selectedSlot = this.previousSelectedSlot;
		}
	}

	@ModifyExpressionValue(
			method = "getBlockBreakingSpeed",
			at = @At(
					value = "FIELD",
					target = "Lnet/minecraft/entity/player/PlayerInventory;selectedSlot:I"
			)
	)
	int fixBlockBreakingSpeed(int original)
	{
		if (original == Deselect.DESELECTED_SLOT_NUMBER)
		{
			return this.previousSelectedSlot;
		}
		else
		{
			return original;
		}
	}
}
