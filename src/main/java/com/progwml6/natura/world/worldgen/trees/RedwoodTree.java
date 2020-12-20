package com.progwml6.natura.world.worldgen.trees;

import com.progwml6.natura.world.NaturaStructures;
import com.progwml6.natura.world.worldgen.trees.config.RedwoodTreeFeatureConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class RedwoodTree extends Tree {

  @Nullable
  @Override
  protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean largeHive) {
    return null;
  }

  /**
   * Get a {@link net.minecraft.world.gen.feature.ConfiguredFeature} of tree
   */
  @Nullable
  public ConfiguredFeature<RedwoodTreeFeatureConfig, ?> getRedwoodTreeFeature(Random randomIn) {
    return NaturaStructures.REDWOOD_TREE;
  }

  @Override
  public boolean attemptGrowTree(ServerWorld world, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, Random rand) {
    ConfiguredFeature<RedwoodTreeFeatureConfig, ?> configuredFeature = this.getRedwoodTreeFeature(rand);

    if (configuredFeature == null) {
      return false;
    }
    else {
      world.setBlockState(pos, Blocks.AIR.getDefaultState(), 4);

      configuredFeature.config.forcePlacement();

      if (configuredFeature.generate(world, chunkGenerator, rand, pos)) {
        return true;
      }
      else {
        world.setBlockState(pos, state, 4);
        return false;
      }
    }
  }
}
