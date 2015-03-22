package mods.natura.plugins.fmp;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import mantle.pulsar.pulse.Handler;
import mantle.pulsar.pulse.Pulse;
import mods.natura.Natura;

@Pulse(id = "Natura FMP Compatibility", modsRequired = ForgeMultiPart.modId)
public class FMPPulse
{
    
    @Handler
    public void init (FMLInitializationEvent evt)
    {
        try
        {
            Natura.logger.debug("[FMP] Registering Natura decorative blocks with FMP.");
            ForgeMultiPart.registerBlocks();
        }
        catch (Exception e)
        {
            Natura.logger.catching(e);
        }
    }

}
