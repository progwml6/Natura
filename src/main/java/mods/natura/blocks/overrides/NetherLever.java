package mods.natura.blocks.overrides;

import mods.natura.client.LeverRender;
import net.minecraft.block.BlockLever;

public class NetherLever extends BlockLever
{
    public NetherLever()
    {
        super();
    }

    @Override
    public int getRenderType ()
    {
        return LeverRender.model;
    }
}
