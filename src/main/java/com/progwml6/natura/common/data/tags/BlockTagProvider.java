package com.progwml6.natura.common.data.tags;

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
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import slimeknights.mantle.registration.object.WoodBlockObject;

import javax.annotation.Nullable;

public class BlockTagProvider extends BlockTagsProvider {

  public BlockTagProvider(DataGenerator generatorIn, @Nullable ExistingFileHelper existingFileHelper) {
    super(generatorIn, Natura.MOD_ID, existingFileHelper);
  }

  @Override
  protected void registerTags() {
    this.addWorld();
  }

  private void addWorld() {
    TagsProvider.Builder<Block> leavesBuilder = this.getOrCreateBuilder(BlockTags.LEAVES);
    TagsProvider.Builder<Block> saplingsBuilder = this.getOrCreateBuilder(BlockTags.SAPLINGS);

    for (TreeType type : TreeType.values()) {
      leavesBuilder.add(NaturaWorld.leaves.get(type));
      saplingsBuilder.add(NaturaWorld.sapling.get(type));
    }

    this.getOrCreateBuilder(BlockTags.LOGS)
      .addTag(NaturaWorld.maple.getLogBlockTag())
      .addTag(NaturaWorld.silverbell.getLogBlockTag())
      .addTag(NaturaWorld.amaranth.getLogBlockTag())
      .addTag(NaturaWorld.tiger.getLogBlockTag())
      .addTag(NaturaWorld.willow.getLogBlockTag())
      .addTag(NaturaWorld.eucalyptus.getLogBlockTag())
      .addTag(NaturaWorld.hopseed.getLogBlockTag())
      .addTag(NaturaWorld.sakura.getLogBlockTag());

    this.getOrCreateBuilder(BlockTags.PLANKS)
      .add(NaturaWorld.maple.get())
      .add(NaturaWorld.silverbell.get())
      .add(NaturaWorld.amaranth.get())
      .add(NaturaWorld.tiger.get())
      .add(NaturaWorld.willow.get())
      .add(NaturaWorld.eucalyptus.get())
      .add(NaturaWorld.hopseed.get())
      .add(NaturaWorld.sakura.get());

    TagsProvider.Builder<Block> redwoodLogsBuilder = this.getOrCreateBuilder(NaturaTags.Blocks.REDWOOD_LOGS);
    for (RedwoodType type : RedwoodType.values()) {
      redwoodLogsBuilder.add(NaturaWorld.redwood.get(type));
    }

    leavesBuilder.add(NaturaWorld.redwood_leaves.get());
    saplingsBuilder.add(NaturaWorld.redwood_sapling.get());

    addWoodTags(NaturaWorld.maple, true);
    addWoodTags(NaturaWorld.silverbell, true);
    addWoodTags(NaturaWorld.amaranth, true);
    addWoodTags(NaturaWorld.tiger, true);
    addWoodTags(NaturaWorld.willow, true);
    addWoodTags(NaturaWorld.eucalyptus, true);
    addWoodTags(NaturaWorld.hopseed, true);
    addWoodTags(NaturaWorld.sakura, true);
  }

  /** Adds all tags relevant to the given wood object */
  private void addWoodTags(WoodBlockObject object, boolean doesBurn) {
    // planks, handled by slimy planks tag
    //this.getOrCreateBuilder(BlockTags.PLANKS).add(object.get());
    this.getOrCreateBuilder(BlockTags.WOODEN_SLABS).add(object.getSlab());
    this.getOrCreateBuilder(BlockTags.WOODEN_STAIRS).add(object.getStairs());
    // logs
    this.getOrCreateBuilder(object.getLogBlockTag()).add(object.getLog(), object.getStrippedLog(), object.getWood(), object.getStrippedWood());

    // doors
    this.getOrCreateBuilder(BlockTags.WOODEN_FENCES).add(object.getFence());
    this.getOrCreateBuilder(Tags.Blocks.FENCES_WOODEN).add(object.getFence());
    this.getOrCreateBuilder(BlockTags.FENCE_GATES).add(object.getFenceGate());
    this.getOrCreateBuilder(Tags.Blocks.FENCE_GATES_WOODEN).add(object.getFenceGate());
    this.getOrCreateBuilder(BlockTags.WOODEN_DOORS).add(object.getDoor());
    this.getOrCreateBuilder(BlockTags.WOODEN_TRAPDOORS).add(object.getTrapdoor());
    // redstone
    this.getOrCreateBuilder(BlockTags.WOODEN_BUTTONS).add(object.getButton());
    this.getOrCreateBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(object.getPressurePlate());

    if (doesBurn) {
      // regular logs is handled by slimy logs tag
      this.getOrCreateBuilder(BlockTags.LOGS_THAT_BURN).addTag(object.getLogBlockTag());
    } else {
      this.getOrCreateBuilder(BlockTags.NON_FLAMMABLE_WOOD)
        .add(object.get(), object.getSlab(), object.getStairs(),
          object.getFence(), object.getFenceGate(), object.getDoor(), object.getTrapdoor(),
          object.getPressurePlate(), object.getButton())
        .addTag(object.getLogBlockTag());
    }

    // signs
    this.getOrCreateBuilder(BlockTags.STANDING_SIGNS).add(object.getSign());
    this.getOrCreateBuilder(BlockTags.WALL_SIGNS).add(object.getWallSign());
  }
}
