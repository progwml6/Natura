package mods.natura.items.blocks;

import java.util.List;

import mantle.blocks.abstracts.MultiItemBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NoColorLeavesItem extends MultiItemBlock
{
    public static final String blockType[] = { "sakura", "ghost", "blood", "willow", "", "", "", "", "", "", "", "", "", "", "", "", "" };

    public NoColorLeavesItem(Block i)
    {
        super(i, "block.leaves", blockType);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata (int md)
    {
        return md | 4;
    }

    /* @Override
     public String getUnlocalizedName (ItemStack itemstack)
     {
         return (new StringBuilder()).append("block.leaves.").append(blockType[itemstack.getItemDamage()]).toString();
     }*/

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        switch (stack.getItemDamage())
        {
        case 0:
            list.add(StatCollector.translateToLocal("tooltip.tree2"));
            break;
        case 1:
            list.add(StatCollector.translateToLocal("tooltip.nethertree"));
            list.add(StatCollector.translateToLocal("tooltip.tree3"));
            break;
        case 2:
            list.add(StatCollector.translateToLocal("tooltip.nethertree"));
            list.add(StatCollector.translateToLocal("tooltip.fireleaves"));
            break;
        case 3:
            list.add(StatCollector.translateToLocal("tooltip.tree11"));
            break;
        }
    }
}
