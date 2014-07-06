package mods.natura.items.blocks;

import mantle.blocks.abstracts.MultiItemBlock;
import net.minecraft.block.Block;

public class OmniShapeBlockItem extends MultiItemBlock
{
    public static final String blockType[] = { "stair", "stair", "stair", "stair", "stair", "stair", "stair", "stair", "slab", "slab", "slab", "slab", "slab", "slab", "fence", "none" };

    public OmniShapeBlockItem(Block i)
    {
        super(i, "", "omni", blockType);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    /* @Override
     public String getUnlocalizedName (ItemStack itemstack)
     {
         return (new StringBuilder()).append(blockType[itemstack.getItemDamage()]).append("omni").toString();
     }*/

    //was commented out already when moving to mantle interfaces
    /*@Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        list.add("So light and fluffy!");
        switch (stack.getItemDamage() % 4)
        {
        case 0:
            list.add("Saves you when you fall");
            break;
        case 1:
            list.add("Causes lightning strikes");
            break;
        case 2:
            list.add("3x3 Converts into charcoal");
            break;
        case 3:
            list.add("2x2 Converts into sulfur");
            break;
        }
    }*/
}
