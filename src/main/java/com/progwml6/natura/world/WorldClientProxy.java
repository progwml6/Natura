package com.progwml6.natura.world;

import javax.annotation.Nonnull;

import com.progwml6.natura.common.ClientProxy;
import com.progwml6.natura.world.block.logs.BlockEnumLog.EnumAxis;
import com.progwml6.natura.world.block.logs.BlockOverworldLog;
import com.progwml6.natura.world.block.logs.BlockOverworldLog.LogType;
import com.progwml6.natura.world.client.LeavesColorizer;

import net.minecraft.block.BlockLeaves;
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

public class WorldClientProxy extends ClientProxy
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

        blockColors.registerBlockColorHandler(
                new IBlockColor()
                {
                    @Override
                    public int colorMultiplier(@Nonnull IBlockState state, IBlockAccess access, BlockPos pos, int tintIndex)
                    {
                        LogType type = state.getValue(BlockOverworldLog.TYPE);
                        if (pos == null)
                        {
                            return LeavesColorizer.getColorStatic(type);
                        }

                        return LeavesColorizer.getColorForPos(access, pos, type);
                    }
                },
                NaturaWorld.overworldLeaves);

        minecraft.getItemColors().registerItemColorHandler(
                new IItemColor()
                {
                    @Override
                    public int getColorFromItemstack(@Nonnull ItemStack stack, int tintIndex)
                    {
                        @SuppressWarnings("deprecation")
                        IBlockState iblockstate = ((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata());
                        return blockColors.colorMultiplier(iblockstate, null, null, tintIndex);
                    }
                },
                NaturaWorld.overworldLeaves);

        super.init();
    }

    @Override
    protected void registerModels()
    {
        // blocks
        ModelLoader.setCustomStateMapper(NaturaWorld.overworldLeaves, (new StateMap.Builder()).ignore(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE).build());

        registerItemBlockMeta(NaturaWorld.cloudBlock);
        registerItemBlockMeta(NaturaWorld.redwoodLog);

        // leaves
        //@formatter:off
        Item leaves = Item.getItemFromBlock(NaturaWorld.overworldLeaves);
        for (BlockOverworldLog.LogType type : BlockOverworldLog.LogType.values())
        {
            String variant = String.format("%s=%s",
                    BlockOverworldLog.TYPE.getName(),
                    BlockOverworldLog.TYPE.getName(type));
            ModelLoader.setCustomModelResourceLocation(leaves, type.getMeta(), new ModelResourceLocation(leaves.getRegistryName(), variant));
        }

        Item log = Item.getItemFromBlock(NaturaWorld.overworldLog);
        for (BlockOverworldLog.LogType type : BlockOverworldLog.LogType.values())
        {
            String variant = String.format("%s=%s,%s=%s",
                    BlockOverworldLog.LOG_AXIS.getName(),
                    BlockOverworldLog.LOG_AXIS.getName(EnumAxis.Y),
                    BlockOverworldLog.TYPE.getName(),
                    BlockOverworldLog.TYPE.getName(type));
            ModelLoader.setCustomModelResourceLocation(log, type.meta, new ModelResourceLocation(log.getRegistryName(), variant));
        }
        //@formatter:on
    }
}
