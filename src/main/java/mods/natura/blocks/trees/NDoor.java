package mods.natura.blocks.trees;

import java.util.List;
import java.util.Random;

import mods.natura.common.NContent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NDoor extends BlockDoor
{
    private int meta;
    public IIcon[] icons;
    public final String doorName;

    public NDoor(Material material, int md, String doorName)
    {
        super(material);
        this.doorName = doorName;
        float f = 0.5F;
        float f1 = 1.0F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
        meta = md;
        this.setHardness(3F);
        this.setStepSound(Block.soundTypeWood);
        this.disableStats();
    }

    public int idDropped (int i, Random random, int j)
    {
        return (i & 8) != 0 ? 0 : Item.getIdFromItem(NContent.doorItem);
    }

    @Override
    public int damageDropped (int par1)
    {
        return meta;
    }

    @Override
    public void registerBlockIcons (IIconRegister iconRegister)
    {
        this.icons = new IIcon[2];

        this.icons[0] = iconRegister.registerIcon("natura:" + doorName + "_door_bottom");
        this.icons[1] = iconRegister.registerIcon("natura:" + doorName + "_door_top");
    }

    @SideOnly(Side.CLIENT)
    /**
     * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
     */
    @Override
    public IIcon getIcon (IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        if (par5 != 1 && par5 != 0)
        {
            int i1 = this.func_150012_g(par1IBlockAccess, par2, par3, par4);
            int j1 = i1 & 3;
            boolean flag2 = (i1 & 8) != 0;

            return this.icons[(flag2 ? 1 : 0)];
        }
        else
        {
            return this.icons[0];
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon (int par1, int par2)
    {
        return this.icons[0];
    }

    @Override
    public ItemStack getPickBlock (MovingObjectPosition target, World world, int x, int y, int z)
    {
        return new ItemStack(NContent.doorItem, 1, meta);
    }

    public void getSubBlocks (int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
    }

    @Override
    public int getRenderType ()
    {
        return 7;
    }
}
