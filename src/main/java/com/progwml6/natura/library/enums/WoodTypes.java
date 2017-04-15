package com.progwml6.natura.library.enums;

import java.util.Locale;

import com.progwml6.natura.nether.block.planks.BlockNetherPlanks;
import com.progwml6.natura.overworld.block.planks.BlockOverworldPlanks;
import com.progwml6.natura.shared.NaturaCommons;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public enum WoodTypes implements IStringSerializable
{
    MAPLE(WorldType.OVERWORLD, BlockOverworldPlanks.PlankType.MAPLE, null, NaturaCommons.maple_stick.copy()),
    SILVERBELL(WorldType.OVERWORLD, BlockOverworldPlanks.PlankType.SILVERBELL, null, NaturaCommons.silverbell_stick.copy()),
    AMARANTH(WorldType.OVERWORLD, BlockOverworldPlanks.PlankType.AMARANTH, null, NaturaCommons.amaranth_stick.copy()),
    TIGER(WorldType.OVERWORLD, BlockOverworldPlanks.PlankType.TIGER, null, NaturaCommons.tiger_stick.copy()),
    WILLOW(WorldType.OVERWORLD, BlockOverworldPlanks.PlankType.WILLOW, null, NaturaCommons.willow_stick.copy()),
    EUCALYPTUS(WorldType.OVERWORLD, BlockOverworldPlanks.PlankType.EUCALYPTUS, null, NaturaCommons.eucalyptus_stick.copy()),
    HOPSEED(WorldType.OVERWORLD, BlockOverworldPlanks.PlankType.HOPSEED, null, NaturaCommons.hopseed_stick.copy()),
    SAKURA(WorldType.OVERWORLD, BlockOverworldPlanks.PlankType.SAKURA, null, NaturaCommons.sakura_stick.copy()),
    REDWOOD(WorldType.OVERWORLD, BlockOverworldPlanks.PlankType.REDWOOD, null, NaturaCommons.redwood_stick.copy()),
    GHOSTWOOD(WorldType.NETHER, null, BlockNetherPlanks.PlankType.GHOSTWOOD, NaturaCommons.ghostwood_stick.copy()),
    BLOODWOOD(WorldType.NETHER, null, BlockNetherPlanks.PlankType.BLOODWOOD, NaturaCommons.bloodwood_stick.copy()),
    DARKWOOD(WorldType.NETHER, null, BlockNetherPlanks.PlankType.DARKWOOD, NaturaCommons.darkwood_stick.copy()),
    FUSEWOOD(WorldType.NETHER, null, BlockNetherPlanks.PlankType.FUSEWOOD, NaturaCommons.fusewood_stick.copy());

    private final WorldType worldType;

    private final BlockOverworldPlanks.PlankType overworldPlankType;

    private final BlockNetherPlanks.PlankType netherPlankType;

    private final ItemStack stickItemStack;

    private WoodTypes(WorldType worldType, BlockOverworldPlanks.PlankType overworldPlankType, BlockNetherPlanks.PlankType netherPlankType, ItemStack stickItemStack)
    {
        this.worldType = worldType;
        this.overworldPlankType = overworldPlankType;
        this.netherPlankType = netherPlankType;
        this.stickItemStack = stickItemStack;
    }

    @Override
    public String getName()
    {
        return this.toString().toLowerCase(Locale.US);
    }

    public WorldType getWorldType()
    {
        return worldType;
    }

    public BlockOverworldPlanks.PlankType getOverworldPlankType()
    {
        return overworldPlankType;
    }

    public BlockNetherPlanks.PlankType getNetherPlankType()
    {
        return netherPlankType;
    }

    public ItemStack getStickItemStack()
    {
        return stickItemStack;
    }

    public enum WorldType
    {
        OVERWORLD, NETHER;
    }
}
