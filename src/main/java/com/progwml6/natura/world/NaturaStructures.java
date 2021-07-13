package com.progwml6.natura.world;

import com.google.common.collect.ImmutableList;
import com.progwml6.natura.common.NaturaModule;
import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.library.utils.Util;
import com.progwml6.natura.world.block.RedwoodType;
import com.progwml6.natura.world.block.TreeType;
import com.progwml6.natura.world.config.FeatureType;
import com.progwml6.natura.world.worldgen.helper.ChanceRandomFeature;
import com.progwml6.natura.world.worldgen.helper.ChanceRandomFeatureConfig;
import com.progwml6.natura.world.worldgen.helper.DisableableConfiguredFeature;
import com.progwml6.natura.world.worldgen.trees.SupplierBlockStateProvider;
import com.progwml6.natura.world.worldgen.trees.config.BaseOverworldTreeFeatureConfig;
import com.progwml6.natura.world.worldgen.trees.config.RedwoodTreeFeatureConfig;
import com.progwml6.natura.world.worldgen.trees.feature.EucalyptusTreeFeature;
import com.progwml6.natura.world.worldgen.trees.feature.HopseedTreeFeature;
import com.progwml6.natura.world.worldgen.trees.feature.OverworldTreeFeature;
import com.progwml6.natura.world.worldgen.trees.feature.RedwoodTreeFeature;
import com.progwml6.natura.world.worldgen.trees.feature.WillowTreeFeature;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockstateprovider.BlockStateProviderType;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.TwoLayerFeature;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.OptionalInt;

/**
 * Contains any logic relevant to structure generation, including trees
 */
@SuppressWarnings("unused")
public final class NaturaStructures extends NaturaModule {

  static final Logger log = Util.getLogger("natura_structures");

  /*
   * Misc
   */
  public static final RegistryObject<BlockStateProviderType<SupplierBlockStateProvider>> supplierBlockstateProvider = BLOCK_STATE_PROVIDER_TYPES
    .register("supplier_state_provider", () -> new BlockStateProviderType<>(SupplierBlockStateProvider.CODEC));

  /*
   * Features
   */
  public static final RegistryObject<Feature<BaseTreeFeatureConfig>> OVERWORLD_TREE_FEATURE = FEATURES
    .register("overworld_tree", () -> new OverworldTreeFeature(BaseTreeFeatureConfig.CODEC));
  public static final RegistryObject<Feature<BaseOverworldTreeFeatureConfig>> WILLOW_TREE_FEATURE = FEATURES
    .register("willow_tree", () -> new WillowTreeFeature(BaseOverworldTreeFeatureConfig.CODEC));
  public static final RegistryObject<Feature<BaseOverworldTreeFeatureConfig>> EUCALYPTUS_TREE_FEATURE = FEATURES
    .register("eucalyptus_tree", () -> new EucalyptusTreeFeature(BaseOverworldTreeFeatureConfig.CODEC));
  public static final RegistryObject<Feature<BaseOverworldTreeFeatureConfig>> HOPSEED_TREE_FEATURE = FEATURES
    .register("hopseed_tree", () -> new HopseedTreeFeature(BaseOverworldTreeFeatureConfig.CODEC));
  public static final RegistryObject<Feature<RedwoodTreeFeatureConfig>> REDWOOD_TREE_FEATURE = FEATURES
    .register("redwood_tree", () -> new RedwoodTreeFeature(RedwoodTreeFeatureConfig.CODEC));
  public static final RegistryObject<Feature<ChanceRandomFeatureConfig>> CHANCE_FEATURE = FEATURES
    .register("chance_feature", () -> new ChanceRandomFeature(ChanceRandomFeatureConfig.CODEC));

  public static ConfiguredFeature<BaseTreeFeatureConfig, ?> MAPLE_TREE;
  public static ConfiguredFeature<BaseTreeFeatureConfig, ?> SILVERBELL_TREE;
  public static ConfiguredFeature<BaseTreeFeatureConfig, ?> AMARANTH_TREE;
  public static ConfiguredFeature<BaseTreeFeatureConfig, ?> TIGER_TREE;
  public static ConfiguredFeature<BaseTreeFeatureConfig, ?> SAKURA_TREE;

  public static ConfiguredFeature<BaseOverworldTreeFeatureConfig, ?> WILLOW_TREE;
  public static ConfiguredFeature<BaseOverworldTreeFeatureConfig, ?> EUCALYPTUS_TREE;
  public static ConfiguredFeature<BaseOverworldTreeFeatureConfig, ?> HOPSEED_TREE;

  public static ConfiguredFeature<RedwoodTreeFeatureConfig, ?> REDWOOD_TREE;

  // Bee Trees!
  public static ConfiguredFeature<BaseTreeFeatureConfig, ?> MAPLE_TREE_005;
  public static ConfiguredFeature<BaseTreeFeatureConfig, ?> SILVERBELL_TREE_005;
  public static ConfiguredFeature<BaseTreeFeatureConfig, ?> AMARANTH_TREE_005;
  public static ConfiguredFeature<BaseTreeFeatureConfig, ?> TIGER_TREE_005;
  public static ConfiguredFeature<BaseTreeFeatureConfig, ?> SAKURA_TREE_005;

  public static ConfiguredFeature<BaseOverworldTreeFeatureConfig, ?> WILLOW_TREE_005;
  public static ConfiguredFeature<BaseOverworldTreeFeatureConfig, ?> EUCALYPTUS_TREE_005;
  public static ConfiguredFeature<BaseOverworldTreeFeatureConfig, ?> HOPSEED_TREE_005;

  public static ConfiguredFeature<?, ?> MAPLE_TREE_GEN;

  public static ConfiguredFeature<?, ?> SILVERBELL_TREE_GEN;

  public static ConfiguredFeature<?, ?> AMARANTH_TREE_GEN;

  public static ConfiguredFeature<?, ?> TIGER_TREE_GEN;

  public static ConfiguredFeature<?, ?> WILLOW_TREE_GEN;
  public static ConfiguredFeature<?, ?> WILLOW_TREE_3_GEN;

  public static ConfiguredFeature<?, ?> EUCALYPTUS_TREE_GEN;
  public static ConfiguredFeature<?, ?> EUCALYPTUS_PLAINS_TREE_GEN;

  public static ConfiguredFeature<?, ?> SAKURA_TREE_GEN;
  public static ConfiguredFeature<?, ?> SAKURA_FOREST_TREE_GEN;

  public static ConfiguredFeature<?, ?> HOPSEED_TREE_GEN;

  public static ConfiguredFeature<?, ?> REDWOOD_TREE_GEN;

  /**
   * Feature configuration
   * <p>
   * PLACEMENT MOVED TO WorldEvents#onBiomeLoad
   */
  @SubscribeEvent
  void commonSetup(FMLCommonSetupEvent event) {
    MAPLE_TREE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, resource("maple_tree"), OVERWORLD_TREE_FEATURE.get().withConfiguration(
      (new BaseTreeFeatureConfig.Builder(new SupplierBlockStateProvider(() -> NaturaWorld.maple.getLog().getDefaultState()),
        new SupplierBlockStateProvider(() -> NaturaWorld.leaves.get(TreeType.MAPLE).getDefaultState()),
        new BlobFoliagePlacer(FeatureSpread.create(2), FeatureSpread.create(0), 3),
        new StraightTrunkPlacer(4, 2, 0),
        new TwoLayerFeature(1, 0, 1)))
        .setIgnoreVines().build())
    );

    SILVERBELL_TREE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, resource("silverbell_tree"), OVERWORLD_TREE_FEATURE.get()
      .withConfiguration(
        (new BaseTreeFeatureConfig.Builder(new SupplierBlockStateProvider(() -> NaturaWorld.silverbell.getLog().getDefaultState()),
          new SupplierBlockStateProvider(() -> NaturaWorld.leaves.get(TreeType.SILVERBELL).getDefaultState()),
          new BlobFoliagePlacer(FeatureSpread.create(2), FeatureSpread.create(0), 3),
          new StraightTrunkPlacer(4, 2, 0),
          new TwoLayerFeature(1, 0, 1)))
          .setIgnoreVines().build())
    );

    AMARANTH_TREE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, resource("amaranth_tree"), OVERWORLD_TREE_FEATURE.get()
      .withConfiguration(
        (new BaseTreeFeatureConfig.Builder(new SupplierBlockStateProvider(() -> NaturaWorld.amaranth.getLog().getDefaultState()),
          new SupplierBlockStateProvider(() -> NaturaWorld.leaves.get(TreeType.AMARANTH).getDefaultState()),
          new BlobFoliagePlacer(FeatureSpread.create(2), FeatureSpread.create(0), 3),
          new StraightTrunkPlacer(9, 8, 0),
          new TwoLayerFeature(1, 0, 1)))
          .setIgnoreVines().build())
    );

    TIGER_TREE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, resource("tiger_tree"), OVERWORLD_TREE_FEATURE.get().withConfiguration(
      (new BaseTreeFeatureConfig.Builder(new SupplierBlockStateProvider(() -> NaturaWorld.tiger.getLog().getDefaultState()),
        new SupplierBlockStateProvider(() -> NaturaWorld.leaves.get(TreeType.TIGER).getDefaultState()),
        new BlobFoliagePlacer(FeatureSpread.create(2), FeatureSpread.create(0), 3),
        new StraightTrunkPlacer(6, 4, 0),
        new TwoLayerFeature(1, 0, 1)))
        .setIgnoreVines().build())
    );

    WILLOW_TREE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, resource("willow_tree"), WILLOW_TREE_FEATURE.get().withConfiguration((
      new BaseOverworldTreeFeatureConfig.Builder(
        new SupplierBlockStateProvider(() -> NaturaWorld.willow.getLog().getDefaultState()),
        new SupplierBlockStateProvider(() -> NaturaWorld.leaves.get(TreeType.WILLOW).getDefaultState()),
        5,
        4))
      .build())
    );

    EUCALYPTUS_TREE = Registry
      .register(WorldGenRegistries.CONFIGURED_FEATURE, resource("eucalyptus_tree"), EUCALYPTUS_TREE_FEATURE.get().withConfiguration((
        new BaseOverworldTreeFeatureConfig.Builder(
          new SupplierBlockStateProvider(() -> NaturaWorld.eucalyptus.getLog().getDefaultState()),
          new SupplierBlockStateProvider(() -> NaturaWorld.leaves.get(TreeType.EUCALYPTUS).getDefaultState()),
          6,
          3))
        .build())
      );

    HOPSEED_TREE = Registry
      .register(WorldGenRegistries.CONFIGURED_FEATURE, resource("hopseed_tree"), HOPSEED_TREE_FEATURE.get().withConfiguration((
        new BaseOverworldTreeFeatureConfig.Builder(
          new SupplierBlockStateProvider(() -> NaturaWorld.hopseed.getLog().getDefaultState()),
          new SupplierBlockStateProvider(() -> NaturaWorld.leaves.get(TreeType.HOPSEED).getDefaultState()),
          2,
          3))
        .build())
      );

    SAKURA_TREE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, resource("sakura_tree"), OVERWORLD_TREE_FEATURE.get().withConfiguration(
      (new BaseTreeFeatureConfig.Builder(new SupplierBlockStateProvider(() -> NaturaWorld.sakura.getLog().getDefaultState()),
        new SupplierBlockStateProvider(() -> NaturaWorld.leaves.get(TreeType.SAKURA).getDefaultState()),
        new FancyFoliagePlacer(FeatureSpread.create(2), FeatureSpread.create(4), 4),
        new FancyTrunkPlacer(3, 11, 0),
        new TwoLayerFeature(0, 0, 0, OptionalInt.of(4))))
        .setIgnoreVines().setHeightmap(Heightmap.Type.MOTION_BLOCKING).build())
    );

    REDWOOD_TREE = Registry
      .register(WorldGenRegistries.CONFIGURED_FEATURE, resource("redwood_tree"), REDWOOD_TREE_FEATURE.get().withConfiguration((
        new RedwoodTreeFeatureConfig.Builder(
          new SupplierBlockStateProvider(() -> NaturaWorld.redwood.get(RedwoodType.BARK).getDefaultState()),
          new SupplierBlockStateProvider(() -> NaturaWorld.redwood.get(RedwoodType.HEART).getDefaultState()),
          new SupplierBlockStateProvider(() -> NaturaWorld.redwood.get(RedwoodType.ROOT).getDefaultState()),
          new SupplierBlockStateProvider(() -> NaturaWorld.redwood_leaves.get().getDefaultState()),
          80,
          60,
          12,
          4,
          0.618D,
          0.381D,
          1.0D,
          1.0D))
        .build())
      );

    // Bee Trees
    MAPLE_TREE_005 = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, resource("maple_tree_005"), OVERWORLD_TREE_FEATURE.get()
      .withConfiguration(MAPLE_TREE.getConfig().copy(ImmutableList.of(Features.Placements.BEES_005_PLACEMENT))));

    SILVERBELL_TREE_005 = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, resource("silverbell_tree_005"), OVERWORLD_TREE_FEATURE.get()
      .withConfiguration(SILVERBELL_TREE.getConfig().copy(ImmutableList.of(Features.Placements.BEES_005_PLACEMENT))));

    AMARANTH_TREE_005 = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, resource("amaranth_tree_005"), OVERWORLD_TREE_FEATURE.get()
      .withConfiguration(AMARANTH_TREE.getConfig().copy(ImmutableList.of(Features.Placements.BEES_005_PLACEMENT))));

    TIGER_TREE_005 = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, resource("tiger_tree_005"), OVERWORLD_TREE_FEATURE.get()
      .withConfiguration(TIGER_TREE.getConfig().copy(ImmutableList.of(Features.Placements.BEES_005_PLACEMENT))));

    SAKURA_TREE_005 = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, resource("sakura_tree_005"), OVERWORLD_TREE_FEATURE.get()
      .withConfiguration(SAKURA_TREE.getConfig().copy(ImmutableList.of(Features.Placements.BEES_005_PLACEMENT))));

    WILLOW_TREE_005 = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, resource("willow_tree_005"),
      WILLOW_TREE_FEATURE.get().withConfiguration(WILLOW_TREE.getConfig().withDecorators(ImmutableList.of(Features.Placements.BEES_005_PLACEMENT))));

    EUCALYPTUS_TREE_005 = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, resource("eucalyptus_tree_005"), EUCALYPTUS_TREE_FEATURE.get()
      .withConfiguration(EUCALYPTUS_TREE.getConfig().withDecorators(ImmutableList.of(Features.Placements.BEES_005_PLACEMENT))));

    HOPSEED_TREE_005 = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, resource("hopseed_tree_005"), HOPSEED_TREE_FEATURE.get()
      .withConfiguration(HOPSEED_TREE.getConfig().withDecorators(ImmutableList.of(Features.Placements.BEES_005_PLACEMENT))));

    // World Gen
    MAPLE_TREE_GEN = getFeature(FeatureType.MAPLE, CHANCE_FEATURE.get(), MAPLE_TREE, 1);

    SILVERBELL_TREE_GEN = getFeature(FeatureType.SIVERBELL, CHANCE_FEATURE.get(), SILVERBELL_TREE, 1);

    AMARANTH_TREE_GEN = getFeature(FeatureType.AMARANTH, CHANCE_FEATURE.get(), AMARANTH_TREE, 1);

    TIGER_TREE_GEN = getFeature(FeatureType.TIGER, CHANCE_FEATURE.get(), TIGER_TREE, 1);

    WILLOW_TREE_GEN = getFeature(FeatureType.WILLOW, CHANCE_FEATURE.get(), WILLOW_TREE, 1);
    WILLOW_TREE_3_GEN = getFeature(FeatureType.WILLOW, CHANCE_FEATURE.get(), WILLOW_TREE, 3);

    EUCALYPTUS_TREE_GEN = getFeature(FeatureType.EUCALYPTUS, CHANCE_FEATURE.get(), EUCALYPTUS_TREE, 1);
    EUCALYPTUS_PLAINS_TREE_GEN = getFeature(FeatureType.EUCALYPTUS_PLAINS, CHANCE_FEATURE.get(), EUCALYPTUS_TREE, 1);

    HOPSEED_TREE_GEN = getFeature(FeatureType.HOPSEED, CHANCE_FEATURE.get(), HOPSEED_TREE, 1);

    SAKURA_TREE_GEN = getFeature(FeatureType.SAKURA, CHANCE_FEATURE.get(), SAKURA_TREE, 3);
    SAKURA_FOREST_TREE_GEN = getFeature(FeatureType.SAKURA_FOREST, CHANCE_FEATURE.get(), SAKURA_TREE, 3);

    REDWOOD_TREE_GEN = getFeature(FeatureType.REDWOOD, CHANCE_FEATURE.get(), REDWOOD_TREE, 1);
  }

  @Nonnull
  private static ConfiguredFeature<?, ?> getFeature(FeatureType type, Feature<ChanceRandomFeatureConfig> feature, ConfiguredFeature<?, ?> actualFeature, int count) {
    Config.FeatureConfig featureConfig = Config.features.get(type);

    ConfiguredFeature<?, ?> configuredFeature = new DisableableConfiguredFeature<>(feature,
      new ChanceRandomFeatureConfig(actualFeature, type, featureConfig.chance), featureConfig.shouldGenerate)
      .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
      .withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(count, 0.1F, 1)));

    //Register the configured feature
    String name = type.getType() + "_" + type.getName();

    Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, resource(name), configuredFeature);
    return configuredFeature;
  }
}
