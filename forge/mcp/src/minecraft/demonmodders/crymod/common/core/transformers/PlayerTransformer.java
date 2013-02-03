package demonmodders.crymod.common.core.transformers;

import static demonmodders.crymod.common.core.ObfuscationHelper.mappings;

import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
import demonmodders.crymod.common.playerinfo.PlayerInformation;

@TransformerExclusions({ "demonmodders.", "fml.", "net.minecraftforge." })
public class PlayerTransformer extends AbstractClassTransformer {

	public PlayerTransformer() {
		super("EntityPlayer");
		addField("summoningmodPlayerInfo", PlayerInformation.class);
	}
	
	@Override
	public boolean transformMethod(String nameDescr, MethodNode method) {

		if (nameDescr.equals(mappings.getProperty("EntityPlayer.writeEntityToNBT"))) {
			
			InsnList hook = new InsnList();
			hook.add(new VarInsnNode(ALOAD, 0));
			hook.add(new VarInsnNode(ALOAD, 1));
			hook.add(makeStaticCall(PlayerInformation.class, "handlePlayerDataSave", mappings.getProperty("EntityPlayer"), mappings.getProperty("NBTTagCompound")));
			
			insertHookEnd(hook, method);
			infoHook("EntityPlayer/writeEntityToNBT");
			return true;
		} else if (nameDescr.equals(mappings.getProperty("EntityPlayer.readEntityFromNBT"))) {

			InsnList hook = new InsnList();
			hook.add(new VarInsnNode(ALOAD, 0));
			hook.add(new VarInsnNode(ALOAD, 1));
			hook.add(makeStaticCall(PlayerInformation.class, "handlePlayerDataLoad", mappings.getProperty("EntityPlayer"), mappings.getProperty("NBTTagCompound")));
			
			insertHookEnd(hook, method);
			infoHook("EntityPlayer/readEntityFromNBT");
			return true;
		} else if (nameDescr.equals(mappings.getProperty("EntityPlayer.clonePlayer"))) {

			InsnList hook = new InsnList();
			hook.add(new VarInsnNode(ALOAD, 0)); // this
			hook.add(new VarInsnNode(ALOAD, 1)); // par1NEntityPlayer
			hook.add(makeStaticCall(PlayerInformation.class, "handlePlayerDataCopy", mappings.getProperty("EntityPlayer"), mappings.getProperty("EntityPlayer")));
						
			insertHookEnd(hook, method);
			infoHook("EntityPlayer/clonePlayer");
			return true;
		} else if (method.name.equals("<init>")) {

			InsnList hook = new InsnList();
			hook.add(new VarInsnNode(ALOAD, 0)); // this
			hook.add(makeStaticCall(PlayerInformation.class, "handlePlayerConstruct", mappings.getProperty("EntityPlayer")));

			insertHookEnd(hook, method);
			infoHook("EntityPlayer constructor");
			return true;
		}
		return false;
	}
}