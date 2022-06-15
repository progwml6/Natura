package com.progwml6.natura.world;

import com.progwml6.natura.Natura;
import com.progwml6.natura.common.ClientEventBase;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = Natura.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WorldClientEvents extends ClientEventBase {

  @SubscribeEvent
  static void clientSetup(FMLClientSetupEvent event) {
    RenderType cutout = RenderType.cutout();
    RenderType cutoutMipped = RenderType.cutoutMipped();

//    for (TreeType type : TreeType.values()) {
//      RenderTypeLookup.setRenderLayer(NaturaWorld.leaves.get(type), cutoutMipped);
//      RenderTypeLookup.setRenderLayer(NaturaWorld.sapling.get(type), cutout);
//    }
//
//    RenderTypeLookup.setRenderLayer(NaturaWorld.redwood_leaves.get(), cutoutMipped);
//    RenderTypeLookup.setRenderLayer(NaturaWorld.redwood_sapling.get(), cutout);
//
//    RenderTypeLookup.setRenderLayer(NaturaWorld.cotton_crop.get(), cutout);
//    RenderTypeLookup.setRenderLayer(NaturaWorld.barley_crop.get(), cutout);

    // doors
    ItemBlockRenderTypes.setRenderLayer(NaturaWorld.maple.getDoor(), cutout);
    ItemBlockRenderTypes.setRenderLayer(NaturaWorld.maple.getTrapdoor(), cutout);
    ItemBlockRenderTypes.setRenderLayer(NaturaWorld.silverbell.getDoor(), cutout);
    ItemBlockRenderTypes.setRenderLayer(NaturaWorld.silverbell.getTrapdoor(), cutout);
    ItemBlockRenderTypes.setRenderLayer(NaturaWorld.amaranth.getDoor(), cutout);
    ItemBlockRenderTypes.setRenderLayer(NaturaWorld.amaranth.getTrapdoor(), cutout);
    ItemBlockRenderTypes.setRenderLayer(NaturaWorld.tiger.getDoor(), cutout);
    ItemBlockRenderTypes.setRenderLayer(NaturaWorld.tiger.getTrapdoor(), cutout);
    ItemBlockRenderTypes.setRenderLayer(NaturaWorld.willow.getDoor(), cutout);
    ItemBlockRenderTypes.setRenderLayer(NaturaWorld.willow.getTrapdoor(), cutout);
    ItemBlockRenderTypes.setRenderLayer(NaturaWorld.eucalyptus.getDoor(), cutout);
    ItemBlockRenderTypes.setRenderLayer(NaturaWorld.eucalyptus.getTrapdoor(), cutout);
    ItemBlockRenderTypes.setRenderLayer(NaturaWorld.hopseed.getDoor(), cutout);
    ItemBlockRenderTypes.setRenderLayer(NaturaWorld.hopseed.getTrapdoor(), cutout);
    ItemBlockRenderTypes.setRenderLayer(NaturaWorld.sakura.getDoor(), cutout);
    ItemBlockRenderTypes.setRenderLayer(NaturaWorld.sakura.getTrapdoor(), cutout);
  }

//  @SubscribeEvent
//  static void registerColorHandlers(ColorHandlerEvent.Item event) {
//    BlockColors blockColors = event.getBlockColors();
//    ItemColors itemColors = event.getItemColors();
//
//    for (TreeType type : TreeType.values()) {
//      blockColors.register(
//        (state, reader, pos, index) -> getLeavesColorByPos(reader, pos, type),
//        NaturaWorld.leaves.get(type)
//      );
//    }
//
//    blockColors.register((state, reader, pos, index) -> getLeavesColorByPos(reader, pos), NaturaWorld.redwood_leaves.get());
//
//    registerBlockItemColorAlias(blockColors, itemColors, NaturaWorld.leaves);
//    registerBlockItemColorAlias(blockColors, itemColors, NaturaWorld.redwood_leaves);
//  }
//
//  private static int getLeavesColorByPos(@Nullable IBlockDisplayReader reader, @Nullable BlockPos pos, TreeType type) {
//    if (pos == null || reader == null) {
//      return LeavesColorizer.getColorStatic(type);
//    }
//
//    return LeavesColorizer.getColorForPos(reader, pos, type);
//  }
//
//  private static int getLeavesColorByPos(@Nullable IBlockDisplayReader reader, @Nullable BlockPos pos) {
//    if (pos == null || reader == null) {
//      return LeavesColorizer.leaves2Color;
//    }
//
//    return BiomeColors.getFoliageColor(reader, pos);
//  }
}
