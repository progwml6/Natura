package com.progwml6.natura.entities.entity.monster;

import javax.annotation.Nullable;

import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityNitroCreeper extends EntityCreeper
{
    /**
     * Time when this creeper was last in an active state (Messed up code here, probably causes creeper animation to go
     * weird)
     */
    private int lastActiveTime;

    /** The amount of time since the creeper was close enough to the player to ignite */
    private int timeSinceIgnited;

    private int fuseTime = 12;

    /** Explosion radius for this creeper. */
    private int explosionRadius = 1;

    public EntityNitroCreeper(World world)
    {
        super(world);
        this.isImmuneToFire = true;
    }

    @Override
    protected void initEntityAI()
    {
        super.initEntityAI();
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
    }

    @Override
    public void fall(float distance, float damageMultiplier)
    {
        if (!this.world.isRemote)
        {
            if (distance > 5.0F)
            {
                this.explode();
            }
            else
            {
                super.fall(distance, damageMultiplier);
            }
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setShort("Fuse", (short) this.fuseTime);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);

        if (compound.hasKey("Fuse", 99))
        {
            this.fuseTime = compound.getShort("Fuse");
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate()
    {
        if (this.isEntityAlive())
        {
            this.lastActiveTime = this.timeSinceIgnited;

            if (this.hasIgnited())
            {
                this.setCreeperState(1);
            }

            int i = this.getCreeperState();

            if (i > 0 && this.timeSinceIgnited == 0)
            {
                this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
            }

            this.timeSinceIgnited += i;

            if (this.timeSinceIgnited < 0)
            {
                this.timeSinceIgnited = 0;
            }

            int difficulty = this.world.getDifficulty().getDifficultyId();
            int powered = this.getPowered() ? 12 : 0;

            if (this.timeSinceIgnited >= this.fuseTime + difficulty + powered)
            {
                this.timeSinceIgnited = this.fuseTime;
                this.explode();
            }
        }

        super.onUpdate();
    }

    /**
     * Creates an explosion as determined by this creeper's power and explosion radius.
     */
    private void explode()
    {
        if (!this.world.isRemote)
        {
            boolean flag = this.world.getGameRules().getBoolean("mobGriefing");
            float f = this.getPowered() ? 20.0F : 3.0F;
            this.dead = true;
            this.world.createExplosion(this, this.posX, this.posY, this.posZ, this.explosionRadius * f, flag);
            this.setDead();
        }
    }

    /**
     * Params: (Float)Render tick. Returns the intensity of the creeper's flash when it is ignited.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public float getCreeperFlashIntensity(float partialTickTime)
    {
        return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * partialTickTime) / (this.fuseTime - 2);
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable()
    {
        return null;
    }

    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (source instanceof EntityDamageSource && ((EntityDamageSource) source).getTrueSource() instanceof EntityIronGolem)
        {
            amount = 1000;
        }

        return super.attackEntityFrom(source, amount);
    }

    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier)
    {
        Item dropItem = this.getDropItem();

        if (dropItem != Items.AIR)
        {
            int numberOfTimes = this.rand.nextInt(4) + 2;

            if (lootingModifier > 0)
            {
                numberOfTimes += this.rand.nextInt(lootingModifier + 1);
            }

            for (int item = 0; item < numberOfTimes; ++item)
            {
                this.dropItem(dropItem, 1);
            }
        }

        if (this.getPowered())
        {
            if (dropItem != Items.AIR)
            {
                int numberOfTimes = this.rand.nextInt(40) + 20;

                if (lootingModifier > 0)
                {
                    numberOfTimes += this.rand.nextInt(lootingModifier * 6 + 1);
                }

                for (int item = 0; item < numberOfTimes; ++item)
                {
                    this.dropItem(dropItem, 1);
                }
            }
        }
    }
}
