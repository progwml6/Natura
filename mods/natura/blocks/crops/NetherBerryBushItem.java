package mods.natura.blocks.crops;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.natura.common.NaturaContent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;

public class NetherBerryBushItem extends ItemBlock
{

    public NetherBerryBushItem(int i)
    {
        super(i);
        //setMaxDamage(0);
        setHasSubtypes(true);
    }
    
    @Override
    public int getMetadata(int meta)
    {
        return meta % 4;
    }
    
    /* Place bushes on dirt, grass, or other bushes only */
    @Override    
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10)
    {
        if (side != 1)
            return false;
        
        else if (player.canPlayerEdit(x, y, z, side, stack) && player.canPlayerEdit(x, y + 1, z, side, stack))
        {
            Block block = Block.blocksList[world.getBlockId(x, y, z)];

            if (block != null && (block.canSustainPlant(world, x, y, z, ForgeDirection.UP, (IPlantable) NaturaContent.netherBerryBush) || block == Block.netherrack) && world.isAirBlock(x, y + 1, z))
            {
                world.setBlock(x, y + 1, z, NaturaContent.netherBerryBush.blockID, stack.getItemDamage() % 4, 3);
                if (!player.capabilities.isCreativeMode)
                	stack.stackSize--;
                if (!world.isRemote)
                	world.playAuxSFX(2001, x, y, z, NaturaContent.netherBerryBush.blockID);
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }

    /* Block name in inventory */
    @Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
        return (new StringBuilder()).append("block.bush.berry.").append(blockType[itemstack.getItemDamage()]).toString();
    }
    public static final String blockType[] =
    {
        "blight", "dusk", "sky", "sting", "blight", "dusk", "sky", "sting",
        "blight", "dusk", "sky", "sting", "blight", "dusk", "sky", "sting"
    };
    
    @Override
	@SideOnly(Side.CLIENT)
	public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		list.add("A little bit evil");
    	switch (stack.getItemDamage() % 4)
    	{
    	case 0: 
    		list.add("Killer healing");
    		break;
    	case 1:
    		list.add("Visible night");
    		break;
    	case 2:
    		list.add("Slow dive");
    		break;
    	case 3:
    		list.add("Hit like a truck");
    		break;
    	}
	}
}
