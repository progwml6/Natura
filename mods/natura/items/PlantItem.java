package mods.natura.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.natura.common.NCraftingItem;
import mods.natura.common.NaturaTab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class PlantItem extends NCraftingItem
{
    public PlantItem(int id)
    {
        super(id, new String[] { "barley.plant", "barley.flour", "wheat.flour", "cotton.plant", "powder.sulfur" }, new String[] { "barley_plant", "barley_flour", "wheat_flour", "cotton_plant", "sulfur" });
        this.setCreativeTab(NaturaTab.tab);
    }
    
    @Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
        return (new StringBuilder()).append("item.").append(unlocalizedNames[itemstack.getItemDamage()]).toString();
    }
    
    @Override
	@SideOnly(Side.CLIENT)
	public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
    	switch (stack.getItemDamage())
    	{
    	case 0: 
    		list.add("Similar to wheat, it grows in the wild");
    		break;
    	case 1:
    	case 2:
    		list.add("Bake me to make bread");
    		list.add("Also used in baking cake");
    		break;
    	case 3:
    		list.add("The source of all string and wool");
    		break;
    	case 4:
    		list.add("2x2 converts into gunpowder");
    		break;
    	}
	}
}
