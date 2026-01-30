package dev.jolkert.deselect.neoforge.client;

import dev.jolkert.deselect.client.DeselectClient;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

// ngl i have no idea how these `EventBusSubscriber`s work, but this compiles so uhhh fuck it we ball
// -morgan 2026-01-24
@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME)
public class GameEventHandler
{
	@SubscribeEvent
	public static void onClientTick(ClientTickEvent.Pre event)
	{
		DeselectClient.deselectAction(Minecraft.getInstance());
	}
}