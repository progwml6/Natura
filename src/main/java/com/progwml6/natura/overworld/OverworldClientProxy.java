package com.progwml6.natura.overworld;

import static com.progwml6.natura.common.ModelRegisterUtil.registerItemBlockMeta;
import static com.progwml6.natura.common.ModelRegisterUtil.registerItemModel;

import javax.annotation.Nonnull;

import com.progwml6.natura.common.ClientProxy;
import com.progwml6.natura.common.block.BlockEnumBerryBush;
import com.progwml6.natura.common.block.BlockGrassStairs;
import com.progwml6.natura.common.block.base.BlockLeavesBase;
import com.progwml6.natura.common.client.GrassColorizer;
import com.progwml6.natura.common.client.LeavesColorizer;
import com.progwml6.natura.overworld.block.crops.BlockNaturaBarley;
import com.progwml6.natura.overworld.block.crops.BlockNaturaCotton;
import com.progwml6.natura.overworld.block.grass.BlockColoredGrass;
import com.progwml6.natura.overworld.block.leaves.BlockRedwoodLeaves;
import com.progwml6.natura.overworld.block.logs.BlockOverworldLog;
import com.progwml6.natura.overworld.block.logs.BlockOverworldLog2;
import com.progwml6.natura.overworld.block.saguaro.BlockSaguaroBaby;
import com.progwml6.natura.overworld.block.saplings.BlockOverworldSapling;
import com.progwml6.natura.overworld.block.saplings.BlockOverworldSapling2;
import com.progwml6.natura.overworld.block.saplings.BlockRedwoodSapling;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;

public class OverworldClientProxy extends ClientProxy
{
    public static GrassColorizer grassColorizer = new GrassColorizer();

    public static Minecraft minecraft = Minecraft.getMinecraft();

    @Override
    public void preInit()
    {
        ((IReloadableResourceManager) minecraft.getResourceManager()).registerReloadListener(grassColorizer);

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
                if (state.getBlock().getClass() == BlockGrassStairs.class)
                {
                    BlockGrassStairs grassStairs = (BlockGrassStairs) state.getBlock();
                    BlockColoredGrass.GrassType type = grassStairs.customModelState.getValue(BlockColoredGrass.TYPE);
                    if (pos == null)
                    {
                        return GrassColorizer.getGrassColorStatic(type);
                    }

                    return GrassColorizer.getGrassColorForPos(access, pos, type);
                }
                else
                {
                    BlockColoredGrass.GrassType type = state.getValue(BlockColoredGrass.TYPE);

                    if (pos == null)
                    {
                        return GrassColorizer.getGrassColorStatic(type);
                    }

                    return GrassColorizer.getGrassColorForPos(access, pos, type);
                }
            }
        }, NaturaOverworld.coloredGrass, NaturaOverworld.coloredGrassSlab, NaturaOverworld.coloredGrassStairsTopiary, NaturaOverworld.coloredGrassStairsBlueGrass, NaturaOverworld.coloredGrassStairsAutumnal);

        blockColors.registerBlockColorHandler(new IBlockColor()
        {
            @Override
            public int colorMultiplier(@Nonnull IBlockState state, IBlockAccess access, BlockPos pos, int tintIndex)
            {
                BlockOverworldLog.LogType type = state.getValue(BlockOverworldLog.TYPE);

                if (pos == null)
                {
                    return LeavesColorizer.getOverworldLeavesColorStatic(type);
                }

                return LeavesColorizer.getOverworldLeavesColorForPos(access, pos, type);
            }
        }, NaturaOverworld.overworldLeaves);

        blockColors.registerBlockColorHandler(new IBlockColor()
        {
            @Override
            public int colorMultiplier(@Nonnull IBlockState state, IBlockAccess access, BlockPos pos, int tintIndex)
            {
                BlockOverworldLog2.LogType type = state.getValue(BlockOverworldLog2.TYPE);

                if (pos == null)
                {
                    return LeavesColorizer.getSecondOverworldLeavesColorStatic(type);
                }

                return LeavesColorizer.getSecondOverworldLeavesColorForPos(access, pos, type);
            }
        }, NaturaOverworld.overworldLeaves2);

        blockColors.registerBlockColorHandler(new IBlockColor()
        {
            @Override
            public int colorMultiplier(@Nonnull IBlockState state, IBlockAccess access, BlockPos pos, int tintIndex)
            {
                BlockRedwoodLeaves.RedwoodType type = state.getValue(BlockRedwoodLeaves.TYPE);

                if (pos == null)
                {
                    return LeavesColorizer.getRedwoodLeavesColorStatic(type);
                }

                return LeavesColorizer.getRedwoodLeavesColorForPos(access, pos, type);
            }
        }, NaturaOverworld.redwoodLeaves);

        minecraft.getItemColors().registerItemColorHandler(new IItemColor()
        {
            @Override
            public int colorMultiplier(@Nonnull ItemStack stack, int tintIndex)
            {
                @SuppressWarnings("deprecation")
                IBlockState iblockstate = ((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata());
                return blockColors.colorMultiplier(iblockstate, null, null, tintIndex);
            }
        }, NaturaOverworld.overworldLeaves, NaturaOverworld.overworldLeaves2, NaturaOverworld.redwoodLeaves, NaturaOverworld.coloredGrass, NaturaOverworld.coloredGrassSlab, NaturaOverworld.coloredGrassStairsTopiary, NaturaOverworld.coloredGrassStairsBlueGrass, NaturaOverworld.coloredGrassStairsAutumnal);

        super.init();
    }

    @Override
    protected void registerModels()
    {
        // blocks
        ModelLoader.setCustomStateMapper(NaturaOverworld.overworldLeaves, (new StateMap.Builder()).ignore(BlockLeavesBase.CHECK_DECAY, BlockLeavesBase.DECAYABLE).build());
        ModelLoader.setCustomStateMapper(NaturaOverworld.overworldLeaves2, (new StateMap.Builder()).ignore(BlockLeavesBase.CHECK_DECAY, BlockLeavesBase.DECAYABLE).build());
        ModelLoader.setCustomStateMapper(NaturaOverworld.redwoodLeaves, (new StateMap.Builder()).ignore(BlockLeavesBase.CHECK_DECAY, BlockLeavesBase.DECAYABLE).build());

        ModelLoader.setCustomStateMapper(NaturaOverworld.overworldSapling, (new StateMap.Builder()).ignore(BlockOverworldSapling.STAGE, BlockSapling.TYPE).build());
        ModelLoader.setCustomStateMapper(NaturaOverworld.overworldSapling2, (new StateMap.Builder()).ignore(BlockOverworldSapling2.STAGE, BlockSapling.TYPE).build());
        ModelLoader.setCustomStateMapper(NaturaOverworld.redwoodSapling, (new StateMap.Builder()).ignore(BlockRedwoodSapling.STAGE, BlockSapling.TYPE).build());

        ModelLoader.setCustomStateMapper(NaturaOverworld.eucalyptusDoor, (new StateMap.Builder()).ignore(BlockDoor.POWERED).build());
        ModelLoader.setCustomStateMapper(NaturaOverworld.hopseedDoor, (new StateMap.Builder()).ignore(BlockDoor.POWERED).build());
        ModelLoader.setCustomStateMapper(NaturaOverworld.sakuraDoor, (new StateMap.Builder()).ignore(BlockDoor.POWERED).build());
        ModelLoader.setCustomStateMapper(NaturaOverworld.redwoodDoor, (new StateMap.Builder()).ignore(BlockDoor.POWERED).build());
        ModelLoader.setCustomStateMapper(NaturaOverworld.redwoodBarkDoor, (new StateMap.Builder()).ignore(BlockDoor.POWERED).build());

        registerItemBlockMeta(NaturaOverworld.redwoodLog);
        registerItemBlockMeta(NaturaOverworld.overworldPlanks);
        registerItemBlockMeta(NaturaOverworld.coloredGrass);
        registerItemBlockMeta(NaturaOverworld.coloredGrassSlab);
        registerItemModel(NaturaOverworld.coloredGrassStairsTopiary);
        registerItemModel(NaturaOverworld.coloredGrassStairsBlueGrass);
        registerItemModel(NaturaOverworld.coloredGrassStairsAutumnal);

        ItemStack stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.bluebellsFlower), 1, 0);
        this.registerItemModelNatura(stack, "bluebells_flower");

        // slabs
        registerItemBlockMeta(NaturaOverworld.overworldSlab);
        registerItemBlockMeta(NaturaOverworld.overworldSlab2);

        // stairs
        registerItemModel(NaturaOverworld.overworldStairsMaple);
        registerItemModel(NaturaOverworld.overworldStairsSilverbell);
        registerItemModel(NaturaOverworld.overworldStairsAmaranth);
        registerItemModel(NaturaOverworld.overworldStairsTiger);
        registerItemModel(NaturaOverworld.overworldStairsWillow);
        registerItemModel(NaturaOverworld.overworldStairsEucalyptus);
        registerItemModel(NaturaOverworld.overworldStairsHopseed);
        registerItemModel(NaturaOverworld.overworldStairsSakura);
        registerItemModel(NaturaOverworld.overworldStairsRedwood);

        Item overworld_log = Item.getItemFromBlock(NaturaOverworld.overworldLog);
        for (BlockOverworldLog.LogType type : BlockOverworldLog.LogType.values())
        {
            String variant = String.format("%s=%s,%s=%s", BlockOverworldLog.LOG_AXIS.getName(),
                    BlockOverworldLog.LOG_AXIS.getName(BlockOverworldLog.EnumAxis.Y), BlockOverworldLog.TYPE.getName(), BlockOverworldLog.TYPE.getName(type));
            ModelLoader.setCustomModelResourceLocation(overworld_log, type.meta, new ModelResourceLocation(overworld_log.getRegistryName(), variant));
        }

        Item overworld_log2 = Item.getItemFromBlock(NaturaOverworld.overworldLog2);
        for (BlockOverworldLog2.LogType type : BlockOverworldLog2.LogType.values())
        {
            String variant = String.format("%s=%s,%s=%s", BlockOverworldLog2.LOG_AXIS.getName(),
                    BlockOverworldLog2.LOG_AXIS.getName(BlockOverworldLog2.EnumAxis.Y), BlockOverworldLog2.TYPE.getName(),
                    BlockOverworldLog2.TYPE.getName(type));
            ModelLoader.setCustomModelResourceLocation(overworld_log2, type.meta, new ModelResourceLocation(overworld_log2.getRegistryName(), variant));
        }

        // leaves
        Item overworld_leaves = Item.getItemFromBlock(NaturaOverworld.overworldLeaves);
        for (BlockOverworldLog.LogType type : BlockOverworldLog.LogType.values())
        {
            String variant = String.format("%s=%s", BlockOverworldLog.TYPE.getName(), BlockOverworldLog.TYPE.getName(type));
            ModelLoader.setCustomModelResourceLocation(overworld_leaves, type.getMeta(), new ModelResourceLocation(overworld_leaves.getRegistryName(), variant));
        }

        Item overworld_leaves2 = Item.getItemFromBlock(NaturaOverworld.overworldLeaves2);
        for (BlockOverworldLog2.LogType type : BlockOverworldLog2.LogType.values())
        {
            String variant = String.format("%s=%s", BlockOverworldLog2.TYPE.getName(), BlockOverworldLog2.TYPE.getName(type));
            ModelLoader.setCustomModelResourceLocation(overworld_leaves2, type.getMeta(), new ModelResourceLocation(overworld_leaves2.getRegistryName(), variant));
        }

        Item redwood_leaves = Item.getItemFromBlock(NaturaOverworld.redwoodLeaves);
        for (BlockRedwoodLeaves.RedwoodType type : BlockRedwoodLeaves.RedwoodType.values())
        {
            String variant = String.format("%s=%s", BlockRedwoodLeaves.TYPE.getName(), BlockRedwoodLeaves.TYPE.getName(type));
            ModelLoader.setCustomModelResourceLocation(redwood_leaves, type.getMeta(), new ModelResourceLocation(redwood_leaves.getRegistryName(), variant));
        }

        // saplings
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.overworldSapling), 1, NaturaOverworld.overworldSapling.getMetaFromState(NaturaOverworld.overworldSapling.getDefaultState().withProperty(BlockOverworldSapling.FOLIAGE, BlockOverworldSapling.SaplingType.MAPLE)));
        this.registerItemModelNatura(stack, "overworld_sapling_maple");
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.overworldSapling), 1, NaturaOverworld.overworldSapling.getMetaFromState(NaturaOverworld.overworldSapling.getDefaultState().withProperty(BlockOverworldSapling.FOLIAGE, BlockOverworldSapling.SaplingType.SILVERBELL)));
        this.registerItemModelNatura(stack, "overworld_sapling_silverbell");
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.overworldSapling), 1, NaturaOverworld.overworldSapling.getMetaFromState(NaturaOverworld.overworldSapling.getDefaultState().withProperty(BlockOverworldSapling.FOLIAGE, BlockOverworldSapling.SaplingType.AMARANTH)));
        this.registerItemModelNatura(stack, "overworld_sapling_amaranth");
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.overworldSapling), 1, NaturaOverworld.overworldSapling.getMetaFromState(NaturaOverworld.overworldSapling.getDefaultState().withProperty(BlockOverworldSapling.FOLIAGE, BlockOverworldSapling.SaplingType.TIGER)));
        this.registerItemModelNatura(stack, "overworld_sapling_tiger");

        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.overworldSapling2), 1, NaturaOverworld.overworldSapling2.getMetaFromState(NaturaOverworld.overworldSapling2.getDefaultState().withProperty(BlockOverworldSapling2.FOLIAGE, BlockOverworldSapling2.SaplingType.WILLOW)));
        this.registerItemModelNatura(stack, "overworld_sapling_willow");
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.overworldSapling2), 1, NaturaOverworld.overworldSapling2.getMetaFromState(NaturaOverworld.overworldSapling2.getDefaultState().withProperty(BlockOverworldSapling2.FOLIAGE, BlockOverworldSapling2.SaplingType.EUCALYPTUS)));
        this.registerItemModelNatura(stack, "overworld_sapling_eucalyptus");
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.overworldSapling2), 1, NaturaOverworld.overworldSapling2.getMetaFromState(NaturaOverworld.overworldSapling2.getDefaultState().withProperty(BlockOverworldSapling2.FOLIAGE, BlockOverworldSapling2.SaplingType.HOPSEED)));
        this.registerItemModelNatura(stack, "overworld_sapling_hopseed");
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.overworldSapling2), 1, NaturaOverworld.overworldSapling2.getMetaFromState(NaturaOverworld.overworldSapling2.getDefaultState().withProperty(BlockOverworldSapling2.FOLIAGE, BlockOverworldSapling2.SaplingType.SAKURA)));
        this.registerItemModelNatura(stack, "overworld_sapling_sakura");

        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.redwoodSapling), 1, NaturaOverworld.redwoodSapling.getMetaFromState(NaturaOverworld.redwoodSapling.getDefaultState().withProperty(BlockRedwoodSapling.FOLIAGE, BlockRedwoodSapling.SaplingType.REDWOOD)));
        this.registerItemModelNatura(stack, "overworld_sapling_redwood");

        Item raspberry_berrybush = Item.getItemFromBlock(NaturaOverworld.overworldBerryBushRaspberry);
        for (int meta = 0; meta <= 3; meta++)
        {
            String variant = String.format("%s=%s", BlockEnumBerryBush.AGE.getName(), Integer.valueOf(meta));
            ModelLoader.setCustomModelResourceLocation(raspberry_berrybush, meta, new ModelResourceLocation(raspberry_berrybush.getRegistryName(), variant));
        }

        Item blueberry_berrybush = Item.getItemFromBlock(NaturaOverworld.overworldBerryBushBlueberry);
        for (int meta = 0; meta <= 3; meta++)
        {
            String variant = String.format("%s=%s", BlockEnumBerryBush.AGE.getName(), Integer.valueOf(meta));
            ModelLoader.setCustomModelResourceLocation(blueberry_berrybush, meta, new ModelResourceLocation(blueberry_berrybush.getRegistryName(), variant));
        }

        Item blackberry_berrybush = Item.getItemFromBlock(NaturaOverworld.overworldBerryBushBlackberry);
        for (int meta = 0; meta <= 3; meta++)
        {
            String variant = String.format("%s=%s", BlockEnumBerryBush.AGE.getName(), Integer.valueOf(meta));
            ModelLoader.setCustomModelResourceLocation(blackberry_berrybush, meta, new ModelResourceLocation(blackberry_berrybush.getRegistryName(), variant));
        }

        Item maloberry_berrybush = Item.getItemFromBlock(NaturaOverworld.overworldBerryBushMaloberry);
        for (int meta = 0; meta <= 3; meta++)
        {
            String variant = String.format("%s=%s", BlockEnumBerryBush.AGE.getName(), Integer.valueOf(meta));
            ModelLoader.setCustomModelResourceLocation(maloberry_berrybush, meta, new ModelResourceLocation(maloberry_berrybush.getRegistryName(), variant));
        }

        Item barley_crop = Item.getItemFromBlock(NaturaOverworld.barleyCrop);
        for (int meta = 0; meta <= 3; meta++)
        {
            String variant = String.format("%s=%s", BlockNaturaBarley.AGE.getName(), Integer.valueOf(meta));
            ModelLoader.setCustomModelResourceLocation(barley_crop, meta, new ModelResourceLocation(barley_crop.getRegistryName(), variant));
        }

        Item cotton_crop = Item.getItemFromBlock(NaturaOverworld.cottonCrop);
        for (int meta = 0; meta <= 4; meta++)
        {
            String variant = String.format("%s=%s", BlockNaturaCotton.AGE.getName(), Integer.valueOf(meta));
            ModelLoader.setCustomModelResourceLocation(cotton_crop, meta, new ModelResourceLocation(cotton_crop.getRegistryName(), variant));
        }

        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.eucalyptusDoor), 1, NaturaOverworld.eucalyptusDoor.getMetaFromState(NaturaOverworld.eucalyptusDoor.getDefaultState().withProperty(BlockDoor.FACING, EnumFacing.EAST).withProperty(BlockDoor.OPEN, Boolean.valueOf(false)).withProperty(BlockDoor.HINGE, BlockDoor.EnumHingePosition.LEFT).withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER)));
        this.registerItemModelNatura(stack, "overworld_door_eucalyptus");
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.hopseedDoor), 1, NaturaOverworld.hopseedDoor.getMetaFromState(NaturaOverworld.hopseedDoor.getDefaultState().withProperty(BlockDoor.FACING, EnumFacing.EAST).withProperty(BlockDoor.OPEN, Boolean.valueOf(false)).withProperty(BlockDoor.HINGE, BlockDoor.EnumHingePosition.LEFT).withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER)));
        this.registerItemModelNatura(stack, "overworld_door_hopseed");
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.sakuraDoor), 1, NaturaOverworld.sakuraDoor.getMetaFromState(NaturaOverworld.sakuraDoor.getDefaultState().withProperty(BlockDoor.FACING, EnumFacing.EAST).withProperty(BlockDoor.OPEN, Boolean.valueOf(false)).withProperty(BlockDoor.HINGE, BlockDoor.EnumHingePosition.LEFT).withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER)));
        this.registerItemModelNatura(stack, "overworld_door_sakura");
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.redwoodDoor), 1, NaturaOverworld.redwoodDoor.getMetaFromState(NaturaOverworld.redwoodDoor.getDefaultState().withProperty(BlockDoor.FACING, EnumFacing.EAST).withProperty(BlockDoor.OPEN, Boolean.valueOf(false)).withProperty(BlockDoor.HINGE, BlockDoor.EnumHingePosition.LEFT).withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER)));
        this.registerItemModelNatura(stack, "overworld_door_redwood");
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.redwoodBarkDoor), 1, NaturaOverworld.redwoodBarkDoor.getMetaFromState(NaturaOverworld.redwoodBarkDoor.getDefaultState().withProperty(BlockDoor.FACING, EnumFacing.EAST).withProperty(BlockDoor.OPEN, Boolean.valueOf(false)).withProperty(BlockDoor.HINGE, BlockDoor.EnumHingePosition.LEFT).withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER)));
        this.registerItemModelNatura(stack, "overworld_door_redwood_bark");

        registerItemModel(NaturaOverworld.saguaro);

        Item saguaro_baby = Item.getItemFromBlock(NaturaOverworld.saguaroBaby);
        for (int meta = 0; meta <= 1; meta++)
        {
            String variant = String.format("%s=%s", BlockSaguaroBaby.AGE.getName(), Integer.valueOf(meta));
            ModelLoader.setCustomModelResourceLocation(saguaro_baby, meta, new ModelResourceLocation(saguaro_baby.getRegistryName(), variant));
        }

        registerItemModel(NaturaOverworld.saguaroFruit);

        registerItemModel(NaturaOverworld.saguaroFruitItem);

        NaturaOverworld.overworldSeeds.registerItemModels();
        NaturaOverworld.overworldSeedBags.registerItemModels();
        NaturaOverworld.overworldDoors.registerItemModels();
    }
}
