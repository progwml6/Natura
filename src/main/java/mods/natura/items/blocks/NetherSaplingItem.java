package mods.natura.items.blocks;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import mods.natura.blocks.trees.EnumSaplingType;
import mods.natura.common.NContent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.List;

public class NetherSaplingItem extends NSaplingItem {

    public static final String blockType[] = {"ghost", "blood", "darkwood", "fusewood"};

    public NetherSaplingItem(Block block) {
        super(block, blockType, NContent.netherSapling);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        EnumSaplingType saplingType = saplingBlock.getSaplingType(stack.getItemDamage());

        switch (saplingType) {
        case GHOSTWOOD:
            list.add(StatCollector.translateToLocal("tooltip.tree3"));
            break;
        case BLOODWOOD:
            list.add(StatCollector.translateToLocal("tooltip.sapling3"));
            list.add(StatCollector.translateToLocal("tooltip.sapling4"));
            break;
        case DARKWOOD:
            list.add(StatCollector.translateToLocal("tooltip.sapling5"));
            break;
        case FUSEWOOD:
            list.add(StatCollector.translateToLocal("tooltip.fusewood.log"));
            break;
        }
    }

}
