package dev.jolkert.deselect.fabric.client;

import dev.jolkert.deselect.client.DeselectClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

public class DeselectFabricClient implements ClientModInitializer
{
	@Override
	public void onInitializeClient()
	{
		KeyBindingHelper.registerKeyBinding(DeselectClient.DESELECT_KEY);
		ClientTickEvents.END_CLIENT_TICK.register(DeselectClient::deselectAction);
	}
}
