package com.progwml6.natura.common.data;

import com.progwml6.natura.Natura;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class NaturaItemTagsProvider extends ItemTagsProvider {

  public NaturaItemTagsProvider(DataGenerator generatorIn, BlockTagsProvider blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
    super(generatorIn, blockTagProvider, Natura.modID, existingFileHelper);
  }

  @Override
  protected void registerTags() {
    this.addWorld();
  }

  private void addWorld() {
    this.copy(BlockTags.LOGS, ItemTags.LOGS);
    this.copy(BlockTags.LEAVES, ItemTags.LEAVES);
    this.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
  }
}
