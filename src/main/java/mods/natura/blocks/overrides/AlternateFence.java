package mods.natura.blocks.overrides;

import java.util.List;

import mods.natura.client.FenceRender;
import mods.natura.common.NContent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AlternateFence extends BlockFence
{
    public AlternateFence(Material par3Material)
    {
        super("", par3Material);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon (int side, int metadata)
    {
        return NContent.planks.getIcon(side, metadata);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons (IIconRegister iconRegister)
    {
    }

    @Override
    public int damageDropped (int meta)
    {
        return meta;
    }

    public static boolean isIdAFence (int par0)
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks (Item par1, CreativeTabs tabs, List list)
    {
        for (int i = 0; i < NContent.woodTextureNames.length; i++)
            list.add(new ItemStack(par1, 1, i));
    }

    @Override
    public boolean canPlaceTorchOnTop (World world, int x, int y, int z)
    {
        return true;
    }

    @Override
    public int getRenderType ()
    {
        return FenceRender.model;
    }

    @Override
    public boolean canConnectFenceTo (IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        Block l = par1IBlockAccess.getBlock(par2, par3, par4);

        if (l != this)
        {
            if (l == null)
                return false;
            if (l.getMaterial().isOpaque() && l.renderAsNormalBlock())
                return l.getMaterial() != Material.plants;
            return (l instanceof BlockFenceGate);
        }
        else
        {
            return true;
        }
    }
}
