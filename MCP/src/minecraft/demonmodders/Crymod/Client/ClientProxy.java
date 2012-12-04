package demonmodders.Crymod.Client;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.registry.TickRegistry;
import demonmodders.Crymod.Client.FX.EntitySummonFX;
import demonmodders.Crymod.Client.Gui.GuiSummoner;
import demonmodders.Crymod.Common.CommonProxy;
import demonmodders.Crymod.Common.Crymod;
import demonmodders.Crymod.Common.Gui.ContainerSummoner;
import demonmodders.Crymod.Common.Gui.GuiType;
import demonmodders.Crymod.Common.Network.PacketClientEffect;
import demonmodders.Crymod.Common.PlayerInfo.PlayerInfo;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void setClientPlayerInfo(PlayerInfo info) {
		ClientTickHandler.instance().setClientPlayerInfo(info);
	}

	@Override
	public void handleClientEffect(PacketClientEffect clientEffect) {
		Minecraft mc = FMLClientHandler.instance().getClient();
		
		switch (clientEffect.type) {
		case SUMMON_GOOD:
			mc.effectRenderer.addEffect(new EntitySummonFX(mc.renderEngine, mc.theWorld, clientEffect.x, clientEffect.y, clientEffect.z, 0, 0, 0));
			break;
		case SUMMON_BAD:
			break;
		}
	}

	@Override
	public Object getClientGuiElement(Container container, int id, EntityPlayer player, World world, int x, int y, int z) {
		switch (GuiType.fromGuiId(id)) {
		case SUMMONING_BOOK:
			return new GuiSummoner("/crymodResource/tex/summoningBook.png", (ContainerSummoner)container);
		case EVIL_TABLET:
			return new GuiSummoner("/crymodResource/tex/evilTablet.png", (ContainerSummoner)container);
		default:
			return null;
		}
	}

	@Override
	public void preInit() {
		MinecraftForgeClient.preloadTexture(Crymod.TEXTURE_FILE);
		TickRegistry.registerTickHandler(ClientTickHandler.instance(), Side.CLIENT);
		MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
		KeyBindingRegistry.registerKeyBinding(new CrymodKeyHandler());
	}

	@Override
	public void init() {
	}

	@Override
	public void postInit() {
		
	}

}
