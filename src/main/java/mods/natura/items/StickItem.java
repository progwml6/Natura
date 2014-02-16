package mods.natura.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class StickItem extends Item
{
    public static final String textureNames[] = { "eucalyptus", "sakura", "ghostwood", "redwood", "bloodwood", "hopseed", "maple", "silverbell", "purpleheart", "tiger", "willow", "darkwood",
            "fusewood" };
    public IIcon[] icons;

    public StickItem()
    {
        super();
        this.setHasSubtypes(true);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconFromDamage (int meta)
    {
        if (meta >= textureNames.length)
            return Items.leather_helmet.getIconFromDamage(meta);
        return icons[meta];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons (IIconRegister iconRegister)
    {
        this.icons = new IIcon[textureNames.length];
        for (int i = 0; i < StickItem.textureNames.length; ++i)
        {
            this.icons[i] = iconRegister.registerIcon("natura:" + textureNames[i] + "_stick");
        }
    }

    @Override
	public void getSubItems (Item id, CreativeTabs tab, List list)
    {
        for (int i = 0; i < textureNames.length; i++)
            list.add(new ItemStack(id, 1, i));
    }

    /*public CreativeTabs[] getCreativeTabs ()
    {
        return new CreativeTabs[] { getCreativeTab(), NaturaTab.tab };
    }*/

}
