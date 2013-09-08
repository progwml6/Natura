package mods.natura.items.blocks;

import java.util.List;

import mods.natura.common.NContent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BerryBushItem extends ItemBlock
{

    public BerryBushItem(int i)
    {
        super(i);
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
            Block block = Block.blocksList[world.getBlockId(x, y, z)];

            if (block != null && block.canSustainPlant(world, x, y, z, ForgeDirection.UP, (IPlantable) NContent.berryBush) && world.isAirBlock(x, y + 1, z))
            {
                world.setBlock(x, y + 1, z, NContent.berryBush.blockID, stack.getItemDamage() % 4, 3);
                if (!player.capabilities.isCreativeMode)
                    stack.stackSize--;
                if (!world.isRemote)
                    world.playAuxSFX(2001, x, y, z, NContent.berryBush.blockID);
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }

    /* Block name in inventory */
    @Override
    public String getUnlocalizedName (ItemStack itemstack)
    {
        return (new StringBuilder()).append("block.").append(blockType[itemstack.getItemDamage()]).append("berryBush").toString();
    }

    public static final String blockType[] = { "rasp", "blue", "black", "geo", "rasp", "blue", "black", "geo", "rasp", "blue", "black", "geo", "rasp", "blue", "black", "geo" };

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        switch (stack.getItemDamage() % 4)
        {
        case 0:
            list.add("Sweet and red");
            list.add("Found in warm areas");
            break;
        case 1:
            list.add("Tart and blue");
            list.add("Found in temperate areas");
            break;
        case 2:
            list.add("Sweet and black");
            list.add("Found in wet areas");
            break;
        case 3:
            list.add("Tasty and yellow-orange");
            list.add("Found in cold areas");
            break;
        }
    }
}
