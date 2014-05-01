package mods.natura.dimension;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class NetheriteChunk extends Chunk
{

    public NetheriteChunk(World world, Block[] lowerIDs, int chunkX, int chunkZ)
    {
        super(world, chunkX, chunkZ);
        ExtendedBlockStorage[] storage = getBlockStorageArray();
        int height = lowerIDs.length / 256;

        for (int x = 0; x < 16; ++x)
        {
            for (int z = 0; z < 16; ++z)
            {
                for (int y = 0; y < height; ++y)
                {
                    Block id = lowerIDs[x << 11 | z << 7 | y];

                    if (id != null && id != Blocks.air)
                    {
                        int k1 = y >> 4;

                        if (storage[k1] == null)
                        {
                            storage[k1] = new ExtendedBlockStorage(k1 << 4, !world.provider.hasNoSky);
                        }

                        storage[k1].func_150818_a(x, y & 15, z, id);
                    }
                }
            }
        }

        /*for (int x = 0; x < 16; ++x)
        {
            for (int z = 0; z < 16; ++z)
            {
                for (int y = 0; y < height; ++y)
                {
                    int id = upperIDs[x << 11 | z << 7 | y] & 0xFF;

                    if (id != 0)
                    {
                        int k1 = (y >> 4) + 8;

                        if (storage[k1] == null)
                        {
                            storage[k1] = new ExtendedBlockStorage(k1 << 4, !world.provider.hasNoSky);
                        }

                        storage[k1].setExtBlockID(x, y & 15, z, id);
                    }
                }
            }
        }*/
    }

}
