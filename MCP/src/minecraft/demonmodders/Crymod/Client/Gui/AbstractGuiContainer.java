package demonmodders.Crymod.Client.Gui;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiContainer;
import cpw.mods.fml.common.Side;
import demonmodders.Crymod.Common.Gui.AbstractContainer;
import demonmodders.Crymod.Common.Network.PacketGuiButton;

public abstract class AbstractGuiContainer extends GuiContainer {

	public AbstractGuiContainer(AbstractContainer container) {
		super(container);
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		((AbstractContainer)inventorySlots).buttonClick(button.id, Side.CLIENT, mc.thePlayer);
		new PacketGuiButton(inventorySlots, button.id).sendToServer();
	}
}
