package mods.natura.blocks.overrides;

import java.util.List;

import mods.natura.blocks.trees.Planks;
import mods.natura.client.FenceRender;
import mods.natura.common.NContent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AlternateFence extends BlockFence
{
    public AlternateFence(int par1, Material par3Material)
    {
        super(par1, "", par3Material);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon (int side, int metadata)
    {
        return NContent.planks.getIcon(side, metadata);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons (IconRegister iconRegister)
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
    public void getSubBlocks (int par1, CreativeTabs tabs, List list)
    {
        for (int i = 0; i < NContent.woodTextureNames.length; i++)
            list.add(new ItemStack(par1, 1, i));
    }

    public boolean canPlaceTorchOnTop (World world, int x, int y, int z)
    {
        return true;
    }

    public int getRenderType ()
    {
        return FenceRender.model;
    }
}
