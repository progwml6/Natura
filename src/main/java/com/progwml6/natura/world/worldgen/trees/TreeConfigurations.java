package com.progwml6.natura.world.worldgen.trees;

import com.progwml6.natura.world.NaturaWorld;
import com.progwml6.natura.world.block.TreeType;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

import java.util.List;
import java.util.OptionalInt;

public class TreeConfigurations {

  private static final BeehiveDecorator BEEHIVE_005 = new BeehiveDecorator(0.05F);

  public static final TreeConfiguration MAPLE = new TreeConfiguration.TreeConfigurationBuilder(
    BlockStateProvider.simple(NaturaWorld.maple.getLog()),
    new StraightTrunkPlacer(4, 2, 0),
    BlockStateProvider.simple(NaturaWorld.leaves.get(TreeType.MAPLE)),
    new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
    new TwoLayersFeatureSize(1, 0, 1)
  ).ignoreVines().build();

  public static final TreeConfiguration SILVERBELL = new TreeConfiguration.TreeConfigurationBuilder(
    BlockStateProvider.simple(NaturaWorld.silverbell.getLog()),
    new StraightTrunkPlacer(4, 2, 0),
    BlockStateProvider.simple(NaturaWorld.leaves.get(TreeType.SILVERBELL)),
    new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
    new TwoLayersFeatureSize(1, 0, 1)
  ).ignoreVines().build();

  public static final TreeConfiguration AMARANTH = new TreeConfiguration.TreeConfigurationBuilder(
    BlockStateProvider.simple(NaturaWorld.amaranth.getLog()),
    new StraightTrunkPlacer(9, 8, 0),
    BlockStateProvider.simple(NaturaWorld.leaves.get(TreeType.AMARANTH)),
    new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
    new TwoLayersFeatureSize(1, 0, 1)
  ).ignoreVines().build();

  public static final TreeConfiguration TIGER = new TreeConfiguration.TreeConfigurationBuilder(
    BlockStateProvider.simple(NaturaWorld.tiger.getLog()),
    new StraightTrunkPlacer(6, 4, 0),
    BlockStateProvider.simple(NaturaWorld.leaves.get(TreeType.TIGER)),
    new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
    new TwoLayersFeatureSize(1, 0, 1)
  ).ignoreVines().build();

  public static final TreeConfiguration SAKURA = new TreeConfiguration.TreeConfigurationBuilder(
    BlockStateProvider.simple(NaturaWorld.tiger.getLog()),
    new FancyTrunkPlacer(3, 11, 0),
    BlockStateProvider.simple(NaturaWorld.leaves.get(TreeType.SAKURA)),
    new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
    new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))
  ).ignoreVines().build();

  public static final TreeConfiguration MAPLE_BEES = new TreeConfiguration.TreeConfigurationBuilder(
    BlockStateProvider.simple(NaturaWorld.maple.getLog()),
    new StraightTrunkPlacer(4, 2, 0),
    BlockStateProvider.simple(NaturaWorld.leaves.get(TreeType.MAPLE)),
    new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
    new TwoLayersFeatureSize(1, 0, 1)
  ).ignoreVines().decorators(List.of(BEEHIVE_005)).build();

  public static final TreeConfiguration SILVERBELL_BEES = new TreeConfiguration.TreeConfigurationBuilder(
    BlockStateProvider.simple(NaturaWorld.silverbell.getLog()),
    new StraightTrunkPlacer(4, 2, 0),
    BlockStateProvider.simple(NaturaWorld.leaves.get(TreeType.SILVERBELL)),
    new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
    new TwoLayersFeatureSize(1, 0, 1)
  ).ignoreVines().decorators(List.of(BEEHIVE_005)).build();

  public static final TreeConfiguration AMARANTH_BEES = new TreeConfiguration.TreeConfigurationBuilder(
    BlockStateProvider.simple(NaturaWorld.amaranth.getLog()),
    new StraightTrunkPlacer(9, 8, 0),
    BlockStateProvider.simple(NaturaWorld.leaves.get(TreeType.AMARANTH)),
    new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
    new TwoLayersFeatureSize(1, 0, 1)
  ).ignoreVines().decorators(List.of(BEEHIVE_005)).build();

  public static final TreeConfiguration TIGER_BEES = new TreeConfiguration.TreeConfigurationBuilder(
    BlockStateProvider.simple(NaturaWorld.tiger.getLog()),
    new StraightTrunkPlacer(6, 4, 0),
    BlockStateProvider.simple(NaturaWorld.leaves.get(TreeType.TIGER)),
    new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
    new TwoLayersFeatureSize(1, 0, 1)
  ).ignoreVines().decorators(List.of(BEEHIVE_005)).build();

  public static final TreeConfiguration SAKURA_BEES = new TreeConfiguration.TreeConfigurationBuilder(
    BlockStateProvider.simple(NaturaWorld.tiger.getLog()),
    new FancyTrunkPlacer(3, 11, 0),
    BlockStateProvider.simple(NaturaWorld.leaves.get(TreeType.SAKURA)),
    new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
    new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))
  ).ignoreVines().decorators(List.of(BEEHIVE_005)).build();
}
