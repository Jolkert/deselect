package dev.jolkert.deselect.fabric;

import dev.jolkert.deselect.Deselect;
import net.fabricmc.api.ModInitializer;

public class DeselectFabricLatest implements ModInitializer
{
	@Override
	public void onInitialize()
	{
		Deselect.init();
	}
}
