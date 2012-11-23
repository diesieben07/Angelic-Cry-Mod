package demonmodders.Crymod.Client;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import net.minecraftforge.client.MinecraftForgeClient;
import demonmodders.Crymod.Client.Gui.GuiSummoner;
import demonmodders.Crymod.Common.CommonProxy;
import demonmodders.Crymod.Common.Crymod;
import demonmodders.Crymod.Common.Gui.GuiType;

public class ClientProxy extends CommonProxy {

	@Override
	public Object getClientGuiElement(Container container, int id, EntityPlayer player, World world, int x, int y, int z) {
		switch (GuiType.fromGuiId(id)) {
		case SUMMONING_BOOK:
			return new GuiSummoner("/crymodResource/tex/summoningBook.png", container);
		case EVIL_TABLET:
			return new GuiSummoner("/crymodResource/tex/evilTablet.png", container);
		default:
			return null;
		}
	}

	@Override
	public void preInit() {
		MinecraftForgeClient.preloadTexture(Crymod.TEXTURE_FILE);
	}

	@Override
	public void init() {
	}

	@Override
	public void postInit() {
		
	}

}
