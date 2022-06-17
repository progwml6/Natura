package com.progwml6.natura.world;

import com.progwml6.natura.Natura;
import com.progwml6.natura.common.NaturaModule;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class NaturaFeatures extends NaturaModule {

  public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> register(String name, F feature, FC featureConfiguration) {
    return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE, Natura.prefix(name).toString(), new ConfiguredFeature<>(feature, featureConfiguration));
  }
}
