package com.progwml6.natura;

import com.progwml6.natura.common.NaturaModule;
import com.progwml6.natura.common.data.loot.NaturaLootTableProvider;
import com.progwml6.natura.common.data.tags.BlockTagProvider;
import com.progwml6.natura.common.data.tags.ItemTagProvider;
import com.progwml6.natura.gadgets.NaturaGadgets;
import com.progwml6.natura.shared.NaturaCommons;
import com.progwml6.natura.world.NaturaWorld;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.Random;

@SuppressWarnings("unused")
@Mod(Natura.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Natura {

  public static final String MOD_ID = "natura";
  public static final Logger LOG = LogManager.getLogger(MOD_ID);
  public static final Random RANDOM = new Random();

  /* Instance of this mod, used for grabbing prototype fields */
  public static Natura instance;

  public Natura() {
    instance = this;

    // initialize modules, done this way rather than with annotations to give us control over the order
    IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

    bus.register(new NaturaCommons());
    bus.register(new NaturaGadgets());
    bus.register(new NaturaWorld());

    // init deferred registers
    NaturaModule.initRegisters();
  }

  @SubscribeEvent
  static void gatherData(final GatherDataEvent event) {
    DataGenerator datagenerator = event.getGenerator();
    ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
    if (event.includeServer()) {
      BlockTagProvider blockTags = new BlockTagProvider(datagenerator, existingFileHelper);

      datagenerator.addProvider(blockTags);
      datagenerator.addProvider(new ItemTagProvider(datagenerator, blockTags, existingFileHelper));
      datagenerator.addProvider(new NaturaLootTableProvider(datagenerator));
    }
  }

  /* Utils */

  /**
   * Gets a resource location for Natura
   *
   * @param name Resource path
   * @return Location for Natura
   */
  public static ResourceLocation getResource(String name) {
    return new ResourceLocation(MOD_ID, name);
  }

  /**
   * Returns the given Resource prefixed with natura resource location. Use this function instead of hardcoding
   * resource locations.
   */
  public static String resourceString(String res) {
    return String.format("%s:%s", MOD_ID, res);
  }

  /**
   * Prefixes the given unlocalized name with natura prefix. Use this when passing unlocalized names for a uniform
   * namespace.
   */
  public static String prefix(String name) {
    return String.format("%s.%s", MOD_ID, name.toLowerCase(Locale.US));
  }
}
