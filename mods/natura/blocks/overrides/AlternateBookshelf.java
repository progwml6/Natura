package mods.natura.blocks.overrides;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.natura.common.NContent;
import net.minecraft.block.BlockBookshelf;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class AlternateBookshelf extends BlockBookshelf
{
    Icon[] icons;

    public AlternateBookshelf(int id)
    {
        super(id);
    }

    @SideOnly(Side.CLIENT)
    public Icon getIcon (int side, int metadata)
    {
        if (side == 0 || side == 1)
            return NContent.planks.getIcon(side, metadata);
        return icons[metadata];
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons (IconRegister iconRegister)
    {
        super.registerIcons(iconRegister);
        this.icons = new Icon[NContent.woodTextureNames.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = iconRegister.registerIcon("natura:" + NContent.woodTextureNames[i] + "_bookshelf");
        }
    }

    @Override
    public int damageDropped (int meta)
    {
        return meta;
    }
    
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    @Override
    public float getEnchantPowerBonus(World world, int x, int y, int z)
    {
        return 1f;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks (int par1, CreativeTabs tabs, List list)
    {
        for (int i = 0; i < icons.length; i++)
            list.add(new ItemStack(par1, 1, i));
    }

}
