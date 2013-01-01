package demonmodders.crymod.client.gui;

import static demonmodders.crymod.common.gui.ContainerEnderBook.NEXT_PAGE;
import static demonmodders.crymod.common.gui.ContainerEnderBook.PREV_PAGE;
import demonmodders.crymod.common.gui.ContainerEnderBook;
import demonmodders.crymod.common.inventory.InventoryEnderBook;
import demonmodders.crymod.common.recipes.SummoningRecipe;

public class GuiEnderBook extends AbstractGuiContainer<ContainerEnderBook, InventoryEnderBook> {

	private static final int ARROW_HEIGHT = 10;
	private static final int ARROW_WIDTH = 18;
	
	private SummoningRecipe currentRecipe = null;
	
	public GuiEnderBook(ContainerEnderBook container) {
		super(container);
		xSize = 146;
		ySize = 180;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		int buttonYPos = (height - ySize) / 2 + ySize - ARROW_HEIGHT - 20;
		controlList.add(new GuiButtonImage(NEXT_PAGE, (width - xSize) / 2 + xSize - ARROW_WIDTH - 20, buttonYPos, ARROW_WIDTH, ARROW_HEIGHT, 256 - ARROW_WIDTH, 0, "/demonmodders/crymod/resource/tex/enderBook.png"));
		controlList.add(new GuiButtonImage(PREV_PAGE, (width - xSize) / 2 + 20, buttonYPos, ARROW_WIDTH, ARROW_HEIGHT, 256 - (ARROW_WIDTH * 2), 0, "/demonmodders/crymod/resource/tex/enderBook.png"));
	}
	
	public void setCurrentRecipe(SummoningRecipe recipe) {
		currentRecipe = recipe;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		if (currentRecipe != null) {
			fontRenderer.drawString(currentRecipe.getDemonName(), 26, 20, 0x000000);
		}
	}

	@Override
	protected String getTextureFile() {
		return "/demonmodders/crymod/resource/tex/enderBook.png";
	}
}