package com.progwml6.natura.world;

import com.progwml6.natura.Natura;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = Natura.modID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WorldEvents {

  @SubscribeEvent
  static void onBiomeLoad(BiomeLoadingEvent event) {
    BiomeGenerationSettingsBuilder generation = event.getGeneration();

    if (event.getCategory() == Biome.Category.JUNGLE) {
      generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, NaturaStructures.TREES_JUNGLE);
    }

    if (event.getCategory() == Biome.Category.FOREST) {
      generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, NaturaStructures.TREES_FOREST);
    }

    if (event.getCategory() == Biome.Category.SWAMP) {
      generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, NaturaStructures.TREES_SWAMP);
    }
  }
}
