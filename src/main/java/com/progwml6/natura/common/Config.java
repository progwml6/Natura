package com.progwml6.natura.common;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import slimeknights.mantle.pulsar.config.PulsarConfig;

public class Config {

  public static PulsarConfig pulseConfig = new PulsarConfig("NaturaModules", "Modules");

  public static class ServerConfig {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.IntValue babyHeatscarMinimum;
    public static final ForgeConfigSpec.IntValue babyHeatscarMaximum;

    public static final ForgeConfigSpec.BooleanValue enableWheatRecipe;

    static {
      BUILDER.push("Mob-Changes");
      babyHeatscarMinimum = BUILDER.comment("Minimum Baby Heatscar Spiders on Spider Death").defineInRange("babyHeatscarMinimum", 2, 0, Integer.MAX_VALUE);
      babyHeatscarMaximum = BUILDER.comment("Minimum Baby Heatscar Spiders on Spider Death").defineInRange("babyHeatscarMaximum", 4, 0, Integer.MAX_VALUE);
      BUILDER.pop();

      BUILDER.push("Enable-Disable");

      boolean value;

      try {
        Class.forName("chococraft.common.ModChocoCraft");
        value = false;
      }
      catch (Exception e) {
        value = true;
      }

      enableWheatRecipe = BUILDER.comment("Enable wheat to flour recipe").define("enableWheatRecipe", value);

      BUILDER.pop();

      SPEC = BUILDER.build();
    }
  }

  public static void init() {
    // The server specific config type seems to load too late. Use common instead.
    ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ServerConfig.SPEC, "natura-common.toml");
  }
}
