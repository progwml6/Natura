package com.progwml6.natura.world.worldgen.helper;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.world.config.FeatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.util.Random;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

public class ChanceRandomFeatureConfig implements IFeatureConfig {

  public static final Codec<ChanceRandomFeatureConfig> CODEC = RecordCodecBuilder.create(builder -> builder.group(
    ConfiguredFeature.field_236264_b_.fieldOf("feature").forGetter(config -> config.feature),
    FeatureType.CODEC.fieldOf("featureType").forGetter(config -> config.featureType)
  ).apply(builder, ChanceRandomFeatureConfig::create));

  private static ChanceRandomFeatureConfig create(Supplier<ConfiguredFeature<?, ?>> feature, FeatureType featureType) {
    return new ChanceRandomFeatureConfig(feature, featureType, Config.features.get(featureType).chance);
  }

  public final Supplier<ConfiguredFeature<?, ?>> feature;
  public final FeatureType featureType;
  public final IntSupplier chance;

  public ChanceRandomFeatureConfig(ConfiguredFeature<?, ?> feature, FeatureType featureType, IntSupplier chance) {
    this(() -> feature, featureType, chance);
  }

  public ChanceRandomFeatureConfig(Supplier<ConfiguredFeature<?, ?>> feature, FeatureType featureType, IntSupplier chance) {
    this.feature = feature;
    this.featureType = featureType;
    this.chance = chance;
  }

  public boolean generate(ISeedReader reader, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos) {
    return this.feature.get().generate(reader, chunkGenerator, random, blockPos);
  }
}
