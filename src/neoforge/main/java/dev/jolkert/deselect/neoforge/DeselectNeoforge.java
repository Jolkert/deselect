package dev.jolkert.deselect.neoforge;

import dev.jolkert.deselect.Deselect;
import dev.jolkert.deselect.client.DeselectClientLts;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(Deselect.MOD_ID)
 @EventBusSubscriber
public class DeselectNeoforge
{
	public DeselectNeoforge()
	{
		Deselect.init();
		Deselect.LOGGER.info("Hello Neoforge!");
	}

	@SubscribeEvent
	public static void onClientInit(FMLClientSetupEvent event)
	{
		DeselectClientLts.init();
	}
}
