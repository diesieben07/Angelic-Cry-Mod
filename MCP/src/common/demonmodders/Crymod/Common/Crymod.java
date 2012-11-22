package demonmodders.Crymod.Common;

import java.util.logging.Logger;

import net.minecraft.src.StringTranslate;
import net.minecraftforge.common.Configuration;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import demonmodders.Crymod.Common.Gui.CrymodGuiHandler;
import demonmodders.Crymod.Common.Items.ItemCryMod;
import demonmodders.Crymod.Common.Network.CrymodPacket;
import demonmodders.Crymod.Common.Network.CrymodPacketHandler;

@Mod(modid = "crymod", name = "Angelic Cry Mod [WIP]", version = "0.1")
@NetworkMod(channels = {CrymodPacket.CHANNEL}, packetHandler = CrymodPacketHandler.class, clientSideRequired = true, serverSideRequired = false)
public class Crymod {
	
	@SidedProxy(clientSide = "demonmodders.Crymod.Client.ClientProxy", serverSide = "demonmodders.Crymod.Common.CommonProxy")
	public static CommonProxy proxy;
	
	@Instance
	public static Crymod instance;
	
	public static Logger logger;
	
	public static Configuration conf;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent evt) {
		logger = Logger.getLogger("Crymod");
		logger.setParent(FMLLog.getLogger());
		logger.setUseParentHandlers(true);
		
		
		logger.info("Crymod Version " + evt.getModMetadata().version + " preInitializing...");
		
		conf = new Configuration(evt.getSuggestedConfigurationFile());
		conf.load();
		
		LanguageLoader.loadLanguages();
		
		NetworkRegistry.instance().registerGuiHandler(this, new CrymodGuiHandler());
		
		proxy.preInit();
	}
	
	@Init
	public void init(FMLInitializationEvent evt) {
		proxy.init();
		
		ItemCryMod.createItems();
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent evt) {
		proxy.postInit();
		conf.save();
	}
	
	public static final String TEXTURE_FILE = "/crymodResource/tex/textures.png";
}
