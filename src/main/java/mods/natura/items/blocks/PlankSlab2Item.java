package mods.natura.items.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.natura.common.NContent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.util.StatCollector;

public class PlankSlab2Item extends ItemBlock
{
    public static final String blockType[] = { "purpleheart", "tiger", "willow", "darkwood", "fusewood", "", "", "" };
    int blockID;
    Block block;

    public PlankSlab2Item(int id)
    {
        super(id);
        this.blockID = id + 256;
        this.block = Block.blocksList[id + 256];
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata (int md)
    {
        return md;
    }

    @Override
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
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        switch (stack.getItemDamage())
        {
        case 0:
            list.add(StatCollector.translateToLocal("tooltip.tree9"));
            break;
        case 1:
            list.add(StatCollector.translateToLocal("tooltip.tree10"));
            break;
        case 2:
            list.add(StatCollector.translateToLocal("tooltip.tree11"));
            break;
        case 3:
            list.add(StatCollector.translateToLocal("tooltip.nethertree"));
            break;
        case 4:
            list.add(StatCollector.translateToLocal("tooltip.nethertree"));
            list.add(StatCollector.translateToLocal("tooltip.fusewood.log"));
            break;
        }
    }

    @Override
    public boolean onItemUse (ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        int id = world.getBlockId(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);
        int trueMeta = meta % 8;
        boolean flag = (id & 8) != 0;

        if ((side == 1 && !flag || side == 0 && flag) && id == this.blockID && trueMeta == stack.getItemDamage())
        {
            if (world.setBlock(x, y, z, NContent.planks.blockID, trueMeta + 8, 3))
            {
                world.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), this.block.stepSound.getPlaceSound(),
                        (this.block.stepSound.getVolume() + 1.0F) / 2.0F, this.block.stepSound.getPitch() * 0.8F);
                --stack.stackSize;
                return true;
            }
        }
        return super.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
    }
}
