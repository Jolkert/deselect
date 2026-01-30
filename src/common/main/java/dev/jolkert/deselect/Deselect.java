package dev.jolkert.deselect;

public class Deselect
{
	// this is the dumbest bugfix of all time. Why in the fuck would you store the `selected` variable as an `int` if
	// youre gonna serialize it in the packet as a 16-bit value. Just make it a `shot` guys. what in the hell are we
	// doing here mojang
	// - morgan 2026-01-30
	public static int DESELECT_SLOT_ID = Short.MIN_VALUE;
}
