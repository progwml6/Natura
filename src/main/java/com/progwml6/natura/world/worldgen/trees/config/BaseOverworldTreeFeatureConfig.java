package com.progwml6.natura.world.worldgen.trees.config;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.treedecorator.TreeDecorator;

import java.util.List;

public class BaseOverworldTreeFeatureConfig implements IFeatureConfig {

  public static final Codec<BaseOverworldTreeFeatureConfig> CODEC = RecordCodecBuilder
    .create((treeConfig) -> treeConfig.group(BlockStateProvider.CODEC.fieldOf("trunk_provider").forGetter((object) -> object.trunkProvider),
      BlockStateProvider.CODEC.fieldOf("leaves_provider").forGetter((instance) -> instance.leavesProvider),

      TreeDecorator.field_236874_c_.listOf().fieldOf("decorators").forGetter((instance) -> instance.decorators),

      Codec.INT.fieldOf("base_height").orElse(0).forGetter((instance) -> instance.baseHeight),
      Codec.INT.fieldOf("random_height").orElse(0).forGetter((instance) -> instance.randomHeight)
    ).apply(treeConfig, BaseOverworldTreeFeatureConfig::new));

  public final BlockStateProvider trunkProvider;
  public final BlockStateProvider leavesProvider;

  public final List<TreeDecorator> decorators;

  public transient boolean forcePlacement;

  public final int baseHeight;
  public final int randomHeight;

  public BaseOverworldTreeFeatureConfig(BlockStateProvider trunkProvider, BlockStateProvider leavesProvider, List<TreeDecorator> decorators,
    int baseHeight, int randomHeight) {
    this.trunkProvider = trunkProvider;
    this.leavesProvider = leavesProvider;

    this.decorators = decorators;

    this.baseHeight = baseHeight;
    this.randomHeight = randomHeight;
  }

  public void forcePlacement() {
    this.forcePlacement = true;
  }

  public BaseOverworldTreeFeatureConfig withDecorators(List<TreeDecorator> decorators) {
    return new BaseOverworldTreeFeatureConfig(this.trunkProvider, this.leavesProvider, decorators, this.baseHeight, this.randomHeight);
  }

  public static class Builder {

    public final BlockStateProvider trunkProvider;
    public final BlockStateProvider leavesProvider;

    private List<TreeDecorator> decorators = ImmutableList.of();

    public final int baseHeight;
    public final int randomHeight;

    public Builder(BlockStateProvider trunkProvider, BlockStateProvider leavesProvider, int baseHeight, int randomHeight) {
      this.trunkProvider = trunkProvider;
      this.leavesProvider = leavesProvider;
      this.baseHeight = baseHeight;
      this.randomHeight = randomHeight;
    }

    public BaseOverworldTreeFeatureConfig.Builder setDecorators(List<TreeDecorator> decorators) {
      this.decorators = decorators;
      return this;
    }

    public BaseOverworldTreeFeatureConfig build() {
      return new BaseOverworldTreeFeatureConfig(this.trunkProvider, this.leavesProvider, this.decorators, this.baseHeight, this.randomHeight);
    }
  }
}
