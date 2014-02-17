package mods.natura.plugins.imc;

import mods.natura.plugins.ICompatPlugin;

public class Forestry implements ICompatPlugin
{

    @Override
    public String getModId ()
    {
        return "Forestry";
    }

    @Override
    public void preInit ()
    {
    }

    @Override
    public void init ()
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

    @Override
    public void postInit ()
    {
    }

}
