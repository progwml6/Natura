package com.progwml6.natura.shared;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public final class NaturaFood {

  /* IMP MEAT */
  public static final Food RAW_IMP_MEAT = (new Food.Builder()).hunger(3).saturation(0.2F).effect(() -> new EffectInstance(Effects.HUNGER, 8 * 20, 0), 1.0F).effect(() -> new EffectInstance(Effects.POISON, 5 * 20, 0), 1.0F).build();
  public static final Food COOKED_IMP_MEAT = (new Food.Builder()).hunger(8).saturation(0.6F).effect(() -> new EffectInstance(Effects.FIRE_RESISTANCE, 15 * 20, 0), 1.0F).effect(() -> new EffectInstance(Effects.POISON, 5 * 20, 0), 1.0F).build();

  /* BERRIES */
  public static final Food BERRY = (new Food.Builder()).hunger(1).saturation(0.4F).fastToEat().build();
  public static final Food BLIGHTBERRY = (new Food.Builder()).hunger(1).saturation(0.4F).fastToEat().effect(() -> new EffectInstance(Effects.REGENERATION, 8 * 20, 0), 1.0F).effect(() -> new EffectInstance(Effects.POISON, 5 * 20, 0), 1.0F).effect(() -> new EffectInstance(Effects.WITHER, 5 * 20, 0), 1.0F).build();
  public static final Food DUSKBERRY = (new Food.Builder()).hunger(1).saturation(0.4F).fastToEat().effect(() -> new EffectInstance(Effects.NIGHT_VISION, 15 * 20, 0), 1.0F).effect(() -> new EffectInstance(Effects.BLINDNESS, 3 * 20, 0), 1.0F).build();
  public static final Food SKYBERRY = (new Food.Builder()).hunger(1).saturation(0.4F).fastToEat().effect(() -> new EffectInstance(Effects.JUMP_BOOST, 8 * 20, 0), 1.0F).effect(() -> new EffectInstance(Effects.SLOWNESS, 3 * 20, 0), 1.0F).build();
  public static final Food STINGBERRY = (new Food.Builder()).hunger(1).saturation(0.4F).fastToEat().effect(() -> new EffectInstance(Effects.STRENGTH, 10 * 20, 0), 1.0F).effect(() -> new EffectInstance(Effects.MINING_FATIGUE, 10 * 20, 0), 1.0F).build();

  public static final Food POTASH_APPLE = (new Food.Builder()).hunger(4).saturation(0.4F).effect(() -> new EffectInstance(Effects.POISON, 2 * 25, 0), 1.0F).build();

  public static final Food CACTUS_JUICE = (new Food.Builder()).hunger(1).saturation(0.1F).fastToEat().build();

  public static final Food BERRY_MEDLEY = (new Food.Builder()).hunger(5).saturation(1.4F).build();
  public static final Food GLOWSHROOM_STEW = (new Food.Builder()).hunger(6).saturation(0.6F).effect(() -> new EffectInstance(Effects.NIGHT_VISION, 45 * 25, 0), 1.0F).effect(() -> new EffectInstance(Effects.POISON, 16 * 25, 0), 1.0F).effect(() -> new EffectInstance(Effects.MINING_FATIGUE, 8 * 25, 0), 1.0F).build();

}
