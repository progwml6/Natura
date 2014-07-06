package mods.natura.items.blocks;

import java.util.List;

import mantle.blocks.abstracts.MultiItemBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class DarkTreeItem extends MultiItemBlock
{
    public static final String blockType[] = { "darkwood", "fusewood" };

    public DarkTreeItem(Block i)
    {
        super(i, "block.log", blockType);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    /*@Override
    public String getUnlocalizedName (ItemStack itemstack)
    {
        if (itemstack.getItemDamage() < blockType.length)
        {
            return (new StringBuilder()).append("block.log.").append(blockType[itemstack.getItemDamage()]).toString();
        }
        return null;
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
            list.add(StatCollector.translateToLocal("tooltip.fusewood.log"));
            break;
        }
    }
}
