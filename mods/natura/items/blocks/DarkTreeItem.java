package mods.natura.items.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class DarkTreeItem extends ItemBlock
{
    public static final String blockType[] =
    {
        "darkwood", "fusewood"
    };

    public DarkTreeItem(int i)
    {
        super(i);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int meta)
    {
        return meta;
    }

    public String getUnlocalizedName(ItemStack itemstack)
    {
        return (new StringBuilder()).append("block.log.").append(blockType[itemstack.getItemDamage()]).toString();
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        switch (stack.getItemDamage())
        {
        case 0:
            list.add("Nether Tree");
            break;
        case 1:
            list.add("Nether Tree");
            list.add("Explosive Personality");
            break;
        case 2:
            list.add("Nether Tree");
            list.add("Fire-resistant leaves");
            break;
        }
    }
}
