package mods.natura.blocks;

import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

public class NStairs extends BlockStairs
{
    public NStairs(int par1, Block par2Block, int par3)
    {
        super(par1, par2Block, par3);
        this.setCreativeTab(NaturaTab.tab);
        this.setLightOpacity(0);
    }

}
