package mods.natura.common;

import mantle.lib.TabTools;
import net.minecraft.item.Item;

public class NaturaTab extends TabTools
{
    public static NaturaTab tab = new NaturaTab("natura.plants");
    public static NaturaTab woodTab = new NaturaTab("natura.trees");
    public static NaturaTab netherTab = new NaturaTab("natura.nether");
    static boolean hasInit;
    static Item icon;
    

    public NaturaTab(String name)
    {
        super(name);
    }

    public static void init (Item wheatBag)
    {
        if (!hasInit)
        {
            hasInit = true;
            icon = wheatBag;
        }
    }

    @Override
	public Item getTabIconItem ()
    {
        return icon;
    }
}
