package mods.natura.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BowlEmpty extends Item
{
    Icon[] icons;
    public static String[] textureNames = new String[] { "", "", "ghostwood", "", "bloodwood", "", "", "", "", "", "", "darkwood", "fusewood" };

    public BowlEmpty(int id)
    {
        super(id);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIconFromDamage (int meta)
    {
        return icons[meta];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons (IconRegister iconRegister)
    {
        this.icons = new Icon[textureNames.length];
        for (int i = 0; i < this.textureNames.length; ++i)
        {
            if (!textureNames[i].equals(""))
                this.icons[i] = iconRegister.registerIcon("natura:" + textureNames[i] + "_bowl");
            else
                this.icons[i] = iconRegister.registerIcon("bowl");

        }
    }

    public String getUnlocalizedName (ItemStack stack)
    {
        return Item.bowlEmpty.getUnlocalizedName(stack);
        /*int arr = MathHelper.clamp_int(stack.getItemDamage(), 0, textureNames.length);
        return "item.bowl." +textureNames[arr];*/
    }
}
