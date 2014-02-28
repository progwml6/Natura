package mods.natura.items.blocks;

import mantle.blocks.abstracts.MultiItemBlock;
import net.minecraft.block.Block;

public class GrassSlabItem extends MultiItemBlock
{
    public static final String blockType[] = { "grass", "bluegrass", "autumngrass" };

    public GrassSlabItem(Block id)
    {
        super(id, "block.soil", "slab", blockType);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    /*    @Override
        public String getUnlocalizedName (ItemStack itemstack)
        {
            int pos = MathHelper.clamp_int(itemstack.getItemDamage(), 0, blockType.length - 1);
            return (new StringBuilder()).append("block.soil.").append(blockType[pos]).append(".slab").toString();
        }*/
}
