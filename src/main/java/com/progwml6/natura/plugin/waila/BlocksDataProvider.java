package com.progwml6.natura.plugin.waila;

import static com.progwml6.natura.nether.NaturaNether.netherLeaves;
import static com.progwml6.natura.nether.NaturaNether.netherLeaves2;
import static com.progwml6.natura.nether.NaturaNether.netherLog;
import static com.progwml6.natura.nether.NaturaNether.netherSapling;
import static com.progwml6.natura.overworld.NaturaOverworld.overworldLeaves;
import static com.progwml6.natura.overworld.NaturaOverworld.overworldLeaves2;
import static com.progwml6.natura.overworld.NaturaOverworld.overworldLog;
import static com.progwml6.natura.overworld.NaturaOverworld.overworldLog2;
import static com.progwml6.natura.overworld.NaturaOverworld.overworldSapling;
import static com.progwml6.natura.overworld.NaturaOverworld.overworldSapling2;
import static com.progwml6.natura.overworld.NaturaOverworld.redwoodLeaves;
import static com.progwml6.natura.overworld.NaturaOverworld.redwoodSapling;

import java.util.List;

import com.progwml6.natura.common.NaturaPulse;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlocksDataProvider extends NaturaPulse implements IWailaDataProvider
{
    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        Block block = accessor.getBlock();

        if (isOverworldLoaded())
        {
            if (block == overworldLog || block == overworldLog2 || block == overworldLeaves)
            {
                return new ItemStack(block, 1, accessor.getMetadata() % 4);
            }

            if (block == overworldLeaves || block == overworldLeaves2 || block == redwoodLeaves)
            {
                return new ItemStack(block, 1, accessor.getMetadata() % 4);
            }

            if (block == overworldSapling || block == overworldSapling2 || block == redwoodSapling)
            {
                return new ItemStack(block, 1, accessor.getMetadata() % 8);
            }
        }

        if (isNetherLoaded())
        {
            if (block == netherLog)
            {
                return new ItemStack(block, 1, accessor.getMetadata() % 4);
            }

            if (block == netherLeaves || block == netherLeaves2)
            {
                return new ItemStack(block, 1, accessor.getMetadata() % 4);
            }

            if (block == netherSapling)
            {
                return new ItemStack(block, 1, accessor.getMetadata() % 8);
            }
        }

        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos)
    {
        return tag;
    }
}
