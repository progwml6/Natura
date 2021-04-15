package com.progwml6.natura.shared;

import net.minecraft.item.Food;

@SuppressWarnings("WeakerAccess")
public final class NaturaFood {

  /* Jerkies from drying racks */
  // for jerky that are similar to cooked meats, start with 1 less hunger
  // saturation is enough so (hunger + (hunger * saturation * 2) between [0.5,1] larger than vanilla
  public static final Food BEEF_JERKY = (new Food.Builder()).hunger(7).saturation(1.05F).build();
  public static final Food CHICKEN_JERKY = (new Food.Builder()).hunger(5).saturation(0.9F).build();
  public static final Food PORK_JERKY = (new Food.Builder()).hunger(7).saturation(1.05F).build();
  public static final Food MUTTON_JERKY = (new Food.Builder()).hunger(5).saturation(1.1F).build();
  public static final Food RABBIT_JERKY = (new Food.Builder()).hunger(4).saturation(0.95F).build();
  public static final Food FISH_JERKY = (new Food.Builder()).hunger(4).saturation(0.95F).build();
  public static final Food SALMON_JERKY = (new Food.Builder()).hunger(5).saturation(1.1F).build();
  // these jerkies do not match a cooked food, first two just cure effects, clownfish simply a nice snack
  public static final Food MONSTER_JERKY = (new Food.Builder()).hunger(5).saturation(0.4F).build();
  public static final Food CLOWNFISH_JERKY = (new Food.Builder()).hunger(3).saturation(0.9F).build();
  public static final Food PUFFERFISH_JERKY = (new Food.Builder()).hunger(4).saturation(0.5F).build();

  private NaturaFood() {}
}
