package mods.natura.dimension;

import java.util.Random;

import mods.natura.common.NContent;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class FireGen extends WorldGenerator
{
    public boolean generate (World world, Random par2Random, int x, int y, int z)
    {
        for (int l = 0; l < 64; ++l)
        {
            int xPos = x + par2Random.nextInt(8) - par2Random.nextInt(8);
            int yPos = y + par2Random.nextInt(4) - par2Random.nextInt(4);
            int zPos = z + par2Random.nextInt(8) - par2Random.nextInt(8);

            if (world.isAirBlock(xPos, yPos, zPos))
            {
                int blockID = world.getBlockId(xPos, yPos - 1, zPos);
                if (blockID == Block.netherrack.blockID || blockID == NContent.taintedSoil.blockID)
                    world.setBlock(xPos, yPos, zPos, Block.fire.blockID, 0, 2);
            }
        }

        return true;
    }
}
