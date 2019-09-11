package com.progwml6.natura.common.data;

import com.progwml6.natura.shared.NaturaCommons;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

import static com.progwml6.natura.common.Tags.Blocks.CLOUDS;

public class NaturaBlockTagsProvider extends BlockTagsProvider {

  private Set<ResourceLocation> filter = null;

  public NaturaBlockTagsProvider(DataGenerator generatorIn) {
    super(generatorIn);
  }

  @Override
  public void registerTags() {
    super.registerTags();

    this.filter = this.tagToBuilder.keySet().stream().map(Tag::getId).collect(Collectors.toSet());

    this.getBuilder(CLOUDS).add(NaturaCommons.white_cloud, NaturaCommons.dark_cloud, NaturaCommons.ash_cloud, NaturaCommons.sulfur_cloud);
  }

  @Override
  protected Path makePath(ResourceLocation id) {
    return this.filter != null && this.filter.contains(id) ? null : super.makePath(id); //We don't want to save vanilla tags.
  }

  @Override
  public String getName() {
    return "Natura Block Tags";
  }

}