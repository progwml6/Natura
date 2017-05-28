package com.progwml6.natura;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.progwml6.natura.common.CommonProxy;
import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.common.gui.GuiHandler;
import com.progwml6.natura.decorative.NaturaDecorative;
import com.progwml6.natura.entities.NaturaEntities;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.nether.NaturaNether;
import com.progwml6.natura.oredict.NaturaOredict;
import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.plugin.CraftingTweaks;
import com.progwml6.natura.plugin.waila.PluginWaila;
import com.progwml6.natura.shared.NaturaCommons;
import com.progwml6.natura.tools.NaturaTools;
import com.progwml6.natura.world.NaturaWorld;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import slimeknights.mantle.pulsar.control.PulseManager;

@Mod(modid = Natura.modID, name = Natura.modName, version = Natura.modVersion, dependencies = "required-after:Forge@[12.18.0.1993,);required-after:mantle@[1.10-0.10.3,);", acceptedMinecraftVersions = "[1.10, 1.11)")
public class Natura
{
    public static final String modID = Util.MODID;

    public static final String modVersion = "${version}";

    public static final String modName = "Natura";

    public static final Logger log = LogManager.getLogger(modID);

    /* Instance of this mod, used for grabbing prototype fields */
    @Instance(modID)
    public static Natura instance;

    @SidedProxy(clientSide = "com.progwml6.natura.common.CommonProxy", serverSide = "com.progwml6.natura.common.CommonProxy")
    public static CommonProxy proxy;

    public static PulseManager pulseManager = new PulseManager(Config.pulseConfig);

    static
    {
        pulseManager.registerPulse(new NaturaCommons());
        pulseManager.registerPulse(new NaturaOverworld());
        pulseManager.registerPulse(new NaturaNether());
        pulseManager.registerPulse(new NaturaDecorative());
        pulseManager.registerPulse(new NaturaTools());
        pulseManager.registerPulse(new NaturaEntities());
        pulseManager.registerPulse(new NaturaOredict());
        pulseManager.registerPulse(new NaturaWorld());

        pulseManager.registerPulse(new PluginWaila());
        pulseManager.registerPulse(new CraftingTweaks());
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        Config.load(event);

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
    }

}
