// Credit to Mekanism and Pupnewfster for these classes
package com.progwml6.natura.world.worldgen.helper;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

import javax.annotation.Nonnull;
import java.util.Random;
import java.util.function.BooleanSupplier;

public class DisableableConfiguredFeature<FC extends IFeatureConfig, F extends Feature<FC>> extends ConfiguredFeature<FC, F> {

  private final BooleanSupplier enabledSupplier;

  public DisableableConfiguredFeature(F feature, FC config, BooleanSupplier enabledSupplier) {
    super(feature, config);
    this.enabledSupplier = enabledSupplier;
  }

  @Override
  public boolean generate(@Nonnull ISeedReader reader, @Nonnull ChunkGenerator chunkGenerator, @Nonnull Random rand, @Nonnull BlockPos pos) {
    if (enabledSupplier.getAsBoolean()) {
      return super.generate(reader, chunkGenerator, rand, pos);
    }

    return false;
  }
}
