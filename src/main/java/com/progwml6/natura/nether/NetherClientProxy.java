package com.progwml6.natura.nether;

import static com.progwml6.natura.common.ModelRegisterUtil.registerItemBlockMeta;
import static com.progwml6.natura.common.ModelRegisterUtil.registerItemModel;

import javax.annotation.Nonnull;

import com.progwml6.natura.common.ClientProxy;
import com.progwml6.natura.common.block.BlockEnumBerryBush;
import com.progwml6.natura.common.client.LeavesColorizer;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.library.client.state.CustomStateMap;
import com.progwml6.natura.nether.block.leaves.BlockNetherLeaves;
import com.progwml6.natura.nether.block.leaves.BlockNetherLeaves2;
import com.progwml6.natura.nether.block.logs.BlockNetherLog;
import com.progwml6.natura.nether.block.logs.BlockNetherLog2;
import com.progwml6.natura.nether.block.saplings.BlockNetherSapling;
import com.progwml6.natura.nether.block.saplings.BlockNetherSapling2;
import com.progwml6.natura.nether.block.shrooms.BlockNetherGlowshroom;
import com.progwml6.natura.nether.block.shrooms.BlockNetherLargeGlowshroom;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;

public class NetherClientProxy extends ClientProxy
{
    public static Minecraft minecraft = Minecraft.getMinecraft();

    @Override
    public void preInit()
    {
        super.preInit();
    }

    @Override
    public void init()
    {
        final BlockColors blockColors = minecraft.getBlockColors();

        blockColors.registerBlockColorHandler(new IBlockColor()
        {
            @Override
            public int colorMultiplier(@Nonnull IBlockState state, IBlockAccess access, BlockPos pos, int tintIndex)
            {
                return LeavesColorizer.noColor;
            }
        }, NaturaNether.netherLeaves);

        blockColors.registerBlockColorHandler(new IBlockColor()
        {
            @Override
            public int colorMultiplier(@Nonnull IBlockState state, IBlockAccess access, BlockPos pos, int tintIndex)
            {
                return LeavesColorizer.noColor;
            }
        }, NaturaNether.netherLeaves2);

        minecraft.getItemColors().registerItemColorHandler(new IItemColor()
        {
            @Override
            public int colorMultiplier(@Nonnull ItemStack stack, int tintIndex)
            {
                @SuppressWarnings("deprecation")
                IBlockState iblockstate = ((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata());
                return blockColors.colorMultiplier(iblockstate, null, null, tintIndex);
            }
        }, NaturaNether.netherLeaves, NaturaNether.netherLeaves2);

        super.init();
    }

    @Override
    protected void registerModels()
    {
        // blocks
        ModelLoader.setCustomStateMapper(NaturaNether.netherLeaves, (new StateMap.Builder()).ignore(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE).build());
        ModelLoader.setCustomStateMapper(NaturaNether.netherLeaves2, (new StateMap.Builder()).ignore(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE).build());

        ModelLoader.setCustomStateMapper(NaturaNether.netherSapling, (new StateMap.Builder()).ignore(BlockNetherSapling.STAGE, BlockSapling.TYPE).build());
        ModelLoader.setCustomStateMapper(NaturaNether.netherSapling2, (new StateMap.Builder()).ignore(BlockNetherSapling2.STAGE, BlockSapling.TYPE).build());

        ModelLoader.setCustomStateMapper(NaturaNether.ghostwoodDoor, (new StateMap.Builder()).ignore(BlockDoor.POWERED).build());
        ModelLoader.setCustomStateMapper(NaturaNether.bloodwoodDoor, (new StateMap.Builder()).ignore(BlockDoor.POWERED).build());
        ModelLoader.setCustomStateMapper(NaturaNether.darkwoodDoor, (new StateMap.Builder()).ignore(BlockDoor.POWERED).build());
        ModelLoader.setCustomStateMapper(NaturaNether.fusewoodDoor, (new StateMap.Builder()).ignore(BlockDoor.POWERED).build());

        ModelLoader.setCustomStateMapper(NaturaNether.blazeHopper, (new StateMap.Builder()).ignore(BlockHopper.ENABLED).build());

        registerItemBlockMeta(NaturaNether.netherPlanks);
        registerItemBlockMeta(NaturaNether.netherTaintedSoil);

        registerItemBlockMeta(NaturaNether.netherGlass);

        registerItemBlockMeta(NaturaNether.netherSlab);

        registerItemBlockMeta(NaturaNether.respawnObelisk);

        registerItemModel(NaturaNether.netherStairsGhostwood);
        registerItemModel(NaturaNether.netherStairsBloodwood);
        registerItemModel(NaturaNether.netherStairsDarkwood);
        registerItemModel(NaturaNether.netherStairsFusewood);

        Item heatSandItem = Item.getItemFromBlock(NaturaNether.netherHeatSand);
        ModelLoader.setCustomModelResourceLocation(heatSandItem, 0, new ModelResourceLocation(Util.resource("nether_heat_sand"), "normal"));

        ModelLoader.setCustomStateMapper(NaturaNether.netherThornVines, new CustomStateMap("nether_thorn_vine"));

        this.registerItemModelNatura(new ItemStack(NaturaNether.netherThornVines), "nether_thorn_vine");

        // logs
        Item nether_log = Item.getItemFromBlock(NaturaNether.netherLog);
        for (BlockNetherLog.LogType type : BlockNetherLog.LogType.values())
        {
            String variant = String.format("%s=%s,%s=%s", BlockNetherLog.LOG_AXIS.getName(),
                    BlockNetherLog.LOG_AXIS.getName(BlockNetherLog.EnumAxis.Y), BlockNetherLog.TYPE.getName(),
                    BlockNetherLog.TYPE.getName(type));
            ModelLoader.setCustomModelResourceLocation(nether_log, type.meta, new ModelResourceLocation(nether_log.getRegistryName(), variant));
        }

        Item nether_log2 = Item.getItemFromBlock(NaturaNether.netherLog2);
        for (int meta = 0; meta <= 15; meta++)
        {
            String variant = String.format("%s=%s", BlockNetherLog2.META.getName(), Integer.valueOf(meta));
            ModelLoader.setCustomModelResourceLocation(nether_log2, meta, new ModelResourceLocation(nether_log2.getRegistryName(), variant));
        }

        // leaves
        Item nether_leaves = Item.getItemFromBlock(NaturaNether.netherLeaves);
        for (BlockNetherLeaves.LeavesType type : BlockNetherLeaves.LeavesType.values())
        {
            String variant = String.format("%s=%s", BlockNetherLeaves.TYPE.getName(), BlockNetherLeaves.TYPE.getName(type));
            ModelLoader.setCustomModelResourceLocation(nether_leaves, type.getMeta(), new ModelResourceLocation(nether_leaves.getRegistryName(), variant));
        }

        Item nether_leaves2 = Item.getItemFromBlock(NaturaNether.netherLeaves2);
        for (BlockNetherLeaves2.LeavesType type : BlockNetherLeaves2.LeavesType.values())
        {
            String variant = String.format("%s=%s", BlockNetherLeaves2.TYPE.getName(), BlockNetherLeaves2.TYPE.getName(type));
            ModelLoader.setCustomModelResourceLocation(nether_leaves2, type.getMeta(), new ModelResourceLocation(nether_leaves2.getRegistryName(), variant));
        }

        // saplings
        ItemStack stack = new ItemStack(Item.getItemFromBlock(NaturaNether.netherSapling), 1, NaturaNether.netherSapling.getMetaFromState(NaturaNether.netherSapling.getDefaultState().withProperty(BlockNetherSapling.FOLIAGE, BlockNetherSapling.SaplingType.GHOSTWOOD)));
        this.registerItemModelNatura(stack, "nether_sapling_ghostwood");

        stack = new ItemStack(Item.getItemFromBlock(NaturaNether.netherSapling), 1, NaturaNether.netherSapling.getMetaFromState(NaturaNether.netherSapling.getDefaultState().withProperty(BlockNetherSapling.FOLIAGE, BlockNetherSapling.SaplingType.FUSEWOOD)));
        this.registerItemModelNatura(stack, "nether_sapling_fusewood");

        stack = new ItemStack(Item.getItemFromBlock(NaturaNether.netherSapling), 1, NaturaNether.netherSapling.getMetaFromState(NaturaNether.netherSapling.getDefaultState().withProperty(BlockNetherSapling.FOLIAGE, BlockNetherSapling.SaplingType.DARKWOOD)));
        this.registerItemModelNatura(stack, "nether_sapling_darkwood");

        stack = new ItemStack(Item.getItemFromBlock(NaturaNether.netherSapling2), 1, NaturaNether.netherSapling2.getMetaFromState(NaturaNether.netherSapling2.getDefaultState().withProperty(BlockNetherSapling2.FOLIAGE, BlockNetherSapling2.SaplingType.BLOODWOOD)));
        this.registerItemModelNatura(stack, "nether_sapling_bloodwood");

        // Bushes
        Item blightberry_berrybush = Item.getItemFromBlock(NaturaNether.netherBerryBushBlightberry);
        for (int meta = 0; meta <= 3; meta++)
        {
            String variant = String.format("%s=%s", BlockEnumBerryBush.AGE.getName(), Integer.valueOf(meta));
            ModelLoader.setCustomModelResourceLocation(blightberry_berrybush, meta, new ModelResourceLocation(blightberry_berrybush.getRegistryName(), variant));
        }

        Item duskberry_berrybush = Item.getItemFromBlock(NaturaNether.netherBerryBushDuskberry);
        for (int meta = 0; meta <= 3; meta++)
        {
            String variant = String.format("%s=%s", BlockEnumBerryBush.AGE.getName(), Integer.valueOf(meta));
            ModelLoader.setCustomModelResourceLocation(duskberry_berrybush, meta, new ModelResourceLocation(duskberry_berrybush.getRegistryName(), variant));
        }

        Item skyberry_berrybush = Item.getItemFromBlock(NaturaNether.netherBerryBushSkyberry);
        for (int meta = 0; meta <= 3; meta++)
        {
            String variant = String.format("%s=%s", BlockEnumBerryBush.AGE.getName(), Integer.valueOf(meta));
            ModelLoader.setCustomModelResourceLocation(skyberry_berrybush, meta, new ModelResourceLocation(skyberry_berrybush.getRegistryName(), variant));
        }

        Item stingberry_berrybush = Item.getItemFromBlock(NaturaNether.netherBerryBushStingberry);
        for (int meta = 0; meta <= 3; meta++)
        {
            String variant = String.format("%s=%s", BlockEnumBerryBush.AGE.getName(), Integer.valueOf(meta));
            ModelLoader.setCustomModelResourceLocation(stingberry_berrybush, meta, new ModelResourceLocation(stingberry_berrybush.getRegistryName(), variant));
        }

        // glowshrooms
        ItemStack glowshroom = new ItemStack(Item.getItemFromBlock(NaturaNether.netherGlowshroom), 1, NaturaNether.netherGlowshroom.getMetaFromState(NaturaNether.netherGlowshroom.getDefaultState().withProperty(BlockNetherGlowshroom.TYPE, BlockNetherGlowshroom.GlowshroomType.GREEN)));
        this.registerItemModelNatura(glowshroom, "nether_glowshroom_green");

        glowshroom = new ItemStack(Item.getItemFromBlock(NaturaNether.netherGlowshroom), 1, NaturaNether.netherGlowshroom.getMetaFromState(NaturaNether.netherGlowshroom.getDefaultState().withProperty(BlockNetherGlowshroom.TYPE, BlockNetherGlowshroom.GlowshroomType.BLUE)));
        this.registerItemModelNatura(glowshroom, "nether_glowshroom_blue");

        glowshroom = new ItemStack(Item.getItemFromBlock(NaturaNether.netherGlowshroom), 1, NaturaNether.netherGlowshroom.getMetaFromState(NaturaNether.netherGlowshroom.getDefaultState().withProperty(BlockNetherGlowshroom.TYPE, BlockNetherGlowshroom.GlowshroomType.PURPLE)));
        this.registerItemModelNatura(glowshroom, "nether_glowshroom_purple");

        Item nether_large_green_glowshroom = Item.getItemFromBlock(NaturaNether.netherLargeGreenGlowshroom);
        for (BlockNetherLargeGlowshroom.EnumType type : BlockNetherLargeGlowshroom.EnumType.values())
        {
            String variant = String.format("%s=%s", BlockNetherLargeGlowshroom.VARIANT.getName(), BlockNetherLargeGlowshroom.VARIANT.getName(type));
            ModelLoader.setCustomModelResourceLocation(nether_large_green_glowshroom, type.getMeta(), new ModelResourceLocation(nether_large_green_glowshroom.getRegistryName(), variant));
        }

        Item nether_large_blue_glowshroom = Item.getItemFromBlock(NaturaNether.netherLargeBlueGlowshroom);
        for (BlockNetherLargeGlowshroom.EnumType type : BlockNetherLargeGlowshroom.EnumType.values())
        {
            String variant = String.format("%s=%s", BlockNetherLargeGlowshroom.VARIANT.getName(), BlockNetherLargeGlowshroom.VARIANT.getName(type));
            ModelLoader.setCustomModelResourceLocation(nether_large_blue_glowshroom, type.getMeta(), new ModelResourceLocation(nether_large_blue_glowshroom.getRegistryName(), variant));
        }

        Item nether_large_purple_glowshroom = Item.getItemFromBlock(NaturaNether.netherLargePurpleGlowshroom);
        for (BlockNetherLargeGlowshroom.EnumType type : BlockNetherLargeGlowshroom.EnumType.values())
        {
            String variant = String.format("%s=%s", BlockNetherLargeGlowshroom.VARIANT.getName(), BlockNetherLargeGlowshroom.VARIANT.getName(type));
            ModelLoader.setCustomModelResourceLocation(nether_large_purple_glowshroom, type.getMeta(), new ModelResourceLocation(nether_large_purple_glowshroom.getRegistryName(), variant));
        }

        stack = new ItemStack(Item.getItemFromBlock(NaturaNether.ghostwoodDoor), 1, NaturaNether.ghostwoodDoor.getMetaFromState(NaturaNether.ghostwoodDoor.getDefaultState().withProperty(BlockDoor.FACING, EnumFacing.EAST).withProperty(BlockDoor.OPEN, Boolean.valueOf(false)).withProperty(BlockDoor.HINGE, BlockDoor.EnumHingePosition.LEFT).withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER)));
        this.registerItemModelNatura(stack, "nether_door_ghostwood");

        stack = new ItemStack(Item.getItemFromBlock(NaturaNether.bloodwoodDoor), 1, NaturaNether.bloodwoodDoor.getMetaFromState(NaturaNether.bloodwoodDoor.getDefaultState().withProperty(BlockDoor.FACING, EnumFacing.EAST).withProperty(BlockDoor.OPEN, Boolean.valueOf(false)).withProperty(BlockDoor.HINGE, BlockDoor.EnumHingePosition.LEFT).withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER)));
        this.registerItemModelNatura(stack, "nether_door_bloodwood");

        registerItemModel(NaturaNether.blazeHopper);

        registerItemModel(NaturaNether.netherLever);

        registerItemModel(NaturaNether.netherButton);

        registerItemModel(NaturaNether.netherPressurePlate);

        registerItemModel(NaturaNether.blazeRail);
        registerItemModel(NaturaNether.blazeRailPowered);
        registerItemModel(NaturaNether.blazeRailActivator);
        registerItemModel(NaturaNether.blazeRailDetector);

        registerItemModel(NaturaNether.netherrackFurnace);
        registerItemModel(NaturaNether.litNetherrackFurnace);

        NaturaNether.netherDoors.registerItemModels();
    }
}
