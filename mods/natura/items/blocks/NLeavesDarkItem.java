package mods.natura.items.blocks;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NLeavesDarkItem extends ItemBlock
{
    public static final String blockType[] = { "darkwood", "darkwood.flowering", "darkwood.fruit", "fusewood", "", "", "", "", "", "", "", "", "", "", "", "", "" };

    public NLeavesDarkItem(int i)
    {
        super(i);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata (int md)
    {
        return md | 4;
    }

    @Override
    public String getUnlocalizedName (ItemStack itemstack)
    {
        return (new StringBuilder()).append("block.leaves.").append(blockType[itemstack.getItemDamage()]).toString();
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
            list.add("Flowers!");
            break;
        case 2:
            list.add("Nether Tree");
            list.add("Contains an apple");
            break;
        case 3:
            list.add("Nether Tree");
            list.add("Explosive Personality");
            break;
        }
    }
}
