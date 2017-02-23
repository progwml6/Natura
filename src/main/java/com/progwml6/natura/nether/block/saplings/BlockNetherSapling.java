package com.progwml6.natura.nether.block.saplings;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.annotation.Nonnull;

import com.progwml6.natura.Natura;
import com.progwml6.natura.library.NaturaRegistry;
import com.progwml6.natura.nether.NaturaNether;
import com.progwml6.natura.nether.block.leaves.BlockNetherLeaves;
import com.progwml6.natura.nether.block.leaves.BlockNetherLeaves2;
import com.progwml6.natura.nether.block.logs.BlockNetherLog;
import com.progwml6.natura.world.worldgen.trees.BaseTreeGenerator;
import com.progwml6.natura.world.worldgen.trees.nether.DarkwoodTreeGenerator;
import com.progwml6.natura.world.worldgen.trees.nether.FusewoodTreeGenerator;
import com.progwml6.natura.world.worldgen.trees.nether.GhostwoodTreeGenerator;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import slimeknights.mantle.block.EnumBlock;

public class BlockNetherSapling extends BlockSapling
{
    public static PropertyEnum<SaplingType> FOLIAGE = PropertyEnum.create("foliage", SaplingType.class);

    public BlockNetherSapling()
    {
        this.setCreativeTab(NaturaRegistry.tabWorld);
        this.setDefaultState(this.blockState.getBaseState());
        this.setSoundType(SoundType.PLANT);
        this.setHardness(0.0F);
    }

    @Override
    public void getSubBlocks(@Nonnull Item itemIn, CreativeTabs tab, @Nonnull List<ItemStack> list)
    {
        for (SaplingType type : SaplingType.values())
        {
            list.add(new ItemStack(this, 1, this.getMetaFromState(this.getDefaultState().withProperty(FOLIAGE, type))));
        }
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState()
    {
        // TYPE has to be included because of the BlockSapling constructor.. but it's never used.
        return new BlockStateContainer(this, FOLIAGE, STAGE, TYPE);
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        if (meta < 0 || meta >= SaplingType.values().length)
        {
            meta = 0;
        }

        SaplingType sapling = SaplingType.values()[meta];

        return this.getDefaultState().withProperty(FOLIAGE, sapling);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FOLIAGE).ordinal();
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return this.getMetaFromState(state);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        Block block = worldIn.getBlockState(pos).getBlock();

        if (block == null || block.isReplaceable(worldIn, pos))
        {
            IBlockState soilBlockState = worldIn.getBlockState(pos.down());
            Block netherSoil = soilBlockState.getBlock();

            if (netherSoil != null)
            {
                if (this.canGrowOnBlock(netherSoil) || netherSoil.canSustainPlant(soilBlockState, worldIn, pos.down(), EnumFacing.UP, this))
                {
                    return true;
                }
            }

            IBlockState ceilingBlockState = worldIn.getBlockState(pos.up());
            Block netherCeiling = ceilingBlockState.getBlock();

            if (netherCeiling != null)
            {
                if (this.canGrowOnBlock(netherCeiling) || netherCeiling.canSustainPlant(ceilingBlockState, worldIn, pos.up(), EnumFacing.DOWN, this))
                {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
    {
        switch (state.getValue(FOLIAGE))
        {
        case BLOODWOOD:
            IBlockState ceilingBlockState = worldIn.getBlockState(pos.up());
            Block netherCeiling = ceilingBlockState.getBlock();

            if (netherCeiling == null)
            {
                return false;
            }

            return this.canGrowOnBlock(netherCeiling) || netherCeiling.canSustainPlant(ceilingBlockState, worldIn, pos.up(), EnumFacing.DOWN, this);
        case DARKWOOD:
        case FUSEWOOD:
        case GHOSTWOOD:
            IBlockState soilBlockState = worldIn.getBlockState(pos.down());
            Block netherSoil = soilBlockState.getBlock();

            if (netherSoil == null)
            {
                return false;
            }

            return this.canGrowOnBlock(netherSoil) || netherSoil.canSustainPlant(soilBlockState, worldIn, pos.down(), EnumFacing.UP, this);
        default:
            return true;
        }
    }

    public boolean canGrowOnBlock(Block block)
    {
        return block == Blocks.SOUL_SAND || block == Blocks.NETHERRACK || block == NaturaNether.netherTaintedSoil;
    }

    @Nonnull
    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
    {
        return EnumPlantType.Nether;
    }

    @Override
    public boolean isReplaceable(IBlockAccess worldIn, @Nonnull BlockPos pos)
    {
        return false;
    }

    @Nonnull
    @Override
    public ItemStack getPickBlock(@Nonnull IBlockState state, RayTraceResult target, @Nonnull World world, @Nonnull BlockPos pos, EntityPlayer player)
    {
        IBlockState iblockstate = world.getBlockState(pos);
        int meta = iblockstate.getBlock().getMetaFromState(iblockstate);
        return new ItemStack(Item.getItemFromBlock(this), 1, meta);
    }

    @Override
    public void generateTree(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random rand)
    {
        if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(worldIn, rand, pos))
        {
            return;
        }
        BaseTreeGenerator gen = new BaseTreeGenerator();

        IBlockState log;
        IBlockState leaves;

        // Only used by darkwood.
        IBlockState flowering;
        IBlockState fruiting;

        switch (state.getValue(FOLIAGE))
        {
        case BLOODWOOD:
            break;
        case DARKWOOD:
            log = NaturaNether.netherLog.getDefaultState().withProperty(BlockNetherLog.TYPE, BlockNetherLog.LogType.DARKWOOD);
            flowering = NaturaNether.netherLeaves2.getDefaultState().withProperty(BlockNetherLeaves2.TYPE, BlockNetherLeaves2.LeavesType.DARKWOOD_FLOWERING);
            fruiting = NaturaNether.netherLeaves2.getDefaultState().withProperty(BlockNetherLeaves2.TYPE, BlockNetherLeaves2.LeavesType.DARKWOOD_FRUIT);
            leaves = NaturaNether.netherLeaves2.getDefaultState().withProperty(BlockNetherLeaves2.TYPE, BlockNetherLeaves2.LeavesType.DARKWOOD);

            gen = new DarkwoodTreeGenerator(3, log, leaves, flowering, fruiting);

            break;
        case FUSEWOOD:
            log = NaturaNether.netherLog.getDefaultState().withProperty(BlockNetherLog.TYPE, BlockNetherLog.LogType.FUSEWOOD);
            leaves = NaturaNether.netherLeaves.getDefaultState().withProperty(BlockNetherLeaves.TYPE, BlockNetherLeaves.LeavesType.FUSEWOOD);

            gen = new FusewoodTreeGenerator(3, log, leaves, false);

            break;
        case GHOSTWOOD:
            log = NaturaNether.netherLog.getDefaultState().withProperty(BlockNetherLog.TYPE, BlockNetherLog.LogType.GHOSTWOOD);
            leaves = NaturaNether.netherLeaves.getDefaultState().withProperty(BlockNetherLeaves.TYPE, BlockNetherLeaves.LeavesType.GHOSTWOOD);

            gen = new GhostwoodTreeGenerator(log, leaves, false);

            break;
        default:
            Natura.log.warn("BlockNetherSapling Warning: Invalid sapling meta/foliage, " + state.getValue(FOLIAGE) + ". Please report!");
            break;

        }

        // replace sapling with air
        worldIn.setBlockToAir(pos);

        // try generating
        gen.generateTree(rand, worldIn, pos);

        // check if it generated
        if (worldIn.isAirBlock(pos))
        {
            // nope, set sapling again
            worldIn.setBlockState(pos, state, 4);
        }
    }

    public enum SaplingType implements IStringSerializable, EnumBlock.IEnumMeta
    {
        GHOSTWOOD, BLOODWOOD, FUSEWOOD, DARKWOOD;

        public final int meta;

        SaplingType()
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
