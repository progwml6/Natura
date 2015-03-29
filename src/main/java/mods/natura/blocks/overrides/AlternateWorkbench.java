package mods.natura.blocks.overrides;

import java.util.List;

import mods.natura.Natura;
import mods.natura.common.NContent;
import mods.natura.gui.NGuiHandler;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AlternateWorkbench extends BlockWorkbench
{

	public AlternateWorkbench()
	{
		super();
	}

	@Override
	public boolean isFullCube()
	{
		return false;
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return this.getMetaFromState(state);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item par1, CreativeTabs tabs, List list)
	{
		for (int i = 0; i < 4; i++) // idk?
			list.add(new ItemStack(par1, 1, i));
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (worldIn.isRemote)
		{
			return true;
		}
		else
		{
			playerIn.openGui(Natura.instance, NGuiHandler.craftingGui, worldIn, pos.getX(), pos.getY(), pos.getZ());
			//player.displayGUIWorkbench(par2, par3, par4);
			return true;
		}
	}
}
