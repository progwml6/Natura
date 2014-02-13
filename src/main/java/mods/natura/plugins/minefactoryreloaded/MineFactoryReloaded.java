package mods.natura.plugins.minefactoryreloaded;

import mods.natura.Natura;
import mods.natura.plugins.ICompatPlugin;

public class MineFactoryReloaded implements ICompatPlugin
{

    @Override
    public String getModId() {
        return "MineFactoryReloaded";
    }

    @Override
    public void preInit() {

    }

    @Override
    public void init() {
        try
        {
            Natura.logger.fine("[MFR] Registering Natura trees/plants/crops with MFR Farming Registry.");
            MRFRegistering.registerWithMFR();
        }
        catch (Throwable pikachu)
        {
            Natura.logger.warning("Something went wrong in Natura plugin MineFactoryReloaded.");
            pikachu.printStackTrace();
        }
    }

    @Override
    public void postInit() {

    }

}
