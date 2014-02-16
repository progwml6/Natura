package mods.natura.blocks.overrides;

import net.minecraft.block.BlockButton;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NetherrackButton extends BlockButton
{
    public NetherrackButton()
    {
        super(false);
    }

    @Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon (int par1, int par2)
    {
        return Blocks.netherrack.getBlockTextureFromSide(1);
    }
}
