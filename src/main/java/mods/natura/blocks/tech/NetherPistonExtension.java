package mods.natura.blocks.tech;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;

public class NetherPistonExtension extends BlockPistonExtension
{
    @SideOnly(Side.CLIENT)
    private IIcon head;
    
    public NetherPistonExtension()
    {
        super();
    }

    @SideOnly(Side.CLIENT)
    public void setHeadTexture (IIcon par1Icon)
    {
        this.head = par1Icon;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon (int par1, int par2)
    {
        int k = getDirectionMeta(par2);
        return par1 == k ? (this.head != null ? this.head : ((par2 & 8) != 0 ? NetherPistonBase.getBaseIcon("piston_top_sticky") : NetherPistonBase.getBaseIcon("piston_top_normal")))
                : (k < 6 && par1 == Facing.oppositeSide[k] ? NetherPistonBase.getBaseIcon("piston_top_normal") : NetherPistonBase.getBaseIcon("piston_side"));
    }
}
