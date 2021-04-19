package com.progwml6.natura;

import com.progwml6.natura.common.NaturaModule;
import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.common.data.tags.NaturaBlockTagsProvider;
import com.progwml6.natura.common.data.tags.NaturaItemTagsProvider;
import com.progwml6.natura.common.data.loot.NaturaLootTableProvider;
import com.progwml6.natura.gadgets.NaturaGadgets;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.shared.NaturaCommons;
import com.progwml6.natura.world.NaturaStructures;
import com.progwml6.natura.world.NaturaWorld;
import com.progwml6.natura.world.config.FeatureType;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimeknights.mantle.registration.RegistrationHelper;

import javax.annotation.Nullable;
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

    bus.register(new NaturaCommons());
    bus.register(new NaturaGadgets());
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

  @Nullable
  private static Block missingBlock(String name) {
    return null;
  }

  @SubscribeEvent
  void missingItems(final RegistryEvent.MissingMappings<Item> event) {
    RegistrationHelper.handleMissingMappings(event, modID, name -> {
      switch (name) {
        case "bonemeal_bag":
          return NaturaCommons.boneMealBag.get();
        case "blue_dye":
          return Items.BLUE_DYE;
      }
      IItemProvider block = missingBlock(name);
      return block == null ? null : block.asItem();
    });
  }

  @SubscribeEvent
  void missingBlocks(final RegistryEvent.MissingMappings<Block> event) {
    RegistrationHelper.handleMissingMappings(event, modID, Natura::missingBlock);
  }
}
