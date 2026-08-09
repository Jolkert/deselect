package dev.jolkert.deselect.neoforge;

import dev.jolkert.deselect.Deselect;
import dev.jolkert.deselect.client.DeselectClient;
import dev.jolkert.deselect.client.DeselectClientLts;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@Mod(Deselect.MOD_ID)
 @EventBusSubscriber
public class DeselectNeoforge
{
	public DeselectNeoforge()
	{
		Deselect.init();
	}

	@SubscribeEvent
	public static void registerBindings(RegisterKeyMappingsEvent event)
	{
		DeselectClientLts.init();
		event.register(DeselectClient.DESELECT_KEY);
	}

	@SubscribeEvent
	public static void onClientTick(ClientTickEvent.Pre event)
	{
		DeselectClient.deselectAction(Minecraft.getInstance());
	}
}
