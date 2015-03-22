package mods.natura.items.blocks;

import mods.natura.common.NContent;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.IIcon;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FenceItem extends ItemBlock
{
    public FenceItem(Block i)
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
    public IIcon getIconFromDamage (int meta)
    {
        return NContent.planks.getIcon(0, meta);
    }
}
