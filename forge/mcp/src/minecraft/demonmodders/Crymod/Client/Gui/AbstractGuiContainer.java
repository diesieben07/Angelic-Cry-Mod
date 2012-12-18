package demonmodders.Crymod.Client.Gui;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.IInventory;
import cpw.mods.fml.common.Side;
import demonmodders.Crymod.Common.Gui.AbstractContainer;
import demonmodders.Crymod.Common.Network.PacketGuiButton;

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
