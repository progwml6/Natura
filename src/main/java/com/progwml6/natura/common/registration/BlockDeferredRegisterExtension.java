package com.progwml6.natura.common.registration;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.WoodButtonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.block.MantleStandingSignBlock;
import slimeknights.mantle.block.MantleWallSignBlock;
import slimeknights.mantle.block.WoodenDoorBlock;
import slimeknights.mantle.block.entity.MantleSignBlockEntity;
import slimeknights.mantle.item.BurnableBlockItem;
import slimeknights.mantle.item.BurnableSignItem;
import slimeknights.mantle.item.BurnableTallBlockItem;
import slimeknights.mantle.registration.RegistrationHelper;
import slimeknights.mantle.registration.deferred.BlockDeferredRegister;
import slimeknights.mantle.registration.object.BuildingBlockObject;
import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.mantle.registration.object.WoodBlockObject;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Additional methods in deferred register extension
 */
public class BlockDeferredRegisterExtension extends BlockDeferredRegister {

  public BlockDeferredRegisterExtension(String modID) {
    super(modID);
  }

  /**
   * Registers a new wood object
   *
   * @param name            Name of the wood object
   * @param behaviorCreator Logic to create the behavior
   * @param flammable       If true, this wood type is flammable
   * @param group           Item group
   * @return Wood object
   */
  public RedwoodBlockObject registerRedwood(String name, Function<WoodBlockObject.WoodVariant, BlockBehaviour.Properties> behaviorCreator, boolean flammable, CreativeModeTab group) {
    WoodType woodType = WoodType.create(resourceName(name));
    RegistrationHelper.registerWoodType(woodType);
    Item.Properties itemProps = new Item.Properties().tab(group);

    // many of these are already burnable via tags, but simplier to set them all here
    Function<Integer, Function<? super Block, ? extends BlockItem>> burnableItem;
    Function<? super Block, ? extends BlockItem> burnableTallItem;
    BiFunction<? super Block, ? super Block, ? extends BlockItem> burnableSignItem;
    Item.Properties signProps = new Item.Properties().stacksTo(16).tab(group);
    if (flammable) {
      burnableItem = burnTime -> block -> new BurnableBlockItem(block, itemProps, burnTime);
      burnableTallItem = block -> new BurnableTallBlockItem(block, itemProps, 200);
      burnableSignItem = (standing, wall) -> new BurnableSignItem(signProps, standing, wall, 200);
    } else {
      Function<? super Block, ? extends BlockItem> defaultItemBlock = block -> new BlockItem(block, itemProps);
      burnableItem = burnTime -> defaultItemBlock;
      burnableTallItem = block -> new DoubleHighBlockItem(block, itemProps);
      burnableSignItem = (standing, wall) -> new SignItem(signProps, standing, wall);
    }

    // planks
    Function<? super Block, ? extends BlockItem> burnable300 = burnableItem.apply(300);
    BlockBehaviour.Properties planksProps = behaviorCreator.apply(WoodBlockObject.WoodVariant.PLANKS).strength(2.0f, 3.0f);
    BuildingBlockObject planks = registerBuilding(name + "_planks", planksProps, block -> burnableItem.apply(block instanceof SlabBlock ? 150 : 300).apply(block));
    ItemObject<FenceBlock> fence = register(name + "_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(planks.get())), burnable300);

    Supplier<? extends Block> redwoodBlock = () -> new Block(behaviorCreator.apply(WoodBlockObject.WoodVariant.PLANKS).strength(2.0f));
    ItemObject<Block> bark = register(name + "_bark", redwoodBlock, burnable300);
    ItemObject<Block> heart = register(name + "_heart", redwoodBlock, burnable300);
    ItemObject<Block> root = register(name + "_root", redwoodBlock, burnable300);

    // doors
    ItemObject<DoorBlock> door = register(name + "_door", () -> new WoodenDoorBlock(behaviorCreator.apply(WoodBlockObject.WoodVariant.PLANKS).strength(3.0F).noOcclusion()), burnableTallItem);
    ItemObject<DoorBlock> barkDoor = register(name + "_bark_door", () -> new WoodenDoorBlock(behaviorCreator.apply(WoodBlockObject.WoodVariant.PLANKS).strength(3.0F).noOcclusion()), burnableTallItem);
    ItemObject<TrapDoorBlock> trapdoor = register(name + "_trapdoor", () -> new TrapDoorBlock(behaviorCreator.apply(WoodBlockObject.WoodVariant.PLANKS).strength(3.0F).noOcclusion().isValidSpawn(Blocks::never)), burnable300);
    ItemObject<FenceGateBlock> fenceGate = register(name + "_fence_gate", () -> new FenceGateBlock(planksProps), burnable300);
    // redstone
    BlockBehaviour.Properties redstoneProps = behaviorCreator.apply(WoodBlockObject.WoodVariant.PLANKS).noCollission().strength(0.5F);
    ItemObject<PressurePlateBlock> pressurePlate = register(name + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, redstoneProps), burnable300);
    ItemObject<WoodButtonBlock> button = register(name + "_button", () -> new WoodButtonBlock(redstoneProps), burnableItem.apply(100));
    // signs
    RegistryObject<StandingSignBlock> standingSign = registerNoItem(name + "_sign", () -> new MantleStandingSignBlock(behaviorCreator.apply(WoodBlockObject.WoodVariant.PLANKS).noCollission().strength(1.0F), woodType));
    RegistryObject<WallSignBlock> wallSign = registerNoItem(name + "_wall_sign", () -> new MantleWallSignBlock(behaviorCreator.apply(WoodBlockObject.WoodVariant.PLANKS).noCollission().strength(1.0F).lootFrom(standingSign), woodType));
    // tell mantle to inject these into the TE
    MantleSignBlockEntity.registerSignBlock(standingSign);
    MantleSignBlockEntity.registerSignBlock(wallSign);
    // sign is included automatically in asItem of the standing sign
    this.itemRegister.register(name + "_sign", () -> burnableSignItem.apply(standingSign.get(), wallSign.get()));
    // finally, return
    return new RedwoodBlockObject(resource(name), woodType, planks, bark, heart, root, fence, fenceGate, door, barkDoor, trapdoor, pressurePlate, button, standingSign, wallSign);
  }

}
