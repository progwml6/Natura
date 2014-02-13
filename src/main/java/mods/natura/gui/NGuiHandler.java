package mods.natura.gui;

import mods.natura.blocks.tech.NetherrackFurnaceLogic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class NGuiHandler implements IGuiHandler
{
    public static final int craftingGui = 1;
    public static final int furnaceGui = 2;

    @Override
    public Object getClientGuiElement (int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (ID == craftingGui)
        {
            return new WorkbenchGui(player.inventory, world);
        }
        if (ID == furnaceGui)
        {
            return new FurnaceGui(player.inventory, (NetherrackFurnaceLogic) world.getBlockTileEntity(x, y, z));
        }
        return null;
    }

    @Override
    public Object getServerGuiElement (int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (ID == craftingGui)
        {
            return new WorkbenchContainer(player.inventory, world);
        }
        if (ID == furnaceGui)
        {
            return new FurnaceContainer(player.inventory, (NetherrackFurnaceLogic) world.getBlockTileEntity(x, y, z));
        }
        return null;
    }
}
