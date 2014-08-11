package mods.natura.plugins.minefactoryreloaded;

import mantle.module.ILoadableModule;
import mods.natura.Natura;

public class MineFactoryReloaded implements ILoadableModule
{

    @SuppressWarnings("unused")
    public static String modId = "MineFactoryReloaded";

    @Override
    public void preInit() {

    }

    @Override
    public void init() {
        try
        {
            Natura.logger.warn("[MFR] Registering Natura trees/plants/crops with MFR Farming Registry.");
            MRFRegistering.registerWithMFR();
        }
        catch (Throwable t)
        {
            Natura.logger.warn("Something went wrong in Natura plugin MineFactoryReloaded.", t);
        }
    }

    @Override
    public void postInit() {

    }

}
