package demonmodders.crymod.common.tileentities;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import demonmodders.crymod.common.inventory.InventoryEnderBook;
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
					byte[] knownRecipes = InventoryEnderBook.getKnownRecipes(owner);
					for (byte recipeId : knownRecipes) {
						if (recipeId == recipe.id()) {
							return;
						}
					}
					item.setDead();
					
					new PacketClientEffect(Type.ENDERBOOK, xCoord, yCoord, zCoord).sendToAllNear(this, 8);
					
					byte[] newRecipeList = Arrays.copyOf(knownRecipes, knownRecipes.length + 1);
					newRecipeList[knownRecipes.length] = recipe.id();
					InventoryEnderBook.setKnownRecipes(owner, newRecipeList);
				}
			}
		}
		
		if (!worldObj.isRemote) {
			return;
		}
		
		this.bookSpreadPrev = this.bookSpread;
        this.bookRotationPrev = this.bookRotation2;
        EntityPlayer var1 = this.worldObj.getClosestPlayer((double)((float)this.xCoord + 0.5F), (double)((float)this.yCoord + 0.5F), (double)((float)this.zCoord + 0.5F), 3.0D);

        if (var1 != null)
        {
            double var2 = var1.posX - (double)((float)this.xCoord + 0.5F);
            double var4 = var1.posZ - (double)((float)this.zCoord + 0.5F);
            this.bookRotation = (float)Math.atan2(var4, var2);
            this.bookSpread += 0.1F;

            if (this.bookSpread < 0.5F || rand.nextInt(40) == 0)
            {
                float var6 = this.field_70373_d;

                do
                {
                    this.field_70373_d += (float)(rand.nextInt(4) - rand.nextInt(4));
                }
                while (var6 == this.field_70373_d);
            }
        }
        else
        {
            this.bookRotation += 0.02F;
            this.bookSpread -= 0.1F;
        }

        while (this.bookRotation2 >= (float)Math.PI)
        {
            this.bookRotation2 -= ((float)Math.PI * 2F);
        }

        while (this.bookRotation2 < -(float)Math.PI)
        {
            this.bookRotation2 += ((float)Math.PI * 2F);
        }

        while (this.bookRotation >= (float)Math.PI)
        {
            this.bookRotation -= ((float)Math.PI * 2F);
        }

        while (this.bookRotation < -(float)Math.PI)
        {
            this.bookRotation += ((float)Math.PI * 2F);
        }

        float var7;

        for (var7 = this.bookRotation - this.bookRotation2; var7 >= (float)Math.PI; var7 -= ((float)Math.PI * 2F))
        {
            ;
        }

        while (var7 < -(float)Math.PI)
        {
            var7 += ((float)Math.PI * 2F);
        }

        this.bookRotation2 += var7 * 0.4F;

        if (this.bookSpread < 0.0F)
        {
            this.bookSpread = 0.0F;
        }

        if (this.bookSpread > 1.0F)
        {
            this.bookSpread = 1.0F;
        }

        ++this.tickCount;
        this.pageFlipPrev = this.pageFlip;
        float var3 = (this.field_70373_d - this.pageFlip) * 0.4F;
        float var8 = 0.2F;

        if (var3 < -var8)
        {
            var3 = -var8;
        }

        if (var3 > var8)
        {
            var3 = var8;
        }

        this.field_70374_e += (var3 - this.field_70374_e) * 0.9F;
        this.pageFlip += this.field_70374_e;
	}
}