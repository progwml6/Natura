package mods.natura.blocks.overrides;

import java.util.List;

import mods.natura.common.NContent;
import net.minecraft.block.BlockBookshelf;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AlternateBookshelf extends BlockBookshelf
{
    IIcon[] icons;

    public AlternateBookshelf()
    {
        super();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon (int side, int metadata)
    {
        if (side == 0 || side == 1)
            return NContent.planks.getIcon(side, metadata);
        return icons[metadata];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons (IIconRegister iconRegister)
    {
        super.registerBlockIcons(iconRegister);
        this.icons = new IIcon[NContent.woodTextureNames.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = iconRegister.registerIcon("natura:" + NContent.woodTextureNames[i] + "_bookshelf");
        }
    }

    @Override
    public int damageDropped (int meta)
    {
        return 0;
    }

    @Override
    public boolean renderAsNormalBlock ()
    {
        return false;
    }

    @Override
    public float getEnchantPowerBonus (World world, int x, int y, int z)
    {
        return 1f;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks (Item par1, CreativeTabs tabs, List list)
    {
        for (int i = 0; i < icons.length; i++)
            list.add(new ItemStack(par1, 1, i));
    }

}
