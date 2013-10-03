package mods.natura.plugins.fmp;

import mods.natura.common.NContent;
import mods.natura.plugins.fmp.register.RegisterWithFMP;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "Natura|ForgeMuliPart", name = "Natura Compat: FMP", version = "0.1", dependencies = "after:ForgeMultipart;after:Natura")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class ForgeMultiPart
{
    @EventHandler
    public static void load (FMLInitializationEvent ev)
    {
        if (!Loader.isModLoaded("ForgeMultipart"))
        {
            FMLLog.warning("Forgemultipart missing - Natura Compat: FMP not loading.");

            return;
        }
        try
        {
            FMLLog.fine("ForgeMultipart detected. Registering Natura decorative blocks with FMP.");
            RegisterWithFMP.registerBlock(NContent.bloodwood);
            RegisterWithFMP.registerBlock(NContent.willow);
            RegisterWithFMP.registerBlock(NContent.planks, 0, 12);
            RegisterWithFMP.registerBlock(NContent.tree, 0, 3);
            RegisterWithFMP.registerBlock(NContent.rareTree, 0, 3);
            RegisterWithFMP.registerBlock(NContent.rareLeaves, 0, 3);
            RegisterWithFMP.registerBlock(NContent.darkLeaves, 0, 3);
            RegisterWithFMP.registerBlock(NContent.redwood, 0, 2);
            RegisterWithFMP.registerBlock(NContent.floraLeaves, 0, 2);
            RegisterWithFMP.registerBlock(NContent.floraLeavesNoColor, 0, 2);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
