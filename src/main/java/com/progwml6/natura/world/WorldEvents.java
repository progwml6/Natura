package com.progwml6.natura.world;

import com.progwml6.natura.Natura;
import com.progwml6.natura.common.config.Config;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = Natura.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WorldEvents {

  @SubscribeEvent
  static void onBiomeLoad(BiomeLoadingEvent event) {
    BiomeGenerationSettingsBuilder generation = event.getGeneration();

    if (!Config.treesBiomesBlacklist.get().contains(event.getName().toString())) {
      if (event.getCategory() == Biome.Category.FOREST) {
        generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, NaturaStructures.SAKURA_FOREST_TREE_GEN);

        generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, NaturaStructures.EUCALYPTUS_TREE_GEN);

        generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, NaturaStructures.MAPLE_TREE_GEN);

        generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, NaturaStructures.SILVERBELL_TREE_GEN);

        generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, NaturaStructures.TIGER_TREE_GEN);
      }

      if (event.getCategory() == Biome.Category.JUNGLE) {
        generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, NaturaStructures.AMARANTH_TREE_GEN);
      }

      if (event.getCategory() == Biome.Category.PLAINS) {
        generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, NaturaStructures.REDWOOD_TREE_GEN);

        generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, NaturaStructures.EUCALYPTUS_PLAINS_TREE_GEN);
      }

      if (event.getCategory() == Biome.Category.EXTREME_HILLS) {
        generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, NaturaStructures.HOPSEED_TREE_GEN);

        generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, NaturaStructures.EUCALYPTUS_TREE_GEN);
      }

      if (event.getCategory() == Biome.Category.RIVER) {
        generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, NaturaStructures.SAKURA_TREE_GEN);

        generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, NaturaStructures.WILLOW_TREE_GEN);
      }

      if (event.getCategory() == Biome.Category.JUNGLE) {
        generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, NaturaStructures.AMARANTH_TREE_GEN);
      }

      if (event.getCategory() == Biome.Category.SWAMP) {
        generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, NaturaStructures.WILLOW_TREE_3_GEN);
      }
    }
  }
}
