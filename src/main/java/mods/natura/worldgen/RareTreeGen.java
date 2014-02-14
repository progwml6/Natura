package mods.natura.worldgen;

import java.util.Random;

import mods.natura.common.NContent;
import mods.natura.common.PHNatura;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.util.ForgeDirection;

public class RareTreeGen extends WorldGenerator
{
    public final int minTreeHeight;
    public final int treeHeightRange;
    public final int metaWood;
    public final int metaLeaves;
    public final boolean seekHeight;

    public RareTreeGen(boolean notify, int treeHeight, int treeRange, int woodMeta, int leavesMeta)
    {
        super(notify);
        this.minTreeHeight = treeHeight;
        this.treeHeightRange = treeRange;
        this.metaWood = woodMeta;
        this.metaLeaves = leavesMeta;
        this.seekHeight = !notify;
    }

    public boolean generate (World world, Random random, int xPos, int yPos, int zPos)
    {
        int height = random.nextInt(this.treeHeightRange) + this.minTreeHeight;
        boolean flag = true;
        if (seekHeight)
        {
            yPos = findGround(world, xPos, yPos, zPos);
            if (yPos == -1)
                return false;
        }

        if (yPos >= 1 && yPos + height + 1 <= 256)
        {
            Block soil = world.getBlock(xPos, yPos - 1, zPos);
            boolean isSoil = (soil != null && soil.canSustainPlant(world, xPos, yPos - 1, zPos, ForgeDirection.UP, NContent.rareSapling));

            if (isSoil)
            {
                if (!checkClear(world, xPos, yPos, zPos, height))
                    return false;

                soil.onPlantGrow(world, xPos, yPos - 1, zPos, xPos, yPos, zPos);
                placeCanopy(world, random, xPos, yPos, zPos, height);
                placeTrunk(world, xPos, yPos, zPos, height);
                return true;
            }
        }
        return false;
    }

    boolean checkClear (World world, int x, int y, int z, int treeHeight)
    {
        for (int yPos = 0; yPos < treeHeight + 1; yPos++)
        {
            int range = 1;

            if (yPos == 0)
                range = 0;
            else if (yPos >= treeHeight - 1)
                range = 2;

            for (int xPos = range; xPos <= range; xPos++)
            {
                for (int zPos = range; zPos <= range; zPos++)
                {
                    Block block = world.getBlock(x + xPos, y + yPos, z + zPos);
                    if (block != null && block != NContent.rareSapling && !block.isLeaves(world, x + xPos, y + yPos, z + zPos))
                        return false;
                }
            }
        }
        return true;
    }

    int findGround (World world, int x, int y, int z)
    {
        int ret = -1;
        int height = y;
        do
        {
            Block heightID = world.getBlock(x, height, z);
            if ((heightID == Blocks.dirt || heightID == Blocks.grass) && !world.getBlock(x, height + 1, z).isOpaqueCube())
            {
                ret = height + 1;
                break;
            }
            height--;
        } while (height > PHNatura.seaLevel);
        return ret;
    }

    void placeCanopy (World world, Random random, int xPos, int yPos, int zPos, int height)
    {
        for (int y = yPos - 3 + height; y <= yPos + height; ++y)
        {
            int k1 = y - (yPos + height);
            int i2 = 0 + 1 - k1 / 2;

            for (int x = xPos - i2; x <= xPos + i2; ++x)
            {
                int k2 = x - xPos;

                for (int z = zPos - i2; z <= zPos + i2; ++z)
                {
                    int i3 = z - zPos;

                    if (Math.abs(k2) != i2 || Math.abs(i3) != i2 || random.nextInt(2) != 0 && k1 != 0)
                    {
                        Block block = world.getBlock(x, y, z);

                        if (block == null || block.canBeReplacedByLeaves(world, x, y, z))
                        {
                            world.setBlock(x, y, z, NContent.rareLeaves, this.metaLeaves, 0);
                        }
                    }
                }
            }
        }
    }

    void placeTrunk (World world, int xPos, int yPos, int zPos, int height)
    {
        for (int localHeight = 0; localHeight < height; ++localHeight)
        {
            Block block = world.getBlock(xPos, yPos + localHeight, zPos);

            if (block == null || block.isLeaves(world, xPos, yPos + localHeight, zPos))
            {
                world.setBlock(xPos, yPos + localHeight, zPos, NContent.rareTree, this.metaWood, 0);
            }
        }
    }

}
