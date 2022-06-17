package com.progwml6.natura.common.registration;

import lombok.Getter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.WoodButtonBlock;
import net.minecraft.world.level.block.state.properties.WoodType;
import slimeknights.mantle.registration.object.BuildingBlockObject;
import slimeknights.mantle.registration.object.FenceBuildingBlockObject;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static slimeknights.mantle.registration.RegistrationHelper.castDelegate;

/**
 * Extension of the fence object with all other wood blocks
 */
public class RedwoodBlockObject extends FenceBuildingBlockObject {

  @Getter
  private final WoodType woodType;
  // basic
  private final Supplier<? extends Block> bark;
  private final Supplier<? extends Block> heart;
  private final Supplier<? extends Block> root;
  // doors
  private final Supplier<? extends FenceGateBlock> fenceGate;
  private final Supplier<? extends DoorBlock> door;
  private final Supplier<? extends DoorBlock> barkDoor;
  private final Supplier<? extends TrapDoorBlock> trapdoor;
  // redstone
  private final Supplier<? extends PressurePlateBlock> pressurePlate;
  private final Supplier<? extends WoodButtonBlock> button;
  // signs
  private final Supplier<? extends StandingSignBlock> sign;
  private final Supplier<? extends WallSignBlock> wallSign;
  // tags
  @Getter
  private final TagKey<Block> logBlockTag;
  @Getter
  private final TagKey<Item> logItemTag;

  public RedwoodBlockObject(ResourceLocation name, WoodType woodType, BuildingBlockObject planks,
                            Supplier<? extends Block> bark, Supplier<? extends Block> heart, Supplier<? extends Block> root,
                            Supplier<? extends FenceBlock> fence, Supplier<? extends FenceGateBlock> fenceGate, Supplier<? extends DoorBlock> door, Supplier<? extends DoorBlock> barkDoor, Supplier<? extends TrapDoorBlock> trapdoor,
                            Supplier<? extends PressurePlateBlock> pressurePlate, Supplier<? extends WoodButtonBlock> button,
                            Supplier<? extends StandingSignBlock> sign, Supplier<? extends WallSignBlock> wallSign) {
    super(planks, fence);
    this.woodType = woodType;
    this.bark = bark;
    this.heart = heart;
    this.root = root;
    this.fenceGate = fenceGate;
    this.door = door;
    this.barkDoor = barkDoor;
    this.trapdoor = trapdoor;
    this.pressurePlate = pressurePlate;
    this.button = button;
    this.sign = sign;
    this.wallSign = wallSign;
    ResourceLocation tagName = new ResourceLocation(name.getNamespace(), name.getPath() + "_logs");
    this.logBlockTag = BlockTags.create(tagName);
    this.logItemTag = ItemTags.create(tagName);
  }

  public RedwoodBlockObject(ResourceLocation name, WoodType woodType, BuildingBlockObject planks,
                            Block bark, Block heart, Block root,
                            Block fence, Block fenceGate, Block door, Block barkDoor, Block trapdoor,
                            Block pressurePlate, Block button, Block sign, Block wallSign) {
    super(planks, fence);
    this.woodType = woodType;
    this.bark = castDelegate(bark.delegate);
    this.heart = castDelegate(heart.delegate);
    this.root = castDelegate(root.delegate);
    this.fenceGate = castDelegate(fenceGate.delegate);
    this.door = castDelegate(door.delegate);
    this.barkDoor = castDelegate(barkDoor.delegate);
    this.trapdoor = castDelegate(trapdoor.delegate);
    this.pressurePlate = castDelegate(pressurePlate.delegate);
    this.button = castDelegate(button.delegate);
    this.sign = castDelegate(sign.delegate);
    this.wallSign = castDelegate(wallSign.delegate);
    ResourceLocation tagName = new ResourceLocation(name.getNamespace(), name.getPath() + "_logs");
    this.logBlockTag = BlockTags.create(tagName);
    this.logItemTag = ItemTags.create(tagName);
  }

  /**
   * Gets the log for this wood type
   */
  public Block getBark() {
    return bark.get();
  }

  /**
   * Gets the stripped log for this wood type
   */
  public Block getHeart() {
    return heart.get();
  }

  /**
   * Gets the wood for this wood type
   */
  public Block getRoot() {
    return root.get();
  }

  /* Doors */

  /**
   * Gets the fence gate for this wood type
   */
  public FenceGateBlock getFenceGate() {
    return fenceGate.get();
  }

  /**
   * Gets the door for this wood type
   */
  public DoorBlock getDoor() {
    return door.get();
  }

  /**
   * Gets the door for this wood type
   */
  public DoorBlock getBarkDoor() {
    return barkDoor.get();
  }

  /**
   * Gets the trapdoor for this wood type
   */
  public TrapDoorBlock getTrapdoor() {
    return trapdoor.get();
  }

  /* Redstone */

  /**
   * Gets the pressure plate for this wood type
   */
  public PressurePlateBlock getPressurePlate() {
    return pressurePlate.get();
  }

  /**
   * Gets the button for this wood type
   */
  public WoodButtonBlock getButton() {
    return button.get();
  }

  /* Signs */

  /* Gets the sign for this wood type, can also be used to get the item */
  public StandingSignBlock getSign() {
    return sign.get();
  }

  /* Gets the wall sign for this wood type */
  public WallSignBlock getWallSign() {
    return wallSign.get();
  }

  @Override
  public List<Block> values() {
    return Arrays.asList(
      get(), getSlab(), getStairs(), getFence(),
      getBark(), getHeart(), getRoot(),
      getFenceGate(), getDoor(), getBarkDoor(), getTrapdoor(),
      getPressurePlate(), getButton(), getSign(), getWallSign());
  }

  /**
   * Variants of wood for the register function
   */
  public enum WoodVariant {LOG, WOOD, PLANKS}
}
