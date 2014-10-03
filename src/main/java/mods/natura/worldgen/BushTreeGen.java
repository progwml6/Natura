package mods.natura.worldgen;

import java.util.Random;

import mods.natura.common.NContent;
import mods.natura.common.PHNatura;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
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
            Block underID = world.getBlock(x, height, z);
            if (underID == Blocks.dirt || underID == Blocks.grass || height < PHNatura.seaLevel)
                foundGround = true;
        } while (!foundGround);
        return height + 1;
    }

    @Override
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
                            Block block12 = world.getBlock(var10, var8, var11);
                            
                            if (!this.isReplaceable(world, var10, var8, var11))
                            {
                                var7 = true;
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
                Block b8 = world.getBlock(x, y - 1, z);

                if ((b8 == Blocks.grass || b8 == Blocks.dirt) && y < 256 - var6 - 1)
                {
                    setBlockAndNotifyAdequately(world, x, y - 1, z, Blocks.dirt, 0);
                    setBlockAndNotifyAdequately(world, x + 1, y - 1, z, Blocks.dirt, 0);
                    setBlockAndNotifyAdequately(world, x, y - 1, z + 1, Blocks.dirt, 0);
                    setBlockAndNotifyAdequately(world, x + 1, y - 1, z + 1, Blocks.dirt, 0);
                    
                    this.growLogs(world, x, y, z);
                    
                    this.growLeaves(world, x, z, y + var6, 2, random);
                    
                    
                    for (var10 = 0; var10 < var6; ++var10)
                    {
                        b8 = world.getBlock(x, y + var10, z);

                        if (b8 == Blocks.air || b8 == null || b8.isLeaves(world, x, y + var10, z))
                        {
                            world.setBlock(x, y + var10, z, NContent.tree, this.woodMetadata, 0);
                        }

                        if (var10 < var6 - 1)
                        {
                            b8 = world.getBlock(x + 1, y + var10, z);

                            if (b8 == Blocks.air || b8 == null || b8.isLeaves(world, x + 1, y + var10, z))
                            {
                                world.setBlock(x + 1, y + var10, z, NContent.tree, this.woodMetadata, 0);
                            }

                            b8 = world.getBlock(x + 1, y + var10, z + 1);

                            if (b8 == Blocks.air || b8 == null || b8.isLeaves(world, x + 1, y + var10, z + 1))
                            {
                                world.setBlock(x + 1, y + var10, z + 1, NContent.tree, this.woodMetadata, 0);
                            }

                            b8 = world.getBlock(x, y + var10, z + 1);

                            if (b8 == Blocks.air || b8 == null || b8.isLeaves(world, x, y + var10, z + 1))
                            {
                                world.setBlock(x, y + var10, z + 1, NContent.tree, this.woodMetadata, 0);
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

                    Block block = par1World.getBlock(var11, var8, var13);

                    if ((var12 >= 0 || var14 >= 0 || var12 * var12 + var14 * var14 <= var10 * var10) && (var12 <= 0 && var14 <= 0 || var12 * var12 + var14 * var14 <= (var10 + 1) * (var10 + 1))
                            && (par6Random.nextInt(4) != 0 || var12 * var12 + var14 * var14 <= (var10 - 1) * (var10 - 1))
                            && (block == null || block.canBeReplacedByLeaves(par1World, var11, var8, var13)))
                    {
                        this.setBlockAndNotifyAdequately(par1World, var11, var8, var13, NContent.floraLeaves, this.leavesMetadata);
                    }
                }
            }
        }
    }
    
    private void growLogs (World world, int x, int y, int z)
    {
        setBlockAndNotifyAdequately(world, x, y, z, NContent.tree, this.woodMetadata);
        setBlockAndNotifyAdequately(world, x + 1, y, z, NContent.tree, this.woodMetadata);
        setBlockAndNotifyAdequately(world, x, y, z + 1, NContent.tree, this.woodMetadata);
        setBlockAndNotifyAdequately(world, x + 1, y, z + 1, NContent.tree, this.woodMetadata);
        
        setBlockAndNotifyAdequately(world, x, y + 1, z, NContent.tree, this.woodMetadata);
        setBlockAndNotifyAdequately(world, x + 1, y + 1, z, NContent.tree, this.woodMetadata);
        setBlockAndNotifyAdequately(world, x, y + 1, z + 1, NContent.tree, this.woodMetadata);
        setBlockAndNotifyAdequately(world, x + 1, y + 1, z + 1, NContent.tree, this.woodMetadata);
    }
    
    protected boolean isReplaceable(World world, int x, int y, int z)
    {
        Block block = world.getBlock(x, y, z);
        return block.isAir(world, x, y, z) || block.isLeaves(world, x, y, z) || block.isWood(world, x, y, z);
    }
}
