package com.progwml6.natura.world;

import javax.annotation.Nonnull;

import com.progwml6.natura.common.ClientProxy;
import com.progwml6.natura.world.block.grass.BlockColoredGrass;
import com.progwml6.natura.world.block.grass.BlockColoredGrassSlab;
import com.progwml6.natura.world.block.leaves.nether.BlockNetherLeaves;
import com.progwml6.natura.world.block.leaves.nether.BlockNetherLeaves2;
import com.progwml6.natura.world.block.leaves.overworld.BlockRedwoodLeaves;
import com.progwml6.natura.world.block.logs.nether.BlockNetherLog;
import com.progwml6.natura.world.block.logs.overworld.BlockOverworldLog;
import com.progwml6.natura.world.block.logs.overworld.BlockOverworldLog2;
import com.progwml6.natura.world.block.saplings.nether.BlockNetherSapling;
import com.progwml6.natura.world.block.saplings.overworld.BlockOverworldSapling;
import com.progwml6.natura.world.block.saplings.overworld.BlockOverworldSapling2;
import com.progwml6.natura.world.block.saplings.overworld.BlockRedwoodSapling;
import com.progwml6.natura.world.client.colorizers.GrassColorizer;
import com.progwml6.natura.world.client.colorizers.LeavesColorizer;

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

public class WorldClientProxy extends ClientProxy
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
        }, NaturaWorld.coloredGrass);

        blockColors.registerBlockColorHandler(new IBlockColor()
        {
            @Override
            public int colorMultiplier(@Nonnull IBlockState state, IBlockAccess access, BlockPos pos, int tintIndex)
            {
                BlockColoredGrass.GrassType type = state.getValue(BlockColoredGrassSlab.TYPE);

                if (pos == null)
                {
                    return GrassColorizer.getGrassColorStatic(type);
                }

                return GrassColorizer.getGrassColorForPos(access, pos, type);
            }
        }, NaturaWorld.coloredGrassSlab);

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
        }, NaturaWorld.overworldLeaves);

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
        }, NaturaWorld.overworldLeaves2);

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
        }, NaturaWorld.redwoodLeaves);

        blockColors.registerBlockColorHandler(new IBlockColor()
        {
            @Override
            public int colorMultiplier(@Nonnull IBlockState state, IBlockAccess access, BlockPos pos, int tintIndex)
            {
                return LeavesColorizer.noColor;
            }
        }, NaturaWorld.netherLeaves);

        blockColors.registerBlockColorHandler(new IBlockColor()
        {
            @Override
            public int colorMultiplier(@Nonnull IBlockState state, IBlockAccess access, BlockPos pos, int tintIndex)
            {
                return LeavesColorizer.noColor;
            }
        }, NaturaWorld.netherLeaves2);

        minecraft.getItemColors().registerItemColorHandler(new IItemColor()
        {
            @Override
            public int getColorFromItemstack(@Nonnull ItemStack stack, int tintIndex)
            {
                @SuppressWarnings("deprecation")
                IBlockState iblockstate = ((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata());
                return blockColors.colorMultiplier(iblockstate, null, null, tintIndex);
            }
        }, NaturaWorld.overworldLeaves, NaturaWorld.overworldLeaves2, NaturaWorld.redwoodLeaves, NaturaWorld.netherLeaves, NaturaWorld.netherLeaves2, NaturaWorld.coloredGrass, NaturaWorld.coloredGrassSlab);

        super.init();
    }

    @Override
    protected void registerModels()
    {
        // blocks
        ModelLoader.setCustomStateMapper(NaturaWorld.overworldLeaves, (new StateMap.Builder()).ignore(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE).build());
        ModelLoader.setCustomStateMapper(NaturaWorld.overworldLeaves2, (new StateMap.Builder()).ignore(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE).build());
        ModelLoader.setCustomStateMapper(NaturaWorld.redwoodLeaves, (new StateMap.Builder()).ignore(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE).build());
        ModelLoader.setCustomStateMapper(NaturaWorld.netherLeaves, (new StateMap.Builder()).ignore(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE).build());
        ModelLoader.setCustomStateMapper(NaturaWorld.netherLeaves2, (new StateMap.Builder()).ignore(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE).build());

        ModelLoader.setCustomStateMapper(NaturaWorld.overworldSapling, (new StateMap.Builder()).ignore(BlockOverworldSapling.STAGE, BlockSapling.TYPE).build());
        ModelLoader.setCustomStateMapper(NaturaWorld.overworldSapling2, (new StateMap.Builder()).ignore(BlockOverworldSapling2.STAGE, BlockSapling.TYPE).build());
        ModelLoader.setCustomStateMapper(NaturaWorld.redwoodSapling, (new StateMap.Builder()).ignore(BlockRedwoodSapling.STAGE, BlockSapling.TYPE).build());
        ModelLoader.setCustomStateMapper(NaturaWorld.netherSapling, (new StateMap.Builder()).ignore(BlockNetherSapling.STAGE, BlockSapling.TYPE).build());

        this.registerItemBlockMeta(NaturaWorld.redwoodLog);
        this.registerItemBlockMeta(NaturaWorld.overworldPlanks);
        this.registerItemBlockMeta(NaturaWorld.netherPlanks);
        this.registerItemBlockMeta(NaturaWorld.coloredGrass);
        this.registerItemBlockMeta(NaturaWorld.coloredGrassSlab);

        Item overworld_log = Item.getItemFromBlock(NaturaWorld.overworldLog);
        for (BlockOverworldLog.LogType type : BlockOverworldLog.LogType.values())
        {
            String variant = String.format("%s=%s,%s=%s", BlockOverworldLog.LOG_AXIS.getName(),
                    BlockOverworldLog.LOG_AXIS.getName(BlockOverworldLog.EnumAxis.Y), BlockOverworldLog.TYPE.getName(), BlockOverworldLog.TYPE.getName(type));
            ModelLoader.setCustomModelResourceLocation(overworld_log, type.meta, new ModelResourceLocation(overworld_log.getRegistryName(), variant));
        }

        Item overworld_log2 = Item.getItemFromBlock(NaturaWorld.overworldLog2);
        for (BlockOverworldLog2.LogType type : BlockOverworldLog2.LogType.values())
        {
            String variant = String.format("%s=%s,%s=%s", BlockOverworldLog2.LOG_AXIS.getName(),
                    BlockOverworldLog2.LOG_AXIS.getName(BlockOverworldLog2.EnumAxis.Y), BlockOverworldLog2.TYPE.getName(),
                    BlockOverworldLog2.TYPE.getName(type));
            ModelLoader.setCustomModelResourceLocation(overworld_log2, type.meta, new ModelResourceLocation(overworld_log2.getRegistryName(), variant));
        }

        Item nether_log = Item.getItemFromBlock(NaturaWorld.netherLog);
        for (BlockNetherLog.LogType type : BlockNetherLog.LogType.values())
        {
            String variant = String.format("%s=%s,%s=%s", BlockNetherLog.LOG_AXIS.getName(),
                    BlockNetherLog.LOG_AXIS.getName(BlockNetherLog.EnumAxis.Y), BlockNetherLog.TYPE.getName(),
                    BlockNetherLog.TYPE.getName(type));
            ModelLoader.setCustomModelResourceLocation(nether_log, type.meta, new ModelResourceLocation(nether_log.getRegistryName(), variant));
        }

        // leaves
        Item overworld_leaves = Item.getItemFromBlock(NaturaWorld.overworldLeaves);
        for (BlockOverworldLog.LogType type : BlockOverworldLog.LogType.values())
        {
            String variant = String.format("%s=%s", BlockOverworldLog.TYPE.getName(), BlockOverworldLog.TYPE.getName(type));
            ModelLoader.setCustomModelResourceLocation(overworld_leaves, type.getMeta(), new ModelResourceLocation(overworld_leaves.getRegistryName(), variant));
        }

        Item overworld_leaves2 = Item.getItemFromBlock(NaturaWorld.overworldLeaves2);
        for (BlockOverworldLog2.LogType type : BlockOverworldLog2.LogType.values())
        {
            String variant = String.format("%s=%s", BlockOverworldLog2.TYPE.getName(), BlockOverworldLog2.TYPE.getName(type));
            ModelLoader.setCustomModelResourceLocation(overworld_leaves2, type.getMeta(), new ModelResourceLocation(overworld_leaves2.getRegistryName(), variant));
        }

        Item redwood_leaves = Item.getItemFromBlock(NaturaWorld.redwoodLeaves);
        for (BlockRedwoodLeaves.RedwoodType type : BlockRedwoodLeaves.RedwoodType.values())
        {
            String variant = String.format("%s=%s", BlockRedwoodLeaves.TYPE.getName(), BlockRedwoodLeaves.TYPE.getName(type));
            ModelLoader.setCustomModelResourceLocation(redwood_leaves, type.getMeta(), new ModelResourceLocation(redwood_leaves.getRegistryName(), variant));
        }

        Item nether_leaves = Item.getItemFromBlock(NaturaWorld.netherLeaves);
        for (BlockNetherLeaves.LeavesType type : BlockNetherLeaves.LeavesType.values())
        {
            String variant = String.format("%s=%s", BlockNetherLeaves.TYPE.getName(), BlockNetherLeaves.TYPE.getName(type));
            ModelLoader.setCustomModelResourceLocation(nether_leaves, type.getMeta(), new ModelResourceLocation(nether_leaves.getRegistryName(), variant));
        }

        Item nether_leaves2 = Item.getItemFromBlock(NaturaWorld.netherLeaves2);
        for (BlockNetherLeaves2.LeavesType type : BlockNetherLeaves2.LeavesType.values())
        {
            String variant = String.format("%s=%s", BlockNetherLeaves2.TYPE.getName(), BlockNetherLeaves2.TYPE.getName(type));
            ModelLoader.setCustomModelResourceLocation(nether_leaves2, type.getMeta(), new ModelResourceLocation(nether_leaves2.getRegistryName(), variant));
        }

        // saplings

        ItemStack stack = new ItemStack(Item.getItemFromBlock(NaturaWorld.overworldSapling), 1, NaturaWorld.overworldSapling.getMetaFromState(NaturaWorld.overworldSapling.getDefaultState().withProperty(BlockOverworldSapling.FOLIAGE, BlockOverworldSapling.SaplingType.MAPLE)));
        this.registerItemModel(stack, "overworld_sapling_maple");
        stack = new ItemStack(Item.getItemFromBlock(NaturaWorld.overworldSapling), 1, NaturaWorld.overworldSapling.getMetaFromState(NaturaWorld.overworldSapling.getDefaultState().withProperty(BlockOverworldSapling.FOLIAGE, BlockOverworldSapling.SaplingType.SILVERBELL)));
        this.registerItemModel(stack, "overworld_sapling_silverbell");
        stack = new ItemStack(Item.getItemFromBlock(NaturaWorld.overworldSapling), 1, NaturaWorld.overworldSapling.getMetaFromState(NaturaWorld.overworldSapling.getDefaultState().withProperty(BlockOverworldSapling.FOLIAGE, BlockOverworldSapling.SaplingType.AMARANTH)));
        this.registerItemModel(stack, "overworld_sapling_amaranth");
        stack = new ItemStack(Item.getItemFromBlock(NaturaWorld.overworldSapling), 1, NaturaWorld.overworldSapling.getMetaFromState(NaturaWorld.overworldSapling.getDefaultState().withProperty(BlockOverworldSapling.FOLIAGE, BlockOverworldSapling.SaplingType.TIGER)));
        this.registerItemModel(stack, "overworld_sapling_tiger");

        stack = new ItemStack(Item.getItemFromBlock(NaturaWorld.overworldSapling2), 1, NaturaWorld.overworldSapling2.getMetaFromState(NaturaWorld.overworldSapling2.getDefaultState().withProperty(BlockOverworldSapling2.FOLIAGE, BlockOverworldSapling2.SaplingType.WILLOW)));
        this.registerItemModel(stack, "overworld_sapling_willow");
        stack = new ItemStack(Item.getItemFromBlock(NaturaWorld.overworldSapling2), 1, NaturaWorld.overworldSapling2.getMetaFromState(NaturaWorld.overworldSapling2.getDefaultState().withProperty(BlockOverworldSapling2.FOLIAGE, BlockOverworldSapling2.SaplingType.EUCALYPTUS)));
        this.registerItemModel(stack, "overworld_sapling_eucalyptus");
        stack = new ItemStack(Item.getItemFromBlock(NaturaWorld.overworldSapling2), 1, NaturaWorld.overworldSapling2.getMetaFromState(NaturaWorld.overworldSapling2.getDefaultState().withProperty(BlockOverworldSapling2.FOLIAGE, BlockOverworldSapling2.SaplingType.HOPSEED)));
        this.registerItemModel(stack, "overworld_sapling_hopseed");
        stack = new ItemStack(Item.getItemFromBlock(NaturaWorld.overworldSapling2), 1, NaturaWorld.overworldSapling2.getMetaFromState(NaturaWorld.overworldSapling2.getDefaultState().withProperty(BlockOverworldSapling2.FOLIAGE, BlockOverworldSapling2.SaplingType.SAKURA)));
        this.registerItemModel(stack, "overworld_sapling_sakura");

        stack = new ItemStack(Item.getItemFromBlock(NaturaWorld.redwoodSapling), 1, NaturaWorld.redwoodSapling.getMetaFromState(NaturaWorld.redwoodSapling.getDefaultState().withProperty(BlockRedwoodSapling.FOLIAGE, BlockRedwoodSapling.SaplingType.REDWOOD)));
        this.registerItemModel(stack, "overworld_sapling_redwood");

        stack = new ItemStack(Item.getItemFromBlock(NaturaWorld.netherSapling), 1, NaturaWorld.netherSapling.getMetaFromState(NaturaWorld.netherSapling.getDefaultState().withProperty(BlockNetherSapling.FOLIAGE, BlockNetherSapling.SaplingType.GHOSTWOOD)));
        this.registerItemModel(stack, "nether_sapling_ghostwood");
        stack = new ItemStack(Item.getItemFromBlock(NaturaWorld.netherSapling), 1, NaturaWorld.netherSapling.getMetaFromState(NaturaWorld.netherSapling.getDefaultState().withProperty(BlockNetherSapling.FOLIAGE, BlockNetherSapling.SaplingType.BLOODWOOD)));
        this.registerItemModel(stack, "nether_sapling_bloodwood");
        stack = new ItemStack(Item.getItemFromBlock(NaturaWorld.netherSapling), 1, NaturaWorld.netherSapling.getMetaFromState(NaturaWorld.netherSapling.getDefaultState().withProperty(BlockNetherSapling.FOLIAGE, BlockNetherSapling.SaplingType.FUSEWOOD)));
        this.registerItemModel(stack, "nether_sapling_fusewood");
        stack = new ItemStack(Item.getItemFromBlock(NaturaWorld.netherSapling), 1, NaturaWorld.netherSapling.getMetaFromState(NaturaWorld.netherSapling.getDefaultState().withProperty(BlockNetherSapling.FOLIAGE, BlockNetherSapling.SaplingType.DARKWOOD)));
        this.registerItemModel(stack, "nether_sapling_darkwood");

    }
}
