package demonmodders.crymod.client.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import demonmodders.crymod.common.gui.AbstractContainer;
import demonmodders.crymod.common.inventory.slots.ScrollingSlot;
import demonmodders.crymod.common.network.PacketGuiButton;

public abstract class AbstractGuiContainer<T extends AbstractContainer<R>, R extends IInventory> extends GuiContainer {

	protected final T container;
	private final Collection<ScrollingSlot> scrollingSlots = new ArrayList<ScrollingSlot>();
	private int slotTickCount = 0;
	
	public AbstractGuiContainer(T container) {
		super(container);
		this.container = container;
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if (container.handleButtonClick(button.id)) {
			container.buttonClick(button.id, Side.CLIENT, mc.thePlayer);
			new PacketGuiButton(inventorySlots, button.id).sendToServer();
		}
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		int texture = mc.renderEngine.getTexture(getTextureFile());
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(texture);
        int xPos = (this.width - this.xSize) / 2;
        int yPos = (this.height - this.ySize) / 2;
        drawTexturedModalRect(xPos, yPos, 0, 0, this.xSize, this.ySize);
	}
	
	protected abstract String getTextureFile();

	@Override
	public void initGui() {
		super.initGui();
		for (Slot slot : (List<Slot>)container.inventorySlots) {
			if (slot instanceof ScrollingSlot) {
				scrollingSlots.add((ScrollingSlot)slot);
			}
		}
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		slotTickCount++;
		if (slotTickCount == 40) {
			for (ScrollingSlot slot : scrollingSlots) {
				slot.tick();
			}
			slotTickCount = 0;
		}
	}
}