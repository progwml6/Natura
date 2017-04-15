package com.progwml6.natura.nether.block.rail;

import com.progwml6.natura.library.NaturaRegistry;

import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRailPowered;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBlazeRailPowered extends BlockRailPowered
{
    final boolean isActivatorRail;

    public BlockBlazeRailPowered(boolean isActivatorRail)
    {
        super();
        this.isActivatorRail = isActivatorRail;
        this.setHardness(0.7F);
        this.setSoundType(SoundType.METAL);
        this.setCreativeTab(NaturaRegistry.tabWorld);
    }

    @Override
    public float getRailMaxSpeed(World world, EntityMinecart cart, BlockPos pos)
    {
        return 0.65F;
    }

    @Override
    public void onMinecartPass(World world, EntityMinecart cart, BlockPos pos)
    {
        IBlockState state = world.getBlockState(pos);

        BlockRailBase blockrailbase = (BlockRailBase) state.getBlock();

        EnumRailDirection enumraildirection = blockrailbase.getRailDirection(world, pos, state, cart);

        if (state.getValue(BlockRailPowered.POWERED))
        {
            if (this.isActivatorRail)
            {

            }
            else
            {
                // Start the cart rolling
                if (enumraildirection == BlockRailBase.EnumRailDirection.EAST_WEST)
                {
                    double speed = 0f;

                    if (world.getBlockState(pos.west()).isNormalCube())
                    {
                        speed -= 0.2f;
                    }
                    else if (world.getBlockState(pos.east()).isNormalCube())
                    {
                        speed += 0.2f;
                    }

                    cart.motionZ += speed;
                }
                else if (enumraildirection == BlockRailBase.EnumRailDirection.NORTH_SOUTH)
                {
                    double speed = 0f;

                    if (world.getBlockState(pos.north()).isNormalCube())
                    {
                        speed += 0.2f;
                    }
                    else if (world.getBlockState(pos.south()).isNormalCube())
                    {
                        speed -= 0.2f;
                    }

                    cart.motionX += speed;
                }

                // Then push it along
                if (Math.abs(cart.motionX) < 0.2)
                {
                    cart.motionX *= 1.2;
                }
                else
                {
                    cart.motionX *= 1.05;
                }

                if (Math.abs(cart.motionZ) < 0.2)
                {
                    cart.motionZ *= 1.2;
                }
                else
                {
                    cart.motionZ *= 1.05;
                }
            }
        }
        else if (!this.isActivatorRail)
        {
            cart.motionX *= 0.5;
            cart.motionZ *= 0.5;
        }
    }
}
