package demonmodders.crymod.common.tileentities;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import demonmodders.crymod.common.items.ItemCryMod;
import demonmodders.crymod.common.network.PacketClientEffect;
import demonmodders.crymod.common.network.PacketClientEffect.Type;
import demonmodders.crymod.common.playerinfo.PlayerInfo;
import demonmodders.crymod.common.recipes.SummoningRecipe;

public class TileEntityEnderbook extends TileEntity {

	/** Used by the render to make the book 'bounce' */
    public int tickCount;

    /** Value used for determining how the page flip should look. */
    public float pageFlip;

    /** The last tick's pageFlip value. */
    public float pageFlipPrev;
    public float field_70373_d;
    public float field_70374_e;

    /** The amount that the book is open. */
    public float bookSpread;

    /** The amount that the book is open. */
    public float bookSpreadPrev;
    public float bookRotation2;
    public float bookRotationPrev;
    public float bookRotation;
    private static Random rand = new Random();
	
	@Override
	public void updateEntity() {
		AxisAlignedBB checkBB = AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(xCoord - 3, yCoord - 1, zCoord - 3, xCoord + 3, yCoord + 1, zCoord + 3);
		List<EntityItem> itemsNearby = worldObj.getEntitiesWithinAABB(EntityItem.class, checkBB);
		for (EntityItem item : itemsNearby) {
			if (item.func_92014_d().itemID == ItemCryMod.recipePage.shiftedIndex) {
				String ownerName = PlayerInfo.getModEntityData(item).getString("tossedBy");
				EntityPlayer owner = worldObj.getPlayerEntityByName(ownerName);
				SummoningRecipe recipe = SummoningRecipe.fromDamage(item.func_92014_d());
				if (owner != null && recipe != null) {
					byte[] knownRecipes = PlayerInfo.getModEntityData(owner).getByteArray("enderBook");
					for (byte recipeId : knownRecipes) {
						if (recipeId == recipe.id()) {
							return;
						}
					}
					item.setDead();
					
					new PacketClientEffect(Type.ENDERBOOK, xCoord, yCoord, zCoord).sendToAllNear(this, 8);
					
					byte[] newRecipeList = Arrays.copyOf(knownRecipes, knownRecipes.length + 1);
					newRecipeList[knownRecipes.length] = recipe.id();
					PlayerInfo.getModEntityData(owner).setByteArray("enderBook", newRecipeList);
				}
			}
		}
		
		if (!worldObj.isRemote) {
			return;
		}
		
		bookSpreadPrev = bookSpread;
        this.bookRotationPrev = this.bookRotation2;
        EntityPlayer closestPlayer = worldObj.getClosestPlayer(xCoord + 0.5F, yCoord + 0.5F, zCoord + 0.5F, 3);

        if (closestPlayer != null) {
        	double distanceX = closestPlayer.posX - xCoord + 0.5F;
            double distanceY = closestPlayer.posZ - zCoord + 0.5F;
            bookRotation = (float)Math.atan2(distanceY, distanceX);
            bookSpread += 0.1F;
            
            if (bookSpread < 0.5F || rand.nextInt(40) == 0) {
                float var6 = field_70373_d;

                do {
                	field_70373_d += rand.nextInt(4) - rand.nextInt(4);
                } while (var6 == this.field_70373_d);
            }
        } else {
            bookRotation += 0.02F;
            bookSpread -= 0.1F;
        }

        while (bookRotation2 >= Math.PI) {
            bookRotation2 -= Math.PI * 2;
        }

        while (bookRotation2 < -Math.PI) {
            bookRotation2 += Math.PI * 2;
        }

        while (bookRotation >= Math.PI) {
            bookRotation -= Math.PI * 2;
        }

        while (bookRotation < -Math.PI) {
            bookRotation += Math.PI * 2;
        }

        float var7;

        for (var7 = bookRotation - bookRotation2; var7 >= Math.PI; var7 -= Math.PI * 2) { }

        while (var7 < -Math.PI) {
            var7 += Math.PI * 2;
        }

        bookRotation2 += var7 * 0.4F;

        if (bookSpread < 0.0F) {
            bookSpread = 0.0F;
        }

        if (bookSpread > 1.0F) {
            bookSpread = 1.0F;
        }

        tickCount++;
        pageFlipPrev = pageFlip;
        float var3 = (field_70373_d - pageFlip) * 0.4F;
        float var8 = 0.2F;

        if (var3 < -var8) {
            var3 = -var8;
        }

        if (var3 > var8) {
            var3 = var8;
        }

        field_70374_e += (var3 - field_70374_e) * 0.9F;
        pageFlip += field_70374_e;
	}
}