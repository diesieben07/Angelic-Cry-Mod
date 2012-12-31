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

	private static final String EVENT_HANDLER = "demonmodders/crymod/common/karma/KarmaEventHandler";
	
	private static final String[] DEOBF = {
		"net.minecraft.entity.ai.EntityAIMate",
		"spawnBaby",
		"theAnimal",
		"targetMate",
		"net/minecraft/entity/EntityAgeable",
		
		"net.minecraft.entity.monster.EntityZombie",
		"convertToVillager",
		
		"net.minecraft.block.Block",
		"onBlockHarvested",
		"net/minecraft/world/World",
		"net/minecraft/entity/player/EntityPlayer",
		
		"net.minecraft.block.BlockPumpkin",
		"onBlockPlacedBy",
		"net/minecraft/entity/EntityLiving"
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
	private static final int BLOCK = 7;
	private static final int ON_BLOCK_HARVESTED = 8;
	private static final int WORLD = 9;
	private static final int ENTTIY_PLAYER = 10;
	private static final int BLOCK_PUMPKIN = 11;
	private static final int ON_BLOCK_PLACED_BY = 12;
	private static final int ENTITY_LIVING = 13;
	
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
					
					hook.add(new MethodInsnNode(Opcodes.INVOKESTATIC, EVENT_HANDLER, "onBreedingSpawnChild", methodDescr));
					
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
					hook.add(new MethodInsnNode(Opcodes.INVOKESTATIC, EVENT_HANDLER, "onZombieConvert", methodDescr));
					
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
		} else if (name.equals(classInfo[BLOCK])) {
			ClassReader reader = new ClassReader(bytes);
			reader.accept(clazz, 0);
			
			for (MethodNode method : (List<MethodNode>)clazz.methods) {
				if (method.name.equals(classInfo[ON_BLOCK_HARVESTED])) {
					InsnList hook = new InsnList();
					hook.add(new VarInsnNode(Opcodes.ALOAD, 0)); // this = the Block
					hook.add(new VarInsnNode(Opcodes.ALOAD, 1)); // par1World
					for (int i = 2; i < 6; i++) {
						hook.add(new VarInsnNode(Opcodes.ILOAD, i)); // par2 - par5 (x, y, z, meta)
					}
					hook.add(new VarInsnNode(Opcodes.ALOAD, 6)); // par6EntityPlayer
					
					final String methodDescr = "(L" + classInfo[BLOCK].replace('.', '/') + ";L" + classInfo[WORLD] + ";IIIIL" + classInfo[ENTTIY_PLAYER] + ";)V";
					hook.add(new MethodInsnNode(Opcodes.INVOKESTATIC, EVENT_HANDLER, "onPlayerBlockHarvest", methodDescr));
					
					AbstractInsnNode insertionPoint = null;
					
					for (int i = 0; i < method.instructions.size(); i++) {
						AbstractInsnNode instruction = method.instructions.get(i);
						if (instruction.getOpcode() == Opcodes.RETURN) {
							insertionPoint = instruction;
						}
					}
					if (insertionPoint != null) {
						method.instructions.insertBefore(insertionPoint, hook);
						System.out.println("[Summoningmod] Inserted Callback Hook into Block/onBlockHarvested");
						hasTransformed = true;
					}
				}
			}
		} else if (false && name.equals(classInfo[BLOCK_PUMPKIN])) {
			ClassReader reader = new ClassReader(bytes);
			reader.accept(clazz, 0);
			
			for (MethodNode method : (List<MethodNode>)clazz.methods) {
				if (method.name.equals(classInfo[ON_BLOCK_PLACED_BY])) {
					InsnList hook = new InsnList();
					hook.add(new VarInsnNode(Opcodes.ALOAD, 1)); // par1World
					for (int i = 2; i < 5; i++) {
						hook.add(new VarInsnNode(Opcodes.ILOAD, i)); // par2 - par4 (x, y, z)
					}
					hook.add(new VarInsnNode(Opcodes.ALOAD, 5)); // par5EntityLiving
					
					final String methodDescr = "(L" + classInfo[WORLD].replace('.', '/') + ";IIIL" + classInfo[ENTITY_LIVING] + ";)V";
					hook.add(new MethodInsnNode(Opcodes.INVOKESTATIC, EVENT_HANDLER, "onLivingPlacePumpkin", methodDescr));

					AbstractInsnNode insertionPoint = null;
					
					for (int i = 0; i < method.instructions.size(); i++) {
						AbstractInsnNode node = method.instructions.get(i);
						System.out.println(node.getOpcode() + " / " + node.getType() + " / " + node.getClass());
						if (node.getOpcode() != -1) {
							insertionPoint = node;
						}
					}
					
					if (insertionPoint == null) {
						method.instructions.insertBefore(insertionPoint, hook);
						System.out.println("[Summoningmod] Inserted Callback Hook into ItemBlock/placeBlockAt");
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