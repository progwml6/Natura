package com.progwml6.natura.world.worldgen;

import com.progwml6.natura.Natura;
import com.progwml6.natura.world.worldgen.trees.TreeConfigurations;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public class WorldConfiguredFeatures {

  public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> MAPLE_TREE = register("tree/maple_tree", Feature.TREE, TreeConfigurations.MAPLE);
  public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> MAPLE_TREE_BEES = register("tree/maple_tree_bees", Feature.TREE, TreeConfigurations.MAPLE_BEES);
  public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> SILVERBELL_TREE = register("tree/silverbell_tree", Feature.TREE, TreeConfigurations.SILVERBELL);
  public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> SILVERBELL_TREE_BEES = register("tree/silverbell_tree_bees", Feature.TREE, TreeConfigurations.SILVERBELL_BEES);
  public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> AMARANTH_TREE = register("tree/amaranth_tree", Feature.TREE, TreeConfigurations.AMARANTH);
  public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> AMARANTH_TREE_BEES = register("tree/amaranth_tree_bees", Feature.TREE, TreeConfigurations.AMARANTH_BEES);
  public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> TIGER_TREE = register("tree/tiger_tree", Feature.TREE, TreeConfigurations.TIGER);
  public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> TIGER_TREE_BEES = register("tree/tiger_tree_bees", Feature.TREE, TreeConfigurations.TIGER_BEES);
  public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> SAKURA_TREE = register("tree/sakura_tree", Feature.TREE, TreeConfigurations.SAKURA);
  public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> SAKURA_TREE_BEES = register("tree/sakura_tree_bees", Feature.TREE, TreeConfigurations.SAKURA_BEES);

  public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> register(String name, F feature, FC featureConfiguration) {
    return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE, Natura.getResource(name).toString(), new ConfiguredFeature<>(feature, featureConfiguration));
  }
}
