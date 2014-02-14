package mods.natura.items;

import java.util.List;

import mods.natura.common.NaturaTab;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.StatCollector;

public class CactusJuice extends ItemFood
{
    public String[] textureNames = new String[] { "waterdrop" };
    public String[] unlocalizedNames;
    public IIcon[] icons;

    public CactusJuice(boolean flag)
    {
        super(1, 0.1f, flag);
        maxStackSize = 64;
        this.setCreativeTab(NaturaTab.tab);
    }

    public int getMaxItemUseDuration (ItemStack itemstack)
    {
        return 12;
    }

    /*@SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister iconRegister)
    {
    	this.icons = new Icon[textureNames.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = iconRegister.registerIcon("natura:"+textureNames[i]);
        }
    }*/

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons (IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("natura:waterdrop");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        list.add(StatCollector.translateToLocal("tooltip.cactusjuice"));
    }
}
