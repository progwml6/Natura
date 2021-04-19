package com.progwml6.natura.world.worldgen.berry.config;

import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class BerryBushFeatureConfig implements IFeatureConfig {

  public final BlockStateProvider bushProvider;
  public final int randomHeight;

  public BerryBushFeatureConfig(BlockStateProvider bushProvider, int randomHeight) {
    this.bushProvider = bushProvider;
    this.randomHeight = randomHeight;
  }

  public static class Builder {

    public final BlockStateProvider bushProvider;
    public final int randomHeight;

    public Builder(BlockStateProvider bushProvider, int randomHeight) {
      this.bushProvider = bushProvider;
      this.randomHeight = randomHeight;
    }

    public BerryBushFeatureConfig build() {
      return new BerryBushFeatureConfig(this.bushProvider, this.randomHeight);
    }
  }
}
