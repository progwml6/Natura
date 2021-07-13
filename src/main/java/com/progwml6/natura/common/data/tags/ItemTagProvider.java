package com.progwml6.natura.common.data.tags;

import com.progwml6.natura.Natura;
import com.progwml6.natura.common.NaturaTags;
import com.progwml6.natura.gadgets.NaturaGadgets;
import com.progwml6.natura.shared.NaturaCommons;
import com.progwml6.natura.world.NaturaWorld;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ItemTagProvider extends ItemTagsProvider {

  public ItemTagProvider(DataGenerator generatorIn, BlockTagsProvider blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
    super(generatorIn, blockTagProvider, Natura.MOD_ID, existingFileHelper);
  }

  @Override
  protected void registerTags() {
    this.addCommon();
    this.addGadgets();
    this.addWorld();
  }

  private void addCommon() {
    this.getOrCreateBuilder(Tags.Items.INGOTS).add(NaturaCommons.driedBrick.get());
  }

  private void addWorld() {
    this.copy(BlockTags.LOGS, ItemTags.LOGS);
    this.copy(BlockTags.LEAVES, ItemTags.LEAVES);
    this.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);

    this.copy(NaturaTags.Blocks.REDWOOD_LOGS, NaturaTags.Items.REDWOOD_LOGS);

    this.copy(BlockTags.PLANKS, ItemTags.PLANKS);

    // wood
    this.copy(BlockTags.NON_FLAMMABLE_WOOD, ItemTags.NON_FLAMMABLE_WOOD);

    // planks
    this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
    this.copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
    this.copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);

    // logs
    this.copy(NaturaWorld.maple.getLogBlockTag(), NaturaWorld.maple.getLogItemTag());
    this.copy(NaturaWorld.silverbell.getLogBlockTag(), NaturaWorld.silverbell.getLogItemTag());
    this.copy(NaturaWorld.amaranth.getLogBlockTag(), NaturaWorld.amaranth.getLogItemTag());
    this.copy(NaturaWorld.tiger.getLogBlockTag(), NaturaWorld.tiger.getLogItemTag());
    this.copy(NaturaWorld.willow.getLogBlockTag(), NaturaWorld.willow.getLogItemTag());
    this.copy(NaturaWorld.eucalyptus.getLogBlockTag(), NaturaWorld.eucalyptus.getLogItemTag());
    this.copy(NaturaWorld.hopseed.getLogBlockTag(), NaturaWorld.hopseed.getLogItemTag());
    this.copy(NaturaWorld.sakura.getLogBlockTag(), NaturaWorld.sakura.getLogItemTag());

    this.copy(BlockTags.LOGS, ItemTags.LOGS);
    this.copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);

    // doors
    this.copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
    this.copy(Tags.Blocks.FENCES_WOODEN, Tags.Items.FENCES_WOODEN);
    this.copy(Tags.Blocks.FENCE_GATES_WOODEN, Tags.Items.FENCE_GATES_WOODEN);
    this.copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
    this.copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);

    // redstone
    this.copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
    this.copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
    this.copy(BlockTags.STANDING_SIGNS, ItemTags.SIGNS);
  }

  private void addGadgets() {
    this.getOrCreateBuilder(Tags.Items.RODS).addTag(NaturaTags.Items.RODS_STONE);
    this.getOrCreateBuilder(NaturaTags.Items.RODS_STONE).add(NaturaGadgets.stoneStick.get());
  }
}
