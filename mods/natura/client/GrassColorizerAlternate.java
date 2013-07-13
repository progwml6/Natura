package mods.natura.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GrassColorizerAlternate
{
    /** Color buffer for grass */
    private static int[] blueGrassBuffer = new int[65536];
    private static int[] orangeGrassBuffer = new int[65536];

    public static void setBlueGrassBiomeColorizer(int[] par0ArrayOfInteger)
    {
        blueGrassBuffer = par0ArrayOfInteger;
    }

    public static int getBlueGrassColor(double temperature, double humidity)
    {
        humidity *= temperature;
        int i = (int)((1.0D - temperature) * 255.0D);
        int j = (int)((1.0D - humidity) * 255.0D);
        return blueGrassBuffer[j << 8 | i];
    }
    
    public static void setOrangeGrassBiomeColorizer(int[] par0ArrayOfInteger)
    {
        orangeGrassBuffer = par0ArrayOfInteger;
    }

    public static int getOrangeGrassColor(double temperature, double humidity)
    {
        humidity *= temperature;
        int i = (int)((1.0D - temperature) * 255.0D);
        int j = (int)((1.0D - humidity) * 255.0D);
        return orangeGrassBuffer[j << 8 | i];
    }
}
