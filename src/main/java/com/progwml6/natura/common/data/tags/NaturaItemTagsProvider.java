package com.progwml6.natura.common.data.tags;

import com.progwml6.natura.Natura;
import com.progwml6.natura.common.NaturaTags;
import com.progwml6.natura.gadgets.NaturaGadgets;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class NaturaItemTagsProvider extends ItemTagsProvider {

  public NaturaItemTagsProvider(DataGenerator generatorIn, BlockTagsProvider blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
    super(generatorIn, blockTagProvider, Natura.modID, existingFileHelper);
  }

  @Override
  protected void registerTags() {
    this.addGadgets();
    this.addWorld();
  }

  private void addWorld() {
    this.copy(BlockTags.LOGS, ItemTags.LOGS);
    this.copy(BlockTags.LEAVES, ItemTags.LEAVES);
    this.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);

    this.copy(NaturaTags.Blocks.REDWOOD_LOGS, NaturaTags.Items.REDWOOD_LOGS);
  }

  private void addGadgets() {
    this.getOrCreateBuilder(Tags.Items.RODS).addTag(NaturaTags.Items.RODS_STONE);
    this.getOrCreateBuilder(NaturaTags.Items.RODS_STONE).add(NaturaGadgets.stoneStick.get());
  }
}
