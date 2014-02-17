package mods.natura.blocks.crops;

import java.util.List;
import java.util.Random;

import mods.natura.common.NaturaTab;
import mods.natura.worldgen.GlowshroomGenBlueGreen;
import mods.natura.worldgen.GlowshroomGenPurple;
import net.minecraft.block.BlockMushroom;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Glowshroom extends BlockMushroom
{
    IIcon[] icons;
    String[] textureNames = { "green", "purple", "blue" };

    public Glowshroom()
    {
        super();
        this.setStepSound(soundTypeGrass);
        this.setCreativeTab(NaturaTab.tab);
    }

    @Override
    public void updateTick (World world, int x, int y, int z, Random random)
    {
        if (random.nextInt(25) == 0)
        {
            byte b0 = 4;
            int l = 5;
            int posX;
            int posY;
            int posZ;

            for (posX = x - b0; posX <= x + b0; ++posX)
            {
                for (posZ = z - b0; posZ <= z + b0; ++posZ)
                {
                    for (posY = y - 1; posY <= y + 1; ++posY)
                    {
                        if (world.getBlock(posX, posY, posZ) == this)
                        {
                            --l;

                            if (l <= 0)
                            {
                                return;
                            }
                        }
                    }
                }
            }

            posX = x + random.nextInt(3) - 1;
            posY = y + random.nextInt(2) - random.nextInt(2);
            posZ = z + random.nextInt(3) - 1;

            for (int l1 = 0; l1 < 4; ++l1)
            {
                if (world.isAirBlock(posX, posY, posZ) && this.canBlockStay(world, posX, posY, posZ))
                {
                    x = posX;
                    y = posY;
                    z = posZ;
                }

                posX = x + random.nextInt(3) - 1;
                posY = y + random.nextInt(2) - random.nextInt(2);
                posZ = z + random.nextInt(3) - 1;
            }

            if (world.isAirBlock(posX, posY, posZ) && this.canBlockStay(world, posX, posY, posZ))
            {
                int meta = world.getBlockMetadata(x, y, z);
                world.setBlock(posX, posY, posZ, this, meta, 3);
            }
        }
    }

    public boolean fertilizeMushroom (World world, int x, int y, int z, Random random)
    {
        if (world.isRemote)
            return false;

        int meta = world.getBlockMetadata(x, y, z);
        world.setBlockToAir(x, y, z);
        WorldGenerator obj = null;

        if (meta == 0)
        {
            obj = new GlowshroomGenBlueGreen(true, 0);
        }
        if (meta == 1)
        {
            obj = new GlowshroomGenPurple(true);
        }
        if (meta == 2)
        {
            obj = new GlowshroomGenBlueGreen(true, 1);
        }

        /*if (this.blockID == Block.mushroomBrown.blockID)
        {
            worldgenbigmushroom = new WorldGenBigMushroom(0);
        }
        else if (this.blockID == Block.mushroomRed.blockID)
        {
            worldgenbigmushroom = new WorldGenBigMushroom(1);
        }*/

        if (obj != null && obj.generate(world, random, x, y, z))
        {
            return true;
        }
        else
        {
            world.setBlock(x, y, z, this, meta, 3);
            return false;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons (IIconRegister iconRegister)
    {
        this.icons = new IIcon[textureNames.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = iconRegister.registerIcon("natura:mushroom_" + textureNames[i]);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon (int side, int meta)
    {
        meta = meta % 3;
        return icons[meta];
    }

    @Override
    public int damageDropped (int meta)
    {
        return meta;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks (Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i < icons.length; i++)
            par3List.add(new ItemStack(par1, 1, i));
    }
}
