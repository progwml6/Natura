package com.progwml6.natura.shared.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Food;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.Pair;
import slimeknights.mantle.item.EdibleItem;
import slimeknights.mantle.item.TooltipItem;

import javax.annotation.Nullable;
import java.util.List;

public class NaturaEdibleItem extends EdibleItem {

  public boolean displayCustomEffectsTooltip; // set to false to not display effects of food in tooltip

  public NaturaEdibleItem(Food foodIn, ItemGroup itemGroup) {
    super(foodIn, itemGroup, false);
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {

    if (this.displayCustomEffectsTooltip) {
      for (Pair<EffectInstance, Float> pair : stack.getItem().getFood().getEffects()) {
        if (pair.getLeft() != null) {
          tooltip.add(new StringTextComponent(pair.getLeft().getPotion().getEffectType().getColor().toString() + I18n.format(pair.getLeft().getEffectName()).trim()));
        }
      }
    }

    super.addInformation(stack, worldIn, tooltip, flagIn);
  }
}
