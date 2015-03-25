package mods.natura.items.tools;

import mods.natura.common.NaturaTab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NaturaBow extends ItemBow
{
    public static final String[] drawNames = new String[] { "_bow_drawn_0", "_bow_drawn_1", "_bow_drawn_2" };
    String woodType;

    public NaturaBow(int damage, String type)
    {
        super();
        this.setMaxDamage(damage);
        this.woodType = type;
        this.setCreativeTab(NaturaTab.tab);
        this.setFull3D();
    }
}
