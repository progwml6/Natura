package com.progwml6.natura.common.config;

import com.progwml6.natura.Natura;
import com.progwml6.natura.common.config.value.CachedBoolean;
import com.progwml6.natura.common.config.value.CachedInteger;
import com.progwml6.natura.common.config.value.CachedValue;
import com.progwml6.natura.world.config.FeatureType;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import static net.minecraftforge.common.ForgeConfigSpec.Builder;
import static net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import static net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class Config {

  /** List of all cached config values, for cache clearing */
  private static final List<CachedValue<?>> SERVER_VALUES = new ArrayList<>();
  /** List of all cached client config values, for cache clearing */
  private static final List<CachedValue<?>> CLIENT_VALUES = new ArrayList<>();

  /** Config for anything that affects gameplay */
  public static final ForgeConfigSpec SERVER_SPEC;
  /** Config for anything that is visual only */
  public static final ForgeConfigSpec CLIENT_SPEC;

  public static final Map<FeatureType, FeatureConfig> features = new EnumMap<>(FeatureType.class);

  public static final CachedValue<List<String>> treesBiomesBlacklist;

  static {
    Builder server = new Builder();
    Builder client = new Builder();

    server.comment("World generation settings for Natura. This config is synced from server to client").push("world_generation");

    for (FeatureType feature : Natura.FEATURE_TYPES) {
      features.put(feature, new FeatureConfig(server, feature.getName(), feature.getType(), feature.isShouldGenerate(), feature.getChance()));
    }

    treesBiomesBlacklist = server(server.comment(
      "Any biome ids added to this list will not have any of the natura trees generate in it. Requires world restart to take effect. For example: [\"minecraft:forest\"]")
      .define("treesBiomesBlacklist", new ArrayList<>()));

    // build all specs
    SERVER_SPEC = server.build();
    CLIENT_SPEC = client.build();
  }

  public static class FeatureConfig {

    public final CachedBoolean shouldGenerate;
    public final CachedInteger chance;

    private FeatureConfig(ForgeConfigSpec.Builder builder, String feature, String type, boolean shouldGenerate, int chance) {
      builder.comment("Generation Settings for " + feature + (!type.isEmpty() ? " " + type : "") + ".").push(feature);
      this.shouldGenerate = server(
        builder.comment("Determines if " + feature + (!type.isEmpty() ? " " + type : "") + " feature should be added to world generation.")
          .define("shouldGenerate", shouldGenerate));
      this.chance = server(builder.comment("Chance that " + feature + (!type.isEmpty() ? " " + type : "") + " generates in a chunk.")
        .defineInRange("chance", chance, 1, 512));
      builder.pop();
    }
  }

  /* Helpers */

  /**
   * Creates a cached config value and adds it to the list to be invalidated on reload
   * @param value  Config value
   * @param <T>    Value type
   * @return Cached config value
   */
  private static <T> CachedValue<T> server(ConfigValue<T> value) {
    CachedValue<T> cached = new CachedValue<>(value);
    SERVER_VALUES.add(cached);
    return cached;
  }

  /**
   * Creates a cached boolean value and adds it to the list to be invalidated on reload
   * @param value  Boolean config value
   * @return Cached config value
   */
  private static CachedBoolean server(BooleanValue value) {
    CachedBoolean cached = new CachedBoolean(value);
    SERVER_VALUES.add(cached);
    return cached;
  }

  /**
   * Creates a cached boolean value for the client and adds it to the list to be invalidated on reload
   * @param value  Boolean config value
   * @return Cached config value
   */
  private static CachedBoolean client(BooleanValue value) {
    CachedBoolean cached = new CachedBoolean(value);
    CLIENT_VALUES.add(cached);
    return cached;
  }

  /**
   * Creates a cached boolean value and adds it to the list to be invalidated on reload
   * @param value  Boolean config value
   * @return Cached config value
   */
  private static CachedInteger server(IntValue value) {
    CachedInteger cached = new CachedInteger(value);
    SERVER_VALUES.add(cached);
    return cached;
  }

  /**
   * Creates a cached boolean value for the client and adds it to the list to be invalidated on reload
   * @param value  Boolean config value
   * @return Cached config value
   */
  private static CachedInteger client(IntValue value) {
    CachedInteger cached = new CachedInteger(value);
    CLIENT_VALUES.add(cached);
    return cached;
  }


  /**
   * Creates a cached config value by anding two config values
   * @param first   First config value, typically a module
   * @param second  Property config value
   * @return Cached config value
   */
  private static CachedBoolean and(CachedBoolean first, BooleanValue second) {
    CachedBoolean cached = new CachedBoolean(() -> first.get() && second.get());
    SERVER_VALUES.add(cached);
    return cached;
  }

  /**
   * Creates a cached config value by anding two config values
   * @param first   First config value, typically a module
   * @param second  Property config value
   * @return Cached config value
   */
  private static CachedBoolean and(CachedBoolean first, CachedBoolean second) {
    CachedBoolean cached = new CachedBoolean(() -> first.get() && second.get());
    SERVER_VALUES.add(cached);
    return cached;
  }

  /**
   * Creates a cached config value by anding three config values
   * @param first   First config value, typically a module
   * @param second  Second config value
   * @param third   Property config value
   * @return Cached config value
   */
  private static CachedBoolean and(CachedBoolean first, CachedBoolean second, BooleanValue third) {
    CachedBoolean cached = new CachedBoolean(() -> first.get() && second.get() && third.get());
    SERVER_VALUES.add(cached);
    return cached;
  }

  /**
   * Clears the cache of all regular config values. Called during the config loaded event
   * @param spec
   */
  public static void clearCache(ForgeConfigSpec spec) {
    if (spec == SERVER_SPEC) {
      SERVER_VALUES.forEach(CachedValue::invalidate);
    }
    else if (spec == CLIENT_SPEC) {
      CLIENT_VALUES.forEach(CachedValue::invalidate);
    }
  }
}
