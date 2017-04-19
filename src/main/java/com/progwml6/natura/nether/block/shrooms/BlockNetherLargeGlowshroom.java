package com.progwml6.natura.nether.block.shrooms;

import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.progwml6.natura.nether.NaturaNether;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockNetherLargeGlowshroom extends Block
{
    public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.<EnumType> create("variant", EnumType.class);

    private final Block smallState;

    private final int glowshroomMeta;

    public BlockNetherLargeGlowshroom(Block smallStateIn, int glowshroomMetaIn)
    {
        super(Material.WOOD);

        this.setLightLevel(0.625f);

        this.smallState = smallStateIn;
        this.glowshroomMeta = glowshroomMetaIn;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(Random random)
    {
        return Math.max(0, random.nextInt(10) - 7);
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return this.glowshroomMeta;
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    @Override
    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(this.smallState);
    }

    @Nonnull
    @Override
    public ItemStack getPickBlock(@Nonnull IBlockState state, RayTraceResult target, @Nonnull World world, @Nonnull BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(Item.getItemFromBlock(this.smallState), 1, this.glowshroomMeta);
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(VARIANT, EnumType.byMetadata(meta));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(VARIANT).getMeta();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, VARIANT);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
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
    @Deprecated
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        Block block = blockAccess.getBlockState(pos.offset(side)).getBlock();

        if (block == NaturaNether.netherLargeBlueGlowshroom || block == NaturaNether.netherLargePurpleGlowshroom)
        {
            return false;
        }
        else
        {
            return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
    {
        if (worldIn.getBlockState(pos.up()).getBlock() instanceof BlockNetherLargeGlowshroom)
        {
            return null;
        }
        else
        {
            return new AxisAlignedBB(pos.getX(), pos.getY() + 0.9375, pos.getZ(), pos.getX() + 1.0D, pos.getY() + 1.0D, pos.getZ() + 1.0D);
        }
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        if (entityIn.motionY < 0)
        {
            entityIn.motionY *= 0.25F;
        }

        entityIn.fallDistance -= 0.25f;

        if (entityIn.motionY == 0f)
        {
            entityIn.motionX *= 0.25f;
            entityIn.motionZ *= 0.25f;
        }

        if (entityIn instanceof EntityLiving)
        {
            EntityLiving living = (EntityLiving) entityIn;

            living.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 100, 0));
        }
    }

    public static enum EnumType implements IStringSerializable
    {
        NORTH_WEST(1, "north_west"), NORTH(2, "north"), NORTH_EAST(3, "north_east"), WEST(4, "west"), CENTER(5, "center"), EAST(6, "east"), SOUTH_WEST(7, "south_west"), SOUTH(8, "south"), SOUTH_EAST(9, "south_east"), STEM(10, "stem"), ALL_INSIDE(0, "all_inside"), ALL_OUTSIDE(14, "all_outside"), ALL_STEM(15, "all_stem");

        private static final EnumType[] META_LOOKUP = new EnumType[16];

        private final int meta;

        private final String name;

        private EnumType(int meta, String name)
        {
            this.meta = meta;
            this.name = name;
        }

        public int getMeta()
        {
            return this.meta;
        }

        @Override
        public String toString()
        {
            return this.name;
        }

        public static EnumType byMetadata(int meta)
        {
            if (meta < 0 || meta >= META_LOOKUP.length)
            {
                meta = 0;
            }

            EnumType enumtype = META_LOOKUP[meta];
            return enumtype == null ? META_LOOKUP[0] : enumtype;
        }

        @Override
        public String getName()
        {
            return this.name;
        }

        static
        {
            for (EnumType enumtype : values())
            {
                META_LOOKUP[enumtype.getMeta()] = enumtype;
            }
        }
    }
}
