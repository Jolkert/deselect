package dev.jolkert.deselect.neoforge;

import dev.jolkert.deselect.client.DeselectClient;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@Mod("deselect")
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class DeselectNeoforge
{
	public DeselectNeoforge()
	{
	}

	@SubscribeEvent
	public static void registerBindings(RegisterKeyMappingsEvent event)
	{
		event.register(DeselectClient.DESELECT_KEY);
	}
}
