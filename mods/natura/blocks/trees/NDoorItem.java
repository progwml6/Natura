package mods.natura.blocks.trees;
import java.util.List;

import mods.natura.common.NaturaContent;
import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NDoorItem extends Item
{
	public Icon[] icons;
	public String[] textureNames = new String[] { "redwood", "eucalyptus", "hopseed", "sakura", "ghostwood", "bloodwood", "redwoodbark" };
	
    public NDoorItem(int id)
    {
        super(id);
        maxStackSize = 64;
        setCreativeTab(NaturaTab.tab);
        setHasSubtypes(true);
    }
    
    public static final String unlocalizedNames[] = {
    	"redwood", "eucalyptus", "hopseed", "sakura", "ghost", "blood", "redwoodBark"
    };
    
    public String getUnlocalizedName(ItemStack itemstack)
    {
        return (new StringBuilder()).append(unlocalizedNames[itemstack.getItemDamage()]).append("NDoor").toString();
    }

    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float clickX, float clickY, float clickZ)
    {
        if (side != 1)
        {
            return false;
        }
        y++;

        Block block;
        switch (itemstack.getItemDamage())
        {
        case 0: block = NaturaContent.redwoodDoor; break;
        case 1: block = NaturaContent.eucalyptusDoor; break;
        case 2: block = NaturaContent.hopseedDoor; break;
        case 3: block = NaturaContent.sakuraDoor; break;
        case 4: block = NaturaContent.ghostDoor; break;
        case 5: block = NaturaContent.bloodDoor; break;
        case 6: block = NaturaContent.redwoodBarkDoor; break;
        default: block = Block.doorWood; break;
        }
        if (!player.canPlayerEdit(x, y, z, side, itemstack) || !player.canPlayerEdit(x, y + 1, z, side, itemstack))
        {
            return false;
        }
        if (!block.canPlaceBlockAt(world, x, y, z))
        {
            return false;
        }
        else
        {
            int rotate = MathHelper.floor_double((double)(((player.rotationYaw + 180F) * 4F) / 360F) - 0.5D) & 3;
            placeDoorBlock(world, x, y, z, rotate, block);
            itemstack.stackSize--;
            return true;
        }
    }

    public static void placeDoorBlock(World world, int x, int y, int z, int rotate, Block block)
    {
        byte var6 = 0;
        byte var7 = 0;

        if (rotate == 0)
        {
            var7 = 1;
        }

        if (rotate == 1)
        {
            var6 = -1;
        }

        if (rotate == 2)
        {
            var7 = -1;
        }

        if (rotate == 3)
        {
            var6 = 1;
        }

        int var8 = (world.isBlockNormalCube(x - var6, y, z - var7) ? 1 : 0) + (world.isBlockNormalCube(x - var6, y + 1, z - var7) ? 1 : 0);
        int var9 = (world.isBlockNormalCube(x + var6, y, z + var7) ? 1 : 0) + (world.isBlockNormalCube(x + var6, y + 1, z + var7) ? 1 : 0);
        boolean var10 = world.getBlockId(x - var6, y, z - var7) == block.blockID || world.getBlockId(x - var6, y + 1, z - var7) == block.blockID;
        boolean var11 = world.getBlockId(x + var6, y, z + var7) == block.blockID || world.getBlockId(x + var6, y + 1, z + var7) == block.blockID;
        boolean var12 = false;

        if (var10 && !var11)
        {
            var12 = true;
        }
        else if (var9 > var8)
        {
            var12 = true;
        }

        world.setBlock(x, y, z, block.blockID, rotate, 2);
        world.setBlock(x, y + 1, z, block.blockID, 8 | (var12 ? 1 : 0), 2);
        world.notifyBlocksOfNeighborChange(x, y, z, block.blockID);
        world.notifyBlocksOfNeighborChange(x, y + 1, z, block.blockID);
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
            this.icons[i] = iconRegister.registerIcon("natura:"+textureNames[i]+"_door_item");
        }
    }
    
    public void getSubItems (int id, CreativeTabs tab, List list)
	{
		for (int i = 0; i < unlocalizedNames.length; i++)
			list.add(new ItemStack(id, 1, i));
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
    	case 3:
    		list.add("Flowering Cherry");
    		break;
    	case 4:
    		list.add("Pale as a ghost");
    		break;
    	case 5:
    		list.add("Fire-resistant door");
    		break;
    	case 6:
    		list.add("Secret Tunnel");
    		break;
    	}
	}
}
