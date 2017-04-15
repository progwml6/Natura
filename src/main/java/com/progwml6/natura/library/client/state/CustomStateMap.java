package com.progwml6.natura.library.client.state;

import java.util.LinkedHashMap;

import javax.annotation.Nonnull;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CustomStateMap extends StateMapperBase
{
    private final String customName;

    public CustomStateMap(String customName)
    {
        this.customName = customName;
    }

    @Nonnull
    @Override
    protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state)
    {
        LinkedHashMap<IProperty<?>, Comparable<?>> linkedhashmap = Maps.newLinkedHashMap(state.getProperties());
        ResourceLocation res = new ResourceLocation(Block.REGISTRY.getNameForObject(state.getBlock()).getResourceDomain(), this.customName);

        return new ModelResourceLocation(res, this.getPropertyString(linkedhashmap));
    }
}
