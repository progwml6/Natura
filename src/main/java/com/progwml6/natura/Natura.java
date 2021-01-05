package com.progwml6.natura;

import com.progwml6.natura.common.NaturaModule;
import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.common.data.NaturaBlockTagsProvider;
import com.progwml6.natura.common.data.NaturaItemTagsProvider;
import com.progwml6.natura.common.data.NaturaLootTableProvider;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.world.NaturaStructures;
import com.progwml6.natura.world.NaturaWorld;
import com.progwml6.natura.world.config.FeatureType;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
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

  // We can't read the config very early on.
  public static boolean configLoaded = false;

  /**
   * Cached value of {@link FeatureType#values()}. DO NOT MODIFY THIS LIST.
   */
  public static final FeatureType[] FEATURE_TYPES = FeatureType.values();

  public Natura() {
    instance = this;

    ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_SPEC);
    ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_SPEC);

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

  @SubscribeEvent
  static void configChanged(final ModConfig.ModConfigEvent configEvent) {
    ModConfig config = configEvent.getConfig();
    if (config.getModId().equals(modID)) {
      Config.clearCache(config.getSpec());
      if (config.getSpec() == Config.SERVER_SPEC) {
        configLoaded = true;
      }
    }
  }
}
