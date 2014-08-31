package mods.natura.plugins.imc;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mantle.pulsar.pulse.Handler;
import mantle.pulsar.pulse.Pulse;

@Pulse(id = "Natura Forestry Compatibility", modsRequired = Forestry.modId)
public class Forestry
{

    public static final String modId = "Forestry";

    @Handler
    public void preInit (FMLPreInitializationEvent evt)
    {
    }

    @Handler
    public void init (FMLInitializationEvent evt)
    {
        /*
        //Forestry
        StringBuilder builder = new StringBuilder();
        String string = builder.append("farmWheat@").append(seeds.itemID).append(".0.").append(crops.blockID).append(".3").toString();
        FMLInterModComms.sendMessage("Forestry", "add-farmable-crop", string);
        builder = new StringBuilder();
        string = builder.append("farmWheat@").append(seeds.itemID).append(".1.").append(crops.blockID).append(".8").toString();
        FMLInterModComms.sendMessage("Forestry", "add-farmable-crop", string);
         */
    }

    @Handler
    public void postInit (FMLPostInitializationEvent evt)
    {
    }

}
