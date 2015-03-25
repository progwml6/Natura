package mods.natura.client;

import mods.natura.Natura;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GrassColorizerAlternate
{
    /** Color buffer for grass */
    private static int[] blueGrassBuffer = new int[65536];
    private static int[] orangeGrassBuffer = new int[65536];

    public static void setBlueGrassBiomeColorizer (int[] par0ArrayOfInteger)
    {
        if (par0ArrayOfInteger.length == 65536)
        {
            blueGrassBuffer = par0ArrayOfInteger;
        }
        else
        {
            Natura.logger.warn("Invalid texture for blue grass biome colors: expected 256x256 (65536) pixels, got {}!", par0ArrayOfInteger.length);
        }
    }

    public static int getBlueGrassColor (double temperature, double humidity)
    {
        humidity *= temperature;
        int i = (int) ((1.0D - temperature) * 255.0D);
        int j = (int) ((1.0D - humidity) * 255.0D);
        return blueGrassBuffer[j << 8 | i];
    }

    public static void setOrangeGrassBiomeColorizer (int[] par0ArrayOfInteger)
    {
        if (par0ArrayOfInteger.length == 65536)
        {
            orangeGrassBuffer = par0ArrayOfInteger;
        }
        else
        {
            Natura.logger.warn("Invalid texture for orange grass biome colors: expected 256x256 (65536) pixels, got {}!", par0ArrayOfInteger.length);
        }
    }

    public static int getOrangeGrassColor (double temperature, double humidity)
    {
        humidity *= temperature;
        int i = (int) ((1.0D - temperature) * 255.0D);
        int j = (int) ((1.0D - humidity) * 255.0D);
        return orangeGrassBuffer[j << 8 | i];
    }
}
