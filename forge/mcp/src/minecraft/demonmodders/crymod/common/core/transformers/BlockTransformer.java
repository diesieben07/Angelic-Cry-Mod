package demonmodders.crymod.common.core.transformers;

import static demonmodders.crymod.common.core.ObfuscationHelper.mappings;

import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import demonmodders.crymod.common.karma.KarmaEventHandler;

public class BlockTransformer extends AbstractClassTransformer {

	public BlockTransformer() {
		super("Block");
	}

	@Override
	protected boolean transformMethod(String methodNameDescr, MethodNode method) {
		if (methodNameDescr.equals(mappings.getProperty("Block.onBlockHarvested"))) {

			InsnList hook = new InsnList();
			hook.add(new VarInsnNode(ALOAD, 0)); // this
			hook.add(new VarInsnNode(ALOAD, 1)); // par1World
			hook.add(new VarInsnNode(ALOAD, 6)); // par6EntityPlayer
			hook.add(makeStaticCall(KarmaEventHandler.class, "onPlayerBlockHarvest", mappings.getProperty("Block"), mappings.getProperty("World"), mappings.getProperty("EntityPlayer")));
			
			insertHookEnd(hook, method);
			infoHook("Block/onBlockHarvested");
			return true;
		}
		return false;
	}

}
