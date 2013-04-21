package mods.natura.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.natura.common.NaturaContent;
import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;

public class SeedFood extends ItemSeedFood
{
	public int crop;
	public SeedFood(int itemID, int hunger, float saturation, int cropID)
	{
		super(itemID, hunger, saturation, cropID, 0);
		crop = cropID;
        this.setCreativeTab(NaturaTab.tab);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float clickX, float clickY, float clickZ)
    {
        if (side != 1)
        {
            return false;
        }
        else if (player.canPlayerEdit(x, y, z, side, stack) && player.canPlayerEdit(x, y + 1, z, side, stack))
        {
            int i1 = world.getBlockId(x, y, z);
            Block soil = Block.blocksList[i1];

            if (soil != null && soil.canSustainPlant(world, x, y, z, ForgeDirection.UP, (IPlantable) NaturaContent.saguaro) && world.isAirBlock(x, y + 1, z))
            {
                world.setBlock(x, y + 1, z, this.crop, 1, 3);
                --stack.stackSize;
                if (!world.isRemote)
                	world.playAuxSFX(2001, x, y, z, crop);
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
	
	@SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("natura:saguaro_fruit_item");
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		list.add("Usable as food or seeds");
	}
}
