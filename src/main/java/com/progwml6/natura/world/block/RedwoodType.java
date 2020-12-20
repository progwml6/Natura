package com.progwml6.natura.world.block;

import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum RedwoodType implements IStringSerializable {
  BARK,
  HEART,
  ROOT;

  private final String name;
  private final int maxDistance;

  RedwoodType() {
    this(7);
  }

  RedwoodType(int maxDistance) {
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
