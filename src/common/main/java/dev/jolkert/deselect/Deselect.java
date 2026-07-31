package dev.jolkert.deselect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Deselect
{
	public static final String MOD_ID = "deselect";
	public static final Logger LOGGER = LoggerFactory.getLogger(Deselect.MOD_ID);

	// this is the dumbest bugfix of all time. Why in the fuck would you store the `selected` variable as an `int` if
	// youre gonna serialize it in the packet as a 16-bit value. Just make it a `short` guys. what in the hell are we
	// doing here mojang
	// - morgan 2026-01-30
	public static int DESELECT_SLOT_ID = Short.MIN_VALUE;

	public static void init()
	{
		Deselect.LOGGER.info("Intializing Deselect...");
	}
}
