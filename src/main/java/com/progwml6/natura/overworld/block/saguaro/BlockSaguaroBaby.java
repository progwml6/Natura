package com.progwml6.natura.overworld.block.saguaro;

import java.util.Random;

import com.progwml6.natura.library.NaturaRegistry;
import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.world.worldgen.saguaro.SaguaroGenerator;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSaguaroBaby extends Block implements IPlantable
{
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 1);

    protected static final AxisAlignedBB SAGUARO_COLLISION_AABB = new AxisAlignedBB(0.325D, 0.0D, 0.325D, 0.675D, 0.675D, 0.675D);

    protected static final AxisAlignedBB SAGUARO_BABY_AABB = new AxisAlignedBB(0.325D, 0.0D, 0.325D, 0.675D, 0.5D, 0.675D);

    public BlockSaguaroBaby()
    {
        super(Material.CACTUS);

        this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));

        this.setSoundType(SoundType.CLOTH);
        this.setHardness(0.3f);
        this.setTickRandomly(true);
        this.setCreativeTab(NaturaRegistry.tabWorld);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return SAGUARO_COLLISION_AABB;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return SAGUARO_BABY_AABB;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        int age = state.getValue(AGE).intValue();

        if (age == 0 && rand.nextInt(200) == 0)
        {
            worldIn.setBlockState(pos, state.withProperty(AGE, age + 1), 3);
        }
        else if (age == 1 && rand.nextInt(200) == 0)
        {
            SaguaroGenerator gen = new SaguaroGenerator(NaturaOverworld.saguaro.getDefaultState(), true, true);
            gen.generateSaguaro(rand, worldIn, pos);
        }
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return super.canPlaceBlockAt(worldIn, pos) ? this.canBlockStay(worldIn, pos) : false;
    }

    /**
     * Called when a neighboring block was changed and marks that this state should perform any checks during a neighbor
     * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
     * block, etc.
     */
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn)
    {
        if (!this.canBlockStay(worldIn, pos))
        {
            worldIn.destroyBlock(pos, true);
        }
    }

    public boolean canBlockStay(World worldIn, BlockPos pos)
    {
        IBlockState state = worldIn.getBlockState(pos.down());
        Block block = state.getBlock();

        return block == this || block == Blocks.SAND || block == null;
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        if (!(entityIn instanceof EntityItem))
        {
            entityIn.attackEntityFrom(DamageSource.cactus, 1.0F);
        }
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(AGE, meta);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state)
    {
        return (state.getValue(AGE)).intValue();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] { AGE });
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
    {
        return EnumPlantType.Desert;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos)
    {
        return getDefaultState();
    }
}
