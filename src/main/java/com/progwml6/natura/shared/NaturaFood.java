package com.progwml6.natura.shared;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class NaturaFood {

  public static final Food RAW_IMPMEAT = (new Food.Builder()).hunger(3).saturation(0.2F).effect(new EffectInstance(Effects.HUNGER, 8 * 20, 0), 1.0F).effect(new EffectInstance(Effects.POISON, 5 * 20, 0), 1.0F).build();
  public static final Food COOKED_IMPMEAT = (new Food.Builder()).hunger(8).saturation(0.6F).effect(new EffectInstance(Effects.FIRE_RESISTANCE, 15 * 20, 0), 1.0F).effect(new EffectInstance(Effects.POISON, 5 * 20, 0), 1.0F).build();

}