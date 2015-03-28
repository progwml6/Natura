package mods.natura.blocks.trees;

import java.util.List;
import java.util.Random;

import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TreeBlock extends BlockLog
{
	public String[] textureNames = new String[] { "eucalyptus_bark", "sakura_bark", "ghostwood_bark", "hopseed_bark", "eucalyptus_heart", "sakura_heart", "ghostwood_heart", "hopseed_heart" };

	public TreeBlock()
	{
		super();
		this.setHardness(1.5F);
		this.setResistance(5F);
		this.setStepSound(Block.soundTypeWood);
		Blocks.fire.setFireInfo(this, 5, 20);
		this.setCreativeTab(NaturaTab.tab);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(this);
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return this.getMetaFromState(state) % 4;
	}

	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		return this.getMetaFromState(world.getBlockState(pos)) % 4 != 2 ? this.getFlammability(world, pos, face) : 0;
	}

	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		return this.getMetaFromState(world.getBlockState(pos)) % 4 != 2 ? this.getFireSpreadSpeed(world, pos, face) : 0;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (int i = 0; i < 4; i++)
			par3List.add(new ItemStack(par1, 1, i));
	}
}
