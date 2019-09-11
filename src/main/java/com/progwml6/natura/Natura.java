package com.progwml6.natura;

import com.progwml6.natura.common.ClientProxy;
import com.progwml6.natura.common.Config;
import com.progwml6.natura.common.ServerProxy;
import com.progwml6.natura.common.data.NaturaLootTableProvider;
import com.progwml6.natura.common.data.NaturaRecipeProvider;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.shared.NaturaCommons;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimeknights.mantle.pulsar.control.PulseManager;
import slimeknights.mantle.util.BlockStateJsonGenerator;
import slimeknights.mantle.util.LanguageJsonGenerator;
import slimeknights.mantle.util.ModelJsonGenerator;

@Mod(Natura.modID)
public class Natura {

  public static final String modID = Util.MODID;

  public static final Logger log = LogManager.getLogger(modID);

  /* Instance of this mod, used for grabbing prototype fields */
  public static Natura instance;

  public static ServerProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

  public static PulseManager pulseManager;

  public Natura() {
    instance = this;
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::preInit);
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::init);
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::postInit);
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::gatherData);

    pulseManager = new PulseManager(Config.pulseConfig);
    pulseManager.registerPulse(new NaturaCommons());
    pulseManager.enablePulses();
  }

  private void preInit(final FMLCommonSetupEvent event) {
    proxy.preInit();
  }

  private void init(final InterModEnqueueEvent event) {
    proxy.init();
  }

  private void postInit(final InterModProcessEvent event) {
    proxy.postInit();
  }

  private void gatherData(final GatherDataEvent event) {
    DataGenerator gen = event.getGenerator();

    if (event.includeServer()) {
      gen.addProvider(new NaturaLootTableProvider(gen));
      gen.addProvider(new NaturaRecipeProvider(gen));

      gen.addProvider(new BlockStateJsonGenerator(gen, modID));
      gen.addProvider(new ModelJsonGenerator(gen, modID));
      gen.addProvider(new LanguageJsonGenerator(gen, modID));
    }
  }
}
