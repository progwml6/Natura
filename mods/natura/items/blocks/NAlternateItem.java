package mods.natura.items.blocks;

import net.minecraft.item.ItemBlock;

public class NAlternateItem extends ItemBlock
{
    public NAlternateItem(int i)
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
}
