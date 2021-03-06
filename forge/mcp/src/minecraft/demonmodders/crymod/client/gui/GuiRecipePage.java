package demonmodders.crymod.client.gui;

import demonmodders.crymod.common.container.ContainerRecipePage;
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
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		SummoningRecipe recipe = container.getInventoryInstance();
		fontRenderer.drawString(recipe.getRecipeName(), 17, 10, recipe.getColor());
	}
}