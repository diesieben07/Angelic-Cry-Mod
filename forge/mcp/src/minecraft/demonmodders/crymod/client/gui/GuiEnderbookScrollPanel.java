package demonmodders.crymod.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;

public class GuiEnderbookScrollPanel extends GuiSlot {

	private final GuiEnderBook parentGui;
	
	public GuiEnderbookScrollPanel(Minecraft mc, GuiEnderBook parentGui) {
		super(mc, parentGui.width, parentGui.height, 16, (parentGui.height - 32) + 4, 25);
		this.parentGui = parentGui;
	}

	@Override
	protected int getSize() {
		return 5;
	}

	@Override
	protected void elementClicked(int var1, boolean var2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean isSelected(int var1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void drawBackground() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void drawSlot(int var1, int var2, int var3, int var4, Tessellator var5) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void overlayBackground(int par1, int par2, int par3, int par4) {
		
	}

	@Override
	protected void drawContainerBackground(Tessellator tess) {
	}
}