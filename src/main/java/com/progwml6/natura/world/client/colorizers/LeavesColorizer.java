package com.progwml6.natura.world.client.colorizers;

import com.progwml6.natura.world.block.leaves.overworld.BlockRedwoodLeaves;
import com.progwml6.natura.world.block.logs.overworld.BlockOverworldLog;
import com.progwml6.natura.world.block.logs.overworld.BlockOverworldLog2;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;

public class LeavesColorizer
{
    public static int mapleColor = 0xcc5718;

    public static int amaranthColor = 10079569;

    public static int noColor = 16777215;

    public static int leaves2Color = 7842607;

    public static int leavesColor = 0xffffff;

    public static int getOverworldLeavesColorStatic(BlockOverworldLog.LogType type)
    {
        switch (type)
        {
        case MAPLE:
            return LeavesColorizer.mapleColor;
        case AMARANTH:
            return LeavesColorizer.amaranthColor;
        case SILVERBELL:
            return LeavesColorizer.leavesColor;
        case TIGER:
            return LeavesColorizer.leavesColor;
        default:
            return LeavesColorizer.leavesColor;
        }
    }

    public static int getOverworldLeavesColorForPos(IBlockAccess access, BlockPos pos, BlockOverworldLog.LogType type)
    {
        switch (type)
        {
        case MAPLE:
            return LeavesColorizer.mapleColor;
        case AMARANTH:
            return BiomeColorHelper.getFoliageColorAtPos(access, pos) + 0x222222;
        case SILVERBELL:
            return LeavesColorizer.leavesColor;
        case TIGER:
            return LeavesColorizer.leavesColor;
        default:
            return LeavesColorizer.leavesColor;
        }
    }

    public static int getSecondOverworldLeavesColorStatic(BlockOverworldLog2.LogType type)
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

    public static int getSecondOverworldLeavesColorForPos(IBlockAccess access, BlockPos pos, BlockOverworldLog2.LogType type)
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

    public static int getRedwoodLeavesColorStatic(BlockRedwoodLeaves.RedwoodType type)
    {
        switch (type)
        {
        case NORMAL:
            return LeavesColorizer.leaves2Color;
        default:
            return LeavesColorizer.leaves2Color;
        }
    }

    public static int getRedwoodLeavesColorForPos(IBlockAccess access, BlockPos pos, BlockRedwoodLeaves.RedwoodType type)
    {
        switch (type)
        {
        case NORMAL:
            return BiomeColorHelper.getFoliageColorAtPos(access, pos);
        default:
            return LeavesColorizer.leaves2Color;
        }
    }

    public static int getNetherLeavesColorStatic(BlockRedwoodLeaves.RedwoodType type)
    {
        switch (type)
        {
        case NORMAL:
            return LeavesColorizer.leaves2Color;
        default:
            return LeavesColorizer.leaves2Color;
        }
    }

    public static int getRedwoodColorForPos(IBlockAccess access, BlockPos pos, BlockRedwoodLeaves.RedwoodType type)
    {
        switch (type)
        {
        case NORMAL:
            return BiomeColorHelper.getFoliageColorAtPos(access, pos);
        default:
            return LeavesColorizer.leaves2Color;
        }
    }
}
