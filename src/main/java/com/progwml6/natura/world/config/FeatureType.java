package com.progwml6.natura.world.config;

import com.mojang.serialization.Codec;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public enum FeatureType implements IStringSerializable {
  MAPLE(10, "tree"),
  SIVERBELL(70, "tree"),
  AMARANTH(1, "tree"),
  TIGER(30, "tree"),
  WILLOW(10, "tree"),
  EUCALYPTUS(25, "tree"),
  EUCALYPTUS_PLAINS(37, "tree"),
  HOPSEED(10, "tree"),
  SAKURA(10, "tree"),
  SAKURA_FOREST(50, "tree"),
  REDWOOD(150, "tree", false),
  SAGUARO(5, "cactus");

  public static Codec<FeatureType> CODEC = IStringSerializable.createEnumCodec(FeatureType::values, FeatureType::byName);
  private static final Map<String, FeatureType> NAME_LOOKUP = Arrays.stream(values())
    .collect(Collectors.toMap(FeatureType::getString, oreType -> oreType));

  private final String name;
  private final String type;
  private final boolean shouldGenerate;
  private final int chance;

  FeatureType(int chance, String type) {
    this(chance, type, true);
  }

  FeatureType(int chance, String type, boolean shouldGenerate) {
    this.name = this.name().toLowerCase(Locale.US);
    this.type = type;
    this.chance = chance;
    this.shouldGenerate = shouldGenerate;
  }

  public int getChance() {
    return chance;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public boolean isShouldGenerate() {
    return shouldGenerate;
  }

  @Nullable
  public static FeatureType get(String name) {
    for (FeatureType ore : values()) {
      if (name.equals(ore.name)) {
        return ore;
      }
    }

    return null;
  }

  @Override
  public String getString() {
    return name;
  }

  @Nullable
  private static FeatureType byName(String name) {
    return NAME_LOOKUP.get(name);
  }
}
