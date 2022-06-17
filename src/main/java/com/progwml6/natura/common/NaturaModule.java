package com.progwml6.natura.common;

import com.progwml6.natura.Natura;
import com.progwml6.natura.common.registration.BlockDeferredRegisterExtension;
import com.progwml6.natura.gadgets.NaturaGadgets;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import slimeknights.mantle.item.BlockTooltipItem;
import slimeknights.mantle.item.TooltipItem;
import slimeknights.mantle.registration.deferred.EntityTypeDeferredRegister;
import slimeknights.mantle.registration.deferred.ItemDeferredRegister;
import slimeknights.mantle.util.SupplierCreativeTab;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Contains base helpers for all Natura modules. Should not be extended by other mods, this is only for internal usage.
 */
public class NaturaModule {

  protected NaturaModule() {
    // "seal" this class to prevent other mods from using our deferred registers, basically, prevent anyone from outside our package from instantiating an instance. Yes, it happened
    // if you are a mod dev and need a protected method here, just copy it, they are all trivial
    if (!this.getClass().getName().startsWith("com.progwml6.natura.")) {
      throw new IllegalStateException("NaturaModule being extended from invalid package " + this.getClass().getName() + ". This is a bug with the mod containing that class, they should create their own deferred registers.");
    }
  }

  // deferred register instances
  // gameplay singleton
  protected static final BlockDeferredRegisterExtension BLOCKS = new BlockDeferredRegisterExtension(Natura.MOD_ID);
  protected static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(Natura.MOD_ID);

  // gameplay instances
  protected static final EntityTypeDeferredRegister ENTITIES = new EntityTypeDeferredRegister(Natura.MOD_ID);
  // worldgen
  protected static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Natura.MOD_ID);
  protected static final DeferredRegister<StructureFeature<?>> STRUCTURE_FEATURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, Natura.MOD_ID);
  protected static final DeferredRegister<ConfiguredStructureFeature<?, ?>> CONFIGURED_STRUCTURE_FEATURES = DeferredRegister.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, Natura.MOD_ID);
  protected static final DeferredRegister<StructurePieceType> STRUCTURE_PIECE = DeferredRegister.create(Registry.STRUCTURE_PIECE_REGISTRY, Natura.MOD_ID);
  protected static final DeferredRegister<BlockStateProviderType<?>> BLOCK_STATE_PROVIDER_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_STATE_PROVIDER_TYPES, Natura.MOD_ID);

  /**
   * Creative tab for items that do not fit in another tab
   */
  @SuppressWarnings("WeakerAccess")
  public static final CreativeModeTab TAB_GENERAL = new SupplierCreativeTab(Natura.MOD_ID, "general", () -> new ItemStack(NaturaGadgets.stoneStick));

  // base item properties
  protected static final Item.Properties HIDDEN_PROPS = new Item.Properties();
  protected static final Item.Properties GENERAL_PROPS = new Item.Properties().tab(TAB_GENERAL);
  protected static final Function<Block, ? extends BlockItem> HIDDEN_BLOCK_ITEM = (b) -> new BlockItem(b, HIDDEN_PROPS);
  protected static final Function<Block, ? extends BlockItem> GENERAL_BLOCK_ITEM = (b) -> new BlockItem(b, GENERAL_PROPS);
  protected static final Function<Block, ? extends BlockItem> GENERAL_TOOLTIP_BLOCK_ITEM = (b) -> new BlockTooltipItem(b, GENERAL_PROPS);
  protected static final Supplier<Item> TOOLTIP_ITEM = () -> new TooltipItem(GENERAL_PROPS);

  /**
   * Called during construction to initialize the registers for this mod
   */
  public static void initRegisters() {
    IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    // gameplay singleton
    BLOCKS.register(bus);
    ITEMS.register(bus);
    // gameplay instance
    ENTITIES.register(bus);
    // worldgen
    FEATURES.register(bus);
    STRUCTURE_FEATURES.register(bus);
    STRUCTURE_PIECE.register(bus);
    CONFIGURED_STRUCTURE_FEATURES.register(bus);
    BLOCK_STATE_PROVIDER_TYPES.register(bus);
  }

  /**
   * We use this builder to ensure that our blocks all have the most important properties set.
   * This way it'll stick out if a block doesn't have a tooltype or sound set.
   * It may be a bit less clear at first, since the actual builder methods tell you what each value means,
   * but as long as we don't statically import the enums it should be just as readable.
   */
  protected static BlockBehaviour.Properties builder(Material material, SoundType soundType) {
    return Block.Properties.of(material).sound(soundType);
  }

  /**
   * Creates a Tinkers Construct resource location
   *
   * @param path Resource path
   * @return Tinkers Construct resource location
   */
  protected static ResourceLocation resource(String path) {
    return Natura.getResource(path);
  }
}
