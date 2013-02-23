package demonmodders.crymod.client.gui;

import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.inventory.IInventory;

import org.lwjgl.opengl.GL11;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import demonmodders.crymod.client.gui.button.GuiButtonQuest;
import demonmodders.crymod.common.container.ContainerQuests;
import demonmodders.crymod.common.quest.Quest;

public class GuiQuests extends AbstractGuiContainer<ContainerQuests, IInventory> {

	private String[] questDescription = null;
	
	public int activeBtn = 1;
	
	public GuiQuests(ContainerQuests container) {
		super(container);
		xSize = 248;
		ySize = 190;
	}

	@Override
	public void initGui() {
		super.initGui();
		int btnCount = 0;
		for (Quest quest : container.getQuester().getQuests()) {
			controlList.add(new GuiButtonQuest(-btnCount, guiLeft + 14, guiTop + 32 + 18 * btnCount, quest, this));
			btnCount++;
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		if (button.id <= 0) {
			int maxWidth = 120;
			activeBtn = button.id;
			List<String> descriptionBuffer = Lists.newArrayList();
			Iterable<String> splitted = Splitter.on(' ').omitEmptyStrings().trimResults().split(container.getQuester().getQuests().get(-button.id).getQuestDescription());
			for (String splitPart : splitted) {
				int splitPartWidth = fontRenderer.getStringWidth(splitPart);
				if (descriptionBuffer.isEmpty() || (fontRenderer.getStringWidth(descriptionBuffer.get(descriptionBuffer.size() - 1)) + splitPartWidth) > maxWidth) {
					descriptionBuffer.add(splitPart);
				} else {
					descriptionBuffer.set(descriptionBuffer.size() - 1, descriptionBuffer.get(descriptionBuffer.size() - 1) + " " + splitPart);
				}
			}
			questDescription = descriptionBuffer.toArray(new String[descriptionBuffer.size()]);
		}
	}

	@Override
	protected String getTextureFile() {
		return "/demonmodders/crymod/resource/tex/questingInterface.png";
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		super.drawGuiContainerForegroundLayer(par1, par2);
		float scale = 0.5F;
		int y = 29;
		GL11.glPushMatrix();
		GL11.glTranslatef(155 * (1 - scale), y * (1 - scale), 0);
		GL11.glScalef(scale, scale, scale);
		
		if (questDescription != null) {
			for (String descr : questDescription) {
				fontRenderer.drawString(descr, 155, y, 0x000000);
				y += fontRenderer.FONT_HEIGHT + 2;
			}
		}
		
		GL11.glPopMatrix();
	}
}