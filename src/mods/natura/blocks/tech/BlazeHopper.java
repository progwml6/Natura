package mods.natura.blocks.tech;

import mods.natura.client.HopperRender;
import mods.natura.common.NContent;
import net.minecraft.block.BlockHopper;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlazeHopper extends BlockHopper
{
    @SideOnly(Side.CLIENT)
    private Icon hopperIcon;
    @SideOnly(Side.CLIENT)
    private Icon hopperTopIcon;
    @SideOnly(Side.CLIENT)
    private Icon hopperInsideIcon;
    public BlazeHopper(int par1)
    {
        super(par1);
    }
    
    public int getRenderType ()
    {
        return HopperRender.model;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon (int par1, int par2)
    {
        return par1 == 1 ? this.hopperTopIcon : this.hopperIcon;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons (IconRegister par1IconRegister)
    {
        this.hopperIcon = par1IconRegister.registerIcon("natura:nhopper_outside");
        this.hopperTopIcon = par1IconRegister.registerIcon("natura:nhopper_top");
        this.hopperInsideIcon = par1IconRegister.registerIcon("natura:nhopper_inside");
    }
    
    @SideOnly(Side.CLIENT)
    public static Icon hopperIcon (String par0Str)
    {
        return par0Str.equals("hopper_outside") ? NContent.netherHopper.hopperIcon : (par0Str.equals("hopper_inside") ? NContent.netherHopper.hopperInsideIcon : null);
    }
    
    @SideOnly(Side.CLIENT)
    public String getItemIconName ()
    {
        return "natura:nhopper";
    }
}
