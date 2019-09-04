package com.progwml6.natura.common;

import com.progwml6.natura.Natura;
import com.progwml6.natura.library.NaturaPulseIds;
import slimeknights.mantle.common.IRegisterUtil;

public class NaturaPulse implements IRegisterUtil {

  @Override
  public String getModId() {
    return Natura.modID;
  }

  protected static boolean isEntitiesLoaded() {
    return Natura.pulseManager.isPulseLoaded(NaturaPulseIds.NATURA_ENTITIES_PULSE_ID);
  }

  protected static boolean isWorldLoaded() {
    return Natura.pulseManager.isPulseLoaded(NaturaPulseIds.NATURA_WORLD_PULSE_ID);
  }

  protected static boolean isOverworldLoaded() {
    return Natura.pulseManager.isPulseLoaded(NaturaPulseIds.NATURA_OVERWORLD_PULSE_ID);
  }

  protected static boolean isNetherLoaded() {
    return Natura.pulseManager.isPulseLoaded(NaturaPulseIds.NATURA_NETHER_PULSE_ID);
  }

  protected static boolean isDecorativeLoaded() {
    return Natura.pulseManager.isPulseLoaded(NaturaPulseIds.NATURA_DECORATIVE_PULSE_ID);
  }
}
