package dev.jolkert.deselect.mixin;

import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Inventory.class)
public interface InventorySelectedDuck
{
	@Accessor("selected")
	int deselect$getSelected();

	@Accessor("selected")
	void deselect$setSelected(int selected);
}
