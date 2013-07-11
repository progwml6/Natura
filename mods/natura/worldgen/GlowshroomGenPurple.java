package mods.natura.worldgen;

import java.util.Random;

import mods.natura.common.NContent;
import net.minecraft.block.Block;
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
                            posX = world.getBlockId(posY, blockID, range);

                            Block block = Block.blocksList[posX];

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
                blockID = world.getBlockId(x, y - 1, z);

                if (blockID != Block.mycelium.blockID && blockID != Block.netherrack.blockID && blockID != NContent.taintedSoil.blockID && blockID != Block.slowSand.blockID)
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

                                Block block = Block.blocksList[world.getBlockId(posX, posY, posZ)];

                                if ((meta != 0 || y >= y + height - 1) && (block == null || block.canBeReplacedByLeaves(world, posX, posY, posZ)))
                                {
                                    this.setBlockAndMetadata(world, posX, posY, posZ, NContent.glowshroomPurple.blockID, meta);
                                }
                            }
                        }
                    }

                    this.setBlockAndMetadata(world, x - 2, y + height - 1, z - 2, NContent.glowshroomPurple.blockID, 1);
                    this.setBlockAndMetadata(world, x + 2, y + height - 1, z - 2, NContent.glowshroomPurple.blockID, 3);
                    this.setBlockAndMetadata(world, x - 2, y + height - 1, z + 2, NContent.glowshroomPurple.blockID, 7);
                    this.setBlockAndMetadata(world, x + 2, y + height - 1, z + 2, NContent.glowshroomPurple.blockID, 9);

                    for (posY = 0; posY < height; ++posY)
                    {
                        range = world.getBlockId(x, y + posY, z);

                        Block block = Block.blocksList[range];

                        if (block == null || block.canBeReplacedByLeaves(world, x, y + posY, z))
                        {
                            this.setBlockAndMetadata(world, x, y + posY, z, NContent.glowshroomPurple.blockID, 10);
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
