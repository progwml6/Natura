package com.progwml6.natura.nether.block.shrooms;

import java.util.Locale;
import java.util.Random;

import javax.annotation.Nonnull;

import com.progwml6.natura.Natura;
import com.progwml6.natura.library.NaturaRegistry;
import com.progwml6.natura.nether.NaturaNether;
import com.progwml6.natura.world.worldgen.glowshroom.BaseGlowshroomGenerator;
import com.progwml6.natura.world.worldgen.glowshroom.nether.BlueGlowshroomGenerator;
import com.progwml6.natura.world.worldgen.glowshroom.nether.GreenGlowshroomGenerator;
import com.progwml6.natura.world.worldgen.glowshroom.nether.PurpleGlowshroomGenerator;

import net.minecraft.block.BlockMushroom;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.mantle.block.EnumBlock;

public class BlockNetherGlowshroom extends BlockMushroom
{
    public static PropertyEnum<GlowshroomType> TYPE = PropertyEnum.create("type", GlowshroomType.class);

    public BlockNetherGlowshroom()
    {
        super();
        this.setCreativeTab(NaturaRegistry.tabWorld);
        this.setSoundType(SoundType.PLANT);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (rand.nextInt(25) == 0)
        {
            int i = 5;

            for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-4, -1, -4), pos.add(4, 1, 4)))
            {
                if (worldIn.getBlockState(blockpos).getBlock() == this)
                {
                    --i;

                    if (i <= 0)
                    {
                        return;
                    }
                }
            }

            BlockPos blockpos1 = pos.add(rand.nextInt(3) - 1, rand.nextInt(2) - rand.nextInt(2), rand.nextInt(3) - 1);

            IBlockState currentState = worldIn.getBlockState(pos);

            for (int k = 0; k < 4; ++k)
            {
                if (worldIn.isAirBlock(blockpos1) && this.canBlockStay(worldIn, blockpos1, currentState))
                {
                    pos = blockpos1;
                }

                blockpos1 = pos.add(rand.nextInt(3) - 1, rand.nextInt(2) - rand.nextInt(2), rand.nextInt(3) - 1);
            }

            currentState = worldIn.getBlockState(pos);
            if (worldIn.isAirBlock(blockpos1) && this.canBlockStay(worldIn, blockpos1, currentState))
            {
                worldIn.setBlockState(blockpos1, currentState, 3);
            }
        }
    }

    @Override
    public void getSubBlocks(@Nonnull Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (GlowshroomType type : GlowshroomType.values())
        {
            list.add(new ItemStack(this, 1, this.getMetaFromState(this.getDefaultState().withProperty(TYPE, type))));
        }
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
    {
        if (pos.getY() >= 0 && pos.getY() < 256)
        {
            IBlockState iblockstate = worldIn.getBlockState(pos.down());
            return (iblockstate.getBlock() == Blocks.MYCELIUM || iblockstate.getBlock() == Blocks.NETHERRACK || iblockstate.getBlock() == Blocks.SOUL_SAND || iblockstate.getBlock() == NaturaNether.netherTaintedSoil) ? true : (worldIn.getLight(pos) < 13 && iblockstate.getBlock().canSustainPlant(iblockstate, worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, this));
        }
        else
        {
            return false;
        }
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, TYPE);
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        if (meta < 0 || meta >= GlowshroomType.values().length)
        {
            meta = 0;
        }

        GlowshroomType type = GlowshroomType.values()[meta];

        return this.getDefaultState().withProperty(TYPE, type);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(TYPE).ordinal();
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return this.getMetaFromState(state);
    }

    @Override
    public boolean generateBigMushroom(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        BaseGlowshroomGenerator gen = new BaseGlowshroomGenerator();

        IBlockState glowshroom;

        switch (state.getValue(TYPE))
        {
        case GREEN:
            glowshroom = NaturaNether.netherLargeGreenGlowshroom.getDefaultState();

            gen = new GreenGlowshroomGenerator(glowshroom);

            break;
        case BLUE:
            glowshroom = NaturaNether.netherLargeBlueGlowshroom.getDefaultState();

            gen = new BlueGlowshroomGenerator(glowshroom);

            break;
        case PURPLE:
            glowshroom = NaturaNether.netherLargePurpleGlowshroom.getDefaultState();

            gen = new PurpleGlowshroomGenerator(glowshroom);

            break;
        default:
            Natura.log.warn("BlockNetherGlowshroom Warning: Invalid meta, " + state.getValue(TYPE) + ". Please report!");

            break;
        }

        worldIn.setBlockToAir(pos);

        gen.generateShroom(rand, worldIn, pos);

        // check if it generated
        if (worldIn.isAirBlock(pos))
        {
            worldIn.setBlockState(pos, state, 4);
            return false;
        }
        else
        {
            return true;
        }
    }

    public enum GlowshroomType implements IStringSerializable, EnumBlock.IEnumMeta
    {
        GREEN, BLUE, PURPLE;

        public final int meta;

        GlowshroomType()
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
