package mods.natura.worldgen;

import java.util.Random;

import mods.natura.common.NContent;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class GlowshroomGenBlueGreen extends WorldGenerator
{
    int mushroomType = -1;

    public GlowshroomGenBlueGreen(boolean flag)
    {
        super(flag);
    }

    public GlowshroomGenBlueGreen(boolean flag, int type)
    {
        super(flag);
        mushroomType = type;
    }

    @Override
    public boolean generate (World world, Random random, int x, int y, int z)
    {
        int type;

        if (this.mushroomType >= 0)
        {
            type = this.mushroomType;
        }
        else
        {
            type = random.nextInt(2);
        }

        int height = random.nextInt(3) + 4;
        boolean flag = true;

        if (y >= 1 && y + height + 1 < 256)
        {
            int blockID;
            int posY;
            int l1;
            // TODO 1.7 correct init value?
            int posX = 0;

            for (blockID = y; blockID <= y + 1 + height; ++blockID)
            {
                byte b0 = 3;

                if (blockID <= y + 3)
                {
                    b0 = 0;
                }

                for (posY = x - b0; posY <= x + b0 && flag; ++posY)
                {
                    for (l1 = z - b0; l1 <= z + b0 && flag; ++l1)
                    {
                        if (blockID >= 0 && blockID < 256)
                        {
                            Block block = world.getBlock(posY, blockID, l1);

                            if (posX != 0 && block != null && !block.isLeaves(world, posY, blockID, l1) && blockID != Block.getIdFromBlock(NContent.glowshroom))
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
                    int j2 = y + height;

                    if (type == 1)
                    {
                        j2 = y + height - 3;
                    }

                    for (posY = j2; posY <= y + height; ++posY)
                    {
                        l1 = 1;

                        if (posY < y + height)
                        {
                            ++l1;
                        }

                        if (type == 0)
                        {
                            l1 = 3;
                        }

                        for (posX = x - l1; posX <= x + l1; ++posX)
                        {
                            for (int posZ = z - l1; posZ <= z + l1; ++posZ)
                            {
                                int meta = 5;

                                if (posX == x - l1)
                                {
                                    --meta;
                                }

                                if (posX == x + l1)
                                {
                                    ++meta;
                                }

                                if (posZ == z - l1)
                                {
                                    meta -= 3;
                                }

                                if (posZ == z + l1)
                                {
                                    meta += 3;
                                }

                                if (type == 0 || posY < y + height)
                                {
                                    if ((posX == x - l1 || posX == x + l1) && (posZ == z - l1 || posZ == z + l1))
                                    {
                                        continue;
                                    }

                                    if (posX == x - (l1 - 1) && posZ == z - l1)
                                    {
                                        meta = 1;
                                    }

                                    if (posX == x - l1 && posZ == z - (l1 - 1))
                                    {
                                        meta = 1;
                                    }

                                    if (posX == x + (l1 - 1) && posZ == z - l1)
                                    {
                                        meta = 3;
                                    }

                                    if (posX == x + l1 && posZ == z - (l1 - 1))
                                    {
                                        meta = 3;
                                    }

                                    if (posX == x - (l1 - 1) && posZ == z + l1)
                                    {
                                        meta = 7;
                                    }

                                    if (posX == x - l1 && posZ == z + (l1 - 1))
                                    {
                                        meta = 7;
                                    }

                                    if (posX == x + (l1 - 1) && posZ == z + l1)
                                    {
                                        meta = 9;
                                    }

                                    if (posX == x + l1 && posZ == z + (l1 - 1))
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
                                    Block localID = type == 1 ? NContent.glowshroomBlue : NContent.glowshroomGreen;
                                    world.setBlock(posX, posY, posZ, localID, meta, 0);
                                }
                            }
                        }
                    }

                    for (posY = 0; posY < height; ++posY)
                    {
                        Block block = world.getBlock(x, y + posY, z);

                        if (block == null || block.canBeReplacedByLeaves(world, x, y + posY, z))
                        {
                            Block localID = type == 1 ? NContent.glowshroomBlue : NContent.glowshroomGreen;
                            world.setBlock(x, y + posY, z, localID, 10, 0);
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
