package mods.natura.blocks.trees;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mods.natura.Natura;
import mods.natura.common.NContent;
import mods.natura.common.NaturaTab;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NLeavesNocolor extends NLeaves
{
    public NLeavesNocolor()
    {
        super();
        this.setCreativeTab(NaturaTab.tab);
    }

    @SideOnly(Side.CLIENT)
    public int getBlockColor ()
    {
        return 16777215;
    }

    @SideOnly(Side.CLIENT)
    /**
     * Returns the color this block should be rendered. Used by leaves.
     */
    public int getRenderColor (int par1)
    {
        return 16777215;
    }

    @SideOnly(Side.CLIENT)
    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    public int colorMultiplier (IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return 16777215;
    }

    public int getFlammability (IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face)
    {
        return metadata % 4 == 0 ? getFlammability(world, x, y, z, face) : 0;
    }

    public int getFireSpreadSpeed (World world, int x, int y, int z, int metadata, ForgeDirection face)
    {
        return metadata % 4 == 0 ? getFireSpreadSpeed(world, x, y, z, face) : 0;
    }

    public int damageDropped (int meta)
    {
        if (meta % 4 == 3)
            return 4;
        return (meta & 3) + 3;
    }

    @Override
    public Item getItemDropped (int meta, Random random, int fortune)
    {
        if (meta % 4 == 3)
            return Item.getItemFromBlock(NContent.rareSapling);
        return Item.getItemFromBlock(NContent.floraSapling);
    }

    @Override
    public ArrayList<ItemStack> getDrops (World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> ret = super.getDrops(world, x, y, z, metadata, fortune);
        
        if (metadata % 4 == 2)
        {
            if (fortune > 3 || Natura.random.nextInt(40 - fortune * 10) == 0)
            {
                ret.add(new ItemStack(Items.redstone));
            }

        }
            
        return ret;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons (IIconRegister iconRegister)
    {
        String[] textureNames = new String[] { "sakura", "ghostwood", "bloodwood", "willow" };
        this.fastIcons = new IIcon[textureNames.length];
        this.fancyIcons = new IIcon[textureNames.length];

        for (int i = 0; i < this.fastIcons.length; i++)
        {
            this.fastIcons[i] = iconRegister.registerIcon("natura:" + textureNames[i] + "_leaves_fast");
            this.fancyIcons[i] = iconRegister.registerIcon("natura:" + textureNames[i] + "_leaves_fancy");
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon (int side, int metadata)
    {
        int meta = metadata % 4;

        if (field_150121_P)
            return fancyIcons[meta];
        else
            return fastIcons[meta];
    }

    @Override
    public void getSubBlocks (Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
        par3List.add(new ItemStack(par1, 1, 3));
    }

    public int getLightOpacity (World world, int x, int y, int z)
    {
        return super.getLightOpacity(world, x, y, z);//lightOpacity[blockID];
    }
}