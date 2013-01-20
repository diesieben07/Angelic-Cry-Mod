package demonmodders.crymod.client;

import java.util.EnumSet;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StringTranslate;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.ReflectionHelper;
import demonmodders.crymod.client.gui.GuiButtonUpdates;

public class GuiTicker implements ITickHandler {

	private final Minecraft mc = Minecraft.getMinecraft();
	private final GuiButton theButton = new GuiButtonUpdates(10, 10);
	
	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		GuiScreen screen = mc.currentScreen;
		if (screen == null || !(screen instanceof GuiMainMenu)) {
			return;
		}
		List<GuiButton> controlList = ReflectionHelper.getPrivateValue(GuiScreen.class, screen, 4);
		if (!controlList.contains(theButton)) {
			theButton.xPosition = screen.width / 2 + 104;
			theButton.yPosition = screen.height / 4 + 132;
			theButton.displayString = StringTranslate.getInstance().translateKey("crymod.ui.updates.button");
			controlList.add(theButton);
		}
	}

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) { }

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}

	@Override
	public String getLabel() {
		return "SummoningmodGui";
	}
}