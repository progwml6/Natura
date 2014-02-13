package mods.natura.blocks.nether;

import java.util.List;
import java.util.Random;

import mods.natura.common.NContent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NetherGlass extends Block
{
    public NetherGlass()
    {
        super(Material.glass);
    }

    @Override
    public int quantityDropped (Random par1Random)
    {
        return 0;
    }

    @Override
    public boolean isOpaqueCube ()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock ()
    {
        return false;
    }

    @Override
    protected boolean canSilkHarvest ()
    {
        return true;
    }
    
    @Override
    public int damageDropped (int metadata)
    {
        return metadata;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered (IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        Block i1 = par1IBlockAccess.getBlock(par2, par3, par4);
        return i1 == this ? false : super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass ()
    {
        return 1;
    }

    @SideOnly(Side.CLIENT)
    public IIcon[] icons;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons (IIconRegister par1IconRegister)
    {
        icons = new IIcon[4];
        icons[0] = par1IconRegister.registerIcon("natura:glass_soul");
        icons[1] = par1IconRegister.registerIcon("natura:glass_heat");
        icons[2] = par1IconRegister.registerIcon("natura:glass_soul_item");
        icons[3] = par1IconRegister.registerIcon("natura:glass_heat_item");
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getBlockTexture (IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        int meta = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        if (meta < 1)
            return icons[0];
        return icons[1];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon (int side, int meta)
    {
        if (meta < 1)
            return icons[2];
        return icons[3];
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool (World world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta == 0)
        {
            return null;
        }
        else if (meta == 1)
        {
            float f = 0.125F;
            return AxisAlignedBB.getAABBPool().getAABB((double) x, (double) y, (double) z, (double) (x + 1), (double) ((float) (y + 1) - f), (double) (z + 1));
        }
        return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public void onEntityCollidedWithBlock (World world, int x, int y, int z, Entity entity)
    {
        if (entity instanceof EntityLivingBase)
        {
            int meta = world.getBlockMetadata(x, y, z);
            if (meta == 0)
            {
                ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 20, 1));
            }
            else if (meta == 1)
            {
                NContent.heatSand.onEntityCollidedWithBlock(world, x, y, z, entity);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks (Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int var4 = 0; var4 < 2; ++var4)
        {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }
}
