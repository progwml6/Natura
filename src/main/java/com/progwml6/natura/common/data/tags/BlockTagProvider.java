package com.progwml6.natura.common.data.tags;

import com.progwml6.natura.Natura;
import com.progwml6.natura.common.registration.RedwoodBlockObject;
import com.progwml6.natura.gadgets.NaturaGadgets;
import com.progwml6.natura.world.NaturaWorld;
import com.progwml6.natura.world.block.TreeType;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import slimeknights.mantle.registration.object.BuildingBlockObject;
import slimeknights.mantle.registration.object.EnumObject;
import slimeknights.mantle.registration.object.WoodBlockObject;

import javax.annotation.Nullable;
import java.util.function.Supplier;

import static net.minecraft.tags.BlockTags.MINEABLE_WITH_AXE;

public class BlockTagProvider extends BlockTagsProvider {

  public BlockTagProvider(DataGenerator generatorIn, @Nullable ExistingFileHelper existingFileHelper) {
    super(generatorIn, Natura.MOD_ID, existingFileHelper);
  }

  @Override
  protected void addTags() {
    this.addWorld();
  }

  private void addWorld() {
    TagsProvider.TagAppender<Block> leavesBuilder = this.tag(BlockTags.LEAVES);
    TagsProvider.TagAppender<Block> saplingsBuilder = this.tag(BlockTags.SAPLINGS);

    for (TreeType type : TreeType.values()) {
      leavesBuilder.add(NaturaWorld.leaves.get(type));
      saplingsBuilder.add(NaturaWorld.sapling.get(type));
    }

    this.tag(BlockTags.LOGS)
      .addTag(NaturaWorld.maple.getLogBlockTag())
      .addTag(NaturaWorld.silverbell.getLogBlockTag())
      .addTag(NaturaWorld.amaranth.getLogBlockTag())
      .addTag(NaturaWorld.tiger.getLogBlockTag())
      .addTag(NaturaWorld.willow.getLogBlockTag())
      .addTag(NaturaWorld.eucalyptus.getLogBlockTag())
      .addTag(NaturaWorld.hopseed.getLogBlockTag())
      .addTag(NaturaWorld.sakura.getLogBlockTag())
      .addTag(NaturaWorld.redwood.getLogBlockTag());

    this.tag(BlockTags.PLANKS)
      .add(NaturaWorld.maple.get())
      .add(NaturaWorld.silverbell.get())
      .add(NaturaWorld.amaranth.get())
      .add(NaturaWorld.tiger.get())
      .add(NaturaWorld.willow.get())
      .add(NaturaWorld.eucalyptus.get())
      .add(NaturaWorld.hopseed.get())
      .add(NaturaWorld.sakura.get())
      .add(NaturaWorld.redwood.get());

//
//    leavesBuilder.add(NaturaWorld.redwood_leaves.get());
//    saplingsBuilder.add(NaturaWorld.redwood_sapling.get());

    addWoodTags(NaturaWorld.maple, true);
    addWoodTags(NaturaWorld.silverbell, true);
    addWoodTags(NaturaWorld.amaranth, true);
    addWoodTags(NaturaWorld.tiger, true);
    addWoodTags(NaturaWorld.willow, true);
    addWoodTags(NaturaWorld.eucalyptus, true);
    addWoodTags(NaturaWorld.hopseed, true);
    addWoodTags(NaturaWorld.sakura, true);

    addWoodTags(NaturaWorld.redwood, true);

    tagPlanks(MINEABLE_WITH_AXE, NaturaWorld.maple, NaturaWorld.silverbell, NaturaWorld.amaranth, NaturaWorld.tiger, NaturaWorld.willow, NaturaWorld.eucalyptus, NaturaWorld.hopseed, NaturaWorld.sakura);
    tagPlanks(MINEABLE_WITH_AXE, NaturaWorld.redwood);

    tagBlocks(MINEABLE_WITH_AXE, NaturaGadgets.punji);
  }

  @Override
  public String getName() {
    return "Tinkers Construct Block Tags";
  }

  /**
   * Applies a tag to a set of suppliers
   */
  @SafeVarargs
  private void tagBlocks(TagKey<Block> tag, Supplier<? extends Block>... blocks) {
    TagAppender<Block> appender = this.tag(tag);
    for (Supplier<? extends Block> block : blocks) {
      appender.add(block.get());
    }
  }

  /**
   * Applies a set of tags to a block
   */
  @SuppressWarnings("SameParameterValue")
  private void tagBlocks(TagKey<Block> tag1, TagKey<Block> tag2, Supplier<? extends Block>... blocks) {
    tagBlocks(tag1, blocks);
    tagBlocks(tag2, blocks);
  }

  /**
   * Applies a tag to a set of blocks
   */
  @SafeVarargs
  private void tagBlocks(TagKey<Block> tag, EnumObject<?, ? extends Block>... blocks) {
    TagAppender<Block> appender = this.tag(tag);
    for (EnumObject<?, ? extends Block> block : blocks) {
      block.forEach(b -> appender.add(b));
    }
  }

  /**
   * Applies a tag to a set of blocks
   */
  @SafeVarargs
  private void tagBlocks(TagKey<Block> tag1, TagKey<Block> tag2, EnumObject<?, ? extends Block>... blocks) {
    tagBlocks(tag1, blocks);
    tagBlocks(tag2, blocks);
  }

  /**
   * Applies a set of tags to a block
   */
  private void tagBlocks(TagKey<Block> tag, BuildingBlockObject... blocks) {
    TagAppender<Block> appender = this.tag(tag);
    for (BuildingBlockObject block : blocks) {
      block.values().forEach(appender::add);
    }
  }

  /**
   * Applies a set of tags to a block
   */
  @SuppressWarnings("SameParameterValue")
  private void tagBlocks(TagKey<Block> tag1, TagKey<Block> tag2, BuildingBlockObject... blocks) {
    tagBlocks(tag1, blocks);
    tagBlocks(tag2, blocks);
  }

  /**
   * Applies a set of tags to either wood or logs from a block
   */
  @SuppressWarnings("SameParameterValue")
  private void tagLogs(TagKey<Block> tag1, TagKey<Block> tag2, WoodBlockObject... blocks) {
    for (WoodBlockObject block : blocks) {
      tag(tag1).add(block.getLog(), block.getWood());
      tag(tag2).add(block.getLog(), block.getWood());
    }
  }

  /**
   * Applies a set of tags to either wood or logs from a block
   */
  @SuppressWarnings("SameParameterValue")
  private void tagPlanks(TagKey<Block> tag, WoodBlockObject... blocks) {
    for (WoodBlockObject block : blocks) {
      tag(tag).add(block.get(), block.getSlab(), block.getStairs(), block.getFence(),
        block.getStrippedLog(), block.getStrippedWood(), block.getFenceGate(), block.getDoor(), block.getTrapdoor(),
        block.getPressurePlate(), block.getButton(), block.getSign(), block.getWallSign());
    }
  }

  /**
   * Adds all tags relevant to the given wood object
   */
  private void addWoodTags(WoodBlockObject object, boolean doesBurn) {
    // planks, handled by slimy planks tag
    //this.tag(BlockTags.PLANKS).add(object.get());
    this.tag(BlockTags.WOODEN_SLABS).add(object.getSlab());
    this.tag(BlockTags.WOODEN_STAIRS).add(object.getStairs());
    // logs
    this.tag(object.getLogBlockTag()).add(object.getLog(), object.getStrippedLog(), object.getWood(), object.getStrippedWood());

    // doors
    this.tag(BlockTags.WOODEN_FENCES).add(object.getFence());
    this.tag(Tags.Blocks.FENCES_WOODEN).add(object.getFence());
    this.tag(BlockTags.FENCE_GATES).add(object.getFenceGate());
    this.tag(Tags.Blocks.FENCE_GATES_WOODEN).add(object.getFenceGate());
    this.tag(BlockTags.WOODEN_DOORS).add(object.getDoor());
    this.tag(BlockTags.WOODEN_TRAPDOORS).add(object.getTrapdoor());
    // redstone
    this.tag(BlockTags.WOODEN_BUTTONS).add(object.getButton());
    this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(object.getPressurePlate());

    if (doesBurn) {
      // regular logs is handled by slimy logs tag
      this.tag(BlockTags.LOGS_THAT_BURN).addTag(object.getLogBlockTag());
    } else {
      this.tag(BlockTags.NON_FLAMMABLE_WOOD)
        .add(object.get(), object.getSlab(), object.getStairs(),
          object.getFence(), object.getFenceGate(), object.getDoor(), object.getTrapdoor(),
          object.getPressurePlate(), object.getButton())
        .addTag(object.getLogBlockTag());
    }

    // signs
    this.tag(BlockTags.STANDING_SIGNS).add(object.getSign());
    this.tag(BlockTags.WALL_SIGNS).add(object.getWallSign());
  }

  /**
   * Applies a set of tags to either wood or logs from a block
   */
  @SuppressWarnings("SameParameterValue")
  private void tagPlanks(TagKey<Block> tag, RedwoodBlockObject... blocks) {
    for (RedwoodBlockObject block : blocks) {
      tag(tag).add(block.get(), block.getSlab(), block.getStairs(), block.getFence(),
        block.getFenceGate(), block.getDoor(), block.getBarkDoor(), block.getTrapdoor(),
        block.getPressurePlate(), block.getButton(), block.getSign(), block.getWallSign());
    }
  }

  /**
   * Adds all tags relevant to the given wood object
   */
  private void addWoodTags(RedwoodBlockObject object, boolean doesBurn) {
    // planks, handled by slimy planks tag
    //this.tag(BlockTags.PLANKS).add(object.get());
    this.tag(BlockTags.WOODEN_SLABS).add(object.getSlab());
    this.tag(BlockTags.WOODEN_STAIRS).add(object.getStairs());
    // logs
    this.tag(object.getLogBlockTag()).add(object.getBark(), object.getRoot(), object.getHeart());

    // doors
    this.tag(BlockTags.WOODEN_FENCES).add(object.getFence());
    this.tag(Tags.Blocks.FENCES_WOODEN).add(object.getFence());
    this.tag(BlockTags.FENCE_GATES).add(object.getFenceGate());
    this.tag(Tags.Blocks.FENCE_GATES_WOODEN).add(object.getFenceGate());
    this.tag(BlockTags.WOODEN_DOORS).add(object.getDoor(), object.getBarkDoor());
    this.tag(BlockTags.WOODEN_TRAPDOORS).add(object.getTrapdoor());
    // redstone
    this.tag(BlockTags.WOODEN_BUTTONS).add(object.getButton());
    this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(object.getPressurePlate());

    if (doesBurn) {
      // regular logs is handled by slimy logs tag
      this.tag(BlockTags.LOGS_THAT_BURN).addTag(object.getLogBlockTag());
    } else {
      this.tag(BlockTags.NON_FLAMMABLE_WOOD)
        .add(object.get(), object.getSlab(), object.getStairs(),
          object.getFence(), object.getFenceGate(), object.getDoor(), object.getBarkDoor(), object.getTrapdoor(),
          object.getPressurePlate(), object.getButton())
        .addTag(object.getLogBlockTag());
    }

    // signs
    this.tag(BlockTags.STANDING_SIGNS).add(object.getSign());
    this.tag(BlockTags.WALL_SIGNS).add(object.getWallSign());
  }
}
