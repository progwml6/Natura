package com.progwml6.natura.common.data;

import com.progwml6.natura.Natura;
import com.progwml6.natura.common.NaturaTags;
import com.progwml6.natura.world.NaturaWorld;
import com.progwml6.natura.world.block.RedwoodType;
import com.progwml6.natura.world.block.TreeType;
import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class NaturaBlockTagsProvider extends BlockTagsProvider {

  public NaturaBlockTagsProvider(DataGenerator generatorIn, @Nullable ExistingFileHelper existingFileHelper) {
    super(generatorIn, Natura.modID, existingFileHelper);
  }

  @Override
  protected void registerTags() {
    this.addWorld();
  }

  private void addWorld() {
    TagsProvider.Builder<Block> logsBuilder = this.getOrCreateBuilder(BlockTags.LOGS);
    TagsProvider.Builder<Block> leavesBuilder = this.getOrCreateBuilder(BlockTags.LEAVES);
    TagsProvider.Builder<Block> saplingsBuilder = this.getOrCreateBuilder(BlockTags.SAPLINGS);

    for (TreeType type : TreeType.values()) {
      logsBuilder.add(NaturaWorld.logs.get(type));
      leavesBuilder.add(NaturaWorld.leaves.get(type));
      saplingsBuilder.add(NaturaWorld.sapling.get(type));
    }

    TagsProvider.Builder<Block> redwoodLogsBuilder = this.getOrCreateBuilder(NaturaTags.Blocks.REDWOOD_LOGS);
    for (RedwoodType type : RedwoodType.values()) {
      redwoodLogsBuilder.add(NaturaWorld.redwood.get(type));
    }

    leavesBuilder.add(NaturaWorld.redwood_leaves.get());
    saplingsBuilder.add(NaturaWorld.redwood_sapling.get());
  }
}
