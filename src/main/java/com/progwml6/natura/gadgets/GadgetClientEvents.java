package com.progwml6.natura.gadgets;

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
public class GadgetClientEvents extends ClientEventBase {

  @SubscribeEvent
  static void clientSetup(FMLClientSetupEvent event) {
    ItemBlockRenderTypes.setRenderLayer(NaturaGadgets.stoneLadder.get(), RenderType.cutout());
    ItemBlockRenderTypes.setRenderLayer(NaturaGadgets.stoneTorch.get(), RenderType.cutout());
    ItemBlockRenderTypes.setRenderLayer(NaturaGadgets.wallStoneTorch.get(), RenderType.cutout());
  }
}
