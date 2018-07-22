package com.progwml6.natura.overworld.block.bush;

import java.util.Random;

import com.progwml6.natura.common.block.BlockEnumBerryBush;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class BlockOverworldBerryBush extends BlockEnumBerryBush
{
    public BlockOverworldBerryBush()
    {
        super();

        Blocks.FIRE.setFireInfo(this, 4, 25);
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        int age = state.getValue(AGE).intValue();

        if (age < 2)
        {
            int setMeta = rand.nextInt(2) + 1 + age;

            worldIn.setBlockState(pos, this.getDefaultState().withProperty(AGE, Integer.valueOf(setMeta)), 4);

            return;
        }

        IBlockState upBlock = worldIn.getBlockState(pos.up());

        if (upBlock == null || worldIn.isAirBlock(pos.up()))
        {
            if (rand.nextInt(3) == 0)
            {
                worldIn.setBlockState(pos.up(), this.getDefaultState().withProperty(AGE, Integer.valueOf(0)), 2);
            }
            return;
        }

        return;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        super.updateTick(worldIn, pos, state, rand);

        int height;

        for (height = 1; worldIn.getBlockState(pos.down(height)).getBlock() == this; ++height)
        {
            ;
        }
        
        boolean canGrow = (rand.nextInt(20) == 0);

        if (worldIn.getLightFromNeighbors(pos) >= 8)
        {
        	if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, canGrow))
        	{
	            int age = state.getValue(AGE).intValue();
	
	            if (age < 3)
	            {
	                worldIn.setBlockState(pos, this.getDefaultState().withProperty(AGE, Integer.valueOf(age + 1)), 2);
	            }
	
	            if (rand.nextInt(3) == 0 && height < 3 && worldIn.getBlockState(pos.up()).getBlock() == Blocks.AIR && age >= 2)
	            {
	                worldIn.setBlockState(pos.up(), this.getDefaultState().withProperty(AGE, Integer.valueOf(0)), 2);
	            }
	            
	            ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
        	}
        }
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, net.minecraftforge.common.IPlantable plantable)
    {
        if (plantable instanceof BlockOverworldBerryBush && state.getPropertyKeys().contains(AGE))
        {
            return (state.getValue(AGE) > 2);
        }

        return super.canSustainPlant(state, world, pos, direction, plantable);
    }
}
