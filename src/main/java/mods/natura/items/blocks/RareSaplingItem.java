package mods.natura.items.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.natura.blocks.trees.EnumSaplingType;
import mods.natura.common.NContent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.List;

public class RareSaplingItem extends NSaplingItem {

    public static final String blockType[] = { "maple", "silverbell", "purpleheart", "tiger", "willow" };

    public RareSaplingItem(Block block) {
        super(block, blockType, NContent.rareSapling);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        EnumSaplingType saplingType = saplingBlock.getSaplingType(stack.getItemDamage());
        switch (saplingType)
        {
            case MAPLE:
                list.add(StatCollector.translateToLocal("tooltip.tree7"));
                break;
            case SILVERBELL:
                list.add(StatCollector.translateToLocal("tooltip.tree8"));
                break;
            case PURPLEHEART:
                list.add(StatCollector.translateToLocal("tooltip.tree9"));
                break;
            case TIGER:
                list.add(StatCollector.translateToLocal("tooltip.tree10"));
                break;
            case WILLOW:
                list.add(StatCollector.translateToLocal("tooltip.tree11"));
                break;
        }
    }
}
