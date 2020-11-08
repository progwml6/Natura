package com.progwml6.natura;

import com.progwml6.natura.common.NaturaModule;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.overworld.NaturaOverworld;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

@Mod(Natura.modID)
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

    bus.register(new NaturaOverworld());

    // init deferred registers
    NaturaModule.initRegisters();
  }
}
