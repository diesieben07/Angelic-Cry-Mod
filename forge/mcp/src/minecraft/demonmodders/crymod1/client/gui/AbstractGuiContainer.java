package demonmodders.crymod1.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import cpw.mods.fml.relauncher.Side;
import demonmodders.crymod1.common.gui.AbstractContainer;
import demonmodders.crymod1.common.network.PacketGuiButton;

public abstract class AbstractGuiContainer<T extends AbstractContainer<R>, R extends IInventory> extends GuiContainer {

	final T container;
	
	public AbstractGuiContainer(T container) {
		super(container);
		this.container = container;
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		container.buttonClick(button.id, Side.CLIENT, mc.thePlayer);
		new PacketGuiButton(inventorySlots, button.id).sendToServer();
	}
}
