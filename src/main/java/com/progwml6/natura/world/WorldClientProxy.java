package com.progwml6.natura.world;

import javax.annotation.Nonnull;

import com.progwml6.natura.common.ClientProxy;
import com.progwml6.natura.world.block.grass.BlockColoredGrass;
import com.progwml6.natura.world.block.grass.BlockColoredGrassSlab;
import com.progwml6.natura.world.block.logs.BlockOverworldLog;
import com.progwml6.natura.world.block.logs.BlockOverworldLog2;
import com.progwml6.natura.world.block.saplings.BlockOverworldSapling;
import com.progwml6.natura.world.block.saplings.BlockOverworldSapling2;
import com.progwml6.natura.world.client.GrassColorizer;
import com.progwml6.natura.world.client.LeavesColorizer;

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
                    return LeavesColorizer.getOverworldColorStatic(type);
                }

                return LeavesColorizer.getOverworldColorForPos(access, pos, type);
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
                    return LeavesColorizer.getOverworld2ColorStatic(type);
                }

                return LeavesColorizer.getOverworld2ColorForPos(access, pos, type);
            }
        }, NaturaWorld.overworldLeaves2);

        minecraft.getItemColors().registerItemColorHandler(new IItemColor()
        {
            @Override
            public int getColorFromItemstack(@Nonnull ItemStack stack, int tintIndex)
            {
                @SuppressWarnings("deprecation")
                IBlockState iblockstate = ((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata());
                return blockColors.colorMultiplier(iblockstate, null, null, tintIndex);
            }
        }, NaturaWorld.overworldLeaves, NaturaWorld.overworldLeaves2, NaturaWorld.coloredGrass, NaturaWorld.coloredGrassSlab);

        super.init();
    }

    @Override
    protected void registerModels()
    {
        // blocks
        ModelLoader.setCustomStateMapper(NaturaWorld.overworldLeaves, (new StateMap.Builder()).ignore(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE).build());
        ModelLoader.setCustomStateMapper(NaturaWorld.overworldLeaves2, (new StateMap.Builder()).ignore(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE).build());
        ModelLoader.setCustomStateMapper(NaturaWorld.overworldSapling, (new StateMap.Builder()).ignore(BlockOverworldSapling.STAGE, BlockSapling.TYPE).build());
        ModelLoader.setCustomStateMapper(NaturaWorld.overworldSapling2, (new StateMap.Builder()).ignore(BlockOverworldSapling2.STAGE, BlockSapling.TYPE).build());
        /**ModelLoader.setCustomStateMapper(NaturaWorld.redwoodSapling, (new StateMap.Builder()).ignore(BlockRedwoodSapling.STAGE, BlockSapling.TYPE).build());
         * TODO: FIX REDWOOD
         */

        this.registerItemBlockMeta(NaturaWorld.cloudBlock);
        this.registerItemBlockMeta(NaturaWorld.redwoodLog);
        this.registerItemBlockMeta(NaturaWorld.overworldPlanks);
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

        /**stack = new ItemStack(Item.getItemFromBlock(NaturaWorld.redwoodSapling), 1, NaturaWorld.redwoodSapling.getMetaFromState(NaturaWorld.redwoodSapling.getDefaultState().withProperty(BlockRedwoodSapling.FOLIAGE, BlockRedwoodSapling.SaplingType.REDWOOD)));
        this.registerItemModel(stack, "overworld_sapling_redwood");
        TODO: FIX REDWOOD
        */
    }
}
