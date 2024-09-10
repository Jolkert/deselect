package dev.jolkert.deselect.mixin;

import dev.jolkert.deselect.access.PreviousSelectionAccess;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(MinecraftClient.class)
public class HandSwapFixMixin
{
	@SuppressWarnings("DataFlowIssue")
	@Inject(
			method = "handleInputEvents",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/network/ClientPlayNetworkHandler;sendPacket(Lnet/minecraft/network/packet/Packet;)V"
			)
	)
	void fixSlotBeforeHandSwap(CallbackInfo ci)
	{
		var self = (MinecraftClient)(Object)this;
		PlayerInventory inventory = self.player.getInventory();
		if (((PreviousSelectionAccess)inventory).deselect$hasHotbarDeselected())
		{
			inventory.selectedSlot = ((PreviousSelectionAccess)inventory).deselect$getPreviousSlot();

			// ive not done enough digging to understand why you dont have to manually send this packet in the actual
			// logic for deselecting and only have to do it here, but if you dont do it here, the `ServerPlayerEntity`
			// on the server side thinks that the slot is still the negative value for the deselected pseudoslot.
			// i assume it's because this is happening within a single tick? but im not 100% sure
			// -morgan 2024-09-10
			self.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(inventory.selectedSlot));
		}
	}
}