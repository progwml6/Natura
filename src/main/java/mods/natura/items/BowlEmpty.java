package mods.natura.items;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BowlEmpty extends Item
{
    public static String[] textureNames = new String[] { "", "", "ghostwood", "", "bloodwood", "", "", "", "", "", "", "darkwood", "fusewood" };

    public BowlEmpty()
    {
        super();
    }

    @Override
    public String getUnlocalizedName (ItemStack stack)
    {
        return Items.bowl.getUnlocalizedName(stack);
        /*int arr = MathHelper.clamp_int(stack.getItemDamage(), 0, textureNames.length);
        return "item.bowl." +textureNames[arr];*/
    }
}
