package com.progwml6.natura.shared.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Food;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.Pair;
import slimeknights.mantle.item.EdibleItem;

import javax.annotation.Nullable;
import java.util.List;

public class EdibleNaturaItem extends EdibleItem {

  public final boolean displayCustomEffectsTooltip; // set to false to not display effects of food in tooltip

  public EdibleNaturaItem(Food foodIn, ItemGroup itemGroup) {
    this(foodIn, itemGroup, false);
  }

  public EdibleNaturaItem(Food foodIn, ItemGroup itemGroup, boolean displayEffectsTooltip) {
    this(foodIn, itemGroup, displayEffectsTooltip, true);
  }

  public EdibleNaturaItem(Food foodIn, ItemGroup itemGroup, boolean displayEffectsTooltip, boolean displayCustomEffectsTooltip) {
    super(foodIn, itemGroup, displayEffectsTooltip);

    this.displayCustomEffectsTooltip = displayCustomEffectsTooltip;
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    super.addInformation(stack, worldIn, tooltip, flagIn);

    if (this.displayCustomEffectsTooltip) {
      for (Pair<EffectInstance, Float> pair : stack.getItem().getFood().getEffects()) {
        if (pair.getLeft() != null) {
          tooltip.add(new StringTextComponent(I18n.format(pair.getLeft().getEffectName()).trim()).applyTextStyle(pair.getLeft().getPotion().getEffectType().getColor()));
        }
      }
    }
  }
}
