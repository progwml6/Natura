package mods.natura.blocks.trees;

import java.util.List;

import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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

public class Planks extends Block
{
	public static String[] textureNames = new String[] { "eucalyptus", "sakura", "ghostwood", "redwood", "bloodwood", "hopseed", "maple", "silverbell", "purpleheart", "tiger", "willow", "darkwood",
			"fusewood" };

	public Planks()
	{
		super(Material.wood);
		Blocks.fire.setFireInfo(this, 5, 20);
		this.setHardness(2.0f);
		this.setCreativeTab(NaturaTab.tab);
		this.setStepSound(Block.soundTypeWood);
	}

	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		return this.getFlammability(world, pos, face);
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		if (this.getMetaFromState(world.getBlockState(pos)) == 2 || this.getMetaFromState(world.getBlockState(pos)) == 4 || this.getMetaFromState(world.getBlockState(pos)) > 10)
			return 0;
		return this.getFireSpreadSpeed(world, pos, face);
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return this.getMetaFromState(state) % 4;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (int i = 0; i < textureNames.length; i++)
			par3List.add(new ItemStack(par1, 1, i));
	}
}
