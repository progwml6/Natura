package com.progwml6.natura.shared.item;

import javax.annotation.Nullable;

import gnu.trove.map.hash.TIntObjectHashMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemEdibleSoup extends ItemNaturaEdible
{
    protected TIntObjectHashMap<ItemStack> bowlsList;

    public ItemEdibleSoup()
    {
        super();
        this.bowlsList = new TIntObjectHashMap<ItemStack>();
    }

    public ItemStack addFood(int meta, int food, float saturation, String name, ItemStack bowl, PotionEffect... effects)
    {
        return this.addFood(meta, food, saturation, name, bowl, effects.length > 0, effects);
    }

    public ItemStack addFood(int meta, int food, float saturation, String name, ItemStack bowl, boolean alwaysEdible, PotionEffect... effects)
    {
        this.bowlsList.put(meta, bowl);

        return super.addFood(meta, food, saturation, name, alwaysEdible, effects);
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
        return this.bowlsList.get(stack.getMetadata());
    }
}
