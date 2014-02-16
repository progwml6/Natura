package mods.natura.items.blocks;

import java.util.List;

import mods.natura.common.NContent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.StatCollector;

public class GlowshroomItem extends ItemBlock
{
    public static final String blockType[] = { "green", "purple", "blue" };

    public GlowshroomItem(Block i)
    {
        super(i);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata (int meta)
    {
        return meta;
    }

    @Override
	public IIcon getIconFromDamage (int i)
    {
        return NContent.glowshroom.getIcon(0, i);
    }

    @Override
	public String getUnlocalizedName (ItemStack itemstack)
    {
        return (new StringBuilder()).append("block.glowshroom.").append(blockType[itemstack.getItemDamage()]).toString();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        list.add(StatCollector.translateToLocal("tooltip.shroom"));
    }
}
