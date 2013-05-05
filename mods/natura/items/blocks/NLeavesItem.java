package mods.natura.items.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class NLeavesItem extends ItemBlock
{
    public static final String blockType[] =
    {
        "redwood", "eucalyptus", "bush", "", "", "", "", "", "", "", "", "", "", "", "", ""
    };

	//TODO: Have names tied to this item
    public NLeavesItem(int i)
    {
        super(i);
        setMaxDamage(0);
        setHasSubtypes(true);
        //MinecraftForgeClient.registerCustomItemRenderer(mod_NSoma.floraLeaves.blockID, this);
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
    		list.add("Giant Sequoia");
    		break;
    	case 1:
    		list.add("The pink wood");
    		break;
    	case 2:
    		list.add("Ascended Glitch");
    		break;
    	}
	}
}
