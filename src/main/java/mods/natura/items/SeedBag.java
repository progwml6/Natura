package mods.natura.items;

import java.util.List;

import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SeedBag extends Item
{
    Block crop;
    int cropMetadata;
    String textureName;

    public SeedBag(Block block, int cMD, String texture)
    {
        super();
        crop = block;
        cropMetadata = cMD;
        textureName = texture;
        this.setCreativeTab(NaturaTab.tab);
    }

    @Override
    public boolean onItemUse (ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10)
    {
        if (side != 1)
            return false;

        boolean planted = false;
        for (int posX = x - 1; posX <= x + 1; posX++)
        {
            for (int posZ = z - 1; posZ <= z + 1; posZ++)
            {
                if (player.canPlayerEdit(posX, y, posZ, side, stack) && player.canPlayerEdit(posX, y + 1, posZ, side, stack))
                {
                    Block block = world.getBlock(posX, y, posZ);

                    if (block != null && block.canSustainPlant(world, posX, y, posZ, ForgeDirection.UP, (IPlantable) crop) && world.isAirBlock(posX, y + 1, posZ))
                    {
                        world.setBlock(posX, y + 1, posZ, crop, cropMetadata, 3);
                        planted = true;
                    }
                }
            }
        }
        if (planted)
        {
            if (!player.capabilities.isCreativeMode)
                stack.stackSize--;
            if (!world.isRemote)
                world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(crop));
        }
        return planted;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons (IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("natura:seedbag_" + textureName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        list.add(StatCollector.translateToLocal("tooltip.seedbag"));
    }
}
