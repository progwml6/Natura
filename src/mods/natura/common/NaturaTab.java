package mods.natura.common;

import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class NaturaTab extends CreativeTabs
{
    public static NaturaTab tab = new NaturaTab();
    static boolean hasInit;
    static int icon;

    public NaturaTab()
    {
        super("naturaTab");
        LanguageRegistry.instance().addStringLocalization("itemGroup.naturaTab", "Natura");
    }

    public static void init (int index)
    {
        if (!hasInit)
        {
            hasInit = true;
            icon = index;
        }
    }

    public int getTabIconItemIndex ()
    {
        return icon;
    }
}
