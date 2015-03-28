package mods.natura.blocks;

import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NPressurePlate extends BlockPressurePlate
{
    Block modelBlock;
    int modelMeta;

    public NPressurePlate(Material material, Sensitivity s, Block block, int meta)
    {
        super(material, s);
        modelBlock = block;
        modelMeta = meta;
        this.setCreativeTab(NaturaTab.tab);
    }
}
