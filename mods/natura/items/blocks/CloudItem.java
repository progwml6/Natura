package mods.natura.items.blocks;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CloudItem extends ItemBlock
{
    public static final String blockType[] = { "normal", "dark", "ash", "sulfur" };

    public CloudItem(int i)
    {
        super(i);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata (int md)
    {
        return md;
    }

    public String getUnlocalizedName (ItemStack itemstack)
    {
        return (new StringBuilder()).append(blockType[itemstack.getItemDamage()]).append("cloud").toString();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        list.add("So light and fluffy!");
        switch (stack.getItemDamage() % 4)
        {
        case 0:
            list.add("Saves you when you fall");
            break;
        case 1:
            list.add("Causes lightning strikes");
            break;
        case 2:
            list.add("3x3 Converts into charcoal");
            break;
        case 3:
            list.add("2x2 Converts into sulfur");
            break;
        }
    }
}
