package dev.jolkert.deselect.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.jolkert.deselect.access.DeselectAccess;
import net.minecraft.client.MouseHandler;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MouseHandler.class)
public class ScrollFixMixin
{
	@WrapOperation(method = "onScroll", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;setSelectedSlot(I)V"))
	void resetSelectedStateOnScroll(Inventory instance, int i, Operation<Void> original)
	{
		DeselectAccess access = (DeselectAccess) instance;
		if (access.deselect$isDeselected())
		{
			instance.setSelectedSlot(access.deselect$getPreviousSlot());
			original.call(instance, instance.getSelectedSlot());
		}
		else
		{
			original.call(instance, i);
		}
	}
}
