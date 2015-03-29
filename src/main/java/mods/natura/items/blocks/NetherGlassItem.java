package mods.natura.items.blocks;

import java.util.List;

import mantle.blocks.abstracts.MultiItemBlock;
import mods.natura.common.NContent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NetherGlassItem extends MultiItemBlock
{
	public static final String blockType[] = { "soul", "heat" };

	public NetherGlassItem(Block block)
	{
		super(block, "tile.glass", blockType);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	/*    @Override
	    public String getUnlocalizedName (ItemStack itemstack)
	    {
	        return (new StringBuilder()).append("tile.glass.").append(blockType[itemstack.getItemDamage()]).toString();
	    }*/

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		switch (stack.getItemDamage())
		{
		case 0:
			list.add(StatCollector.translateToLocal("tooltip.glass.soul"));
			break;
		}
	}
}
