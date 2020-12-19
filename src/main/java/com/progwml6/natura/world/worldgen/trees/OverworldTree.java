package com.progwml6.natura.world.worldgen.trees;

import com.progwml6.natura.world.NaturaStructures;
import com.progwml6.natura.world.block.TreeType;
import com.progwml6.natura.world.worldgen.trees.config.BaseOverworldTreeFeatureConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.trees.Tree;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class OverworldTree extends Tree {

  private final TreeType treeType;

  public OverworldTree(TreeType treeType) {
    this.treeType = treeType;
  }

  @Deprecated
  @Nullable
  @Override
  protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean largeHive) {
    switch (this.treeType) {
      case MAPLE:
        return NaturaStructures.MAPLE_TREE;
      case SILVERBELL:
        return NaturaStructures.SILVERBELL_TREE;
      case AMARANTH:
        return NaturaStructures.AMARANTH_TREE;
      case TIGER:
        return NaturaStructures.TIGER_TREE;
      case SAKURA:
        return NaturaStructures.SAKURA_TREE;
    }

    return null;
  }

  /**
   * Get a {@link net.minecraft.world.gen.feature.ConfiguredFeature} of tree
   */
  @Nullable
  public ConfiguredFeature<BaseOverworldTreeFeatureConfig, ?> getOverworldTreeFeature(Random randomIn, boolean largeHive) {
    switch (this.treeType) {
      case WILLOW:
        return NaturaStructures.WILLOW_TREE;
      case EUCALYPTUS:
        return NaturaStructures.EUCALYPTUS_TREE;
      case HOPSEED:
        return NaturaStructures.HOPSEED_TREE;
    }

    return null;
  }

  @Override
  public boolean attemptGrowTree(ServerWorld world, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, Random rand) {
    ConfiguredFeature<BaseTreeFeatureConfig, ?> configuredTreeFeature = this.getTreeFeature(rand, this.hasNearbyFlora(world, pos));

    if(configuredTreeFeature == null) {
      ConfiguredFeature<BaseOverworldTreeFeatureConfig, ?> configuredFeature = this.getOverworldTreeFeature(rand, this.hasNearbyFlora(world, pos));

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
    } else {
      world.setBlockState(pos, Blocks.AIR.getDefaultState(), 4);

      configuredTreeFeature.config.forcePlacement();

      if (configuredTreeFeature.generate(world, chunkGenerator, rand, pos)) {
        return true;
      }
      else {
        world.setBlockState(pos, state, 4);
        return false;
      }
    }
  }

  private boolean hasNearbyFlora(IWorld world, BlockPos pos) {
    for (BlockPos blockpos : BlockPos.Mutable.getAllInBoxMutable(pos.down().north(2).west(2), pos.up().south(2).east(2))) {
      if (world.getBlockState(blockpos).isIn(BlockTags.FLOWERS)) {
        return true;
      }
    }

    return false;
  }
}
