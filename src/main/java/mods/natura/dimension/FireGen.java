package mods.natura.dimension;

import java.util.Random;

import mods.natura.common.NContent;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class FireGen extends WorldGenerator
{
    @Override
    public boolean generate (World world, Random par2Random, BlockPos pos)
    {
        for (int l = 0; l < 64; ++l)
        {
            int xPos = pos.getX() + par2Random.nextInt(8) - par2Random.nextInt(8);
            int yPos = pos.getY() + par2Random.nextInt(4) - par2Random.nextInt(4);
            int zPos = pos.getZ() + par2Random.nextInt(8) - par2Random.nextInt(8);
            BlockPos temp = new BlockPos(xPos, yPos, zPos);
            if (world.isAirBlock(temp))
            {
                IBlockState blockID = world.getBlockState(new BlockPos(xPos, yPos - 1, zPos));
                if (blockID.getBlock() == Blocks.netherrack || blockID.getBlock() == NContent.taintedSoil)
                    world.setBlockState(temp, Blocks.fire.getDefaultState(), 2);
            }
        }

        return true;
    }
}
