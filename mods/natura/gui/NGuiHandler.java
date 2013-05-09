package mods.natura.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class NGuiHandler implements IGuiHandler
{
    public static final int craftingGui = 1;
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (ID == craftingGui)
        {
            return new WorkbenchGui(player.inventory, world);
        }
        /*if (ID == furnaceGuiID)
        {
            return new FurnaceGui(player.inventory, (FurnaceLogic) world.getBlockTileEntity(x, y, z));
        }*/
        return null;
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
    {
        if (ID == craftingGui)
        {
            return new WorkbenchContainer(player.inventory, world);
        }
        /*if (ID == furnaceGuiID)
        {
            TileEntity tile = world.getBlockTileEntity(x, y, z);
            if (tile instanceof FurnaceLogic)
                return ((FurnaceLogic)tile).getGuiContainer(player.inventory);
        }*/
        return null;
    }
}
