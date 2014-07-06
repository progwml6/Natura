package mods.natura.items.blocks;

import java.util.List;

import mantle.blocks.abstracts.MultiItemBlock;
import mods.natura.common.NContent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BerryBushItem extends MultiItemBlock
{
    public static final String blockType[] = { "rasp", "blue", "black", "geo", "rasp", "blue", "black", "geo", "rasp", "blue", "black", "geo", "rasp", "blue", "black", "geo" };

    public BerryBushItem(Block block)
    {
        super(block, "block", "berryBush", blockType);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata (int meta)
    {
        return meta % 4;
    }

    /* Place bushes on dirt, grass, or other bushes only */
    @Override
    public boolean onItemUse (ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10)
    {
        if (side != 1)
            return false;

        else if (player.canPlayerEdit(x, y, z, side, stack) && player.canPlayerEdit(x, y + 1, z, side, stack))
        {
            Block block = world.getBlock(x, y, z);

            if (block != null && block.canSustainPlant(world, x, y, z, ForgeDirection.UP, NContent.berryBush) && world.isAirBlock(x, y + 1, z))
            {
                world.setBlock(x, y + 1, z, NContent.berryBush, stack.getItemDamage() % 4, 3);
                if (!player.capabilities.isCreativeMode)
                    stack.stackSize--;
                if (!world.isRemote)
                    world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(NContent.berryBush));
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }

    /* Block name in inventory */
    /* @Override
     public String getUnlocalizedName (ItemStack itemstack)
     {
         return (new StringBuilder()).append("block.").append(blockType[itemstack.getItemDamage()]).append("berryBush").toString();
     }*/

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        switch (stack.getItemDamage() % 4)
        {
        case 0:
            list.add(StatCollector.translateToLocal("tooltip.berrybush1"));
            list.add(StatCollector.translateToLocal("tooltip.berrybush2"));
            break;
        case 1:
            list.add(StatCollector.translateToLocal("tooltip.berrybush3"));
            list.add(StatCollector.translateToLocal("tooltip.berrybush4"));
            break;
        case 2:
            list.add(StatCollector.translateToLocal("tooltip.berrybush5"));
            list.add(StatCollector.translateToLocal("tooltip.berrybush6"));
            break;
        case 3:
            list.add(StatCollector.translateToLocal("tooltip.berrybush7"));
            list.add(StatCollector.translateToLocal("tooltip.berrybush8"));
            break;
        }
    }
}
