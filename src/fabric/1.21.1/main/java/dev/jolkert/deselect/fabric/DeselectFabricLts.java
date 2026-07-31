package dev.jolkert.deselect.fabric;

import dev.jolkert.deselect.Deselect;
import dev.jolkert.deselect.client.DeselectClient;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

public class DeselectFabricLts implements ModInitializer
{

	@Override
	public void onInitialize()
	{
		Deselect.init();
	}
}
