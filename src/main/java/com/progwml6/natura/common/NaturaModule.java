package com.progwml6.natura.common;

import com.progwml6.natura.Natura;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.blockstateprovider.BlockStateProviderType;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import slimeknights.mantle.item.BlockTooltipItem;
import slimeknights.mantle.registration.deferred.BlockDeferredRegister;
import slimeknights.mantle.registration.deferred.ItemDeferredRegister;
import slimeknights.mantle.util.SupplierItemGroup;

import javax.annotation.Nullable;
import java.util.function.Function;

/**
 * Contains base helpers for all Natura modules
 */
public class NaturaModule {
  // deferred register instances
  protected static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(Natura.modID);
  protected static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(Natura.modID);
  protected static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Natura.modID);
  protected static final DeferredRegister<Structure<?>> STRUCTURE_FEATURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, Natura.modID);
  protected static final DeferredRegister<BlockStateProviderType<?>> BLOCK_STATE_PROVIDER_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_STATE_PROVIDER_TYPES, Natura.modID);

  /** Creative tab for items that do not fit in another tab */
  @SuppressWarnings("WeakerAccess")
  public static final ItemGroup TAB_GENERAL = new SupplierItemGroup(Natura.modID, "general", () -> new ItemStack(Item.getItemFromBlock(Blocks.SLIME_BLOCK)));

  // base item properties
  protected static final Item.Properties GENERAL_PROPS = new Item.Properties().group(TAB_GENERAL);
  protected static final Function<Block,? extends BlockItem> GENERAL_BLOCK_ITEM = (b) -> new BlockItem(b, GENERAL_PROPS);
  protected static final Function<Block,? extends BlockItem> GENERAL_TOOLTIP_BLOCK_ITEM = (b) -> new BlockTooltipItem(b, GENERAL_PROPS);

  /** Called during construction to initialize the registers for this mod */
  public static void initRegisters() {
    IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    BLOCKS.register(bus);
    ITEMS.register(bus);
    FEATURES.register(bus);
    STRUCTURE_FEATURES.register(bus);
    BLOCK_STATE_PROVIDER_TYPES.register(bus);
  }

  /**
   * We use this builder to ensure that our blocks all have the most important properties set.
   * This way it'll stick out if a block doesn't have a tooltype or sound set.
   * It may be a bit less clear at first, since the actual builder methods tell you what each value means,
   * but as long as we don't statically import the enums it should be just as readable.
   */
  protected static Block.Properties builder(Material material, @Nullable ToolType toolType, SoundType soundType) {
    //noinspection ConstantConditions
    return Block.Properties.create(material).harvestTool(toolType).sound(soundType);
  }

  /**
   * Creates a Natura resource location
   * @param id  Resource path
   * @return  Natura resource location
   */
  protected static ResourceLocation location(String id) {
    return new ResourceLocation(Natura.modID, id);
  }
}
