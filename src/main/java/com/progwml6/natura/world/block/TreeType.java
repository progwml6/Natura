package com.progwml6.natura.world.block;

import net.minecraft.util.StringRepresentable;

import java.util.Locale;

public enum TreeType implements StringRepresentable {
  MAPLE,
  SILVERBELL,
  AMARANTH,
  TIGER,
  WILLOW(10),
  EUCALYPTUS,
  HOPSEED,
  SAKURA;
  private final int maxDistance;

  TreeType() {
    this(7);
  }

  TreeType(int maxDistance) {
    this.maxDistance = maxDistance;
  }

  @Override
  public String getSerializedName() {
    return this.name().toLowerCase(Locale.US);
  }

  @Override
  public String toString() {
    return this.name().toLowerCase(Locale.US);
  }

  public int getMaxDistance() {
    return maxDistance;
  }
}
