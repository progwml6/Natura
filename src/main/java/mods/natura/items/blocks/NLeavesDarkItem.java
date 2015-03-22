package mods.natura.items.blocks;

import java.util.List;

import mantle.blocks.abstracts.MultiItemBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NLeavesDarkItem extends MultiItemBlock
{
    public static final String blockType[] = { "darkwood", "darkwood.flowering", "darkwood.fruit", "fusewood", "", "", "", "", "", "", "", "", "", "", "", "", "" };

    public NLeavesDarkItem(Block i)
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
            list.add(StatCollector.translateToLocal("tooltip.nethertree"));
            break;
        case 1:
            list.add(StatCollector.translateToLocal("tooltip.nethertree"));
            list.add(StatCollector.translateToLocal("tooltip.darkwood1"));
            break;
        case 2:
            list.add(StatCollector.translateToLocal("tooltip.nethertree"));
            list.add(StatCollector.translateToLocal("tooltip.darkwood2"));
            break;
        case 3:
            list.add(StatCollector.translateToLocal("tooltip.nethertree"));
            list.add(StatCollector.translateToLocal("tooltip.fusewood.log"));
            break;
        }
    }
}
