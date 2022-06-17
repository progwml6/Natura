package com.progwml6.natura.common;

import com.progwml6.natura.Natura;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class NaturaTags {

  /**
   * Called on mod construct to set up tags
   */
  public static void init() {
    Blocks.init();
    Items.init();
  }

  public static class Blocks {

    private static void init() {}

    /**
     * Makes a tag in the tinkers domain
     */
    public static TagKey<Block> tag(String name) {
      return TagKey.create(Registry.BLOCK_REGISTRY, Natura.getResource(name));
    }

    private static TagKey<Block> forgeTag(String name) {
      return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation("forge", name));
    }
  }

  public static class Items {

    private static void init() {}

    public static final TagKey<Item> RODS_STONE = forgeTag("rods/stone");

    /**
     * Makes a tag in the tinkers domain
     */
    private static TagKey<Item> tag(String name) {
      return TagKey.create(Registry.ITEM_REGISTRY, Natura.getResource(name));
    }

    /**
     * Makes a tag in the forge domain
     */
    public static TagKey<Item> forgeTag(String name) {
      return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("forge", name));
    }
  }
}
