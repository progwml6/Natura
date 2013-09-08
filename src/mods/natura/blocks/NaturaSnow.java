package mods.natura.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class NaturaSnow extends BlockSnow
{

    public NaturaSnow()
    {
        super(78);
    }

    public void updateTick (World world, int x, int y, int z, Random random)
    {
        int meta;

        if (world.getSavedLightValue(EnumSkyBlock.Block, x, y, z) > 11)
        {
            meta = world.getBlockMetadata(x, y, z);

            if (meta > 0)
            {
                world.setBlockMetadataWithNotify(x, y, z, meta - 1, 4);
            }
            else
            {
                world.setBlockToAir(x, y, z);
            }
        }

        else if (world.getWorldInfo().isRaining() && random.nextInt(10) == 0)
        {
            meta = world.getBlockMetadata(x, y, z);

            if (meta < 8)
            {
                world.setBlockMetadataWithNotify(x, y, z, meta + 1, 2);
            }
            else
            {
                this.scanHeight(world, x, y, z, random);
            }
        }
    }

    void scanHeight (World world, int x, int y, int z, Random random)
    {
        if (world.isAirBlock(x, y + 1, z))
        {
            int var6;

            for (var6 = 1; world.getBlockId(x, y - var6, z) == Block.blockSnow.blockID; ++var6)
            {
                ;
            }

            if (var6 < 3)
            {
                world.setBlock(x, y, z, Block.blockSnow.blockID, 0, 3);
            }
        }
    }
}
