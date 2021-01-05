package com.progwml6.natura.world.worldgen.helper;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class ChanceRandomFeature extends Feature<ChanceRandomFeatureConfig> {

  public ChanceRandomFeature(Codec<ChanceRandomFeatureConfig> codec) {
    super(codec);
  }

  @Override
  public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, ChanceRandomFeatureConfig config) {
    if (rand.nextInt(config.chance.getAsInt()) == 0) {
      return config.generate(reader, generator, rand, pos);
    }

    return false;
  }
}
