package com.progwml6.natura.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import slimeknights.mantle.pulsar.config.PulsarConfig;

public class Config {

  public static PulsarConfig pulseConfig = new PulsarConfig("NaturaModules", "Modules");

  /**
   * Common specific configuration
   */
  public static class Common {

    Common(ForgeConfigSpec.Builder builder) {

    }
  }

  public static final ForgeConfigSpec commonSpec;
  public static final Common COMMON;

  static {
    final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
    commonSpec = specPair.getRight();
    COMMON = specPair.getLeft();
  }
}
