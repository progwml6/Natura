package mods.natura.blocks;

import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NFenceGate extends BlockFenceGate
{
    Block modelBlock;
    int modelMeta;

    public NFenceGate(Block block, int meta)
    {
        super();
        modelBlock = block;
        modelMeta = meta;
        this.setCreativeTab(NaturaTab.tab);
    }
}
