package com.progwml6.natura.world;

import com.progwml6.natura.common.NaturaModule;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.world.block.TreeType;
import com.progwml6.natura.world.worldgen.trees.SupplierBlockStateProvider;
import com.progwml6.natura.world.worldgen.trees.config.BaseOverworldTreeFeatureConfig;
import com.progwml6.natura.world.worldgen.trees.feature.OverworldTreeFeature;
import com.progwml6.natura.world.worldgen.trees.feature.WillowTreeFeature;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockstateprovider.BlockStateProviderType;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.TwoLayerFeature;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.Logger;

/**
 * Contains any logic relevant to structure generation, including trees
 */
@SuppressWarnings("unused")
public final class NaturaStructures extends NaturaModule {

  static final Logger log = Util.getLogger("natura_structures");

  /*
   * Misc
   */
  public static final RegistryObject<BlockStateProviderType<SupplierBlockStateProvider>> supplierBlockstateProvider = BLOCK_STATE_PROVIDER_TYPES.register("supplier_state_provider", () -> new BlockStateProviderType<>(SupplierBlockStateProvider.CODEC));

  /*
   * Features
   */
  public static final RegistryObject<Feature<BaseTreeFeatureConfig>> OVERWORLD_TREE_FEATURE = FEATURES.register("overworld_tree", () -> new OverworldTreeFeature(BaseTreeFeatureConfig.CODEC));
  public static final RegistryObject<Feature<BaseOverworldTreeFeatureConfig>> WILLOW_TREE_FEATURE = FEATURES.register("willow_tree", () -> new WillowTreeFeature(BaseOverworldTreeFeatureConfig.CODEC));
  
  public static ConfiguredFeature<BaseTreeFeatureConfig, ?> MAPLE_TREE;
  public static ConfiguredFeature<BaseTreeFeatureConfig, ?> SILVERBELL_TREE;
  public static ConfiguredFeature<BaseTreeFeatureConfig, ?> AMARANTH_TREE;
  public static ConfiguredFeature<BaseTreeFeatureConfig, ?> TIGER_TREE;
  public static ConfiguredFeature<BaseOverworldTreeFeatureConfig, ?> WILLOW_TREE;

  /**
   * Feature configuration
   *
   * PLACEMENT MOVED TO WorldEvents#onBiomeLoad
   */
  @SubscribeEvent
  void commonSetup(FMLCommonSetupEvent event) {
    MAPLE_TREE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, location("maple_tree"), OVERWORLD_TREE_FEATURE.get().withConfiguration((new BaseTreeFeatureConfig.Builder(new SupplierBlockStateProvider(() -> NaturaWorld.logs.get(TreeType.MAPLE).getDefaultState()),
      new SupplierBlockStateProvider(() -> NaturaWorld.leaves.get(TreeType.MAPLE).getDefaultState()),
      new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3),
      new StraightTrunkPlacer(4, 2, 0),
      new TwoLayerFeature(1, 0, 1)))
      .setIgnoreVines().build())
    );

    SILVERBELL_TREE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, location("silverbell_tree"), OVERWORLD_TREE_FEATURE.get().withConfiguration((new BaseTreeFeatureConfig.Builder(new SupplierBlockStateProvider(() -> NaturaWorld.logs.get(TreeType.SILVERBELL).getDefaultState()),
      new SupplierBlockStateProvider(() -> NaturaWorld.leaves.get(TreeType.SILVERBELL).getDefaultState()),
      new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3),
      new StraightTrunkPlacer(4, 2, 0),
      new TwoLayerFeature(1, 0, 1)))
      .setIgnoreVines().build())
    );

    AMARANTH_TREE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, location("amaranth_tree"), OVERWORLD_TREE_FEATURE.get().withConfiguration((new BaseTreeFeatureConfig.Builder(new SupplierBlockStateProvider(() -> NaturaWorld.logs.get(TreeType.AMARANTH).getDefaultState()),
      new SupplierBlockStateProvider(() -> NaturaWorld.leaves.get(TreeType.AMARANTH).getDefaultState()),
      new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3),
      new StraightTrunkPlacer(9, 8, 0),
      new TwoLayerFeature(1, 0, 1)))
      .setIgnoreVines().build())
    );

    TIGER_TREE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, location("tiger_tree"), OVERWORLD_TREE_FEATURE.get().withConfiguration((new BaseTreeFeatureConfig.Builder(new SupplierBlockStateProvider(() -> NaturaWorld.logs.get(TreeType.TIGER).getDefaultState()),
      new SupplierBlockStateProvider(() -> NaturaWorld.leaves.get(TreeType.TIGER).getDefaultState()),
      new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3),
      new StraightTrunkPlacer(6, 4, 0),
      new TwoLayerFeature(1, 0, 1)))
      .setIgnoreVines().build())
    );

    WILLOW_TREE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, location("willow_tree"), WILLOW_TREE_FEATURE.get().withConfiguration((
      new BaseOverworldTreeFeatureConfig.Builder(
        new SupplierBlockStateProvider(() -> NaturaWorld.logs.get(TreeType.WILLOW).getDefaultState()),
        new SupplierBlockStateProvider(() -> NaturaWorld.leaves.get(TreeType.WILLOW).getDefaultState()),
        5,
        4))
      .build())
    );
  }
}
