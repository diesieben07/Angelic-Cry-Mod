//trolololololol my god its works
// yes it does :D
package demonmodders.Crymod.Common;

import java.util.logging.Logger;

import net.minecraftforge.common.Configuration;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "crymod", name = "Angelic Cry Mod [WIP]", version = "0.1")
public class Crymod {
	
	@SidedProxy(clientSide = "demonmodders.Crymod.Client.ClientProxy", serverSide = "demonmodders.Crymod.Common.CommonProxy")
	public static CommonProxy proxy;
	
	public static Logger logger;
	
	public static Configuration conf;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent evt) {
		logger = Logger.getLogger("Crymod");
		logger.setParent(FMLLog.getLogger());
		logger.setUseParentHandlers(true);
		
		
		logger.info("Crymod Version " + evt.getModMetadata().version + " preInitializing...");
		
		conf = new Configuration(evt.getSuggestedConfigurationFile());
		
		proxy.preInit();
	}
	
	@Init
	public void init(FMLInitializationEvent evt) {
		proxy.init();
	}
	
	@PostInit
	public void PostInit(FMLPostInitializationEvent evt) {
		proxy.postInit();
	}
	
	public static final String TEXTURE_FILE = "/crymodResource/tex/textures.png";
}
