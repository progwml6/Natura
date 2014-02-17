package mods.natura.blocks.overrides;

import mods.natura.client.LeverRender;
import mods.natura.common.NaturaTab;
import net.minecraft.block.BlockLever;

public class NetherLever extends BlockLever
{
    public NetherLever()
    {
        super();
        setCreativeTab(NaturaTab.tabNether);
    }
    
    @Override
	public int getRenderType ()
    {
        return LeverRender.model;
    }
}
