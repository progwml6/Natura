package com.progwml6.natura.common.data;

import com.progwml6.natura.common.Tags;
import com.progwml6.natura.shared.NaturaCommons;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

public class NaturaItemTagsProvider extends ItemTagsProvider {

  private Set<ResourceLocation> filter = null;

  public NaturaItemTagsProvider(DataGenerator generatorIn) {
    super(generatorIn);
  }

  @Override
  public void registerTags() {
    super.registerTags();

    this.filter = this.tagToBuilder.keySet().stream().map(Tag::getId).collect(Collectors.toSet());

    this.copy(Tags.Blocks.CLOUDS, Tags.Items.CLOUDS);

    getBuilder(Tags.Items.CROPS_BARLEY).add(NaturaCommons.barley);
  }

  @Override
  protected Path makePath(ResourceLocation id) {
    return this.filter != null && this.filter.contains(id) ? null : super.makePath(id); //We don't want to save vanilla tags.
  }

  @Override
  public String getName() {
    return "Natura Item Tags";
  }

}
