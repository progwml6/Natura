package mods.natura.items.blocks;

import java.util.List;

import mantle.blocks.abstracts.MultiItemBlock;
import mods.natura.common.NContent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BerryBushItem extends MultiItemBlock
{
	public static final String blockType[] = { "rasp", "blue", "black", "geo", "rasp", "blue", "black", "geo", "rasp", "blue", "black", "geo", "rasp", "blue", "black", "geo" };

	public BerryBushItem(Block block)
	{
		super(block, "block", "berryBush", blockType);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int meta)
	{
		return meta % 4;
	}

	/* Place bushes on dirt, grass, or other bushes only */
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (side != EnumFacing.UP)
			return false;

		else if (playerIn.canPlayerEdit(pos, side, stack) && playerIn.canPlayerEdit(pos.up(), side, stack))
		{
			Block block = worldIn.getBlockState(pos).getBlock();

			if (block != null && block.canSustainPlant(worldIn, pos, EnumFacing.UP, NContent.berryBush) && worldIn.isAirBlock(pos.up()))
			{
				worldIn.setBlockState(pos.up(), NContent.berryBush.getDefaultState(), 3);//, stack.getItemDamage() % 4, 3);
				if (!playerIn.capabilities.isCreativeMode)
					stack.stackSize--;
				if (!worldIn.isRemote)
					worldIn.playAuxSFX(2001, pos, Block.getIdFromBlock(NContent.berryBush));
				return true;
			}
			else
				return false;
		}
		else
			return false;
	}

	/* Block name in inventory */
	/* @Override
	 public String getUnlocalizedName (ItemStack itemstack)
	 {
	     return (new StringBuilder()).append("block.").append(blockType[itemstack.getItemDamage()]).append("berryBush").toString();
	 }*/

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		switch (stack.getItemDamage() % 4)
		{
		case 0:
			list.add(StatCollector.translateToLocal("tooltip.berrybush1"));
			list.add(StatCollector.translateToLocal("tooltip.berrybush2"));
			break;
		case 1:
			list.add(StatCollector.translateToLocal("tooltip.berrybush3"));
			list.add(StatCollector.translateToLocal("tooltip.berrybush4"));
			break;
		case 2:
			list.add(StatCollector.translateToLocal("tooltip.berrybush5"));
			list.add(StatCollector.translateToLocal("tooltip.berrybush6"));
			break;
		case 3:
			list.add(StatCollector.translateToLocal("tooltip.berrybush7"));
			list.add(StatCollector.translateToLocal("tooltip.berrybush8"));
			break;
		}
	}
}
