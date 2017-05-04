package com.progwml6.natura.shared.block.clouds;

import java.util.Locale;

import com.progwml6.natura.library.NaturaRegistry;
import com.progwml6.natura.shared.NaturaCommons;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.mantle.block.EnumBlock;

public class BlockCloud extends EnumBlock<BlockCloud.CloudType>
{
    public static PropertyEnum<CloudType> TYPE = PropertyEnum.create("type", CloudType.class);

    public BlockCloud()
    {
        super(NaturaRegistry.cloud, TYPE, CloudType.class);
        this.setCreativeTab(NaturaRegistry.tabWorld);
        this.setHardness(0.3F);
        this.setSoundType(SoundType.CLOTH);
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        if (state.getValue(BlockCloud.TYPE) == BlockCloud.CloudType.SULFUR && entityIn instanceof EntityArrow && !worldIn.isRemote)
        {
            EntityArrow entityarrow = (EntityArrow) entityIn;

            if (entityarrow.isBurning())
            {
                this.explode(worldIn, pos, 1, entityarrow.shootingEntity instanceof EntityLiving ? (EntityLiving) entityarrow.shootingEntity : null);
                worldIn.setBlockToAir(pos);
                return;
            }
        }

        if (entityIn.motionY < 0.0D)
        {
            entityIn.motionY *= 0.005D;
        }

        entityIn.fallDistance = 0.0F;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack itemstack = playerIn.getHeldItem(hand);

        if (state.getValue(BlockCloud.TYPE) == BlockCloud.CloudType.SULFUR && itemstack.getItem() != Items.AIR && itemstack.getItem() == Items.FLINT_AND_STEEL)
        {
            worldIn.setBlockToAir(pos);
            this.explode(worldIn, pos, 1, playerIn);
            return true;
        }

        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn)
    {
    }

    public void explode(World world, BlockPos pos, int size, EntityLivingBase living)
    {
        world.createExplosion(living, pos.getX(), pos.getY(), pos.getZ(), size, true);
    }

    @Override
    public boolean canDropFromExplosion(Explosion par1Explosion)
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Deprecated
    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return blockAccess.getBlockState(pos.offset(side)).getMaterial() == this.blockMaterial ? false : super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isBlockSolid(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
    {
        Material material = worldIn.getBlockState(pos).getMaterial();
        return material == this.blockMaterial ? false : super.isBlockSolid(worldIn, pos, side);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        if (worldIn.getBlockState(pos.down()).getBlock() == NaturaCommons.clouds)
        {
            return null;
        }
        else
        {
            return new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1.0D, pos.getY() + 0.0625D, pos.getZ() + 1.0D);
        }
    }

    public enum CloudType implements IStringSerializable, EnumBlock.IEnumMeta
    {
        WHITE, DARK, ASH, SULFUR;

        public final int meta;

        CloudType()
        {
            this.meta = this.ordinal();
        }

        @Override
        public String getName()
        {
            return this.toString().toLowerCase(Locale.US);
        }

        @Override
        public int getMeta()
        {
            return this.meta;
        }
    }
}
