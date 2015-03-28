package mods.natura.blocks.trees;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class OverworldTreeBlock extends BlockLog
{
	public String[] textureNames = new String[] { "maple_bark", "maple_heart", "silverbell_bark", "silverbell_heart", "purpleheart_bark", "purpleheart_heart", "tiger_bark", "tiger_heart" };

	public OverworldTreeBlock()
	{
		super();
		this.setHardness(2.0F);
		this.setStepSound(Block.soundTypeWood);
		Blocks.fire.setFireInfo(this, 5, 20);
		this.setCreativeTab(NaturaTab.tab);
	}

	@Override
	public ArrayList<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		ArrayList<ItemStack> ret = Lists.newArrayList();
		ret.add(new ItemStack(this, 1, this.getMetaFromState(state) % 4));
		return ret;
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
		for (int i = 0; i < textureNames.length / 2; i++)
			par3List.add(new ItemStack(par1, 1, i));
	}
}
