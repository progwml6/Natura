package mods.natura.worldgen;

import java.util.Random;

import mods.natura.common.NContent;
import mods.natura.common.PHNatura;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WillowGen extends WorldGenerator
{
    public final boolean seekHeight;

    public WillowGen(boolean notify)
    {
        super(notify);
        seekHeight = !notify;
    }

    int findGround (World world, int x, int y, int z)
    {
        int ret = -1;
        int height = y;
        do
        {
            Block heightID = world.getBlock(x, height, z);
            if ((heightID == Blocks.dirt || heightID == Blocks.grass || heightID == Blocks.sand) && !world.getBlock(x, height + 1, z).isOpaqueCube())
            {
                ret = height + 1;
                break;
            }
            height--;
        } while (height > PHNatura.seaLevel);
        return ret;
    }

    @Override
    public boolean generate (World world, Random par2Random, int x, int y, int z)
    {
        if (seekHeight)
        {
            y = findGround(world, x, y, z);
            if (y == -1)
                return false;
        }
        int l;

        for (l = par2Random.nextInt(4) + 5; world.getBlock(x, y - 1, z).getMaterial() == Material.water; --y)
        {
            ;
        }

        boolean flag = true;

        if (y >= 1 && y + l + 1 <= 128)
        {
            int i1;
            int j1;
            int k1;
            int l1;

            for (i1 = y; i1 <= y + 1 + l; ++i1)
            {
                byte b0 = 1;

                if (i1 == y)
                {
                    b0 = 0;
                }

                if (i1 >= y + 1 + l - 2)
                {
                    b0 = 3;
                }

                for (j1 = x - b0; j1 <= x + b0 && flag; ++j1)
                {
                    for (k1 = z - b0; k1 <= z + b0 && flag; ++k1)
                    {
                        if (i1 >= 0 && i1 < 128)
                        {
                            Block b1 = world.getBlock(j1, i1, k1);

                            if (b1 != null && !b1.isLeaves(world, j1, i1, k1))
                            {
                                if (b1 != Blocks.water)
                                {
                                    flag = false;
                                }
                                else if (i1 > y)
                                {
                                    flag = false;
                                }
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
                Block bi1 = world.getBlock(x, y - 1, z);

                if ((bi1 == Blocks.grass || bi1 == Blocks.dirt) && y < 128 - l - 1)
                {
                    world.setBlock(x, y - 1, z, Blocks.dirt);
                    int i2;
                    int j2;

                    for (j2 = y - 3 + l; j2 <= y + l; ++j2)
                    {
                        j1 = j2 - (y + l);
                        k1 = 2 - j1 / 2;

                        for (l1 = x - k1; l1 <= x + k1; ++l1)
                        {
                            i2 = l1 - x;

                            for (int k2 = z - k1; k2 <= z + k1; ++k2)
                            {
                                int l2 = k2 - z;

                                Block block = world.getBlock(l1, j2, k2);

                                if ((Math.abs(i2) != k1 || Math.abs(l2) != k1 || par2Random.nextInt(2) != 0 && j1 != 0) && (block == null || block.canBeReplacedByLeaves(world, l1, j2, k2)))
                                {
                                    world.setBlock(l1, j2, k2, NContent.floraLeavesNoColor, 3, 0);
                                }
                            }
                        }
                    }

                    for (j2 = 0; j2 < l; ++j2)
                    {
                        Block block = world.getBlock(x, y + j2, z);

                        if (block != null && block.isLeaves(world, x, y + j2, z) || block == Blocks.water)
                        {
                            world.setBlock(x, y + j2, z, NContent.willow);
                        }
                    }

                    for (j2 = y - 3 + l; j2 <= y + l; ++j2)
                    {
                        j1 = j2 - (y + l);
                        k1 = 2 - j1 / 2;

                        for (l1 = x - k1; l1 <= x + k1; ++l1)
                        {
                            for (i2 = z - k1; i2 <= z + k1; ++i2)
                            {
                                Block block = world.getBlock(l1, j2, i2);
                                if (block != null && block.isLeaves(world, l1, j2, i2))
                                {
                                    if (par2Random.nextInt(4) == 0 && world.getBlock(l1 - 1, j2, i2) == Blocks.air)
                                    {
                                        this.generateVines(world, l1 - 1, j2, i2, 3);
                                    }

                                    if (par2Random.nextInt(4) == 0 && world.getBlock(l1 + 1, j2, i2) == Blocks.air)
                                    {
                                        this.generateVines(world, l1 + 1, j2, i2, 3);
                                    }

                                    if (par2Random.nextInt(4) == 0 && world.getBlock(l1, j2, i2 - 1) == Blocks.air)
                                    {
                                        this.generateVines(world, l1, j2, i2 - 1, 3);
                                    }

                                    if (par2Random.nextInt(4) == 0 && world.getBlock(l1, j2, i2 + 1) == Blocks.air)
                                    {
                                        this.generateVines(world, l1, j2, i2 + 1, 3);
                                    }
                                }
                            }
                        }
                    }

                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * Generates vines at the given position until it hits a block.
     */
    private void generateVines (World par1World, int par2, int par3, int par4, int par5)
    {
        par1World.setBlock(par2, par3, par4, NContent.floraLeavesNoColor, par5, 0);
        int i1 = 4;

        while (true)
        {
            --par3;

            if (par1World.getBlock(par2, par3, par4) != Blocks.air || i1 <= 0)
            {
                return;
            }

            par1World.setBlock(par2, par3, par4, NContent.floraLeavesNoColor, par5, 0);
            --i1;
        }
    }
}
