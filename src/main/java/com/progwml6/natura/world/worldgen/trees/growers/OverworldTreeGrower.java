package com.progwml6.natura.world.worldgen.trees.growers;

import com.progwml6.natura.world.block.TreeType;
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
    return null;
  }

  @Nullable
  public Holder<? extends ConfiguredFeature<?, ?>> createTreeFeature(boolean largeHive) {
    return null;
  }
}
