package mods.natura.items.tools;

import mods.natura.common.NaturaTab;
import net.minecraft.item.ItemSpade;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NaturaShovel extends ItemSpade
{
    String texture;

    public NaturaShovel(ToolMaterial toolmaterial, String texture)
    {
        super(toolmaterial);
        this.texture = texture;
        this.setCreativeTab(NaturaTab.tab);
    }
}
