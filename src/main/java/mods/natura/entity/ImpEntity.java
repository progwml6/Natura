package mods.natura.entity;

import com.google.common.base.Predicate;

import mods.natura.common.NContent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderHell;

public class ImpEntity extends EntityAnimal
{
    public ImpEntity(World par1World)
    {
        super(par1World);
        //this.texture = "/mods/natura/textures/mob/imp.png";
        this.setSize(0.9F, 0.9F);
        ((PathNavigateGround) this.getNavigator()).setAvoidsWater(true);
        this.isImmuneToFire = true;
        float f = 0.25F;
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 0.38F));
        this.tasks.addTask(2, new EntityAITempt(this, 0.3F, NContent.bowlStew, false));
        this.tasks.addTask(3, new EntityAIMate(this, f));
        this.tasks.addTask(3, new EntityAIAvoidEntity(this, new Predicate()
        {
            public boolean apply (Entity entity)
            {
                return entity instanceof EntityPlayer;
            }

            public boolean apply (Object p_apply_1_)
            {
                return this.apply((Entity) p_apply_1_);
            }
        }, 8.0F, 0.25F, 0.3F));
        this.tasks.addTask(5, new EntityAIFollowParent(this, 0.28F));
        this.tasks.addTask(6, new EntityAIWander(this, f));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes ()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D); //Health
    }

    @Override
    protected void updateAITasks ()
    {
        super.updateAITasks();
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    @Override
    protected String getLivingSound ()
    {
        return "mob.pig.say";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    @Override
    protected String getHurtSound ()
    {
        return "mob.pig.say";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    @Override
    protected String getDeathSound ()
    {
        return "mob.pig.death";
    }

    /**
     * Plays step sound at given x, y, z for the entity
     */
    protected void playStepSound (int par1, int par2, int par3, int par4)
    {
        this.playSound("mob.pig.step", 0.15F, 1.0F);
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    @Override
    protected Item getDropItem ()
    {
        return NContent.impMeat;
    }

    /**
     * Drop 0-2 items of this living's type. @param par1 - Whether this entity has recently been hit by a player. @param
     * par2 - Level of Looting used to kill this mob.
     */
    @Override
    protected void dropFewItems (boolean par1, int par2)
    {
        int amount = this.rand.nextInt(3) + 1 + this.rand.nextInt(1 + par2);

        for (int iter = 0; iter < amount; ++iter)
        {
            this.dropItem(NContent.impMeat, 1);
        }

        amount = this.rand.nextInt(5) + 2 + this.rand.nextInt(1 + par2 * 2);
        for (int iter = 0; iter < amount; ++iter)
        {
            this.entityDropItem(new ItemStack(NContent.plantItem, 1, 6), 0f);
        }
    }

    /**
     * This function is used when two same-species animals in 'love mode' breed to generate the new baby animal.
     */
    public ImpEntity spawnBabyAnimal (EntityAgeable par1EntityAgeable)
    {
        return new ImpEntity(this.worldObj);
    }

    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    @Override
    public boolean isBreedingItem (ItemStack par1ItemStack)
    {
        return par1ItemStack != null && par1ItemStack.getItem() == NContent.bowlStew && par1ItemStack.getItemDamage() >= 13;
    }

    @Override
    public EntityAgeable createChild (EntityAgeable par1EntityAgeable)
    {
        return this.spawnBabyAnimal(par1EntityAgeable);
    }

    @Override
    public boolean getCanSpawnHere ()
    {
        //return true;
        return this.worldObj.provider instanceof WorldProviderHell && this.worldObj.checkNoEntityCollision(this.getBoundingBox())
                && this.worldObj.getCollidingBoundingBoxes(this, this.getBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(this.getBoundingBox());
    }
}
