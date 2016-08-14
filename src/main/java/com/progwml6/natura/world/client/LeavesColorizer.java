package com.progwml6.natura.world.client;

import com.progwml6.natura.world.block.logs.BlockOverworldLog;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;

public class LeavesColorizer
{
    public static int mapleColor = 0xcc5718;

    public static int silverbellColor = 10079569;

    public static int basicColor = 0xffffff;

    public static int getColorStatic(BlockOverworldLog.LogType type)
    {
        if (type == BlockOverworldLog.LogType.MAPLE)
        {
            return LeavesColorizer.mapleColor;
        }
        else if (type == BlockOverworldLog.LogType.PURPLEHEART)
        {
            return LeavesColorizer.silverbellColor;
        }

        return LeavesColorizer.basicColor;
    }

    public static int getColorForPos(IBlockAccess world, BlockPos pos, BlockOverworldLog.LogType type)
    {
        if (type == BlockOverworldLog.LogType.MAPLE)
        {
            return LeavesColorizer.mapleColor;
        }
        else if (type == BlockOverworldLog.LogType.PURPLEHEART)
        {
            return BiomeColorHelper.getFoliageColorAtPos(world, pos) + 0x222222;
        }
        return LeavesColorizer.basicColor;
    }
}
