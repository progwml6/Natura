package com.progwml6.natura.shared.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Food;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class NaturaSoupItem extends NaturaEdibleItem {

  public NaturaSoupItem(Food foodIn, ItemGroup itemGroup) {
    super(foodIn, itemGroup);
  }

  /**
   * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
   * the Item before the action is complete.
   */
  @Override
  public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
    super.onItemUseFinish(stack, worldIn, entityLiving);
    return new ItemStack(Items.BOWL);
  }

}
