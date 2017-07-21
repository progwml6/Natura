package com.progwml6.natura.library.enums;

import java.util.Locale;

import javax.annotation.Nonnull;

import net.minecraft.util.IStringSerializable;

public enum WoodTypes implements IStringSerializable
{
    MAPLE(WorldType.OVERWORLD, 0, 0),
    SILVERBELL(WorldType.OVERWORLD, 1, 1),
    AMARANTH(WorldType.OVERWORLD, 2, 2),
    TIGER(WorldType.OVERWORLD, 3, 3),
    WILLOW(WorldType.OVERWORLD, 4, 4),
    EUCALYPTUS(WorldType.OVERWORLD, 5, 5),
    HOPSEED(WorldType.OVERWORLD, 6, 6),
    SAKURA(WorldType.OVERWORLD, 7, 7),
    REDWOOD(WorldType.OVERWORLD, 8, 8),
    GHOSTWOOD(WorldType.NETHER, 0, 9),
    BLOODWOOD(WorldType.NETHER, 1, 12),
    FUSEWOOD(WorldType.NETHER, 3, 11),
    DARKWOOD(WorldType.NETHER, 2, 10);
    
    @Nonnull
    private final WorldType worldType;

    @Nonnull
    private final int plankMeta;

    @Nonnull
    private final int stickMeta;

    private WoodTypes(@Nonnull WorldType worldType, @Nonnull int plankMeta, @Nonnull int stickMeta)
    {
        this.worldType = worldType;
        this.plankMeta = plankMeta;
        this.stickMeta = stickMeta;
    }

    @Override
    public String getName()
    {
        return this.toString().toLowerCase(Locale.US);
    }

    @Nonnull
    public WorldType getWorldType()
    {
        return worldType;
    }

    @Nonnull
    public int getPlankMeta()
    {
        return plankMeta;
    }

    @Nonnull
    public int getStickMeta()
    {
        return stickMeta;
    }

    public enum WorldType
    {
        OVERWORLD, NETHER;
    }
}
