package mods.natura.items.blocks;

import java.util.List;

import mantle.blocks.abstracts.MultiItemBlock;
import mods.natura.common.NContent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PlankSlab1Item extends MultiItemBlock
{
    public static final String blockType[] = { "eucalyptus", "sakura", "ghost", "redwood", "blood", "bush", "maple", "silverbell" };
    Block block;

    public PlankSlab1Item(Block id)
    {
        super(id, "block.wood", "slab", blockType);
        this.block = id;
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    /*@Override
    public String getUnlocalizedName (ItemStack itemstack)
    {
        int damage = itemstack.getItemDamage();
        if (damage >= blockType.length)
        {
            if (blockType.length == 0)
            {
                return "";
            }
            damage %= blockType.length;
        }

        return (new StringBuilder()).append("block.wood.").append(blockType[damage]).append(".slab").toString();
    }*/

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        switch (stack.getItemDamage())
        {
        case 0:
            list.add(StatCollector.translateToLocal("tooltip.tree1"));
            break;
        case 1:
            list.add(StatCollector.translateToLocal("tooltip.tree2"));
            break;
        case 2:
            list.add(StatCollector.translateToLocal("tooltip.tree3"));
            break;
        case 3:
            list.add(StatCollector.translateToLocal("tooltip.tree4"));
            break;
        case 4:
            list.add(StatCollector.translateToLocal("tooltip.tree5"));
            break;
        case 5:
            list.add(StatCollector.translateToLocal("tooltip.tree6"));
            break;
        case 6:
            list.add(StatCollector.translateToLocal("tooltip.tree7"));
            break;
        case 7:
            list.add(StatCollector.translateToLocal("tooltip.tree8"));
            break;
        }
    }

    @Override
    public boolean onItemUse (ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        Block id = world.getBlock(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);
        int trueMeta = meta % 8;
        boolean flag = id != null;

        if ((side == 1 && !flag || side == 0 && flag) && id == this.block && trueMeta == stack.getItemDamage())
        {
            if (world.setBlock(x, y, z, NContent.planks, trueMeta, 3))
            {
                world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, this.block.stepSound.getBreakSound(), (this.block.stepSound.getVolume() + 1.0F) / 2.0F, this.block.stepSound.getPitch() * 0.8F);
                --stack.stackSize;
                return true;
            }
        }
        return super.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
    }
}
