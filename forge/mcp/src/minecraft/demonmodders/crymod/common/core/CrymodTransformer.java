package demonmodders.crymod.common.core;

import static org.objectweb.asm.ClassWriter.COMPUTE_FRAMES;
import static org.objectweb.asm.ClassWriter.COMPUTE_MAXS;

import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import cpw.mods.fml.relauncher.IClassTransformer;

public class CrymodTransformer implements IClassTransformer {

	private static final String[] DEOBF = {
		"net.minecraft.entity.ai.EntityAIMate",
		"spawnBaby",
		"theAnimal",
		"targetMate",
		"net/minecraft/entity/EntityAgeable",
		
		"net.miencraft.entity.monster.EntityZombie",
		"convertToVillager"
	};
	
	private static final String[] OBF = {
		"",
		""
	};
	
	private static final boolean IS_OBF = false;
	private static final int ENTITY_AI_MATE = 0;
	private static final int SPAWN_BABY = 1;
	private static final int THE_ANIMAL = 2;
	private static final int TARGET_MATE = 3;
	private static final int ENTITY_AGEABLE_TYPE = 4;
	private static final int ENTITY_ZOMBIE = 5;
	private static final int CONVERT_TO_VILLAGER = 6;
	
	@Override
	public byte[] transform(String name, byte[] bytes) {
		boolean hasTransformed = false;
		
		final String[] classInfo = IS_OBF ? OBF : DEOBF;
		
		ClassNode clazz = new ClassNode();
		
		if (name.equals(classInfo[ENTITY_AI_MATE])) {
			ClassReader reader = new ClassReader(bytes);
			reader.accept(clazz, 0);
			for (MethodNode method : (List<MethodNode>)clazz.methods) {
				if (method.name.equals(classInfo[SPAWN_BABY])) {
					
					InsnList hook = new InsnList();
					hook.add(new VarInsnNode(Opcodes.ALOAD, 1)); // var1: the baby; possibly null
					hook.add(new VarInsnNode(Opcodes.ALOAD, 0)); // this: the EntityAIMate 
					
					final String methodDescr = "(L" + classInfo[ENTITY_AGEABLE_TYPE] + ";L" + classInfo[ENTITY_AI_MATE].replace('.', '/') + ";)V";
					
					hook.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "demonmodders/crymod/common/karma/KarmaEventHandler", "onBreedingSpawnChild", methodDescr));
					
					AbstractInsnNode insertionPoint = null;
					
					for (int i = 0; i < method.instructions.size(); i++) {
						AbstractInsnNode instruction = method.instructions.get(i);
						if (instruction.getOpcode() == Opcodes.RETURN) {
							insertionPoint = instruction;
						}
					}
					if (insertionPoint != null) {
						method.instructions.insertBefore(insertionPoint, hook);
						System.out.println("[Summoningmod] Inserted Callback Hook into EntityAIMate/spawnBaby");
						hasTransformed = true;
					}
				}
			}
		} else if (name.equals(classInfo[ENTITY_ZOMBIE])) {
			ClassReader reader = new ClassReader(bytes);
			reader.accept(clazz, 0);
			
			for (MethodNode method : (List<MethodNode>)clazz.methods) {
				if (method.name.equals(classInfo[CONVERT_TO_VILLAGER])) {
					InsnList hook = new InsnList();
					hook.add(new VarInsnNode(Opcodes.ALOAD, 0)); // this: the Zombie

					final String methodDescr = "(L" + classInfo[ENTITY_ZOMBIE].replace('.', '/') + ";)V";
					hook.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "demonmodders/crymod/common/karma/KarmaEventHandler", "onZombieConvert", methodDescr));
					
					AbstractInsnNode insertionPoint = null;
					
					for (int i = 0; i < method.instructions.size(); i++) {
						AbstractInsnNode instruction = method.instructions.get(i);
						if (instruction.getOpcode() == Opcodes.RETURN) {
							insertionPoint = instruction;
						}
					}
					if (insertionPoint != null) {
						method.instructions.insertBefore(insertionPoint, hook);
						System.out.println("[Summoningmod] Inserted Callback Hook into EntityZombie/convertToVillager");
						hasTransformed = true;
					}
				}
			}
		}
		
		if (hasTransformed) {
			ClassWriter writer = new ClassWriter(COMPUTE_MAXS | COMPUTE_FRAMES);
			clazz.accept(writer);
			return writer.toByteArray();
		} else {
			return bytes;
		}
	}

}