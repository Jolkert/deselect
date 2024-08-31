package dev.jolkert.deselect.mixin;

import dev.jolkert.deselect.Deselect;
import dev.jolkert.deselect.access.PreviousSelectionAccess;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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

	@Inject(method = "scrollInHotbar", at = @At("HEAD"))
	void resetSelectedStateOnScroll(double scrollAmount, CallbackInfo ci)
	{
		if (this.selectedSlot == Deselect.DESELECTED_SLOT_NUMBER)
		{
			this.selectedSlot = this.previousSelectedSlot;
		}
	}
}
