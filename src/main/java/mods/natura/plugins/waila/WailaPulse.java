package mods.natura.plugins.waila;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import mantle.pulsar.pulse.Handler;
import mantle.pulsar.pulse.Pulse;
import mods.natura.Natura;

@Pulse(id = "Natura Waila Compatibility", modsRequired = WailaPulse.modId)
public class WailaPulse
{
    
    public static final String modId = "Waila";
    
    @Handler
    public void init (FMLInitializationEvent evt)
    {
        Natura.logger.info("Waila detected.");
        FMLInterModComms.sendMessage(modId, "register", "mods.natura.plugins.waila.WailaRegistrar.wailaCallback");
    }
    
}
