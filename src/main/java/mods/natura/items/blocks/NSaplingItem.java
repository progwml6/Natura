package mods.natura.items.blocks;

import java.util.List;

import mantle.blocks.abstracts.MultiItemBlock;
import mods.natura.blocks.trees.EnumSaplingType;
import mods.natura.blocks.trees.NSaplingBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class NSaplingItem extends MultiItemBlock
{
	private Block bID;

	protected NSaplingBlock saplingBlock;

	private static final String unlocalizedName = "block.sapling";

	public NSaplingItem(Block block, String[] blockType, NSaplingBlock saplingBlock)
	{
		super(block, unlocalizedName, blockType);
		setMaxDamage(0);
		setHasSubtypes(true);
		this.bID = block;
		this.saplingBlock = saplingBlock;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		int meta = itemstack.getItemDamage();
		EnumSaplingType saplingType = saplingBlock.getSaplingType(meta);
		return (new StringBuilder()).append(unlocalizedName).append(".").append(saplingType).toString();
	}

	public abstract void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4);

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		IBlockState iblockstate = worldIn.getBlockState(pos);
		Block blockID = iblockstate.getBlock();

		if (blockID == Blocks.snow_layer && ((Integer) iblockstate.getValue(BlockSnow.LAYERS)).intValue() < 1)
		{
			side = EnumFacing.UP;
		}
		else if (!blockID.isReplaceable(worldIn, pos))
		{
			pos = pos.offset(side);
		}
		//else if (blockID != Blocks.vine && blockID != Blocks.tallgrass && blockID != Blocks.deadbush && (blockID == null || !blockID.isReplaceable(world, x, y, z)))

		if (stack.getItemDamage() == 5)
		{
			Block block = worldIn.getBlockState(pos.up()).getBlock();//worldIn.getBlock(x, y + 1, z);
			if (block == null || worldIn.isAirBlock(pos.up()))
				return false;
		}
		else
		{
			Block block = worldIn.getBlockState(pos.down()).getBlock();//worldIn.getBlock(x, y - 1, z);
			if (block == null || worldIn.isAirBlock(pos.down()))
				return false;
		}

		if (stack.stackSize == 0)
		{
			return false;
		}
		else if (!playerIn.canPlayerEdit(pos, side, stack))
		{
			return false;
		}
		else if (pos.getY() == 255 && this.bID.getMaterial().isSolid())
		{
			return false;
		}
		else if (worldIn.canBlockBePlaced(this.bID, pos, false, side, (Entity) null, stack))
		{
			Block block = this.bID;
			int i = this.getMetadata(stack.getItemDamage());
			IBlockState iblockstate1 = this.bID.onBlockPlaced(worldIn, pos, side, hitX, hitY, hitZ, i, playerIn);

			if (placeBlockAt(stack, playerIn, worldIn, pos, side, hitX, hitY, hitZ, iblockstate1))
			{
				worldIn.playSoundEffect(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, block.stepSound.getBreakSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getFrequency() * 0.8F);
				--stack.stackSize;
			}

			return true;
		}
		else
		{
			return false;
		}
	}
}
