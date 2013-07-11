package mods.natura.items.blocks;

import mods.natura.common.NaturaTab;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class NOverrideItem extends ItemBlock
{
    public NOverrideItem(int i)
    {
        super(i);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata (int meta)
    {
        return meta;
    }

    public CreativeTabs[] getCreativeTabs ()
    {
        return new CreativeTabs[] { getCreativeTab(), NaturaTab.tab };
    }
}
