package dev.jolkert.deselect.access;

public interface DeselectAccess
{
	int deselect$getPreviousSlot();
	void deselect$setPreviousSlot(int slot);
	boolean deselect$isDeselected();
}
