package mods.natura.entity;

import mods.natura.common.NContent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
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
        this.moveSpeed = 0.9F;
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
	
	protected void updateEntityActionState()
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
            double d0 = (double)(this.width * 2.0F);

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
                double d3 = vec3.yCoord - (double)i;
                float f2 = (float)(Math.atan2(d2, d1) * 180.0D / Math.PI) - 90.0F;
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
                    this.rotationYaw = (float)(Math.atan2(d5, d4) * 180.0D / Math.PI) - 90.0F;
                    f3 = (f4 - this.rotationYaw + 90.0F) * (float)Math.PI / 180.0F;
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

	/**
	 * Initialize this creature.
	 */
	public void initCreature ()
	{
		if (this.worldObj.rand.nextInt(25) == 0)
		{
			NitroCreeper creeper = new NitroCreeper(this.worldObj);
			creeper.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
			creeper.initCreature();
			this.worldObj.spawnEntityInWorld(creeper);
			creeper.mountEntity(this);
		}
		if (this.worldObj.rand.nextInt(10) == 0)
		{
			EntitySkeleton skeleton = new EntitySkeleton(this.worldObj);
			skeleton.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
			skeleton.initCreature();
			skeleton.setCurrentItemOrArmor(0, new ItemStack(Item.bow));
			this.worldObj.spawnEntityInWorld(skeleton);
			if (this.ridingEntity != null)
				skeleton.mountEntity(this.ridingEntity);
			else
				skeleton.mountEntity(this);
		}
	}
}
