package com.progwml6.natura.common;

import com.progwml6.natura.Natura;
import com.progwml6.natura.library.NaturaRegistry;
import com.progwml6.natura.library.registration.BlockDeferredRegister;
import com.progwml6.natura.library.registration.EntityTypeDeferredRegister;
import com.progwml6.natura.library.registration.ItemDeferredRegister;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import slimeknights.mantle.item.BlockTooltipItem;
import slimeknights.tconstruct.library.TinkerRegistry;

import java.util.function.Function;

public abstract class NaturaModule {

  protected static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(Natura.MOD_ID);
  protected static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(Natura.MOD_ID);
  protected static final EntityTypeDeferredRegister ENTITIES = new EntityTypeDeferredRegister(Natura.MOD_ID);

  // base item properties
  protected static final Item.Properties GENERAL_PROPS = new Item.Properties().group(NaturaRegistry.tabGeneral);
  protected static final Function<Block,? extends BlockItem> GENERAL_BLOCK_ITEM = (b) -> new BlockItem(b, GENERAL_PROPS);
  protected static final Function<Block,? extends BlockItem> GENERAL_TOOLTIP_BLOCK_ITEM = (b) -> new BlockTooltipItem(b, GENERAL_PROPS);

  /** Called during construction to initialize the registers for this mod */
  public static void initRegisters() {
    IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    BLOCKS.register(bus);
    ITEMS.register(bus);
    ENTITIES.register(bus);
  }
}
