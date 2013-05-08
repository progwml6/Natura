package mods.natura.items;

import java.util.List;

import mods.natura.common.NaturaTab;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class StickItem extends Item
{
    public static final String textureNames[] = { "eucalyptus", "sakura", "ghostwood", "redwood", "bloodwood", "hopseed", "maple", "silverbell", "purpleheart", "tiger", "willow", "darkwood",
            "fusewood" };
    public Icon[] icons;

    public StickItem(int par1)
    {
        super(par1);
        this.setHasSubtypes(true);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIconFromDamage (int meta)
    {
        return icons[meta];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons (IconRegister iconRegister)
    {
        this.icons = new Icon[textureNames.length + 1];

        icons[0] = iconRegister.registerIcon("stick");
        for (int i = 0; i < this.textureNames.length; ++i)
        {
            this.icons[i + 1] = iconRegister.registerIcon("natura:" + textureNames[i] + "_stick");
        }
    }

    public void getSubItems (int id, CreativeTabs tab, List list)
    {
        if (tab == getCreativeTab())
            list.add(new ItemStack(id, 1, 0));

        else
            for (int i = 0; i < textureNames.length; i++)
                list.add(new ItemStack(id, 1, i + 1));
    }

    public CreativeTabs[] getCreativeTabs ()
    {
        return new CreativeTabs[] { getCreativeTab(), NaturaTab.tab };
    }

}
