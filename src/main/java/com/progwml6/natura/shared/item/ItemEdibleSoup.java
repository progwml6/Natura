package com.progwml6.natura.shared.item;

import javax.annotation.Nullable;

import gnu.trove.map.hash.TIntObjectHashMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemEdibleSoup extends ItemNaturaEdible
{
    protected TIntObjectHashMap<ItemStack> bowlsList;

    public ItemEdibleSoup()
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
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entity)
    {
        super.onItemUseFinish(stack, world, entity);
        ItemStack bowl = this.bowlsList.get(stack.getMetadata()).copy();
        if (entity instanceof EntityPlayer) {
            if (!((EntityPlayer)entity).inventory.addItemStackToInventory(bowl)) {
                ((EntityPlayer)entity).dropItem(bowl, false, false);
            }
        }
        return stack.stackSize <= 0 ? null : stack;
    }
}
