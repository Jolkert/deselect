package dev.jolkert.deselect.mixin;

import dev.jolkert.deselect.access.DeselectAccess;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import javax.annotation.Nullable;

@Mixin(Gui.class)
public abstract class BlockSlotRenderingMixin
{
	@Shadow @Nullable protected abstract Player getCameraPlayer();
	
	@SuppressWarnings("DataFlowIssue") // Gui::renderItemHotbar does the null check for us -morgan 2024-09-10
	@ModifyArgs(
		method = "renderItemHotbar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V",
			ordinal = 1
		)
	)
	void suppressDrawingWhenDeselected(Args args)
	{
		Player player = this.getCameraPlayer();

		if (((DeselectAccess)player.getInventory()).deselect$isDeselected())
		{
			args.set(3, 0); // width to 0
			args.set(4, 0); // height to 0
		}
	}
}