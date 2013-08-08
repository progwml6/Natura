package mods.natura.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

public class NFenceGate extends BlockFenceGate
{
    Block modelBlock;
    int modelMeta;

    public NFenceGate(int id, Block block, int meta)
    {
        super(id);
        modelBlock = block;
        modelMeta = meta;
        this.setCreativeTab(NaturaTab.tab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons (IconRegister iconRegister)
    {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon (int side, int meta)
    {
        return modelBlock.getIcon(side, modelMeta);
    }
}
