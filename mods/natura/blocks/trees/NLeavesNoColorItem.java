package mods.natura.blocks.trees;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class NLeavesNoColorItem extends ItemBlock
{
    public static final String blockType[] =
    {
    	 "sakura", "ghost", "blood", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
    };

    public NLeavesNoColorItem(int i)
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

    @Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
        return (new StringBuilder()).append(blockType[itemstack.getItemDamage()]).append("NLeaves").toString();
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
    	switch (stack.getItemDamage())
    	{
    	case 0: 
    		list.add("Flowering Cherry");
    		break;
    	case 1:
    		list.add("Nether Tree");
    		list.add("Fire-resistant leaves");
    		break;
    	case 2:
    		list.add("Nether Tree");
    		list.add("Pale as a ghost");
    		break;
    	}
	}
}
