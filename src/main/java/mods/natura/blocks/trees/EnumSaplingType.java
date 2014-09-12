package mods.natura.blocks.trees;

import java.util.Locale;

public enum EnumSaplingType {
    REDWOOD(120), EUCALYPTUS, HOPSEED, SAKURA,
    MAPLE, SILVERBELL, PURPLEHEART, TIGER, WILLOW,
    GHOSTWOOD, BLOODWOOD, DARKWOOD, FUSEWOOD;

    int growthProbability;

    EnumSaplingType() {
        this(10);
    }

    EnumSaplingType(int growthProbability) {
        this.growthProbability = growthProbability;
    }

    public String toString() {
        return super.toString().toLowerCase(Locale.ENGLISH);
    }

    static EnumSaplingType[] floraSaplings = {REDWOOD, EUCALYPTUS, HOPSEED, SAKURA};
    static EnumSaplingType[] rareSaplings = {MAPLE, SILVERBELL, PURPLEHEART, TIGER, WILLOW};
    static EnumSaplingType[] netherSaplings = {GHOSTWOOD, BLOODWOOD, DARKWOOD, FUSEWOOD};
}
