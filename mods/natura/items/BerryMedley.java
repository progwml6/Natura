package mods.natura.items;

import java.util.List;

import mods.natura.common.NaturaTab;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BerryMedley extends ItemFood
{
	public Icon[] icons;
	public String[] textureNames = new String[] { "medley" };
	
    public BerryMedley(int id, int heal)
    {
        super(id, heal, 1.4F, false);
        setHasSubtypes(true);
        setMaxDamage(0);
        this.setCreativeTab(NaturaTab.tab);
        this.setAlwaysEdible();
    }
    
    @Override
    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        ItemStack stack = super.onEaten(par1ItemStack, par2World, par3EntityPlayer);
        
        if (!par3EntityPlayer.capabilities.isCreativeMode)
        {
            if (par1ItemStack.stackSize <= 0)
            {
                return new ItemStack(Item.bowlEmpty);
            }

            par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Item.bowlEmpty));
        }
        
        return stack;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack itemstack)
    {
        return 32;
    }

    @SideOnly(Side.CLIENT)
	@Override
	public Icon getIconFromDamage(int meta)
	{
		return icons[meta];
	}
	
	@SideOnly(Side.CLIENT)
	@Override
    public void registerIcons(IconRegister iconRegister)
    {
		this.icons = new Icon[textureNames.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = iconRegister.registerIcon("natura:berry_"+textureNames[i]);
        }
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		list.add("An irresistable combination of berries");
	}

    /* Name override */
    @Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
        return (new StringBuilder()).append("item.berry.").append(textureNames[itemstack.getItemDamage()]).toString();
    }
}
