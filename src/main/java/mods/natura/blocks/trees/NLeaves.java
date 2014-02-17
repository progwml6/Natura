package mods.natura.blocks.trees;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mods.natura.common.NContent;
import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NLeaves extends BlockLeaves
{
    int[] adjacentTreeBlocks;

    public NLeaves()
    {
        super();
        this.setTickRandomly(true);
        this.setHardness(0.2F);
        this.setLightOpacity(1);
        this.setStepSound(Block.soundTypeGrass);
        this.setBlockName("floraLeaves");
        setCreativeTab(CreativeTabs.tabDecorations);
        // TODO 1.7 Where the heck did this go? setBurnProperties(this, 30, 60);
        this.setCreativeTab(NaturaTab.tab);
    }

    @Override
    public int getBlockColor ()
    {
        double var1 = 0.5D;
        double var3 = 1.0D;
        return ColorizerFoliage.getFoliageColor(var1, var3);
    }

    /**
     * Returns the color this block should be rendered. Used by leaves.
     */
    @Override
    public int getRenderColor (int var1)
    {
        return ColorizerFoliage.getFoliageColorBasic();
    }

    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    @Override
    public int colorMultiplier (IBlockAccess var1, int x, int y, int z)
    {
        //int meta = var1.getBlockMetadata(x, y, z);

        /*if (meta == 1)
        {
            return 16777215;
        }
        else if (meta == 2)
        {
            return 7579477;
        }
        else
        {*/
        int var6 = 0;
        int var7 = 0;
        int var8 = 0;

        for (int var9 = -1; var9 <= 1; ++var9)
        {
            for (int var10 = -1; var10 <= 1; ++var10)
            {
                int var11 = var1.getBiomeGenForCoords(x + var10, z + var9).getBiomeFoliageColor(x, y, z);
                var6 += (var11 & 16711680) >> 16;
                var7 += (var11 & 65280) >> 8;
                var8 += var11 & 255;
            }
        }

        return (var6 / 9 & 255) << 16 | (var7 / 9 & 255) << 8 | var8 / 9 & 255;
        //}
    }

    @Override
    public void updateTick (World world, int x, int y, int z, Random random)
    {
        if (!world.isRemote)
        {
            int meta = world.getBlockMetadata(x, y, z);

            if ((meta & 4) == 0)
            {
                boolean nearbyTree = false;
                byte range = 4;
                for (int posX = x - range; posX <= x + range; posX++)
                {
                    for (int posY = y - range; posY <= y + range; posY++)
                    {
                        for (int posZ = z - range; posZ <= z + range; posZ++)
                        {
                            Block block = world.getBlock(posX, posY, posZ);
                            if (block != null && block.canSustainLeaves(world, posX, posY, posZ))
                                nearbyTree = true;
                        }
                    }
                }

                if (!nearbyTree)
                    this.removeLeaves(world, x, y, z);
            }
        }
    }

    public void removeLeaves (World world, int x, int y, int z)
    {
        this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
        world.setBlock(x, y, z, Blocks.air, 0, 7);
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped (Random var1)
    {
        return var1.nextInt(20) == 0 ? 1 : 0;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public Item getItemDropped (int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return NContent.floraSapling.getItem();
    }

    @Override
    public void dropBlockAsItemWithChance (World world, int x, int y, int z, int metadata, float par6, int fortune)
    {
        if (!world.isRemote)
        {
            ArrayList<ItemStack> items = getDrops(world, x, y, z, metadata, fortune);

            for (ItemStack item : items)
            {
                if (world.rand.nextFloat() <= par6)
                {
                    // TODO 1.7 used to be dropBlockAsItem_do is this right?
                    this.dropBlockAsItem(world, x, y, z, item);
                }
            }
        }
    }

    public IIcon[] fastIcons;
    public IIcon[] fancyIcons;

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon (int side, int metadata)
    {
        int meta = metadata % 4;
        if (metadata == 3)
            meta = 0;

        if (field_150121_P)
            return fancyIcons[meta];
        else
            return fastIcons[meta];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons (IIconRegister iconRegister)
    {
        String[] textureNames = new String[] { "redwood", "eucalyptus", "hopseed" };
        this.fastIcons = new IIcon[textureNames.length];
        this.fancyIcons = new IIcon[textureNames.length];

        for (int i = 0; i < this.fastIcons.length; i++)
        {
            this.fastIcons[i] = iconRegister.registerIcon("natura:" + textureNames[i] + "_leaves_fast");
            this.fancyIcons[i] = iconRegister.registerIcon("natura:" + textureNames[i] + "_leaves_fancy");
        }
    }

    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */

    @Override
    public boolean shouldSideBeRendered (IBlockAccess var1, int var2, int var3, int var4, int var5)
    {
        return this.field_150121_P ? super.shouldSideBeRendered(var1, var2, var3, var4, var5) : true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks (Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
    }

    @Override
    public int getDamageValue (World par1World, int par2, int par3, int par4)
    {
        return this.damageDropped(par1World.getBlockMetadata(par2, par3, par4)) % 3;
    }

    public int getLightOpacity (World world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z) % 4;
        if (meta == 0)
        {
            return 255;
        }
        return this.getLightOpacity();
    }

    @Override
    public String[] func_150125_e ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean getRenderLevel ()
    {
        return this.field_150121_P;
    }
}
