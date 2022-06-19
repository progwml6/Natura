package com.progwml6.natura.world.worldgen.trees.growers;

import com.progwml6.natura.world.block.TreeType;
import com.progwml6.natura.world.worldgen.WorldConfiguredFeatures;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class OverworldTreeGrower extends AbstractTreeGrower {

  private final TreeType treeType;

  public OverworldTreeGrower(TreeType treeType) {
    this.treeType = treeType;
  }

  @Nullable
  @Override
  protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(Random pRandom, boolean pLargeHive) {
    return switch (this.treeType) {
      case MAPLE -> pLargeHive ? WorldConfiguredFeatures.MAPLE_TREE_BEES : WorldConfiguredFeatures.MAPLE_TREE;
      case SILVERBELL ->
        pLargeHive ? WorldConfiguredFeatures.SILVERBELL_TREE_BEES : WorldConfiguredFeatures.SILVERBELL_TREE;
      case AMARANTH -> pLargeHive ? WorldConfiguredFeatures.AMARANTH_TREE_BEES : WorldConfiguredFeatures.AMARANTH_TREE;
      case TIGER -> pLargeHive ? WorldConfiguredFeatures.TIGER_TREE_BEES : WorldConfiguredFeatures.TIGER_TREE;
      case SAKURA -> pLargeHive ? WorldConfiguredFeatures.SAKURA_TREE_BEES : WorldConfiguredFeatures.SAKURA_TREE;
      default -> null;
    };
  }

  @Nullable
  public Holder<? extends ConfiguredFeature<?, ?>> createTreeFeature(boolean largeHive) {
    return null;
  }
}
