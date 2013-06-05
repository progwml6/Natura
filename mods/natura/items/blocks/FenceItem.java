package mods.natura.items.blocks;

import mods.natura.blocks.overrides.AlternateFence;
import mods.natura.common.NContent;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FenceItem extends ItemBlock
{
    public FenceItem(int i)
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

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIconFromDamage(int meta)
    {
        return NContent.planks.getIcon(0, meta);
    }
}
