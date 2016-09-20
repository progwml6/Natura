package com.progwml6.natura.nether.block.glass;

import java.util.Locale;
import java.util.Random;

import javax.annotation.Nullable;

import com.progwml6.natura.library.NaturaRegistry;
import com.progwml6.natura.nether.NaturaNether;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.mantle.block.EnumBlock;

public class BlockNetherGlass extends EnumBlock<BlockNetherGlass.GlassType>
{
    public static PropertyEnum<GlassType> TYPE = PropertyEnum.create("type", GlassType.class);

    protected static final AxisAlignedBB HEAT_SAND_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D);

    public BlockNetherGlass()
    {
        super(Material.GLASS, TYPE, GlassType.class);

        this.setHardness(1.0F);
        this.setResistance(3000F);
        this.setSoundType(SoundType.GLASS);
        this.setCreativeTab(NaturaRegistry.tabWorld);
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    protected boolean canSilkHarvest()
    {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @SuppressWarnings("deprecation")
    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        Block block = blockAccess.getBlockState(pos.offset(side)).getBlock();

        return block == this ? false : super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

    @Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
    {
        if (blockState.getValue(TYPE) == GlassType.SOUL)
        {
            return NULL_AABB;
        }
        else
        {
            return HEAT_SAND_AABB;
        }
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        if (entityIn instanceof EntityLivingBase)
        {
            if (state.getValue(TYPE) == GlassType.SOUL)
            {
                ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20, 1));
            }
            else if (state.getValue(TYPE) == GlassType.HEAT)
            {
                NaturaNether.netherHeatSand.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
            }
        }
    }

    public enum GlassType implements IStringSerializable, EnumBlock.IEnumMeta
    {
        SOUL, HEAT;

        public final int meta;

        GlassType()
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
