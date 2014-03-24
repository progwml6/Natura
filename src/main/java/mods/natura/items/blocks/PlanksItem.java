package mods.natura.items.blocks;

import java.util.List;

import mantle.blocks.abstracts.MultiItemBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PlanksItem extends MultiItemBlock
{
    public static final String blockType[] = { "eucalyptus", "sakura", "ghost", "redwood", "blood", "bush", "maple", "silverbell", "purpleheart", "tiger", "willow", "darkwood", "fusewood", "", "", "" };

    public PlanksItem(Block i)
    {
        super(i, "block", "NPlanks", blockType);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    /*    @Override
        public String getUnlocalizedName (ItemStack itemstack)
        {
            return (new StringBuilder()).append(blockType[itemstack.getItemDamage()]).append("NPlanks").toString();
        }*/

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        switch (stack.getItemDamage())
        {
        case 0:
            list.add(StatCollector.translateToLocal("tooltip.tree1"));
            break;
        case 1:
            list.add(StatCollector.translateToLocal("tooltip.tree2"));
            break;
        case 2:
            list.add(StatCollector.translateToLocal("tooltip.tree3"));
            break;
        case 3:
            list.add(StatCollector.translateToLocal("tooltip.tree4"));
            break;
        case 4:
            list.add(StatCollector.translateToLocal("tooltip.tree5"));
            break;
        case 5:
            list.add(StatCollector.translateToLocal("tooltip.tree6"));
            break;
        case 6:
            list.add(StatCollector.translateToLocal("tooltip.tree7"));
            break;
        case 7:
            list.add(StatCollector.translateToLocal("tooltip.tree8"));
            break;
        case 8:
            list.add(StatCollector.translateToLocal("tooltip.tree9"));
            break;
        case 9:
            list.add(StatCollector.translateToLocal("tooltip.tree10"));
            break;
        case 10:
            list.add(StatCollector.translateToLocal("tooltip.tree11"));
            break;
        case 11:
            list.add(StatCollector.translateToLocal("tooltip.nethertree"));
            break;
        case 12:
            list.add(StatCollector.translateToLocal("tooltip.nethertree"));
            list.add(StatCollector.translateToLocal("tooltip.fusewood.log"));
            break;
        }
    }
}
