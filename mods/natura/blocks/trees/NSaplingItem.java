package mods.natura.blocks.trees;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.natura.common.NaturaContent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class NSaplingItem extends ItemBlock
{
	public static final String blockType[] =
	{
	    "redwood", "eucalyptus", "bush", "sakura", "ghost", "blood", "", "", "", "", "", "", "", "", "", ""
	};
	
    public NSaplingItem(int i)
    {
        super(i);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int md)
    {
        return md;
    }

    public Icon getIconFromDamage(int i)
    {
        return NaturaContent.floraSapling.getBlockTextureFromSideAndMetadata(0, i);
    }
    
    @Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
        return (new StringBuilder()).append(blockType[itemstack.getItemDamage()]).append("NSapling").toString();
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
    	switch (stack.getItemDamage())
    	{
    	case 0: 
    		list.add("Plant these in a 7x7");
    		list.add("It takes days to grow");
    		break;
    	case 1:
    		list.add("The pink wood");
    		//list.add("Also makes a good healing potion");
    		break;
    	case 2:
    		list.add("Ascended Glitch");
    		break;
    	case 3:
    		list.add("Flowering Cherry");
    		break;
    	case 4:
    		list.add("Grows on Soul Sand");
    		list.add("Pale as a ghost");
    		break;
    	case 5:
    		list.add("Grows on Soul Sand");
    		list.add("Fire-resistant tree");
    		break;
    	}
	}
}
