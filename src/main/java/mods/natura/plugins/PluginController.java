package mods.natura.plugins;

import static mods.natura.Natura.pulsar;
import mods.natura.plugins.imc.*;
import mods.natura.plugins.nei.NEIPulse;
import mods.natura.plugins.te4.TE4Pulse;

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
    	pulsar.registerPulse(new TE4Pulse());
    	pulsar.registerPulse(new BuildCraftPulse());
    	pulsar.registerPulse(new ForestryPulse());
    	pulsar.registerPulse(new TreeCapitatorPulse());
    	pulsar.registerPulse(new NEIPulse());
    }

}
