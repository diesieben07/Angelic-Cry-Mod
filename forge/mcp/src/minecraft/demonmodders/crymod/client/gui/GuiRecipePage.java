package demonmodders.crymod.client.gui;

import demonmodders.crymod.common.gui.ContainerRecipePage;
import demonmodders.crymod.common.recipes.SummoningRecipe;

public class GuiRecipePage extends AbstractGuiContainer<ContainerRecipePage, SummoningRecipe> {

	public GuiRecipePage(ContainerRecipePage container) {
		super(container);
		xSize = 123;
		ySize = 161;
	}

	@Override
	protected String getTextureFile() {
		return "/demonmodders/crymod/resource/tex/recipePage.png";
	}

	@Override
	protected boolean isPointInRegion(int par1, int par2, int par3, int par4, int par5, int par6) {
		return false;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		SummoningRecipe recipe = container.getInventoryInstance();
		fontRenderer.drawString(recipe.getDemonName(), 17, 10, recipe.isAngel() ? 0x400040 : 0x660000);
	}
}