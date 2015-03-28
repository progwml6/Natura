package mods.natura.blocks.trees;

import java.util.ArrayList;
import java.util.List;

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

import com.google.common.collect.Lists;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WillowBlock extends BlockLog
{
    public String[] textureNames = new String[] { "willow_bark", "willow_heart" };

    public WillowBlock()
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
        ArrayList<ItemStack> i = Lists.newArrayList();
        i.add(new ItemStack(this));
        return i;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks (Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i < textureNames.length / 2; i++)
            par3List.add(new ItemStack(par1, 1, i));
    }
}
