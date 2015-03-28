package mods.natura.blocks;

import java.util.Random;

import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
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
    public void updateTick (World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        int meta;

        if (worldIn.getLightFor(EnumSkyBlock.BLOCK, pos) > 11)
        {
            meta = this.getMetaFromState(state);

            if (meta > 0)
            {
                worldIn.setBlockState(pos, this.getStateFromMeta(meta - 1), 4);
            }
            else
            {
                worldIn.setBlockToAir(pos);
            }
        }

        else if (worldIn.getWorldInfo().isRaining() && rand.nextInt(10) == 0)
        {
            meta = this.getMetaFromState(state);

            if (meta < 8)
            {
                worldIn.setBlockState(pos, this.getStateFromMeta(meta + 1), 2);
            }
            else
            {
                this.scanHeight(worldIn, pos, rand);
            }
        }
    }

    void scanHeight (World world, BlockPos pos, Random random)
    {
        if (world.isAirBlock(pos.up()))
        {
            int var6;

            for (var6 = 1; world.getBlockState(pos.down(var6)).getBlock() == Blocks.snow; ++var6)
            {
                ;
            }

            if (var6 < 3)
            {
                world.setBlockState(pos, Blocks.snow.getDefaultState().withProperty(LAYERS, 0), 3);
            }
        }
    }
}
