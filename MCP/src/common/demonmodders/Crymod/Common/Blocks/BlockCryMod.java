package demonmodders.Crymod.Common.Blocks;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Material;
import demonmodders.Crymod.Common.Crymod;

public class BlockCryMod extends Block {

	public static Block rechargeStation;
	
	public static void createBlocks() {
		rechargeStation = new BlockRechargeStation(Crymod.conf.getBlock("rechargeStationId", 3956).getInt()).setBlockName("rechargeStation");
		GameRegistry.registerBlock(rechargeStation);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTabToDisplayOn() {
		return CreativeTabs.tabMisc;
	}

	public BlockCryMod(int blockId, int texture, Material material) {
		super(blockId, texture, material);
	}

	@Override
	public String getTextureFile() {
		return Crymod.TEXTURE_FILE;
	}
}