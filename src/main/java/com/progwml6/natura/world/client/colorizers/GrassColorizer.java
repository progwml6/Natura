package com.progwml6.natura.world.client.colorizers;

import java.io.IOException;

import javax.annotation.Nonnull;

import com.progwml6.natura.Natura;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.world.block.grass.BlockColoredGrass;

import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.Biome;

public class GrassColorizer implements IResourceManagerReloadListener
{
    private static int[] colorBufferBlue = new int[65536];

    private static int[] colorBufferPurple = new int[65536];

    private static final ResourceLocation LOC_GRASS_BLUE_PNG = Util.getResource("textures/colormap/bluegrasscolor.png");

    private static final ResourceLocation LOC_GRASS_ORANGE_PNG = Util.getResource("textures/colormap/orangegrasscolor.png");

    public static int getBlueGrassColor(double temperature, double humidity)
    {
        humidity *= temperature;
        int i = (int) ((1.0D - temperature) * 255.0D);
        int j = (int) ((1.0D - humidity) * 255.0D);
        return colorBufferBlue[j << 8 | i];
    }

    public static int getOrangeGrassColor(double temperature, double humidity)
    {
        humidity *= temperature;
        int i = (int) ((1.0D - temperature) * 255.0D);
        int j = (int) ((1.0D - humidity) * 255.0D);
        return colorBufferPurple[j << 8 | i];
    }

    public static int getGrassColorForPos(IBlockAccess blockAccess, BlockPos pos, BlockColoredGrass.GrassType type)
    {
        int i = 0;
        int j = 0;
        int k = 0;

        for (BlockPos.MutableBlockPos mutableblockpos : BlockPos.getAllInBoxMutable(pos.add(-1, 0, -1), pos.add(1, 0, 1)))
        {
            Biome biome = blockAccess.getBiome(mutableblockpos);

            double temp;
            double rainfall;
            int grassColor;

            switch (type)
            {
            case TOPIARY:
                grassColor = biome.getGrassColorAtPos(mutableblockpos);
                break;
            case BLUEGRASS:
                temp = MathHelper.clamp_float(biome.getFloatTemperature(pos), 0.0F, 1.0F);
                rainfall = MathHelper.clamp_float(biome.getRainfall(), 0.0F, 1.0F);
                grassColor = GrassColorizer.getBlueGrassColor(temp, rainfall);
                break;
            case AUTUMNAL:
                temp = MathHelper.clamp_float(biome.getFloatTemperature(pos), 0.0F, 1.0F);
                rainfall = MathHelper.clamp_float(biome.getRainfall(), 0.0F, 1.0F);
                grassColor = GrassColorizer.getOrangeGrassColor(temp, rainfall);
                break;
            default:
                grassColor = biome.getGrassColorAtPos(mutableblockpos);
                break;
            }

            i += (grassColor & 16711680) >> 16;
            j += (grassColor & 65280) >> 8;
            k += grassColor & 255;
        }

        return (i / 9 & 255) << 16 | (j / 9 & 255) << 8 | k / 9 & 255;
    }

    public static int getGrassColorStatic(BlockColoredGrass.GrassType type)
    {
        switch (type)
        {
        case TOPIARY:
            return ColorizerGrass.getGrassColor(0.5D, 1.0D);
        case BLUEGRASS:
            return GrassColorizer.getBlueGrassColor(0.5D, 0.5D);
        case AUTUMNAL:
            return GrassColorizer.getOrangeGrassColor(1.0D, 1.0D);
        default:
            return ColorizerGrass.getGrassColor(0.5D, 1.0D);
        }
    }

    @Override
    public void onResourceManagerReload(@Nonnull IResourceManager resourceManager)
    {
        try
        {
            colorBufferBlue = TextureUtil.readImageData(resourceManager, LOC_GRASS_BLUE_PNG);
            colorBufferPurple = TextureUtil.readImageData(resourceManager, LOC_GRASS_ORANGE_PNG);
        }
        catch (IOException e)
        {
            Natura.log.error(e);
        }
    }
}
