package mods.natura.plugins;

import static mods.natura.Natura.moduleLoader;
import mods.natura.plugins.fmp.ForgeMultiPart;
import mods.natura.plugins.imc.*;
import mods.natura.plugins.nei.NotEnoughItems;
import mods.natura.plugins.te3.ThermalExpansion3;
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
        moduleLoader.registerModule(ThermalExpansion3.class);
        moduleLoader.registerModule(BuildCraft.class);
        moduleLoader.registerModule(Forestry.class);
        moduleLoader.registerModule(TreeCapitator.class);
        moduleLoader.registerModule(Thaumcraft.class);
        moduleLoader.registerModule(ForgeMultiPart.class);
        moduleLoader.registerModule(NotEnoughItems.class);

    }

}
