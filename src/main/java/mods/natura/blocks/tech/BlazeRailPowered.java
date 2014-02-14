package mods.natura.blocks.tech;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailPowered;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.world.World;

public class BlazeRailPowered extends BlockRailPowered
{
    boolean activator;

    public BlazeRailPowered(boolean activator)
    {
        super();
        this.activator = activator;
    }

    @Override
    public float getRailMaxSpeed (World world, EntityMinecart cart, int y, int x, int z)
    {
        return 0.65f;
    }

    /**
     * This function is called by any minecart that passes over this rail.
     * It is called once per update tick that the minecart is on the rail.
     * @param world The world.
     * @param cart The cart on the rail.
     * @param y The rail X coordinate.
     * @param x The rail Y coordinate.
     * @param z The rail Z coordinate.
     */
    public void onMinecartPass (World world, EntityMinecart cart, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta >= 8)
        {
            if (activator)
            {

            }
            else
            {
                //Start the cart rolling
                if (meta == 8)
                {
                    double speed = 0f;
                    if (world.getBlock(x, y, z + 1).isNormalCube())
                        speed += 0.2f;
                    if (world.getBlock(x, y, z - 1).isNormalCube())
                        speed -= 0.2f;
                    cart.motionX += speed;
                }
                else if (meta == 9)
                {
                    double speed = 0f;
                    if (world.getBlock(x + 1, y, z).isNormalCube())
                        speed -= 0.2f;
                    if (world.getBlock(x - 1, y, z).isNormalCube())
                        speed += 0.2f;
                    cart.motionZ += speed;
                }

                //Then push it along
                if (Math.abs(cart.motionX) < 0.2)
                    cart.motionX *= 1.2;
                else
                    cart.motionX *= 1.05;
                if (Math.abs(cart.motionZ) < 0.2)
                    cart.motionZ *= 1.2;
                else
                    cart.motionZ *= 1.05;
            }
        }
        else if (!activator)
        {
            cart.motionX *= 0.5;
            cart.motionZ *= 0.5;
        }
    }
}
