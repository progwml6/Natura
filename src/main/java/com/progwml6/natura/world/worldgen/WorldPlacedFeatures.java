package com.progwml6.natura.world.worldgen;

import com.google.common.collect.ImmutableList;
import com.progwml6.natura.Natura;
import com.progwml6.natura.world.NaturaWorld;
import com.progwml6.natura.world.block.TreeType;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.SurfaceWaterDepthFilter;

import java.util.List;

public class WorldPlacedFeatures {

  public static final Holder<PlacedFeature> PLACED_MAPLE_TREE = register("tree/maple_tree", WorldConfiguredFeatures.MAPLE_TREE, treeCheckArea(PlacementUtils.countExtra(1, 0.1F, 1), NaturaWorld.sapling.get(TreeType.MAPLE).defaultBlockState()));

  private static List<PlacementModifier> treeCheckArea(BlockState sapling) {
    return List.of(InSquarePlacement.spread(), SurfaceWaterDepthFilter.forMaxDepth(0), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, PlacementUtils.filteredByBlockSurvival(sapling.getBlock()), BiomeFilter.biome());
  }

  private static List<PlacementModifier> treeCheckArea(PlacementModifier count, BlockState sapling) {
    return treeCheckArea(count, sapling, true);
  }

  private static List<PlacementModifier> treeCheckArea(PlacementModifier count, BlockState sapling, boolean checkSurvival) {
    ImmutableList.Builder<PlacementModifier> list = ImmutableList.builder();

    list.add(count, InSquarePlacement.spread(), SurfaceWaterDepthFilter.forMaxDepth(0), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR);

    if (checkSurvival) {
      list.add(PlacementUtils.filteredByBlockSurvival(sapling.getBlock()));
    }

    list.add(BiomeFilter.biome());

    return list.build();
  }

  public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<PlacedFeature> register(String name, Holder<? extends ConfiguredFeature<?, ?>> feature, List<PlacementModifier> placements) {
    return BuiltinRegistries.registerExact(BuiltinRegistries.PLACED_FEATURE, Natura.getResource(name).toString(), new PlacedFeature(Holder.hackyErase(feature), List.copyOf(placements)));
  }
}
