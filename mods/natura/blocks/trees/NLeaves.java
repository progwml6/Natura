package mods.natura.blocks.trees;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mods.natura.common.NContent;
import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NLeaves extends BlockLeaves
{
    int[] adjacentTreeBlocks;

    public NLeaves(int id)
    {
        super(id);
        this.setTickRandomly(true);
        this.setHardness(0.2F);
        this.setLightOpacity(1);
        this.setStepSound(Block.soundGrassFootstep);
        this.setUnlocalizedName("floraLeaves");
        setCreativeTab(CreativeTabs.tabDecorations);
		setBurnProperties(this.blockID, 30, 60);
		this.setCreativeTab(NaturaTab.tab);
    }

    public int getBlockColor()
    {
        double var1 = 0.5D;
        double var3 = 1.0D;
        return ColorizerFoliage.getFoliageColor(var1, var3);
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate()
    {
        return 20;
    }

    /**
     * Returns the color this block should be rendered. Used by leaves.
     */
    public int getRenderColor(int var1)
    {
        return ColorizerFoliage.getFoliageColorBasic();
    }

    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    public int colorMultiplier(IBlockAccess var1, int x, int y, int z)
    {
        int meta = var1.getBlockMetadata(x, y, z);

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
                    int var11 = var1.getBiomeGenForCoords(x + var10, z + var9).getBiomeFoliageColor();
                    var6 += (var11 & 16711680) >> 16;
                    var7 += (var11 & 65280) >> 8;
                    var8 += var11 & 255;
                }
            }

            return (var6 / 9 & 255) << 16 | (var7 / 9 & 255) << 8 | var8 / 9 & 255;
        //}
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(Random var1)
    {
        return var1.nextInt(20) == 0 ? 1 : 0;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public int idDropped(int var1, Random var2, int var3)
    {
        return NContent.floraSapling.blockID;
    }
    
    @Override
    public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
    	if (!par1World.isRemote)
        {
            ArrayList<ItemStack> items = getBlockDropped(par1World, par2, par3, par4, par5, par7);

            for (ItemStack item : items)
            {
                if (par1World.rand.nextFloat() <= par6)
                {
                    this.dropBlockAsItem_do(par1World, par2, par3, par4, item);
                }
            }
        }
    }

	protected String[] textureNames = new String[] { "redwood", "eucalyptus", "hopseed" };
	public Icon[] fastIcons;
	public Icon[] fancyIcons;
	
	@Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int metadata)
    {
    	if (graphicsLevel)
    		return fancyIcons[metadata % 4];
    	else
    		return fastIcons[metadata % 4];
    }
    
    @SideOnly(Side.CLIENT)
	@Override
	public void registerIcons (IconRegister iconRegister)
	{
		this.fastIcons = new Icon[textureNames.length];
		this.fancyIcons = new Icon[textureNames.length];

		for (int i = 0; i < this.fastIcons.length; i++)
		{
			this.fastIcons[i] = iconRegister.registerIcon("natura:" + textureNames[i]+"_leaves_fast");
			this.fancyIcons[i] = iconRegister.registerIcon("natura:" + textureNames[i]+"_leaves_fancy");
		}
	}

    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    
    @Override
    public boolean shouldSideBeRendered(IBlockAccess var1, int var2, int var3, int var4, int var5)
    {
        return this.graphicsLevel ? super.shouldSideBeRendered(var1, var2, var3, var4, var5) : true;
    }

    @SideOnly(Side.CLIENT)

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
    }
    
    public int getDamageValue(World par1World, int par2, int par3, int par4)
    {
        return this.damageDropped(par1World.getBlockMetadata(par2, par3, par4)) % 3;
    }
}
