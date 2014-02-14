package mods.natura.worldgen;

import java.util.Random;

import mods.natura.common.NContent;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class GlowshroomGenPurple extends WorldGenerator
{

    public GlowshroomGenPurple(boolean flag)
    {
        super(flag);
    }

    public boolean generate (World world, Random random, int x, int y, int z)
    {
        int type = 1;

        int height = random.nextInt(4) + 1;
        boolean flag = true;

        if (y >= 1 && y + height + 1 < 256)
        {
            int blockID;
            int posY;
            int range;
            int posX;

            for (blockID = y; blockID <= y + 1 + height; ++blockID)
            {
                byte b0 = 3;

                if (blockID <= y + 3)
                {
                    b0 = 0;
                }

                for (posY = x - b0; posY <= x + b0 && flag; ++posY)
                {
                    for (range = z - b0; range <= z + b0 && flag; ++range)
                    {
                        if (blockID >= 0 && blockID < 256)
                        {
                            Block block = world.getBlock(posY, blockID, range);

                            if (posX != 0 && block != null && !block.isLeaves(world, posY, blockID, range))
                            {
                                flag = false;
                            }
                        }
                        else
                        {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag)
            {
                return false;
            }
            else
            {
                Block blockb = world.getBlock(x, y - 1, z);

                if (blockb != Blocks.mycelium && blockb != Blocks.netherrack && blockb != NContent.taintedSoil && blockb != Blocks.soul_sand)
                {
                    return false;
                }
                else
                {
                    int heightPos = y + height - 1;

                    for (posY = heightPos; posY <= y + height; ++posY)
                    {
                        range = 2;

                        if (posY < y + height)
                        {
                            ++range;
                        }

                        for (posX = x - range; posX <= x + range; ++posX)
                        {
                            for (int posZ = z - range; posZ <= z + range; ++posZ)
                            {
                                int meta = 5;

                                if (posX == x - range)
                                {
                                    --meta;
                                }

                                if (posX == x + range)
                                {
                                    ++meta;
                                }

                                if (posZ == z - range)
                                {
                                    meta -= 3;
                                }

                                if (posZ == z + range)
                                {
                                    meta += 3;
                                }

                                if (type == 1 || posY < y + height)
                                {
                                    int swap = posY < y + height ? 2 : 1;
                                    if (type == 1)
                                    {
                                        if ((posX == x - range || posX == x + range) && (posZ == z - range || posZ == z + range))
                                        {
                                            continue;
                                        }
                                    }

                                    if (posY < y + height)
                                    {
                                        if ((posX <= x - range + 1 || posX >= x + range - 1) && (posZ <= z - range + 1 || posZ >= z + range - 1))
                                        {
                                            continue;
                                        }
                                    }

                                    if (posX == x - (range - swap) && posZ == z - range)
                                    {
                                        meta = 1;
                                    }

                                    if (posX == x - range && posZ == z - (range - swap))
                                    {
                                        meta = 1;
                                    }

                                    if (posX == x + (range - swap) && posZ == z - range)
                                    {
                                        meta = 3;
                                    }

                                    if (posX == x + range && posZ == z - (range - swap))
                                    {
                                        meta = 3;
                                    }

                                    if (posX == x - (range - swap) && posZ == z + range)
                                    {
                                        meta = 7;
                                    }

                                    if (posX == x - range && posZ == z + (range - swap))
                                    {
                                        meta = 7;
                                    }

                                    if (posX == x + (range - swap) && posZ == z + range)
                                    {
                                        meta = 9;
                                    }

                                    if (posX == x + range && posZ == z + (range - swap))
                                    {
                                        meta = 9;
                                    }
                                }

                                if (meta == 5 && posY < y + height)
                                {
                                    meta = 0;
                                }

                                Block block = world.getBlock(posX, posY, posZ);

                                if ((meta != 0 || y >= y + height - 1) && (block == null || block.canBeReplacedByLeaves(world, posX, posY, posZ)))
                                {
                                    world.setBlock(posX, posY, posZ, NContent.glowshroomPurple, meta,0);
                                }
                            }
                        }
                    }

                    world.setBlock(x - 2, y + height - 1, z - 2, NContent.glowshroomPurple, 1, 0);
                    world.setBlock(x + 2, y + height - 1, z - 2, NContent.glowshroomPurple, 3, 0);
                    world.setBlock(x - 2, y + height - 1, z + 2, NContent.glowshroomPurple, 7, 0);
                    world.setBlock(x + 2, y + height - 1, z + 2, NContent.glowshroomPurple, 9, 0);

                    for (posY = 0; posY < height; ++posY)
                    {
                        Block block = world.getBlock(x, y + posY, z);
                        ;

                        if (block == null || block.canBeReplacedByLeaves(world, x, y + posY, z))
                        {
                            world.setBlock(x, y + posY, z, NContent.glowshroomPurple, 10, 0);
                        }
                    }

                    return true;
                }
            }
        }
        else
        {
            return false;
        }
    }
}
