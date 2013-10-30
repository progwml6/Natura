package mods.natura.plugins.minefactoryreloaded;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "Natura|CompatMineFactoryReloaded", name = "Natura compat: MFR", version = "0.1", dependencies = "after:MineFactoryReloaded;required-after:Natura")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class MineFactoryReloaded
{
    @EventHandler
    public static void load (FMLInitializationEvent ev)
    {
        if (!Loader.isModLoaded("MineFactoryReloaded"))
        {
            FMLLog.warning("MineFactoryReloaded missing - Natura Compat: MFR not loading");
            return;
        }
        try
        {
            FMLLog.fine("MineFactoryReloaded detected. Registering Natura trees/plants/crops with MFR's Farming Registry.");
            MRFRegistering.registerWithMFR();
        }
        catch (Throwable pikachu)
        {
            System.err.println("Something went wrong in Natura plugin: MineFactoryReloaded.");
            pikachu.printStackTrace();
        }
    }
}
