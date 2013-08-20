package mods.natura.common;

import cpw.mods.fml.common.registry.LanguageRegistry;

public class NProxyCommon
{
    public void registerRenderer ()
    {

    }

    public void addNames ()
    {
        /*LanguageRegistry.instance().addStringLocalization("entity.Natura.Imp.name", "en_US", "Imp");
        LanguageRegistry.instance().addStringLocalization("entity.Natura.FlameSpider.name", "en_US", "Heatscar Spider");
        LanguageRegistry.instance().addStringLocalization("entity.Natura.NitroCreeper.name", "en_US", "Nitro Creeper");
        LanguageRegistry.instance().addStringLocalization("entity.Natura.FlameSpiderBaby.name", "en_US", "Baby Heatscar Spider");*/


        String langDir = "/assets/natura/lang/";
        String[] langFiles = { "en_US.xml" };

        for (String langFile : langFiles)
        {
            try
            {
                LanguageRegistry.instance().loadLocalization(langDir + langFile, langFile.substring(langFile.lastIndexOf('/') + 1, langFile.lastIndexOf('.')), true);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

}
