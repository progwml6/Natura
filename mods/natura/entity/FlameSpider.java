package mods.natura.entity;

import mods.natura.common.NContent;
import mods.tinker.tconstruct.entity.BlueSlime;
import net.minecraft.block.Block;
import net.minecraft.block.StepSound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FlameSpider extends EntitySpider
{
    public FlameSpider(World par1World)
    {
        super(par1World);
        this.texture = "/mods/natura/textures/mob/flamespider.png";
        this.setSize(2.7F, 1.9F);
        this.isImmuneToFire = true;
        this.moveSpeed = 1.35F;
        this.experienceValue = 25;
    }

    public int getMaxHealth ()
    {
        return 50;
    }

    @SideOnly(Side.CLIENT)
    /**
     * How large the spider should be scaled.
     */
    public float spiderScaleAmount ()
    {
        return 2.0F;
    }

    protected Entity findPlayerToAttack ()
    {
        double d0 = 24.0D;
        return this.worldObj.getClosestVulnerablePlayerToEntity(this, d0);
    }

    protected void updateEntityActionState ()
    {
        this.worldObj.theProfiler.startSection("ai");

        if (this.fleeingTick > 0)
        {
            --this.fleeingTick;
        }

        this.hasAttacked = this.isMovementCeased();
        float f = 24.0F;

        if (this.entityToAttack == null)
        {
            this.entityToAttack = this.findPlayerToAttack();

            if (this.entityToAttack != null)
            {
                this.pathToEntity = this.worldObj.getPathEntityToEntity(this, this.entityToAttack, f, true, false, false, true);
            }
        }
        else if (this.entityToAttack.isEntityAlive())
        {
            float f1 = this.entityToAttack.getDistanceToEntity(this);

            if (this.canEntityBeSeen(this.entityToAttack))
            {
                this.attackEntity(this.entityToAttack, f1);
            }
        }
        else
        {
            this.entityToAttack = null;
        }

        this.worldObj.theProfiler.endSection();

        if (!this.hasAttacked && this.entityToAttack != null && (this.pathToEntity == null || this.rand.nextInt(20) == 0))
        {
            this.pathToEntity = this.worldObj.getPathEntityToEntity(this, this.entityToAttack, f, true, false, false, true);
        }
        else if (!this.hasAttacked && (this.pathToEntity == null && this.rand.nextInt(180) == 0 || this.rand.nextInt(120) == 0 || this.fleeingTick > 0) && this.entityAge < 100)
        {
            this.updateWanderPath();
        }

        int i = MathHelper.floor_double(this.boundingBox.minY + 0.5D);
        boolean flag = this.isInWater();
        boolean flag1 = this.handleLavaMovement();
        this.rotationPitch = 0.0F;

        if (this.pathToEntity != null && this.rand.nextInt(100) != 0)
        {
            this.worldObj.theProfiler.startSection("followpath");
            Vec3 vec3 = this.pathToEntity.getPosition(this);
            double d0 = (double) (this.width * 2.0F);

            while (vec3 != null && vec3.squareDistanceTo(this.posX, vec3.yCoord, this.posZ) < d0 * d0)
            {
                this.pathToEntity.incrementPathIndex();

                if (this.pathToEntity.isFinished())
                {
                    vec3 = null;
                    this.pathToEntity = null;
                }
                else
                {
                    vec3 = this.pathToEntity.getPosition(this);
                }
            }

            this.isJumping = false;

            if (vec3 != null)
            {
                double d1 = vec3.xCoord - this.posX;
                double d2 = vec3.zCoord - this.posZ;
                double d3 = vec3.yCoord - (double) i;
                float f2 = (float) (Math.atan2(d2, d1) * 180.0D / Math.PI) - 90.0F;
                float f3 = MathHelper.wrapAngleTo180_float(f2 - this.rotationYaw);
                this.moveForward = this.moveSpeed;

                if (f3 > 30.0F)
                {
                    f3 = 30.0F;
                }

                if (f3 < -30.0F)
                {
                    f3 = -30.0F;
                }

                this.rotationYaw += f3;

                if (this.hasAttacked && this.entityToAttack != null)
                {
                    double d4 = this.entityToAttack.posX - this.posX;
                    double d5 = this.entityToAttack.posZ - this.posZ;
                    float f4 = this.rotationYaw;
                    this.rotationYaw = (float) (Math.atan2(d5, d4) * 180.0D / Math.PI) - 90.0F;
                    f3 = (f4 - this.rotationYaw + 90.0F) * (float) Math.PI / 180.0F;
                    this.moveStrafing = -MathHelper.sin(f3) * this.moveForward * 1.0F;
                    this.moveForward = MathHelper.cos(f3) * this.moveForward * 1.0F;
                }

                if (d3 > 0.0D)
                {
                    this.isJumping = true;
                }
            }

            if (this.entityToAttack != null)
            {
                this.faceEntity(this.entityToAttack, 30.0F, 30.0F);
            }

            if (this.isCollidedHorizontally && !this.hasPath())
            {
                this.isJumping = true;
            }

            if (this.rand.nextFloat() < 0.8F && (flag || flag1))
            {
                this.isJumping = true;
            }

            this.worldObj.theProfiler.endSection();
        }
        else
        {
            super.updateEntityActionState();
            this.pathToEntity = null;
        }
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

    /*public float getSpeedModifier()
    {
        float f = 1.0F;

        if (this.isPotionActive(Potion.moveSpeed))
        {
            f *= 1.0F + 0.2F * (float)(this.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }

        if (this.isPotionActive(Potion.moveSlowdown))
        {
            f *= 1.0F - 0.15F * (float)(this.getActivePotionEffect(Potion.moveSlowdown).getAmplifier() + 1);
        }

        if (f < 0.0F)
        {
            f = 0.0F;
        }
        
        f *= 1.5f;

        return f;
    }*/

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

    /**
     * Initialize this creature.
     */
    public void initCreature ()
    {
        /*if (this.worldObj.rand.nextInt(100) == 0)
        {
        	NitroCreeper creeper = new NitroCreeper(this.worldObj);
        	creeper.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
        	creeper.initCreature();
        	this.worldObj.spawnEntityInWorld(creeper);
        	creeper.mountEntity(this);
        }*/
        if (this.worldObj.rand.nextInt(10) == 0)
        {
            EntitySkeleton skeleton = new EntitySkeleton(this.worldObj);
            skeleton.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
            skeleton.initCreature();
            skeleton.setCurrentItemOrArmor(0, new ItemStack(Item.bow));
            skeleton.mountEntity(this);
        }
    }

    /*protected FlameSpiderBaby createBabyInstance ()
    {
    	return new FlameSpiderBaby(this.worldObj);
    }

    public void setDead ()
    {

    	if (!this.worldObj.isRemote)
    	{
    		int amount = rand.nextInt(6) + 5;
    		for (int i = 0; i < amount; i++)
    		{
    			double f = rand.nextDouble() * 2;
    			double f1 = rand.nextDouble() * 2;
    			FlameSpiderBaby babyspider = this.createBabyInstance();
    			babyspider.setLocationAndAngles(this.posX + (double) f, this.posY + 0.5D, this.posZ + (double) f1, this.rand.nextFloat() * 360.0F, 0.0F);
    			this.worldObj.spawnEntityInWorld(babyspider);
    		}
    	}

    	super.setDead();
    }*/
}
