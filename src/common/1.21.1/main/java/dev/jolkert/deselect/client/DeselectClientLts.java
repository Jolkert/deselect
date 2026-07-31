package dev.jolkert.deselect.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class DeselectClientLts
{
	public static void init()
	{
		DeselectClient.init(new KeyMapping(
			"key.deselect.deselect",
			InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_Z,
			KeyMapping.CATEGORY_INVENTORY
		));
	}
}
