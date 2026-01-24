package dev.jolkert.deselect.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.jolkert.deselect.Deselect;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerGamePacketListenerImpl.class)
public class AcceptDeselectOnServerMixin
{
	@ModifyExpressionValue(
			method = "handleSetCarriedItem",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/network/protocol/game/ServerboundSetCarriedItemPacket;getSlot()I",
					ordinal = 0
			)
	)
	int modifyCondition(int _original, ServerboundSetCarriedItemPacket packet)
	{
		boolean isSlotInvalid = (packet.getSlot() != Deselect.DESELECTED_VALUE
				&& packet.getSlot() < 0)
				|| packet.getSlot() >= Inventory.getSelectionSize();

		return isSlotInvalid ? -1 : 1;
	}
}
