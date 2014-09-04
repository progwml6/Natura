package mods.natura.plugins.minefactoryreloaded;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import mantle.pulsar.pulse.Handler;
import mantle.pulsar.pulse.Pulse;
import mods.natura.Natura;

@Pulse(id = "Natura MFR Compatibility", modsRequired = MFRPulse.modId)
public class MFRPulse
{

    public static final String modId = "MineFactoryReloaded";

    @Handler
    public void init(FMLInitializationEvent evt) {
        try
        {
            Natura.logger.debug("[MFR] Registering Natura trees/plants/crops with MFR Farming Registry.");
            MRFRegistering.registerWithMFR();
        }
        catch (Throwable t)
        {
            Natura.logger.warn("Something went wrong in Natura plugin MineFactoryReloaded.", t);
        }
    }

}
