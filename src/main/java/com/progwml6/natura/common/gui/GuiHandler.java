package com.progwml6.natura.common.gui;

import com.progwml6.natura.common.GuiIDs;
import com.progwml6.natura.common.gui.client.WorkbenchGui;
import com.progwml6.natura.common.gui.common.WorkbenchContainer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (ID == GuiIDs.CRAFTING_TABLE)
        {
            return new WorkbenchContainer(player.inventory, world, new BlockPos(x, y, z));
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (ID == GuiIDs.CRAFTING_TABLE)
        {
            return new WorkbenchGui(player.inventory, world, new BlockPos(x, y, z));
        }

        return null;
    }
}
