package mods.natura.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PieItem extends BerryItem 
{

	public PieItem(int id, int heal) {
		super(id, heal);
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons (IconRegister iconRegister)
    {
        this.icons = new Icon[textureNames.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = iconRegister.registerIcon("natura:pie_" + textureNames[i]);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        list.add("Fresh, and delicious!");
    }

    /* Name override */
    @Override
    public String getUnlocalizedName (ItemStack itemstack)
    {
        return (new StringBuilder()).append("item.pie.").append(textureNames[itemstack.getItemDamage()]).toString();
    }

}
