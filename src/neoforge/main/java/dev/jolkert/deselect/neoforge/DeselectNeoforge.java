package dev.jolkert.deselect.neoforge;

import dev.jolkert.deselect.client.DeselectClient;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;

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
