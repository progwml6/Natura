package mods.natura.entity;

import mods.natura.common.NContent;
import mods.natura.common.PHNatura;
import net.minecraft.block.Block;
import net.minecraft.block.StepSound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class HeatscarSpider extends EntitySpider
{
    public HeatscarSpider(World par1World)
    {
        super(par1World);
        this.setSize(2.7F, 1.9F);
        this.isImmuneToFire = true;
        this.experienceValue = 25;
    }

    @SideOnly(Side.CLIENT)
    /**
     * How large the spider should be scaled.
     */
    public float spiderScaleAmount ()
    {
        return 2.0F;
    }
    
    @Override
    protected void func_110147_ax()
    {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(50.0D); //Health
        this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(24D); //Detection range
        this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(1.35); //Movespeed
        this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(4.0); //Base damage
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

    protected void jump ()
    {
        this.motionY = 0.62D;

        if (this.isPotionActive(Potion.jump))
        {
            this.motionY += (double) ((float) (this.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
        }

        if (this.isSprinting())
        {
            float f = this.rotationYaw * 0.017453292F;
            this.motionX -= (double) (MathHelper.sin(f) * 0.2F);
            this.motionZ += (double) (MathHelper.cos(f) * 0.2F);
        }

        this.isAirBorne = true;
        ForgeHooks.onLivingJump(this);
    }

    @Override
    protected void fall (float par1)
    {
        par1 = ForgeHooks.onLivingFall(this, par1);
        if (par1 <= 0)
        {
            return;
        }

        super.fall(par1);
        int i = MathHelper.ceiling_float_int(par1 - 5.0F);

        if (i > 0)
        {
            if (i > 4)
            {
                this.playSound("damage.fallbig", 1.0F, 1.0F);
            }
            else
            {
                this.playSound("damage.fallsmall", 1.0F, 1.0F);
            }

            this.attackEntityFrom(DamageSource.fall, i);
            int j = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - 0.20000000298023224D - (double) this.yOffset), MathHelper.floor_double(this.posZ));

            if (j > 0)
            {
                StepSound stepsound = Block.blocksList[j].stepSound;
                this.playSound(stepsound.getStepSound(), stepsound.getVolume() * 0.5F, stepsound.getPitch() * 0.75F);
            }
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
                        b0 = 5;
                    }
                    else if (this.worldObj.difficultySetting == 3)
                    {
                        b0 = 10;
                    }
                }

                if (b0 > 0)
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

    public int getAttackStrength (Entity par1Entity)
    {
        return 4;
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
            int k = this.rand.nextInt(3) + 2;

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

    protected BabyHeatscarSpider createBabyInstance ()
    {
        return new BabyHeatscarSpider(this.worldObj);
    }

    public void setDead ()
    {

        if (!this.worldObj.isRemote)
        {
            int num = PHNatura.babyHeatscarMaximum - PHNatura.babyHeatscarMinimum + 1;
            int amount = rand.nextInt(num) + PHNatura.babyHeatscarMinimum;
            for (int i = 0; i < amount; i++)
            {
                double f = rand.nextDouble() * 2;
                double f1 = rand.nextDouble() * 2;
                BabyHeatscarSpider babyspider = this.createBabyInstance();
                babyspider.setLocationAndAngles(this.posX + (double) f, this.posY + 0.5D, this.posZ + (double) f1, this.rand.nextFloat() * 360.0F, 0.0F);
                this.worldObj.spawnEntityInWorld(babyspider);
            }
        }

        super.setDead();
    }
}
