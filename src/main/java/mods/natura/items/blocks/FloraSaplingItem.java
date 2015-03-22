package mods.natura.items.blocks;

import java.util.List;

import mods.natura.blocks.trees.EnumSaplingType;
import mods.natura.common.NContent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FloraSaplingItem extends NSaplingItem
{
    public static final String blockType[] = {"redwood", "eucalyptus", "bush", "sakura"};

    public FloraSaplingItem(Block block)
    {
        super(block, blockType, NContent.floraSapling);
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        EnumSaplingType saplingType = saplingBlock.getSaplingType(stack.getItemDamage());

        switch (saplingType) {
            case REDWOOD:
                list.add(StatCollector.translateToLocal("tooltip.sapling1"));
                list.add(StatCollector.translateToLocal("tooltip.sapling2"));
                break;
            case EUCALYPTUS:
                list.add(StatCollector.translateToLocal("tooltip.tree1"));
                break;
            case HOPSEED:
                list.add(StatCollector.translateToLocal("tooltip.tree6"));
                break;
            case SAKURA:
                list.add(StatCollector.translateToLocal("tooltip.tree2"));
                break;
        }
    }

}
