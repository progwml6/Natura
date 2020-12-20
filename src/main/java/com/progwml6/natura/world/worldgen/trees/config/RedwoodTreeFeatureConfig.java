package com.progwml6.natura.world.worldgen.trees.config;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.treedecorator.TreeDecorator;

import java.util.List;

public class RedwoodTreeFeatureConfig implements IFeatureConfig {

  public static final Codec<RedwoodTreeFeatureConfig> CODEC = RecordCodecBuilder
    .create((treeConfig) -> treeConfig.group(BlockStateProvider.CODEC.fieldOf("bark_provider").forGetter((object) -> object.barkProvider),
      BlockStateProvider.CODEC.fieldOf("heart_provider").forGetter((object) -> object.heartProvider),
      BlockStateProvider.CODEC.fieldOf("root_provider").forGetter((object) -> object.rootProvider),
      BlockStateProvider.CODEC.fieldOf("leaves_provider").forGetter((instance) -> instance.leavesProvider),

      TreeDecorator.field_236874_c_.listOf().fieldOf("decorators").forGetter((instance) -> instance.decorators),

      Codec.INT.fieldOf("base_height").orElse(0).forGetter((instance) -> instance.baseHeight),
      Codec.INT.fieldOf("random_height").orElse(0).forGetter((instance) -> instance.randomHeight),
      Codec.INT.fieldOf("height_limit").orElse(0).forGetter((instance) -> instance.heightLimit),
      Codec.INT.fieldOf("leaf_distance_limit").orElse(0).forGetter((instance) -> instance.leafDistanceLimit),

      Codec.DOUBLE.fieldOf("height_attenuation").orElse(0.00D).forGetter((instance) -> instance.heightAttenuation),
      Codec.DOUBLE.fieldOf("branch_slope").orElse(0.00D).forGetter((instance) -> instance.branchSlope),
      Codec.DOUBLE.fieldOf("scale_width").orElse(0.00D).forGetter((instance) -> instance.scaleWidth),
      Codec.DOUBLE.fieldOf("leaf_density").orElse(0.00D).forGetter((instance) -> instance.leafDensity)
    ).apply(treeConfig, RedwoodTreeFeatureConfig::new));

  public final BlockStateProvider barkProvider;
  public final BlockStateProvider heartProvider;
  public final BlockStateProvider rootProvider;
  public final BlockStateProvider leavesProvider;

  public final List<TreeDecorator> decorators;

  public transient boolean forcePlacement;

  public final int baseHeight;
  public final int randomHeight;
  public final int heightLimit;
  public final int leafDistanceLimit;

  public final double heightAttenuation;
  public final double branchSlope;
  public final double scaleWidth;
  public final double leafDensity;

  public RedwoodTreeFeatureConfig(BlockStateProvider barkProvider, BlockStateProvider heartProvider, BlockStateProvider rootProvider,
    BlockStateProvider leavesProvider, List<TreeDecorator> decorators, int baseHeight, int randomHeight, int heightLimit, int leafDistanceLimit,
    double heightAttenuation, double branchSlope, double scaleWidth, double leafDensity) {
    this.barkProvider = barkProvider;
    this.heartProvider = heartProvider;
    this.rootProvider = rootProvider;
    this.leavesProvider = leavesProvider;

    this.decorators = decorators;

    this.baseHeight = baseHeight;
    this.randomHeight = randomHeight;
    this.heightLimit = heightLimit;
    this.leafDistanceLimit = leafDistanceLimit;

    this.heightAttenuation = heightAttenuation;
    this.branchSlope = branchSlope;
    this.scaleWidth = scaleWidth;
    this.leafDensity = leafDensity;
  }

  public RedwoodTreeFeatureConfig withDecorators(List<TreeDecorator> decorators) {
    return new RedwoodTreeFeatureConfig(this.barkProvider, this.heartProvider, this.rootProvider, this.leavesProvider, decorators, this.baseHeight,
      this.randomHeight, this.heightLimit, this.leafDistanceLimit, this.heightAttenuation, this.branchSlope, this.scaleWidth, this.leafDensity);
  }

  public void forcePlacement() {
    this.forcePlacement = true;
  }

  public static class Builder {

    public final BlockStateProvider barkProvider;
    public final BlockStateProvider heartProvider;
    public final BlockStateProvider rootProvider;
    public final BlockStateProvider leavesProvider;

    private List<TreeDecorator> decorators = ImmutableList.of();

    public final int baseHeight;
    public final int randomHeight;
    public final int heightLimit;
    public final int leafDistanceLimit;

    public final double heightAttenuation;
    public final double branchSlope;
    public final double scaleWidth;
    public final double leafDensity;

    public Builder(BlockStateProvider barkProvider, BlockStateProvider heartProvider, BlockStateProvider rootProvider,
      BlockStateProvider leavesProvider, int baseHeight, int randomHeight, int heightLimit, int leafDistanceLimit,
      double heightAttenuation, double branchSlope, double scaleWidth, double leafDensity) {
      this.barkProvider = barkProvider;
      this.heartProvider = heartProvider;
      this.rootProvider = rootProvider;
      this.leavesProvider = leavesProvider;

      this.baseHeight = baseHeight;
      this.randomHeight = randomHeight;
      this.heightLimit = heightLimit;
      this.leafDistanceLimit = leafDistanceLimit;

      this.heightAttenuation = heightAttenuation;
      this.branchSlope = branchSlope;
      this.scaleWidth = scaleWidth;
      this.leafDensity = leafDensity;
    }

    public RedwoodTreeFeatureConfig.Builder setDecorators(List<TreeDecorator> decorators) {
      this.decorators = decorators;
      return this;
    }

    public RedwoodTreeFeatureConfig build() {
      return new RedwoodTreeFeatureConfig(this.barkProvider, this.heartProvider, this.rootProvider, this.leavesProvider,
        this.decorators, this.baseHeight, this.randomHeight, this.heightLimit, this.leafDistanceLimit,
        this.heightAttenuation, this.branchSlope, this.scaleWidth, this.leafDensity);
    }
  }
}
