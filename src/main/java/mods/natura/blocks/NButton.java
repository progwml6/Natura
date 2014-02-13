package mods.natura.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockButton;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

public class NButton extends BlockButton
{
    Block modelBlock;
    int modelMeta;

    public NButton(int id, Block block, int meta)
    {
        super(id, true);
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
