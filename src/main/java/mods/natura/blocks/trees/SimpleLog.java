package mods.natura.blocks.trees;

import java.util.List;

import mods.natura.common.NaturaTab;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SimpleLog extends Planks
{
	public String[] textureNames = new String[] { "redwood_bark", "redwood_heart", "redwood_root" };

	public SimpleLog()
	{
		super();
		this.setCreativeTab(NaturaTab.tab);
	}

	@Override
	public boolean canSustainLeaves(IBlockAccess world, BlockPos pos)
	{
		return true;
	}

	@Override
	public boolean isWood(IBlockAccess world, BlockPos pos)
	{
		return true;
	}

	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		return this.getFlammability(world, pos, face);
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		return this.getFireSpreadSpeed(world, pos, face);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (int i = 0; i < 3; i++)
			par3List.add(new ItemStack(par1, 1, i));
	}
}
