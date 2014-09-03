package mods.natura.plugins;

import static mods.natura.Natura.pulsar;
import mods.natura.plugins.fmp.ForgeMultiPart;
import mods.natura.plugins.imc.*;
import mods.natura.plugins.minefactoryreloaded.MineFactoryReloaded;
import mods.natura.plugins.nei.NotEnoughItems;
import mods.natura.plugins.te4.ThermalExpansion4;
import mods.natura.plugins.thaumcraft.Thaumcraft;

/**
 * Master controller for Natura compat plugins.
 *
 * @author Sunstrike <sunstrike@azurenode.net>
 */
public class PluginController
{

    private PluginController()
    {
    } // Not to be instantiated.

    public static void registerBuiltins ()
    {
    	pulsar.registerPulse(new ThermalExpansion4());
    	pulsar.registerPulse(new BuildCraft());
    	pulsar.registerPulse(new Forestry());
    	pulsar.registerPulse(new TreeCapitator());
    	pulsar.registerPulse(new Thaumcraft());
    	pulsar.registerPulse(new ForgeMultiPart());
    	pulsar.registerPulse(new NotEnoughItems());
    	pulsar.registerPulse(new MineFactoryReloaded());
    }

}
