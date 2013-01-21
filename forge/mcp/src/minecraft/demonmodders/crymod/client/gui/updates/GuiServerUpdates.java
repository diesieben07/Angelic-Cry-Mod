package demonmodders.crymod.client.gui.updates;

import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.StringTranslate;
import demonmodders.crymod.common.Crymod;
import demonmodders.crymod.common.network.PacketClientRequest;
import demonmodders.crymod.common.network.PacketClientRequest.Action;

public class GuiServerUpdates extends GuiUpdates {

	@Override
	protected void done() {
		mc.displayGuiScreen(null);
	}

	@Override
	protected void retry() {
		new PacketClientRequest(Action.UPDATE_RETRY).sendToServer();
	}

	@Override
	protected void update() {
		new PacketClientRequest(Action.UPDATE).sendToServer();
	}

	@Override
	public void drawDefaultBackground() {
		drawBackground(0);
	}

	@Override
	public void initGui() {
		ServerData server = mc.getServerData();
		String ip = Crymod.color(server == null ? "Missingno" : server.serverName + " [" + server.serverIP + "]", "2", "f");
		headingText = StringTranslate.getInstance().translateKeyFormat("crymod.ui.updates.server", ip);
		super.initGui();
	}
}