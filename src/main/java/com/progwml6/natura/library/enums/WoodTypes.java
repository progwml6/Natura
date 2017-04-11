package com.progwml6.natura.library.enums;

import java.util.Locale;

import net.minecraft.util.IStringSerializable;

public enum WoodTypes implements IStringSerializable
{
    MAPLE(WorldType.OVERWORLD),
    SILVERBELL(WorldType.OVERWORLD),
    AMARANTH(WorldType.OVERWORLD),
    TIGER(WorldType.OVERWORLD),
    WILLOW(WorldType.OVERWORLD),
    EUCALYPTUS(WorldType.OVERWORLD),
    HOPSEED(WorldType.OVERWORLD),
    SAKURA(WorldType.OVERWORLD),
    REDWOOD(WorldType.OVERWORLD),
    GHOSTWOOD(WorldType.NETHER),
    BLOODWOOD(WorldType.NETHER),
    DARKWOOD(WorldType.NETHER),
    FUSEWOOD(WorldType.NETHER);

    private final WorldType worldType;

    private WoodTypes(WorldType worldType)
    {
        this.worldType = worldType;
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

    public enum WorldType
    {
        OVERWORLD, NETHER;
    }
}
