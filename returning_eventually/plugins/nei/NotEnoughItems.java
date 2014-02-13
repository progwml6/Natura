package mods.natura.plugins.nei;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import mods.natura.Natura;
import mods.natura.plugins.ICompatPlugin;

public class NotEnoughItems implements ICompatPlugin
{
    @Override
    public String getModId() {
        return "NotEnoughItems";
    }

    @Override
    public void preInit() {

    }

    @Override
    public void init() {
        if (FMLCommonHandler.instance().getSide().isServer())
            return;

        try
        {
            Natura.logger.fine("[NEI] Registering Natura NEI plugin.");
            NEICompat.registerNEICompat();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void postInit() {

    }

}
