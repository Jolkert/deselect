package dev.jolkert.deselect.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.jolkert.deselect.Deselect;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayNetworkHandler.class)
public class AcceptDeselectOnServerMixin
{
	@ModifyExpressionValue(
			method = "onUpdateSelectedSlot",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/network/packet/c2s/play/UpdateSelectedSlotC2SPacket;getSelectedSlot()I",
					ordinal = 0
			)
	)
	int modifyCondition(int _original, UpdateSelectedSlotC2SPacket packet)
	{
		boolean isSlotInvalid = (packet.getSelectedSlot() != Deselect.DESELECTED_SLOT_NUMBER
				&& packet.getSelectedSlot() < 0)
				|| packet.getSelectedSlot() >= PlayerInventory.getHotbarSize();

		return isSlotInvalid ? -1 : 1;
	}
}
