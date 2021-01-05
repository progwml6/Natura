package com.progwml6.natura.common.config.value;

import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

import java.util.function.IntSupplier;

/**
 * Same as {@link CachedValue}, but implements {@link IntSupplier}
 */
public class CachedInteger extends CachedValue<Integer> implements IntSupplier {

  /**
   * Creates a new class instance using a integer supplier
   * @param supplier  integer supplier
   */
  public CachedInteger(IntSupplier supplier) {
    super(supplier::getAsInt);
  }

  /**
   * Creates a new instance from a integer value
   * @param config  integer value
   */
  public CachedInteger(ConfigValue<Integer> config) {
    super(config);
  }

  @Override
  public int getAsInt() {
    return get();
  }
}
