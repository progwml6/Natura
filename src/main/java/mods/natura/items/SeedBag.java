package mods.natura.items;

import java.util.List;

import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SeedBag extends Item
{
	Block crop;

	int cropMetadata;

	String textureName;

	public SeedBag(Block block, int cMD, String texture)
	{
		super();
		crop = block;
		cropMetadata = cMD;
		textureName = texture;
		this.setCreativeTab(NaturaTab.tab);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (side != EnumFacing.UP)
			return false;

		boolean planted = false;
		for (int posX = pos.getX() - 1; posX <= pos.getX() + 1; posX++)
		{
			for (int posZ = pos.getZ() - 1; posZ <= pos.getZ() + 1; posZ++)
			{
				if (playerIn.canPlayerEdit(new BlockPos(posX, pos.getY(), posZ), side, stack) && playerIn.canPlayerEdit(new BlockPos(posX, pos.getY() + 1, posZ), side, stack))
				{
					Block block = worldIn.getBlockState(new BlockPos(posX, pos.getY(), posZ)).getBlock();

					if (block != null && block.canSustainPlant(worldIn, new BlockPos(posX, pos.getY(), posZ), EnumFacing.UP, (IPlantable) crop) && worldIn.isAirBlock(new BlockPos(posX, pos.getY() + 1, posZ)))
					{
						worldIn.setBlockState(new BlockPos(posX, pos.getY() + 1, posZ), crop.getDefaultState(), 3);//, cropMetadata, 3);
						planted = true;
					}
				}
			}
		}
		if (planted)
		{
			if (!playerIn.capabilities.isCreativeMode)
				stack.stackSize--;
			if (!worldIn.isRemote)
				worldIn.playAuxSFX(2001, pos, Block.getIdFromBlock(crop));
		}
		return planted;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		list.add(StatCollector.translateToLocal("tooltip.seedbag"));
	}
}
