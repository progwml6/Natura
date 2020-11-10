package com.progwml6.natura;

import com.progwml6.natura.common.NaturaModule;
import com.progwml6.natura.common.data.NaturaBlockTagsProvider;
import com.progwml6.natura.common.data.NaturaItemTagsProvider;
import com.progwml6.natura.common.data.NaturaLootTableProvider;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.world.NaturaStructures;
import com.progwml6.natura.world.NaturaWorld;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

@SuppressWarnings("unused")
@Mod(Natura.modID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Natura {

  public static final String modID = Util.MODID;

  public static final Logger log = LogManager.getLogger(modID);
  public static final Random random = new Random();

  /* Instance of this mod, used for grabbing prototype fields */
  public static Natura instance;

  public Natura() {
    instance = this;

    // initialize modules, done this way rather than with annotations to give us control over the order
    IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

    bus.register(new NaturaWorld());
    bus.register(new NaturaStructures());

    // init deferred registers
    NaturaModule.initRegisters();
  }

  @SubscribeEvent
  static void gatherData(final GatherDataEvent event) {
    if (event.includeServer()) {
      DataGenerator datagenerator = event.getGenerator();
      ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
      NaturaBlockTagsProvider blockTags = new NaturaBlockTagsProvider(datagenerator, existingFileHelper);

      datagenerator.addProvider(blockTags);
      datagenerator.addProvider(new NaturaItemTagsProvider(datagenerator, blockTags, existingFileHelper));
      datagenerator.addProvider(new NaturaLootTableProvider(datagenerator));
    }
  }
}
