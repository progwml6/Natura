package mods.natura.blocks.nether;

import java.util.List;
import java.util.Random;

import mods.natura.common.NContent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NetherGlass extends Block
{
    public PropertyInteger METADATA;

    public NetherGlass()
    {
        super(Material.glass);
        this.setDefaultState(this.blockState.getBaseState().withProperty(METADATA, 0));
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
    public boolean isFullCube ()
    {
        return false;
    }

    @Override
    protected boolean canSilkHarvest ()
    {
        return true;
    }

    @Override
    public int damageDropped (IBlockState state)
    {
        return getMetaFromState(state);
    }

    public int getMetaFromState (IBlockState state)
    {
        return (Integer) state.getValue(METADATA);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered (IBlockAccess worldIn, BlockPos pos, EnumFacing side)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();
        return block != this && super.shouldSideBeRendered(worldIn, pos, side);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox (World worldIn, BlockPos pos, IBlockState state)
    {
        int meta = getMetaFromState(state);
        if (meta == 0)
        {
            return null;
        }
        else if (meta == 1)
        {
            float f = 0.125F;
            return new AxisAlignedBB((double) (float) pos.getX(), (double) pos.getY(), (double) (float) pos.getZ(), (double) ((float) (pos.getX() + 1) - f), (double) ((float) (pos.getY() + 1) - f), (double) (float) (pos.getZ() + 1));
            //return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1 - f, z + 1);
        }
        return super.getCollisionBoundingBox(worldIn, pos, state);
    }

    @Override
    public void onEntityCollidedWithBlock (World world, BlockPos pos, IBlockState state, Entity entity)
    {
        if (entity instanceof EntityLivingBase)
        {
            int meta = getMetaFromState(state);
            if (meta == 0)
            {
                ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 20, 1));
            }
            else if (meta == 1)
            {
                NContent.heatSand.onEntityCollidedWithBlock(world, pos, state, entity);
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
