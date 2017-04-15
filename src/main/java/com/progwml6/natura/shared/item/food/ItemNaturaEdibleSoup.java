package com.progwml6.natura.shared.item.food;

import javax.annotation.Nullable;

import gnu.trove.map.hash.TIntObjectHashMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemNaturaEdibleSoup extends ItemNaturaEdible
{
    protected TIntObjectHashMap<ItemStack> bowlsList;

    public ItemNaturaEdibleSoup()
    {
        super();
        this.bowlsList = new TIntObjectHashMap<>();
    }

    public ItemStack addFood(int meta, int food, float saturation, int duration, String name, ItemStack bowl, PotionEffect... effects)
    {
        return this.addFood(meta, food, saturation, duration, name, bowl, effects.length > 0, effects);
    }

    public ItemStack addFood(int meta, int food, float saturation, int duration, String name, ItemStack bowl, boolean alwaysEdible, PotionEffect... effects)
    {
        this.bowlsList.put(meta, bowl);

        return super.addFood(meta, food, saturation, duration, name, alwaysEdible, effects);
    }

    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    @Override
    @Nullable
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        super.onItemUseFinish(stack, worldIn, entityLiving);
        ItemStack bowl = this.bowlsList.get(stack.getMetadata()).copy();
        if (entityLiving instanceof EntityPlayer)
        {
            if (!((EntityPlayer) entityLiving).inventory.addItemStackToInventory(bowl))
            {
                ((EntityPlayer) entityLiving).dropItem(bowl, false, false);
            }
        }
        return stack.getCount() <= 0 ? ItemStack.EMPTY : stack;
    }
}
