package mods.natura.blocks.overrides;

import java.util.List;

import mods.natura.common.NContent;
import net.minecraft.block.BlockBookshelf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AlternateBookshelf extends BlockBookshelf
{
	public AlternateBookshelf()
	{
		super();
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return this.getMetaFromState(state);
	}

	@Override
	public boolean isFullCube()
	{
		return false;
	}

	@Override
	public float getEnchantPowerBonus(World world, BlockPos pos)
	{
		return 1;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item par1, CreativeTabs tabs, List list)
	{
		for (int i = 0; i < 4; i++) // idk?
			list.add(new ItemStack(par1, 1, i));
	}

}
