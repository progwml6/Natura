package mods.natura.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class StickItem extends Item
{
    public static final String textureNames[] = { "eucalyptus", "sakura", "ghostwood", "redwood", "bloodwood", "hopseed", "maple", "silverbell", "purpleheart", "tiger", "willow", "darkwood",
            "fusewood" };

    public StickItem()
    {
        super();
        this.setHasSubtypes(true);
    }

    @Override
    public void getSubItems (Item id, CreativeTabs tab, List list)
    {
        for (int i = 0; i < textureNames.length; i++)
            list.add(new ItemStack(id, 1, i));
    }

    /*public CreativeTabs[] getCreativeTabs ()
    {
        return new CreativeTabs[] { getCreativeTab(), NaturaTab.tab };
    }*/

}
