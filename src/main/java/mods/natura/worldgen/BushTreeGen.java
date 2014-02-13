package mods.natura.worldgen;

import java.util.Random;

import mods.natura.common.NContent;
import mods.natura.common.PHNatura;
import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BushTreeGen extends WorldGenerator
{
    /** The base height of the tree */
    private final int baseHeight;

    /** Sets the metadata for the wood blocks used */
    private final int woodMetadata;

    /** Sets the metadata for the leaves used in huge trees */
    private final int leavesMetadata;

    public BushTreeGen(boolean par1, int height, int mdwood, int mdleaves)
    {
        super(par1);
        this.baseHeight = height;
        this.woodMetadata = mdwood;
        this.leavesMetadata = mdleaves;
    }

    int findGround (World world, int x, int y, int z)
    {
        boolean foundGround = false;
        int height = PHNatura.seaLevel + 64;
        do
        {
            height--;
            int underID = world.getBlockId(x, height, z);
            if (underID == Block.dirt.blockID || underID == Block.grass.blockID || height < PHNatura.seaLevel)
                foundGround = true;
        } while (!foundGround);
        return height + 1;
    }

    public boolean generate (World world, Random random, int x, int y, int z)
    {
        int var6 = random.nextInt(3) + this.baseHeight;
        boolean var7 = true;

        y = findGround(world, x, y, z);

        if (y >= 1 && y + var6 + 1 <= 256)
        {
            int var8;
            int var10;
            int var11;
            int var12;

            for (var8 = y; var8 <= y + 1 + var6; ++var8)
            {
                byte var9 = 2;

                if (var8 == y)
                {
                    var9 = 1;
                }

                if (var8 >= y + 1 + var6 - 2)
                {
                    var9 = 2;
                }

                for (var10 = x - var9; var10 <= x + var9 && var7; ++var10)
                {
                    for (var11 = z - var9; var11 <= z + var9 && var7; ++var11)
                    {
                        if (var8 >= 0 && var8 < 256)
                        {
                            var12 = world.getBlockId(var10, var8, var11);

                            if (var12 != 0 && (Block.blocksList[var12] != null && !Block.blocksList[var12].isLeaves(world, var10, var8, var11)) && var12 != Block.grass.blockID
                                    && var12 != Block.dirt.blockID && (Block.blocksList[var12] != null && !Block.blocksList[var12].isWood(world, var10, var8, var11)) && var12 != Block.sapling.blockID)
                            {
                                var7 = false;
                            }
                        }
                        else
                        {
                            var7 = false;
                        }
                    }
                }
            }

            if (!var7)
            {
                return false;
            }
            else
            {
                var8 = world.getBlockId(x, y - 1, z);

                if ((var8 == Block.grass.blockID || var8 == Block.dirt.blockID) && y < 256 - var6 - 1)
                {
                    world.setBlock(x, y - 1, z, Block.dirt.blockID);
                    world.setBlock(x + 1, y - 1, z, Block.dirt.blockID);
                    world.setBlock(x, y - 1, z + 1, Block.dirt.blockID);
                    world.setBlock(x + 1, y - 1, z + 1, Block.dirt.blockID);
                    this.growLeaves(world, x, z, y + var6, 2, random);

                    for (int var14 = y + var6 - 2 - random.nextInt(4); var14 > y + var6 / 2; var14 -= 2 + random.nextInt(4))
                    {
                        float var15 = random.nextFloat() * (float) Math.PI * 2.0F;
                        var11 = x + (int) (0.5F + MathHelper.cos(var15) * 4.0F);
                        var12 = z + (int) (0.5F + MathHelper.sin(var15) * 4.0F);
                        this.growLeaves(world, var11, var12, var14, 0, random);

                        for (int var13 = 0; var13 < 5; ++var13)
                        {
                            var11 = x + (int) (1.5F + MathHelper.cos(var15) * (float) var13);
                            var12 = z + (int) (1.5F + MathHelper.sin(var15) * (float) var13);
                            this.setBlockAndMetadata(world, var11, var14 - 3 + var13 / 2, var12, NContent.tree.blockID, this.woodMetadata);
                        }
                    }

                    for (var10 = 0; var10 < var6; ++var10)
                    {
                        var11 = world.getBlockId(x, y + var10, z);

                        if (var11 == 0 || Block.blocksList[var11] == null || Block.blocksList[var11].isLeaves(world, x, y + var10, z))
                        {
                            this.setBlockAndMetadata(world, x, y + var10, z, NContent.tree.blockID, this.woodMetadata);

                            /*if (var10 > 0)
                            {
                                if (random.nextInt(3) > 0 && world.isAirBlock(x - 1, y + var10, z))
                                {
                                    this.setBlockAndMetadata(world, x - 1, y + var10, z, Block.vine.blockID, 8);
                                }

                                if (random.nextInt(3) > 0 && world.isAirBlock(x, y + var10, z - 1))
                                {
                                    this.setBlockAndMetadata(world, x, y + var10, z - 1, Block.vine.blockID, 1);
                                }
                            }*/
                        }

                        if (var10 < var6 - 1)
                        {
                            var11 = world.getBlockId(x + 1, y + var10, z);

                            if (var11 == 0 || Block.blocksList[var11] == null || Block.blocksList[var11].isLeaves(world, x + 1, y + var10, z))
                            {
                                this.setBlockAndMetadata(world, x + 1, y + var10, z, NContent.tree.blockID, this.woodMetadata);

                                /*if (var10 > 0)
                                {
                                    if (random.nextInt(3) > 0 && world.isAirBlock(x + 2, y + var10, z))
                                    {
                                        this.setBlockAndMetadata(world, x + 2, y + var10, z, Block.vine.blockID, 2);
                                    }

                                    if (random.nextInt(3) > 0 && world.isAirBlock(x + 1, y + var10, z - 1))
                                    {
                                        this.setBlockAndMetadata(world, x + 1, y + var10, z - 1, Block.vine.blockID, 1);
                                    }
                                }*/
                            }

                            var11 = world.getBlockId(x + 1, y + var10, z + 1);

                            if (var11 == 0 || Block.blocksList[var11] == null || Block.blocksList[var11].isLeaves(world, x + 1, y + var10, z + 1))
                            {
                                this.setBlockAndMetadata(world, x + 1, y + var10, z + 1, NContent.tree.blockID, this.woodMetadata);

                                /*if (var10 > 0)
                                {
                                    if (random.nextInt(3) > 0 && world.isAirBlock(x + 2, y + var10, z + 1))
                                    {
                                        this.setBlockAndMetadata(world, x + 2, y + var10, z + 1, Block.vine.blockID, 2);
                                    }

                                    if (random.nextInt(3) > 0 && world.isAirBlock(x + 1, y + var10, z + 2))
                                    {
                                        this.setBlockAndMetadata(world, x + 1, y + var10, z + 2, Block.vine.blockID, 4);
                                    }
                                }*/
                            }

                            var11 = world.getBlockId(x, y + var10, z + 1);

                            if (var11 == 0 || Block.blocksList[var11] == null || Block.blocksList[var11].isLeaves(world, x, y + var10, z + 1))
                            {
                                this.setBlockAndMetadata(world, x, y + var10, z + 1, NContent.tree.blockID, this.woodMetadata);

                                /*if (var10 > 0)
                                {
                                    if (random.nextInt(3) > 0 && world.isAirBlock(x - 1, y + var10, z + 1))
                                    {
                                        this.setBlockAndMetadata(world, x - 1, y + var10, z + 1, Block.vine.blockID, 8);
                                    }

                                    if (random.nextInt(3) > 0 && world.isAirBlock(x, y + var10, z + 2))
                                    {
                                        this.setBlockAndMetadata(world, x, y + var10, z + 2, Block.vine.blockID, 4);
                                    }
                                }*/
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

    private void growLeaves (World par1World, int par2, int par3, int par4, int par5, Random par6Random)
    {
        byte var7 = 2;

        for (int var8 = par4 - var7; var8 <= par4; ++var8)
        {
            int var9 = var8 - par4;
            int var10 = par5 + 1 - var9;

            for (int var11 = par2 - var10; var11 <= par2 + var10 + 1; ++var11)
            {
                int var12 = var11 - par2;

                for (int var13 = par3 - var10; var13 <= par3 + var10 + 1; ++var13)
                {
                    int var14 = var13 - par3;

                    Block block = Block.blocksList[par1World.getBlockId(var11, var8, var13)];

                    if ((var12 >= 0 || var14 >= 0 || var12 * var12 + var14 * var14 <= var10 * var10) && (var12 <= 0 && var14 <= 0 || var12 * var12 + var14 * var14 <= (var10 + 1) * (var10 + 1))
                            && (par6Random.nextInt(4) != 0 || var12 * var12 + var14 * var14 <= (var10 - 1) * (var10 - 1))
                            && (block == null || block.canBeReplacedByLeaves(par1World, var11, var8, var13)))
                    {
                        this.setBlockAndMetadata(par1World, var11, var8, var13, NContent.floraLeaves.blockID, this.leavesMetadata);
                    }
                }
            }
        }
    }
}
