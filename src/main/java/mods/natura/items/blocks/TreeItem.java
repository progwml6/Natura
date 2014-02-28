package mods.natura.items.blocks;

import java.util.List;

import mantle.blocks.abstracts.MultiItemBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TreeItem extends MultiItemBlock
{
    public static final String blockType[] = { "eucalyptus", "sakura", "ghost", "hopseed" };

    public TreeItem(Block i)
    {
        super(i, "", "log", blockType);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    /*  @Override
      public String getUnlocalizedName (ItemStack itemstack)
      {
          int i = MathHelper.clamp_int(itemstack.getItemDamage(), 0, 3);
          return (new StringBuilder()).append(blockType[i]).append("Log").toString();
      }*/

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        switch (stack.getItemDamage() % 4)
        {
        case 0:
            list.add(StatCollector.translateToLocal("tooltip.tree1"));
            break;
        case 1:
            list.add(StatCollector.translateToLocal("tooltip.tree2"));
            break;
        case 2:
            list.add(StatCollector.translateToLocal("tooltip.nethertree"));
            list.add(StatCollector.translateToLocal("tooltip.tree3"));
            break;
        case 3:
            list.add(StatCollector.translateToLocal("tooltip.tree6"));
            break;
        }
    }
}
