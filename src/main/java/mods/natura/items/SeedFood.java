package mods.natura.items;

import java.util.List;

import mods.natura.common.NContent;
import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.common.IPlantable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.StatCollector;

public class SeedFood extends ItemSeedFood
{
    public Block crop;

    public SeedFood(int hunger, float saturation, Block cropID)
    {
        super(hunger, saturation, cropID, 0);
        crop = cropID;
        this.setCreativeTab(NaturaTab.tab);
    }

    @Override
    public boolean onItemUse (ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float clickX, float clickY, float clickZ)
    {
        if (side != 1)
        {
            return false;
        }
        else if (player.canPlayerEdit(x, y, z, side, stack) && player.canPlayerEdit(x, y + 1, z, side, stack))
        {
            Block soil = world.getBlock(x, y, z);

            if (soil != null && soil.canSustainPlant(world, x, y, z, ForgeDirection.UP, (IPlantable) NContent.saguaro) && world.isAirBlock(x, y + 1, z))
            {
                world.setBlock(x, y + 1, z, this.crop, 1, 3);
                --stack.stackSize;
                if (!world.isRemote)
                    world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(crop));
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
    public void registerIcons (IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("natura:saguaro_fruit_item");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        list.add(StatCollector.translateToLocal("tooltip.sagurofruit"));
    }
}
