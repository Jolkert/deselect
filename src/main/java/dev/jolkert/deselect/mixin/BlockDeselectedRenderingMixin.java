package dev.jolkert.deselect.mixin;

import dev.jolkert.deselect.access.PreviousSelectionAccess;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(InGameHud.class)
public abstract class BlockDeselectedRenderingMixin
{
	@Shadow @Nullable protected abstract PlayerEntity getCameraPlayer();

	@SuppressWarnings("DataFlowIssue") // InGameHud::renderHotbar does the null check for us -morgan 2024-09-10
	@ModifyArgs(
		method = "renderHotbar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
			ordinal = 1
		)
	)
	void suppressDrawingWhenDeselected(Args args)
	{
		PlayerEntity player = this.getCameraPlayer();

		if (((PreviousSelectionAccess)player.getInventory()).deselect$hasHotbarDeselected())
		{
			args.set(5, 0); // width to 0
			args.set(6, 0); // height to 0
		}
	}
}
