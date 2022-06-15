package com.progwml6.natura.shared;

import net.minecraft.world.food.FoodProperties;

@SuppressWarnings("WeakerAccess")
public final class NaturaFood {

  public static final FoodProperties CACTUS_JUICE = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.1F).build();

  /* Jerkies from drying racks */
  // for jerky that are similar to cooked meats, start with 1 less nutrition
  // saturation is enough so (nutrition + (nutrition * saturation * 2) between [0.5,1] larger than vanilla
  public static final FoodProperties BEEF_JERKY = (new FoodProperties.Builder()).nutrition(7).saturationMod(1.05F).build();
  public static final FoodProperties CHICKEN_JERKY = (new FoodProperties.Builder()).nutrition(5).saturationMod(0.9F).build();
  public static final FoodProperties PORK_JERKY = (new FoodProperties.Builder()).nutrition(7).saturationMod(1.05F).build();
  public static final FoodProperties MUTTON_JERKY = (new FoodProperties.Builder()).nutrition(5).saturationMod(1.1F).build();
  public static final FoodProperties RABBIT_JERKY = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.95F).build();
  public static final FoodProperties FISH_JERKY = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.95F).build();
  public static final FoodProperties SALMON_JERKY = (new FoodProperties.Builder()).nutrition(5).saturationMod(1.1F).build();
  // these jerkies do not match a cooked food, first two just cure effects, clownfish simply a nice snack
  public static final FoodProperties MONSTER_JERKY = (new FoodProperties.Builder()).nutrition(5).saturationMod(0.4F).build();
  public static final FoodProperties CLOWNFISH_JERKY = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.9F).build();
  public static final FoodProperties PUFFERFISH_JERKY = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.5F).build();

  public static final FoodProperties POTASH_APPLE = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.4F).build();

  private NaturaFood() {}
}
