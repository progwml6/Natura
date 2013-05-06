package mods.natura.items.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.natura.common.NContent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class GlowshroomItem extends ItemBlock
{
    public static final String blockType[] =
    {
        "green", "purple"
    };

    public GlowshroomItem(int i)
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
    
    public Icon getIconFromDamage (int i)
    {
        return NContent.glowshroom.getIcon(0, i);
    }

    public String getUnlocalizedName(ItemStack itemstack)
    {
        return (new StringBuilder()).append("block.glowshroom.").append(blockType[itemstack.getItemDamage()]).toString();
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		list.add("Neon juice");
	}
}
