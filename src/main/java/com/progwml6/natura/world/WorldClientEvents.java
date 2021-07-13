package com.progwml6.natura.world;

import com.progwml6.natura.Natura;
import com.progwml6.natura.common.ClientEventBase;
import com.progwml6.natura.world.block.TreeType;
import com.progwml6.natura.world.client.LeavesColorizer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import javax.annotation.Nullable;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = Natura.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WorldClientEvents extends ClientEventBase {

  @SubscribeEvent
  static void clientSetup(FMLClientSetupEvent event) {
    for (TreeType type : TreeType.values()) {
      RenderTypeLookup.setRenderLayer(NaturaWorld.leaves.get(type), RenderType.getCutoutMipped());
      RenderTypeLookup.setRenderLayer(NaturaWorld.sapling.get(type), RenderType.getCutout());
    }

    RenderTypeLookup.setRenderLayer(NaturaWorld.redwood_leaves.get(), RenderType.getCutoutMipped());
    RenderTypeLookup.setRenderLayer(NaturaWorld.redwood_sapling.get(), RenderType.getCutout());

    RenderTypeLookup.setRenderLayer(NaturaWorld.cotton_crop.get(), RenderType.getCutout());
    RenderTypeLookup.setRenderLayer(NaturaWorld.barley_crop.get(), RenderType.getCutout());
  }

  @SubscribeEvent
  static void registerColorHandlers(ColorHandlerEvent.Item event) {
    BlockColors blockColors = event.getBlockColors();
    ItemColors itemColors = event.getItemColors();

    for (TreeType type : TreeType.values()) {
      blockColors.register(
        (state, reader, pos, index) -> getLeavesColorByPos(reader, pos, type),
        NaturaWorld.leaves.get(type)
      );
    }

    blockColors.register((state, reader, pos, index) -> getLeavesColorByPos(reader, pos), NaturaWorld.redwood_leaves.get());

    registerBlockItemColorAlias(blockColors, itemColors, NaturaWorld.leaves);
    registerBlockItemColorAlias(blockColors, itemColors, NaturaWorld.redwood_leaves);
  }

  private static int getLeavesColorByPos(@Nullable IBlockDisplayReader reader, @Nullable BlockPos pos, TreeType type) {
    if (pos == null || reader == null) {
      return LeavesColorizer.getColorStatic(type);
    }

    return LeavesColorizer.getColorForPos(reader, pos, type);
  }

  private static int getLeavesColorByPos(@Nullable IBlockDisplayReader reader, @Nullable BlockPos pos) {
    if (pos == null || reader == null) {
      return LeavesColorizer.leaves2Color;
    }

    return BiomeColors.getFoliageColor(reader, pos);
  }
}
