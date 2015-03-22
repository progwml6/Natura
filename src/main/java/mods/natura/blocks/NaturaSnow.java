package mods.natura.blocks;

import java.util.Random;

import net.minecraft.block.BlockSnow;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class NaturaSnow extends BlockSnow
{

    public NaturaSnow()
    {
        super();
    }

    @Override
    public void updateTick (World world, BlockPos pos, Random random)
    {
        int meta;

        if (world.getSavedLightValue(EnumSkyBlock.Block, pos) > 11)
        {
            meta = world.getBlockMetadata(pos);

            if (meta > 0)
            {
                world.setBlockMetadataWithNotify(pos, meta - 1, 4);
            }
            else
            {
                world.setBlockToAir(pos);
            }
        }

        else if (world.getWorldInfo().isRaining() && random.nextInt(10) == 0)
        {
            meta = world.getBlockMetadata(pos);

            if (meta < 8)
            {
                world.setBlockMetadataWithNotify(pos, meta + 1, 2);
            }
            else
            {
                this.scanHeight(world, pos, random);
            }
        }
    }

    void scanHeight (World world, int x, int y, int z, Random random)
    {
        if (world.isAirBlock(x, y + 1, z))
        {
            int var6;

            for (var6 = 1; world.getBlock(x, y - var6, z) == Blocks.snow; ++var6)
            {
                ;
            }

            if (var6 < 3)
            {
                world.setBlock(x, y, z, Blocks.snow, 0, 3);
            }
        }
    }
}
