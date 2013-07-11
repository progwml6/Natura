package mods.natura.entity;

import mods.natura.common.NContent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FlameSpiderBaby extends EntitySpider
{
    public FlameSpiderBaby(World par1World)
    {
        super(par1World);
        this.texture = "/mods/natura/textures/mob/flamespider.png";
        this.setSize(1.2F, 0.8F);
        this.isImmuneToFire = true;
    }

    public int getMaxHealth ()
    {
        return 10;
    }

    @SideOnly(Side.CLIENT)
    /**
     * How large the spider should be scaled.
     */
    public float spiderScaleAmount ()
    {
        return 0.85F;
    }

    protected Entity findPlayerToAttack ()
    {
        double d0 = 16.0D;
        return this.worldObj.getClosestVulnerablePlayerToEntity(this, d0);
    }

    protected void attackEntity (Entity par1Entity, float par2)
    {
        if (par2 > 2.0F && par2 < 6.0F && this.rand.nextInt(10) == 0)
        {
            if (this.onGround)
            {
                double d0 = par1Entity.posX - this.posX;
                double d1 = par1Entity.posZ - this.posZ;
                float f2 = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
                this.motionX = d0 / (double) f2 * 0.5D + this.motionX * 0.20000000298023224D;
                this.motionZ = d1 / (double) f2 * 0.5D + this.motionZ * 0.20000000298023224D;
                this.motionY = 0.62D;
            }
        }
        else
        {
            super.attackEntity(par1Entity, par2);
        }
    }

    public boolean attackEntityAsMob (Entity par1Entity)
    {
        if (super.attackEntityAsMob(par1Entity))
        {
            if (par1Entity instanceof EntityLiving)
            {
                byte b0 = 0;

                if (this.worldObj.difficultySetting > 1)
                {
                    if (this.worldObj.difficultySetting == 2)
                    {
                        b0 = 2;
                    }
                    else if (this.worldObj.difficultySetting == 3)
                    {
                        b0 = 4;
                    }
                }

                if (b0 > 0 && rand.nextInt(3) == 0)
                {
                    par1Entity.setFire(b0);
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    protected int getDropItemId ()
    {
        return NContent.plantItem.itemID;
    }

    public EntityItem dropItemWithOffset (int par1, int par2, float par3)
    {
        return this.entityDropItem(new ItemStack(par1, par2, 7), par3);
    }

    protected void dropFewItems (boolean par1, int par2)
    {
        int j = this.getDropItemId();

        if (j > 0)
        {
            int k = this.rand.nextInt(2);

            if (par2 > 0)
            {
                k += this.rand.nextInt(par2 + 1);
            }

            for (int l = 0; l < k; ++l)
            {
                this.dropItem(j, 1);
            }
        }
    }

    public boolean getCanSpawnHere ()
    {
        return this.worldObj.difficultySetting > 0 && this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty()
                && !this.worldObj.isAnyLiquid(this.boundingBox);
    }

    /**
     * Initialize this creature.
     */
    public void initCreature ()
    {
    }
}
