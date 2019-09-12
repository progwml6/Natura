package com.progwml6.natura.shared;

import com.progwml6.natura.Natura;
import com.progwml6.natura.common.NaturaPulse;
import com.progwml6.natura.common.ServerProxy;
import com.progwml6.natura.common.conditions.ConfigOptionCondition;
import com.progwml6.natura.common.conditions.PulseLoadedCondition;
import com.progwml6.natura.library.NaturaPulseIds;
import com.progwml6.natura.library.NaturaRegistry;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.shared.block.CloudBlock;
import com.progwml6.natura.shared.item.BoneBagItem;
import com.progwml6.natura.shared.item.NaturaEdibleItem;
import com.progwml6.natura.shared.item.NaturaSoupItem;
import com.progwml6.natura.shared.item.SeedBagItem;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;
import org.apache.logging.log4j.Logger;
import slimeknights.mantle.item.GeneratedItem;
import slimeknights.mantle.pulsar.pulse.Pulse;

@Pulse(id = NaturaPulseIds.NATURA_COMMONS_PULSE_ID, forced = true)
@ObjectHolder(Natura.modID)
public class NaturaCommons extends NaturaPulse {

  static final Logger log = Util.getLogger(NaturaPulseIds.NATURA_COMMONS_PULSE_ID);

  public static ServerProxy proxy = DistExecutor.runForDist(() -> CommonsClientProxy::new, () -> ServerProxy::new);

  public static final CloudBlock white_cloud = null;
  public static final CloudBlock dark_cloud = null;
  public static final CloudBlock ash_cloud = null;
  public static final CloudBlock sulfur_cloud = null;

  public static final GeneratedItem barley = null;
  public static final GeneratedItem barley_flour = null;
  public static final GeneratedItem wheat_flour = null;
  public static final GeneratedItem cotton = null;
  public static final GeneratedItem sulfur_powder = null;
  public static final GeneratedItem ghostwood_fletching = null;
  public static final GeneratedItem imp_leather = null;
  public static final GeneratedItem flame_string = null;

  public static final NaturaEdibleItem raw_impmeat = null;
  public static final NaturaEdibleItem cooked_impmeat = null;

  public static final NaturaEdibleItem raspberry = null;
  public static final NaturaEdibleItem blueberry = null;
  public static final NaturaEdibleItem blackberry = null;
  public static final NaturaEdibleItem maloberry = null;
  public static final NaturaEdibleItem blightberry = null;
  public static final NaturaEdibleItem duskberry = null;
  public static final NaturaEdibleItem skyberry = null;
  public static final NaturaEdibleItem stingberry = null;
  public static final NaturaSoupItem berry_medley = null;

  public static final NaturaEdibleItem potash_apple = null;

  public static final NaturaEdibleItem cactus_juice = null;

  public static final SeedBagItem wheat_seed_bag = null;
  public static final SeedBagItem carrots_seed_bag = null;
  public static final SeedBagItem potatoes_seed_bag = null;
  public static final SeedBagItem nether_wart_seed_bag = null;

  public static final BoneBagItem bone_meal_bag = null;

  public static final GeneratedItem maple_stick = null;
  public static final GeneratedItem silverbell_stick = null;
  public static final GeneratedItem amaranth_stick = null;
  public static final GeneratedItem tiger_stick = null;
  public static final GeneratedItem willow_stick = null;
  public static final GeneratedItem eucalyptus_stick = null;
  public static final GeneratedItem hopseed_stick = null;
  public static final GeneratedItem sakura_stick = null;
  public static final GeneratedItem redwood_stick = null;
  public static final GeneratedItem ghostwood_stick = null;
  public static final GeneratedItem darkwood_stick = null;
  public static final GeneratedItem fusewood_stick = null;
  public static final GeneratedItem bloodwood_stick = null;

  @SubscribeEvent
  public void registerBlocks(final RegistryEvent.Register<Block> event) {
    IForgeRegistry<Block> registry = event.getRegistry();

    register(registry, new CloudBlock(), "white_cloud");
    register(registry, new CloudBlock(), "dark_cloud");
    register(registry, new CloudBlock(), "ash_cloud");
    register(registry, new CloudBlock(true), "sulfur_cloud");
  }

  @SubscribeEvent
  public void registerItems(final RegistryEvent.Register<Item> event) {
    IForgeRegistry<Item> registry = event.getRegistry();

    registerBlockItem(registry, white_cloud, NaturaRegistry.tabWorld);
    registerBlockItem(registry, dark_cloud, NaturaRegistry.tabWorld);
    registerBlockItem(registry, ash_cloud, NaturaRegistry.tabWorld);
    registerBlockItem(registry, sulfur_cloud, NaturaRegistry.tabWorld);

    register(registry, new GeneratedItem(NaturaRegistry.tabGeneral), "barley");
    register(registry, new GeneratedItem(NaturaRegistry.tabGeneral), "barley_flour");
    register(registry, new GeneratedItem(NaturaRegistry.tabGeneral), "wheat_flour");
    register(registry, new GeneratedItem(NaturaRegistry.tabGeneral), "cotton");
    register(registry, new GeneratedItem(NaturaRegistry.tabGeneral), "sulfur_powder");
    register(registry, new GeneratedItem(NaturaRegistry.tabGeneral), "ghostwood_fletching");
    register(registry, new GeneratedItem(NaturaRegistry.tabGeneral), "imp_leather");
    register(registry, new GeneratedItem(NaturaRegistry.tabGeneral), "flame_string");

    register(registry, new NaturaEdibleItem(NaturaFood.RAW_IMPMEAT, NaturaRegistry.tabGeneral), "raw_impmeat");
    register(registry, new NaturaEdibleItem(NaturaFood.COOKED_IMPMEAT, NaturaRegistry.tabGeneral), "cooked_impmeat");

    register(registry, new NaturaEdibleItem(NaturaFood.BERRY, NaturaRegistry.tabGeneral), "raspberry");
    register(registry, new NaturaEdibleItem(NaturaFood.BERRY, NaturaRegistry.tabGeneral), "blueberry");
    register(registry, new NaturaEdibleItem(NaturaFood.BERRY_MEDLEY, NaturaRegistry.tabGeneral), "blackberry");
    register(registry, new NaturaEdibleItem(NaturaFood.BERRY, NaturaRegistry.tabGeneral), "maloberry");
    register(registry, new NaturaEdibleItem(NaturaFood.BLIGHTBERRY, NaturaRegistry.tabGeneral), "blightberry");
    register(registry, new NaturaEdibleItem(NaturaFood.DUSKBERRY, NaturaRegistry.tabGeneral), "duskberry");
    register(registry, new NaturaEdibleItem(NaturaFood.SKYBERRY, NaturaRegistry.tabGeneral), "skyberry");
    register(registry, new NaturaEdibleItem(NaturaFood.STINGBERRY, NaturaRegistry.tabGeneral), "stingberry");
    register(registry, new NaturaSoupItem(NaturaFood.BERRY_MEDLEY, NaturaRegistry.tabGeneral), "berry_medley");

    register(registry, new NaturaEdibleItem(NaturaFood.POTASH_APPLE, NaturaRegistry.tabGeneral), "potash_apple");

    register(registry, new NaturaEdibleItem(NaturaFood.CACTUS_JUICE, NaturaRegistry.tabGeneral), "cactus_juice");

    register(registry, new NaturaSoupItem(NaturaFood.GLOWSHROOM_STEW, NaturaRegistry.tabGeneral), "glowshroom_stew");

    register(registry, new SeedBagItem(Blocks.WHEAT.getDefaultState().with(CropsBlock.AGE, 0), NaturaRegistry.tabGeneral), "wheat_seed_bag");
    register(registry, new SeedBagItem(Blocks.CARROTS.getDefaultState().with(CropsBlock.AGE, 0), NaturaRegistry.tabGeneral), "carrots_seed_bag");
    register(registry, new SeedBagItem(Blocks.POTATOES.getDefaultState().with(CropsBlock.AGE, 0), NaturaRegistry.tabGeneral), "potatoes_seed_bag");
    register(registry, new SeedBagItem(Blocks.NETHER_WART.getDefaultState().with(NetherWartBlock.AGE, 0), NaturaRegistry.tabGeneral), "nether_wart_seed_bag");

    register(registry, new BoneBagItem(NaturaRegistry.tabGeneral), "bone_meal_bag");

    register(registry, new GeneratedItem(NaturaRegistry.tabGeneral), "maple_stick");
    register(registry, new GeneratedItem(NaturaRegistry.tabGeneral), "silverbell_stick");
    register(registry, new GeneratedItem(NaturaRegistry.tabGeneral), "amaranth_stick");
    register(registry, new GeneratedItem(NaturaRegistry.tabGeneral), "tiger_stick");
    register(registry, new GeneratedItem(NaturaRegistry.tabGeneral), "willow_stick");
    register(registry, new GeneratedItem(NaturaRegistry.tabGeneral), "eucalyptus_stick");
    register(registry, new GeneratedItem(NaturaRegistry.tabGeneral), "hopseed_stick");
    register(registry, new GeneratedItem(NaturaRegistry.tabGeneral), "sakura_stick");
    register(registry, new GeneratedItem(NaturaRegistry.tabGeneral), "redwood_stick");
    register(registry, new GeneratedItem(NaturaRegistry.tabGeneral), "ghostwood_stick");
    register(registry, new GeneratedItem(NaturaRegistry.tabGeneral), "darkwood_stick");
    register(registry, new GeneratedItem(NaturaRegistry.tabGeneral), "fusewood_stick");
    register(registry, new GeneratedItem(NaturaRegistry.tabGeneral), "bloodwood_stick");
  }

  @SubscribeEvent
  public void preInit(final FMLCommonSetupEvent event) {
    proxy.preInit();
  }

  @SubscribeEvent
  public void init(final InterModEnqueueEvent event) {
    proxy.init();
  }

  @SubscribeEvent
  public void registerRecipeSerializers(RegistryEvent.Register<IRecipeSerializer<?>> event) {
    CraftingHelper.register(PulseLoadedCondition.Serializer.INSTANCE);
    CraftingHelper.register(ConfigOptionCondition.Serializer.INSTANCE);
  }

  @SubscribeEvent
  public void postInit(final InterModProcessEvent event) {
    proxy.postInit();

    NaturaRegistry.tabGeneral.setDisplayIcon(new ItemStack(cotton));

    if (!isOverworldLoaded()) {
      NaturaRegistry.tabWorld.setDisplayIcon(new ItemStack(white_cloud));
    }
  }
}
