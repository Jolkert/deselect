package dev.jolkert.deselect.mixin;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.simibubi.create.content.equipment.toolbox.RadialToolboxMenu;
import com.simibubi.create.content.equipment.toolbox.ToolboxBlockEntity;
import dev.jolkert.deselect.Deselect;
import dev.jolkert.deselect.access.DeselectAccess;
import dev.jolkert.deselect.neoforge.DeselectNeoforge;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(RadialToolboxMenu.class)
public class CreateToolboxFixMixin
{
	@Inject(method = "<init>", at = @At("HEAD"))
    private static void fixToolbox(List<ToolboxBlockEntity> toolboxes, RadialToolboxMenu.State state, ToolboxBlockEntity selectedBox, CallbackInfo ci)
	{
		if (selectedBox == null)
			return;

        Player player = (Player)Minecraft.getInstance().player;
		var inventory = player.getInventory();
		if (((DeselectAccess)inventory).deselect$isDeselected())
		{
			inventory.selected = ((DeselectAccess)inventory).deselect$getPreviousSlot();
		}
	}
}
