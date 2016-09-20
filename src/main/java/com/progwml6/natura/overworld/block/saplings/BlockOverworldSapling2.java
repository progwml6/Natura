package com.progwml6.natura.overworld.block.saplings;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.annotation.Nonnull;

import com.progwml6.natura.Natura;
import com.progwml6.natura.library.NaturaRegistry;
import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.overworld.block.logs.BlockOverworldLog2;
import com.progwml6.natura.world.worldgen.trees.BaseTreeGenerator;
import com.progwml6.natura.world.worldgen.trees.overworld.EucalyptusTreeGenerator;
import com.progwml6.natura.world.worldgen.trees.overworld.HopseedTreeGenerator;
import com.progwml6.natura.world.worldgen.trees.overworld.WillowTreeGenerator;

import net.minecraft.block.BlockSapling;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import slimeknights.mantle.block.EnumBlock;

public class BlockOverworldSapling2 extends BlockSapling
{
    public static PropertyEnum<SaplingType> FOLIAGE = PropertyEnum.create("foliage", SaplingType.class);

    public BlockOverworldSapling2()
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
    public boolean isReplaceable(IBlockAccess worldIn, @Nonnull BlockPos pos)
    {
        return false;
    }

    @Nonnull
    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
    {
        return EnumPlantType.Plains;
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

        switch (state.getValue(FOLIAGE))
        {
        case WILLOW:
            log = NaturaOverworld.overworldLog2.getDefaultState().withProperty(BlockOverworldLog2.TYPE, BlockOverworldLog2.LogType.WILLOW);
            leaves = NaturaOverworld.overworldLeaves2.getDefaultState().withProperty(BlockOverworldLog2.TYPE, BlockOverworldLog2.LogType.WILLOW);

            gen = new WillowTreeGenerator(4, 5, log, leaves);

            break;
        case EUCALYPTUS:
            log = NaturaOverworld.overworldLog2.getDefaultState().withProperty(BlockOverworldLog2.TYPE, BlockOverworldLog2.LogType.EUCALYPTUS);
            leaves = NaturaOverworld.overworldLeaves2.getDefaultState().withProperty(BlockOverworldLog2.TYPE, BlockOverworldLog2.LogType.EUCALYPTUS);

            gen = new EucalyptusTreeGenerator(6, 3, log, leaves);

            break;
        case HOPSEED:
            log = NaturaOverworld.overworldLog2.getDefaultState().withProperty(BlockOverworldLog2.TYPE, BlockOverworldLog2.LogType.HOPSEED);
            leaves = NaturaOverworld.overworldLeaves2.getDefaultState().withProperty(BlockOverworldLog2.TYPE, BlockOverworldLog2.LogType.HOPSEED);

            gen = new HopseedTreeGenerator(5, 4, log, leaves);

            break;
        case SAKURA:
            break;
        default:
            Natura.log.warn("BlockOverworldLog2 Warning: Invalid sapling meta/foliage, " + state.getValue(FOLIAGE) + ". Please report!");
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

        System.out.println(state.getValue(FOLIAGE));
        System.out.println(state.getValue(STAGE));
    }

    public enum SaplingType implements IStringSerializable, EnumBlock.IEnumMeta
    {
        WILLOW, EUCALYPTUS, HOPSEED, SAKURA;

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
