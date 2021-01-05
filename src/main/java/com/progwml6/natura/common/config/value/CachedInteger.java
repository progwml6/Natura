package com.progwml6.natura.common.config.value;

import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

import java.util.function.IntSupplier;

/**
 * Same as {@link CachedValue}, but implements {@link IntSupplier}
 */
public class CachedInteger extends CachedValue<Integer> implements IntSupplier {

  /**
   * Creates a new class instance using a boolean supplier
   * @param supplier  Boolean supplier
   */
  public CachedInteger(IntSupplier supplier) {
    super(supplier::getAsInt);
  }

  /**
   * Creates a new instance from a boolean value
   * @param config  Boolean value
   */
  public CachedInteger(ConfigValue<Integer> config) {
    super(config);
  }

  @Override
  public int getAsInt() {
    return get();
  }
}
