package mods.natura.items.tools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.natura.common.NaturaTab;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class NaturaBow extends ItemBow
{
    public static final String[] drawNames = new String[] {"_bow_drawn_0", "_bow_drawn_1", "_bow_drawn_2"};
    Icon[] icons;
    String woodType;
    
	public NaturaBow(int id, int damage, String type)
	{
		super(id);
        this.setMaxDamage(damage);
        this.woodType = type;
        this.setCreativeTab(NaturaTab.tab);
	}

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("natura:"+woodType+"_bow");
        this.icons = new Icon[bowPullIconNameArray.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = par1IconRegister.registerIcon("natura:"+woodType+drawNames[i]);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
    	if (usingItem != null)
    	{
    		int time = 72000 - useRemaining;
    		if (time < 8)
    			return icons[0];
    		if (time < 14)
    			return icons[1];
    		return icons[2];
    	}
        return getIcon(stack, renderPass);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getItemIconForUseDuration(int par1)
    {
        return this.icons[par1];
    }
}
