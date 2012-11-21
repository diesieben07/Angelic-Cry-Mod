package demonmodders.Crymod.Common;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;

public class CommonProxy {
	public void preInit() {
		
	}
	
	public void init() {
		
	}
	
	public void postInit() {
		
	}

	public Object getClientGuiElement(Container container, int id, EntityPlayer player, World world, int x, int y, int z) {
		// NO OP on server
		return null;
	}
}
