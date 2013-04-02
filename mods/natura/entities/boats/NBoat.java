package mods.natura.entities.boats;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class NBoat extends Item
{
    public NBoat(int i)
    {
        super(i);
        //maxStackSize = 1;
    }
    
    public int getMetadata(int md)
    {
        return md;
    }
    
    public String getItemNameIS(ItemStack itemstack)
    {
        return (new StringBuilder()).append(itemType[itemstack.getItemDamage()]).append("Boat").toString();
    }
    
    public static final String itemType[] =
    {
        "redwood", "blood", "white", "eucalyptus"
    };
    
    /*public int getIconFromDamage(int md)
    {
        return iconIndex + md;
    }*/
}
