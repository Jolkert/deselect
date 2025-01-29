package dev.jolkert.deselect.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.jolkert.deselect.access.PreviousSelectionAccess;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class DeselectScrollingHandlerMixin
{
	@Shadow @Final private MinecraftClient client;

	@Inject(
			method = "onMouseScroll",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/entity/player/PlayerInventory;setSelectedSlot(I)V"
			),
			cancellable = true
	)
	void resetSelectedStateOnScroll(long window, double horizontal, double vertical, CallbackInfo ci, @Local PlayerInventory playerInventory)
	{
		if (((PreviousSelectionAccess)playerInventory).deselect$hasHotbarDeselected())
		{
			playerInventory.selectedSlot = ((PreviousSelectionAccess)playerInventory).deselect$getPreviousSlot();
			ci.cancel();
		}
	}
}
