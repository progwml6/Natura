package mods.natura.blocks;

import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;

public class NStairs extends BlockStairs
{
    public NStairs(IBlockState modelState)
    {
        super(modelState);
        this.setCreativeTab(NaturaTab.tab);
        this.setLightOpacity(0);
    }

}
