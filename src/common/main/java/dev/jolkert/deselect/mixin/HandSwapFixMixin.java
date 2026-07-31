package dev.jolkert.deselect.mixin;

import dev.jolkert.deselect.access.DeselectAccess;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class HandSwapFixMixin
{

	@Inject(
		method = "handleKeybinds",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/multiplayer/ClientPacketListener;send(Lnet/minecraft/network/protocol/Packet;)V"
		)
	)
	void fixSlotBeforeSwap(CallbackInfo ci)
	{
		var self = (Minecraft) (Object) this;
		Inventory inventory = self.player.getInventory();
		if (((DeselectAccess) inventory).deselect$isDeselected())
		{
			((InventorySelectedDuck) inventory)
				.deselect$setSelected(((DeselectAccess) inventory).deselect$getPreviousSlot());

			// ive not done enough digging to understand why you dont have to manually send this packet in the actual
			// logic for deselecting and only have to do it here, but if you dont do it here, the `ServerPlayerEntity`
			// on the server side thinks that the slot is still the negative value for the deselected pseudoslot.
			// i assume it's because this is happening within a single tick? but im not 100% sure
			// -morgan 2024-09-10
			self.getConnection().send(new ServerboundSetCarriedItemPacket(((InventorySelectedDuck) inventory).deselect$getSelected()));
		}
	}
}
