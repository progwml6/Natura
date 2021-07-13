package com.progwml6.natura.world.block;

import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum PlankType implements IStringSerializable {
  MAPLE,
  SILVERBELL,
  AMARANTH,
  TIGER,
  WILLOW,
  EUCALYPTUS,
  HOPSEED,
  SAKURA,
  REDWOOD;

  private final String name;

  PlankType() {
    this.name = this.name().toLowerCase(Locale.US);
  }

  @Override
  public String getString() {
    return name;
  }

  @Override
  public String toString() {
    return name;
  }
}
