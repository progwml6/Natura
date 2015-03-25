package mods.natura.items.tools;

import mods.natura.common.NaturaTab;
import net.minecraft.item.ItemAxe;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NaturaHatchet extends ItemAxe
{
    String texture;

    public NaturaHatchet(ToolMaterial toolmaterial, String texture)
    {
        super(toolmaterial);
        this.texture = texture;
        this.setCreativeTab(NaturaTab.tab);
    }
}
