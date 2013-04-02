package mods.natura.items;

import java.util.List;

import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NaturaSeeds extends ItemSeeds
{
	public String[] textureNames = new String[] { "barley", "cotton" };
	public Icon[] icons;

    public int blockType;
	
    public NaturaSeeds(int id, int cropID, int soilID)
    {
        super(id, cropID, soilID);
        blockType = cropID;
        this.setCreativeTab(NaturaTab.tab);
        this.setHasSubtypes(true);
    }
    
    @SideOnly(Side.CLIENT)
	@Override
    public void updateIcons(IconRegister iconRegister)
    {
		this.icons = new Icon[textureNames.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = iconRegister.registerIcon("natura:"+textureNames[i]+"_seeds");
        }
    }
    
    public void getSubItems(int id, CreativeTabs tab, List list)
    {
		for (int i = 0; i < textureNames.length; i++)
			list.add(new ItemStack(id, 1, i));
    }
    
    @SideOnly(Side.CLIENT)
	@Override
	public Icon getIconFromDamage(int meta)
	{
		return icons[meta];
	}
    
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int xPos, int yPos, int zPos, int side, float xClick, float yClick, float zClick)
    {
        if (side != 1)
        {
            return false;
        }
        else if (player.canPlayerEdit(xPos, yPos, zPos, side, stack) && player.canPlayerEdit(xPos, yPos + 1, zPos, side, stack))
        {
            int i1 = world.getBlockId(xPos, yPos, zPos);
            Block soil = Block.blocksList[i1];

            if (soil != null && soil.canSustainPlant(world, xPos, yPos, zPos, ForgeDirection.UP, this) && world.isAirBlock(xPos, yPos + 1, zPos))
            {
                world.setBlock(xPos, yPos + 1, zPos, this.blockType, stack.getItemDamage()*4, 3);
                --stack.stackSize;
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
    
    public String getUnlocalizedName(ItemStack stack)
	{
		int arr = MathHelper.clamp_int(stack.getItemDamage(), 0, textureNames.length);
		return "item." +textureNames[arr]+".seed";
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
    		list.add("A great source of string and wool");
    		break;
    	}
	}
}
