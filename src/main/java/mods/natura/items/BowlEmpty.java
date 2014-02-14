package mods.natura.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BowlEmpty extends Item
{
    IIcon[] icons;
    public static String[] textureNames = new String[] { "", "", "ghostwood", "", "bloodwood", "", "", "", "", "", "", "darkwood", "fusewood" };

    public BowlEmpty()
    {
        super();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconFromDamage (int meta)
    {
        return icons[meta];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons (IIconRegister iconRegister)
    {
        this.icons = new IIcon[textureNames.length];
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
        return Items.bowl.getUnlocalizedName(stack);
        /*int arr = MathHelper.clamp_int(stack.getItemDamage(), 0, textureNames.length);
        return "item.bowl." +textureNames[arr];*/
    }
}
