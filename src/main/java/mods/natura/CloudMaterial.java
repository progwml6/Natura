package mods.natura;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class CloudMaterial extends Material
{
    public CloudMaterial()
    {
        super(MapColor.snowColor);
        //this.setReplaceable();
        this.setNoPushMobility();
    }

    /**
     * Returns if blocks of these materials are liquids.
     */
    @Override
    public boolean isLiquid ()
    {
        return false;
    }

    /**
     * Returns if this material is considered solid or not
     */
    @Override
    public boolean blocksMovement ()
    {
        return false;
    }

    @Override
    public boolean isSolid ()
    {
        return false;
    }

    @Override
    public boolean getCanBlockGrass ()
    {
        return false;
    }
}
