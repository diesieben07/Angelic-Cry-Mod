package demonmodders.crymod.common.core.transformers;

import static demonmodders.crymod.common.core.ObfuscationHelper.mappings;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import demonmodders.crymod.common.karma.KarmaEventHandler;

public class ItemStackTransformer extends AbstractClassTransformer {

	public ItemStackTransformer() {
		super("ItemStack");
	}

	@Override
	protected boolean transformMethod(String methodNameDescr, MethodNode method) {
		if (methodNameDescr.equals(mappings.getProperty("ItemStack.tryPlaceItemIntoWorld"))) {
			for (int i = 0; i < method.instructions.size(); i++) {
				AbstractInsnNode node = method.instructions.get(i);
				System.out.println(node.getOpcode());
			}
			
			InsnList hook = new InsnList();
			hook.add(new VarInsnNode(ALOAD, 0)); // this
			hook.add(new VarInsnNode(ALOAD, 1)); // par1EntityPlayer
			hook.add(new VarInsnNode(ILOAD, 3)); // x
			hook.add(new VarInsnNode(ILOAD, 4)); // y
			hook.add(new VarInsnNode(ILOAD, 5)); // z
			hook.add(new VarInsnNode(ILOAD, 6)); // side
			hook.add(new VarInsnNode(ILOAD, 10)); // var10 (= success)
			hook.add(makeStaticCall(KarmaEventHandler.class, "onTryPlaceItem", mappings.getProperty("ItemStack"), mappings.getProperty("EntityPlayer"), "I", "I", "I", "I", "Z"));
			
			AbstractInsnNode insertionPoint = null;
			
			for (int i = 0; i < method.instructions.size(); i++) {
				AbstractInsnNode node = method.instructions.get(i);
				if (node.getOpcode() == ILOAD && node.getNext().getOpcode() == IRETURN) {
					insertionPoint = node;
				}
			}
			
			if (insertionPoint != null) {
				method.instructions.insertBefore(insertionPoint, hook);
				infoHook("ItemStack/tryPlaceItemIntoWorld");
				return true;
			}
		}
		
		return false;
	}

}
