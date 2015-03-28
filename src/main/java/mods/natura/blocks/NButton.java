package mods.natura.blocks;

import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockButton;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NButton extends BlockButton
{
    Block modelBlock;
    int modelMeta;

    public NButton(Block block, int meta)
    {
        super(true);
        modelBlock = block;
        modelMeta = meta;
        this.setCreativeTab(NaturaTab.tab);
    }
}
