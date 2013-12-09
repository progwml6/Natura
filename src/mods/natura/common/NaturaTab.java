package mods.natura.common;

import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class NaturaTab extends CreativeTabs
{
    public static NaturaTab tab = new NaturaTab("natura.plants");
    public static NaturaTab woodTab = new NaturaTab("natura.trees");
    public static NaturaTab netherTab = new NaturaTab("natura.nether");
    static boolean hasInit;
    static int icon;

    public NaturaTab(String name)
    {
        super(name);
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
