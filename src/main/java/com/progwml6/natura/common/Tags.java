package com.progwml6.natura.common;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class Tags {

  public static class Blocks {

    public static final Tag<Block> CLOUDS = tag("clouds");

    private static Tag<Block> tag(String name) {
      return new BlockTags.Wrapper(new ResourceLocation("natura", name));
    }

    private static Tag<Block> forgeTag(String name) {
      return new BlockTags.Wrapper(new ResourceLocation("forge", name));
    }
  }

  public static class Items {

    public static final Tag<Item> CLOUDS = tag("clouds");
    public static final Tag<Item> CROPS_BARLEY = forgeTag("crops/barley");

    private static Tag<Item> tag(String name) {
      return new ItemTags.Wrapper(new ResourceLocation("natura", name));
    }

    private static Tag<Item> forgeTag(String name) {
      return new ItemTags.Wrapper(new ResourceLocation("forge", name));
    }
  }
}
