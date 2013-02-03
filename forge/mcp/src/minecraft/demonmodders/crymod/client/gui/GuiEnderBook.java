package demonmodders.crymod.client.gui;

import net.minecraft.client.gui.GuiButton;
import demonmodders.crymod.common.gui.ContainerEnderBook;
import demonmodders.crymod.common.inventory.InventoryEnderBook;
import demonmodders.crymod.common.playerinfo.PlayerInformation;
import demonmodders.crymod.common.recipes.SummoningRecipe;

public class GuiEnderBook extends AbstractGuiContainer<ContainerEnderBook, InventoryEnderBook> {

	private static final int ARROW_HEIGHT = 10;
	private static final int ARROW_WIDTH = 18;
	
	public static final int PREV_PAGE = 1;
	public static final int NEXT_PAGE = 0;
	
	private SummoningRecipe currentRecipe = null;
	private SummoningRecipe[] knownRecipes;
	
	public GuiEnderBook(ContainerEnderBook container) {
		super(container);
		xSize = 146;
		ySize = 180;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		knownRecipes = PlayerInformation.forPlayer(mc.thePlayer).getEnderBookRecipes();
		int buttonYPos = (height - ySize) / 2 + ySize - ARROW_HEIGHT - 20;
		controlList.add(new GuiButtonImage(GuiEnderBook.NEXT_PAGE, (width - xSize) / 2 + xSize - ARROW_WIDTH - 20, buttonYPos, ARROW_WIDTH, ARROW_HEIGHT, 256 - ARROW_WIDTH, 0, "/demonmodders/crymod/resource/tex/enderBook.png"));
		controlList.add(new GuiButtonImage(GuiEnderBook.PREV_PAGE, (width - xSize) / 2 + 20, buttonYPos, ARROW_WIDTH, ARROW_HEIGHT, 256 - (ARROW_WIDTH * 2), 0, "/demonmodders/crymod/resource/tex/enderBook.png"));
	}
	
	public void setCurrentRecipe(SummoningRecipe recipe) {
		currentRecipe = recipe;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		if (currentRecipe != null) {
			fontRenderer.drawString(currentRecipe.getRecipeName(), 26, 20, 0x000000);
		}
		int y = 0;
		for (SummoningRecipe recipe : knownRecipes) {
			fontRenderer.drawString(recipe.getRecipeName(), - (width - xSize) / 2 + 5, y, 0xFFFAFA);
			y += 15;
		}
	}

	@Override
	protected String getTextureFile() {
		return "/demonmodders/crymod/resource/tex/enderBook.png";
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
		case GuiEnderBook.NEXT_PAGE:
			container.setActivePage(container.currentPage + 1);
			break;
		case GuiEnderBook.PREV_PAGE:
			container.setActivePage(container.currentPage - 1);
			break;
		}
	}
}