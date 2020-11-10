package com.progwml6.natura.world.block;

import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum TreeType implements IStringSerializable {
  MAPLE,
  SILVERBELL,
  AMARANTH,
  TIGER,
  WILLOW(10),
  EUCALYPTUS,
  HOPSEED,
  SAKURA;

  private final String name;
  private final int maxDistance;

  TreeType() {
    this(7);
  }

  TreeType(int maxDistance) {
    this.name = this.name().toLowerCase(Locale.US);
    this.maxDistance = maxDistance;
  }

  @Override
  public String getString() {
    return name;
  }

  @Override
  public String toString() {
    return name;
  }

  public int getMaxDistance() {
    return maxDistance;
  }
}
