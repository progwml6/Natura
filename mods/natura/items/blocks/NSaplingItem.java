package mods.natura.items.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.natura.common.NContent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class NSaplingItem extends ItemBlock
{
	public static final String blockType[] =
	{
	    "redwood", "eucalyptus", "bush", "sakura", "ghost", "blood", "darkwood", "fusewood", "", "", "", "", "", "", "", "", ""
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
        return NContent.floraSapling.getIcon(0, i);
    }
    
    @Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
        return (new StringBuilder()).append("block.sapling.").append(blockType[itemstack.getItemDamage()]).toString();
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
    		break;
    	case 2:
    		list.add("Ascended Glitch");
    		break;
    	case 3:
    		list.add("Flowering Cherry");
    		break;
    	case 4:
    		list.add("Pale as a ghost");
    		break;
    	case 5:
    		list.add("Lava-resistant");
            list.add("Grows on the ceiling");
    		break;
    	case 6:
            list.add("Produces chalky apples");
            break;
        case 7:
            list.add("Explosive personality");
            break;
    	}
	}
}
