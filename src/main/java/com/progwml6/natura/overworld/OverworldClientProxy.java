package com.progwml6.natura.overworld;

import javax.annotation.Nonnull;

import com.progwml6.natura.common.ClientProxy;
import com.progwml6.natura.common.block.BlockEnumBerryBush;
import com.progwml6.natura.common.client.GrassColorizer;
import com.progwml6.natura.common.client.LeavesColorizer;
import com.progwml6.natura.overworld.block.grass.BlockColoredGrass;
import com.progwml6.natura.overworld.block.leaves.BlockRedwoodLeaves;
import com.progwml6.natura.overworld.block.logs.BlockOverworldLog;
import com.progwml6.natura.overworld.block.logs.BlockOverworldLog2;
import com.progwml6.natura.overworld.block.saplings.BlockOverworldSapling;
import com.progwml6.natura.overworld.block.saplings.BlockOverworldSapling2;
import com.progwml6.natura.overworld.block.saplings.BlockRedwoodSapling;

import net.minecraft.block.BlockLeaves;
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
                BlockColoredGrass.GrassType type = state.getValue(BlockColoredGrass.TYPE);

                if (pos == null)
                {
                    return GrassColorizer.getGrassColorStatic(type);
                }

                return GrassColorizer.getGrassColorForPos(access, pos, type);
            }
        }, NaturaOverworld.coloredGrass, NaturaOverworld.coloredGrassSlab);

        blockColors.registerBlockColorHandler(new IBlockColor()
        {
            @Override
            public int colorMultiplier(@Nonnull IBlockState state, IBlockAccess access, BlockPos pos, int tintIndex)
            {
                BlockColoredGrass.GrassType type = state.getValue(BlockColoredGrass.TYPE);

                if (pos == null)
                {
                    return GrassColorizer.getGrassColorStatic(type);
                }

                return GrassColorizer.getGrassColorForPos(access, pos, type);
            }
        }, NaturaOverworld.coloredGrass, NaturaOverworld.coloredGrassSlab);

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
            public int getColorFromItemstack(@Nonnull ItemStack stack, int tintIndex)
            {
                @SuppressWarnings("deprecation")
                IBlockState iblockstate = ((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata());
                return blockColors.colorMultiplier(iblockstate, null, null, tintIndex);
            }
        }, NaturaOverworld.overworldLeaves, NaturaOverworld.overworldLeaves2, NaturaOverworld.redwoodLeaves, NaturaOverworld.coloredGrass, NaturaOverworld.coloredGrassSlab);

        super.init();
    }

    @Override
    protected void registerModels()
    {
        // blocks
        ModelLoader.setCustomStateMapper(NaturaOverworld.overworldLeaves, (new StateMap.Builder()).ignore(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE).build());
        ModelLoader.setCustomStateMapper(NaturaOverworld.overworldLeaves2, (new StateMap.Builder()).ignore(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE).build());
        ModelLoader.setCustomStateMapper(NaturaOverworld.redwoodLeaves, (new StateMap.Builder()).ignore(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE).build());

        ModelLoader.setCustomStateMapper(NaturaOverworld.overworldSapling, (new StateMap.Builder()).ignore(BlockOverworldSapling.STAGE, BlockSapling.TYPE).build());
        ModelLoader.setCustomStateMapper(NaturaOverworld.overworldSapling2, (new StateMap.Builder()).ignore(BlockOverworldSapling2.STAGE, BlockSapling.TYPE).build());
        ModelLoader.setCustomStateMapper(NaturaOverworld.redwoodSapling, (new StateMap.Builder()).ignore(BlockRedwoodSapling.STAGE, BlockSapling.TYPE).build());

        this.registerItemBlockMeta(NaturaOverworld.redwoodLog);
        this.registerItemBlockMeta(NaturaOverworld.overworldPlanks);
        this.registerItemBlockMeta(NaturaOverworld.coloredGrass);
        this.registerItemBlockMeta(NaturaOverworld.coloredGrassSlab);

        // slabs
        this.registerItemBlockMeta(NaturaOverworld.overworldSlab);
        this.registerItemBlockMeta(NaturaOverworld.overworldSlab2);

        // stairs
        this.registerItemModel(NaturaOverworld.overworldStairsMaple);
        this.registerItemModel(NaturaOverworld.overworldStairsSilverbell);
        this.registerItemModel(NaturaOverworld.overworldStairsAmaranth);
        this.registerItemModel(NaturaOverworld.overworldStairsTiger);
        this.registerItemModel(NaturaOverworld.overworldStairsWillow);
        this.registerItemModel(NaturaOverworld.overworldStairsEucalyptus);
        this.registerItemModel(NaturaOverworld.overworldStairsHopseed);
        this.registerItemModel(NaturaOverworld.overworldStairsSakura);
        this.registerItemModel(NaturaOverworld.overworldStairsRedwood);

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
        ItemStack stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.overworldSapling), 1, NaturaOverworld.overworldSapling.getMetaFromState(NaturaOverworld.overworldSapling.getDefaultState().withProperty(BlockOverworldSapling.FOLIAGE, BlockOverworldSapling.SaplingType.MAPLE)));
        this.registerItemModel(stack, "overworld_sapling_maple");
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.overworldSapling), 1, NaturaOverworld.overworldSapling.getMetaFromState(NaturaOverworld.overworldSapling.getDefaultState().withProperty(BlockOverworldSapling.FOLIAGE, BlockOverworldSapling.SaplingType.SILVERBELL)));
        this.registerItemModel(stack, "overworld_sapling_silverbell");
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.overworldSapling), 1, NaturaOverworld.overworldSapling.getMetaFromState(NaturaOverworld.overworldSapling.getDefaultState().withProperty(BlockOverworldSapling.FOLIAGE, BlockOverworldSapling.SaplingType.AMARANTH)));
        this.registerItemModel(stack, "overworld_sapling_amaranth");
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.overworldSapling), 1, NaturaOverworld.overworldSapling.getMetaFromState(NaturaOverworld.overworldSapling.getDefaultState().withProperty(BlockOverworldSapling.FOLIAGE, BlockOverworldSapling.SaplingType.TIGER)));
        this.registerItemModel(stack, "overworld_sapling_tiger");

        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.overworldSapling2), 1, NaturaOverworld.overworldSapling2.getMetaFromState(NaturaOverworld.overworldSapling2.getDefaultState().withProperty(BlockOverworldSapling2.FOLIAGE, BlockOverworldSapling2.SaplingType.WILLOW)));
        this.registerItemModel(stack, "overworld_sapling_willow");
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.overworldSapling2), 1, NaturaOverworld.overworldSapling2.getMetaFromState(NaturaOverworld.overworldSapling2.getDefaultState().withProperty(BlockOverworldSapling2.FOLIAGE, BlockOverworldSapling2.SaplingType.EUCALYPTUS)));
        this.registerItemModel(stack, "overworld_sapling_eucalyptus");
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.overworldSapling2), 1, NaturaOverworld.overworldSapling2.getMetaFromState(NaturaOverworld.overworldSapling2.getDefaultState().withProperty(BlockOverworldSapling2.FOLIAGE, BlockOverworldSapling2.SaplingType.HOPSEED)));
        this.registerItemModel(stack, "overworld_sapling_hopseed");
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.overworldSapling2), 1, NaturaOverworld.overworldSapling2.getMetaFromState(NaturaOverworld.overworldSapling2.getDefaultState().withProperty(BlockOverworldSapling2.FOLIAGE, BlockOverworldSapling2.SaplingType.SAKURA)));
        this.registerItemModel(stack, "overworld_sapling_sakura");

        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.redwoodSapling), 1, NaturaOverworld.redwoodSapling.getMetaFromState(NaturaOverworld.redwoodSapling.getDefaultState().withProperty(BlockRedwoodSapling.FOLIAGE, BlockRedwoodSapling.SaplingType.REDWOOD)));
        this.registerItemModel(stack, "overworld_sapling_redwood");

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
    }
}
