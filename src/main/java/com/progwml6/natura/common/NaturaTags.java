package com.progwml6.natura.common;

import com.progwml6.natura.Natura;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags.IOptionalNamedTag;

public class NaturaTags {

  public static class Blocks {

    public static final IOptionalNamedTag<Block> REDWOOD_LOGS = tag("redwood_logs");

    private static IOptionalNamedTag<Block> tag(String name) {
      return BlockTags.createOptional(Natura.getResource(name));
    }

    private static IOptionalNamedTag<Block> forgeTag(String name) {
      return BlockTags.createOptional(new ResourceLocation("forge", name));
    }
  }

  public static class Items {

    public static final IOptionalNamedTag<Item> REDWOOD_LOGS = tag("redwood_logs");

    public static final IOptionalNamedTag<Item> RODS_STONE = forgeTag("rods/stone");

    private static IOptionalNamedTag<Item> tag(String name) {
      return ItemTags.createOptional(Natura.getResource(name));
    }

    private static IOptionalNamedTag<Item> forgeTag(String name) {
      return ItemTags.createOptional(new ResourceLocation("forge", name));
    }
  }
}
