package com.progwml6.natura.world.client;

import com.progwml6.natura.world.block.logs.BlockOverworldLog;
import com.progwml6.natura.world.block.logs.BlockOverworldLog2;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;

public class LeavesColorizer
{
    public static int mapleColor = 0xcc5718;

    public static int purpleheartColor = 10079569;

    public static int noColor = 16777215;

    public static int leaves2Color = 7842607;

    public static int leavesColor = 0xffffff;

    public static int getOverworldColorStatic(BlockOverworldLog.LogType type)
    {
        switch (type)
        {
        case MAPLE:
            return LeavesColorizer.mapleColor;
        case PURPLEHEART:
            return LeavesColorizer.purpleheartColor;
        case SILVERBELL:
            return LeavesColorizer.leavesColor;
        case TIGER:
            return LeavesColorizer.leavesColor;
        default:
            return LeavesColorizer.leavesColor;
        }
    }

    public static int getOverworldColorForPos(IBlockAccess access, BlockPos pos, BlockOverworldLog.LogType type)
    {
        switch (type)
        {
        case MAPLE:
            return LeavesColorizer.mapleColor;
        case PURPLEHEART:
            return BiomeColorHelper.getFoliageColorAtPos(access, pos) + 0x222222;
        case SILVERBELL:
            return LeavesColorizer.leavesColor;
        case TIGER:
            return LeavesColorizer.leavesColor;
        default:
            return LeavesColorizer.leavesColor;
        }
    }

    public static int getOverworld2ColorStatic(BlockOverworldLog2.LogType type)
    {
        switch (type)
        {
        case WILLOW:
            return LeavesColorizer.noColor;
        case EUCALYPTUS:
            return LeavesColorizer.leaves2Color;
        case HOPSEED:
            return LeavesColorizer.leaves2Color;
        case SAKURA:
            return LeavesColorizer.noColor;
        default:
            return LeavesColorizer.leaves2Color;
        }
    }

    public static int getOverworld2ColorForPos(IBlockAccess access, BlockPos pos, BlockOverworldLog2.LogType type)
    {
        switch (type)
        {
        case WILLOW:
            return LeavesColorizer.noColor;
        case EUCALYPTUS:
            return BiomeColorHelper.getFoliageColorAtPos(access, pos);
        case HOPSEED:
            return BiomeColorHelper.getFoliageColorAtPos(access, pos);
        case SAKURA:
            return LeavesColorizer.noColor;
        default:
            return LeavesColorizer.leaves2Color;
        }
    }
}
