package com.progwml6.natura.world.dimension;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class ChunkNetherite extends Chunk
{
    public ChunkNetherite(World worldIn, ChunkPrimer primer, int x, int z)
    {
        super(worldIn, x, z);
        boolean flag = !worldIn.provider.getHasNoSky();
        ExtendedBlockStorage[] storageArrays = this.getBlockStorageArray();

        for (int j = 0; j < 16; ++j)
        {
            for (int k = 0; k < 16; ++k)
            {
                for (int l = 0; l < 256; ++l)
                {
                    IBlockState iblockstate = primer.getBlockState(j, l, k);

                    if (iblockstate.getMaterial() != Material.AIR)
                    {
                        int i1 = l >> 4;

                        if (storageArrays[i1] == NULL_BLOCK_STORAGE)
                        {
                            storageArrays[i1] = new ExtendedBlockStorage(i1 << 4, flag);
                        }

                        storageArrays[i1].set(j, l & 15, k, iblockstate);
                    }
                }
            }
        }
    }
}
