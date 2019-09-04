package com.progwml6.natura.shared;

import com.progwml6.natura.Natura;
import com.progwml6.natura.common.NaturaPulse;
import com.progwml6.natura.common.ServerProxy;
import com.progwml6.natura.library.NaturaPulseIds;
import com.progwml6.natura.library.NaturaRegistry;
import com.progwml6.natura.library.Util;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;
import org.apache.logging.log4j.Logger;
import slimeknights.mantle.pulsar.pulse.Pulse;

@Pulse(id = NaturaPulseIds.NATURA_COMMONS_PULSE_ID, forced = true)
@ObjectHolder(Natura.modID)
public class NaturaCommons extends NaturaPulse {

  static final Logger log = Util.getLogger(NaturaPulseIds.NATURA_COMMONS_PULSE_ID);

  public static ServerProxy proxy = DistExecutor.runForDist(() -> CommonsClientProxy::new, () -> ServerProxy::new);

  @SubscribeEvent
  public void registerBlocks(final RegistryEvent.Register<Block> event) {
    IForgeRegistry<Block> registry = event.getRegistry();
  }

  @SubscribeEvent
  public void registerItems(final RegistryEvent.Register<Item> event) {
    IForgeRegistry<Item> registry = event.getRegistry();
  }

  @SubscribeEvent
  public void preInit(final FMLCommonSetupEvent event) {
    proxy.preInit();
  }

  @SubscribeEvent
  public void init(final InterModEnqueueEvent event) {
    proxy.init();
  }

  @SubscribeEvent
  public void postInit(final InterModProcessEvent event) {
    proxy.postInit();
    NaturaRegistry.generalItemGroup.setDisplayIcon(new ItemStack(cotton));

    if (!isOverworldLoaded()) {
      NaturaRegistry.worldItemGroup.setDisplayIcon(new ItemStack(new ItemStack(white_cloud)));
    }
  }
}
