package com.progwml6.natura.nether;

import javax.annotation.Nonnull;

import com.progwml6.natura.common.ClientProxy;
import com.progwml6.natura.common.block.BlockEnumBerryBush;
import com.progwml6.natura.common.client.LeavesColorizer;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.nether.block.leaves.BlockNetherLeaves;
import com.progwml6.natura.nether.block.leaves.BlockNetherLeaves2;
import com.progwml6.natura.nether.block.logs.BlockNetherLog;
import com.progwml6.natura.nether.block.saplings.BlockNetherSapling;

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
            public int getColorFromItemstack(@Nonnull ItemStack stack, int tintIndex)
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

        this.registerItemBlockMeta(NaturaNether.netherPlanks);
        this.registerItemBlockMeta(NaturaNether.netherTaintedSoil);

        this.registerItemBlockMeta(NaturaNether.netherGlass);

        this.registerItemBlockMeta(NaturaNether.netherSlab);

        this.registerItemBlockMeta(NaturaNether.respawnObelisk);

        this.registerItemModel(NaturaNether.netherStairsGhostwood);
        this.registerItemModel(NaturaNether.netherStairsBloodwood);
        this.registerItemModel(NaturaNether.netherStairsDarkwood);
        this.registerItemModel(NaturaNether.netherStairsFusewood);

        Item heatSandItem = Item.getItemFromBlock(NaturaNether.netherHeatSand);
        ModelLoader.setCustomModelResourceLocation(heatSandItem, 0, new ModelResourceLocation(Util.resource("nether_heat_sand"), "normal"));

        Item nether_log = Item.getItemFromBlock(NaturaNether.netherLog);
        for (BlockNetherLog.LogType type : BlockNetherLog.LogType.values())
        {
            String variant = String.format("%s=%s,%s=%s", BlockNetherLog.LOG_AXIS.getName(),
                    BlockNetherLog.LOG_AXIS.getName(BlockNetherLog.EnumAxis.Y), BlockNetherLog.TYPE.getName(),
                    BlockNetherLog.TYPE.getName(type));
            ModelLoader.setCustomModelResourceLocation(nether_log, type.meta, new ModelResourceLocation(nether_log.getRegistryName(), variant));
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
        this.registerItemModel(stack, "nether_sapling_ghostwood");
        stack = new ItemStack(Item.getItemFromBlock(NaturaNether.netherSapling), 1, NaturaNether.netherSapling.getMetaFromState(NaturaNether.netherSapling.getDefaultState().withProperty(BlockNetherSapling.FOLIAGE, BlockNetherSapling.SaplingType.BLOODWOOD)));
        this.registerItemModel(stack, "nether_sapling_bloodwood");
        stack = new ItemStack(Item.getItemFromBlock(NaturaNether.netherSapling), 1, NaturaNether.netherSapling.getMetaFromState(NaturaNether.netherSapling.getDefaultState().withProperty(BlockNetherSapling.FOLIAGE, BlockNetherSapling.SaplingType.FUSEWOOD)));
        this.registerItemModel(stack, "nether_sapling_fusewood");
        stack = new ItemStack(Item.getItemFromBlock(NaturaNether.netherSapling), 1, NaturaNether.netherSapling.getMetaFromState(NaturaNether.netherSapling.getDefaultState().withProperty(BlockNetherSapling.FOLIAGE, BlockNetherSapling.SaplingType.DARKWOOD)));
        this.registerItemModel(stack, "nether_sapling_darkwood");

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
    }
}
