package mods.natura.items.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CloudItem extends ItemBlock
{
    public static final String blockType[] = { "normal", "dark", "ash", "sulfur" };

    public CloudItem(Block i)
    {
        super(i);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata (int md)
    {
        return md;
    }

    @Override
    public String getUnlocalizedName (ItemStack itemstack)
    {
        return (new StringBuilder()).append(blockType[itemstack.getItemDamage()]).append("cloud").toString();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        list.add(StatCollector.translateToLocal("tooltip.cloud1"));
        switch (stack.getItemDamage() % 4)
        {
        case 0:
            list.add(StatCollector.translateToLocal("tooltip.cloud2"));
            break;
        case 1:
            list.add(StatCollector.translateToLocal("tooltip.cloud3"));
            break;
        case 2:
            list.add(StatCollector.translateToLocal("tooltip.cloud4"));
            break;
        case 3:
            list.add(StatCollector.translateToLocal("tooltip.cloud5"));
            break;
        }
    }
}
