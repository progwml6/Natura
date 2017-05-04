package com.progwml6.natura.plugin.waila;

import com.google.common.eventbus.Subscribe;

import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import slimeknights.mantle.pulsar.pulse.Pulse;

@Pulse(id = PluginWaila.PulseId, modsRequired = PluginWaila.modid, defaultEnable = true)
public class PluginWaila
{
    public static final String modid = "Waila";

    public static final String PulseId = modid + "Integration";

    @Subscribe
    public void preInit(FMLPreInitializationEvent event)
    {
        FMLInterModComms.sendMessage(modid, "register", "com.progwml6.natura.plugin.waila.WailaRegistrar.wailaCallback");
    }
}
