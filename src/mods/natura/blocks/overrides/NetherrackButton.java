package mods.natura.blocks.overrides;

import net.minecraft.block.Block;
import net.minecraft.block.BlockButton;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NetherrackButton extends BlockButton
{
    public NetherrackButton(int par1)
    {
        super(par1, false);
    }

    @SideOnly(Side.CLIENT)
    public Icon getIcon (int par1, int par2)
    {
        return Block.netherrack.getBlockTextureFromSide(1);
    }
}
