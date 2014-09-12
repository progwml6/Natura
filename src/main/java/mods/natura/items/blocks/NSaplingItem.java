package mods.natura.items.blocks;

import java.util.List;

import mantle.blocks.abstracts.MultiItemBlock;
import mods.natura.blocks.trees.EnumSaplingType;
import mods.natura.blocks.trees.NSaplingBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class NSaplingItem extends MultiItemBlock
{
    private Block bID;
    protected NSaplingBlock saplingBlock;
    private static final String unlocalizedName = "block.sapling";

    public NSaplingItem(Block block, String[] blockType, NSaplingBlock saplingBlock)
    {
        super(block, unlocalizedName, blockType);
        setMaxDamage(0);
        setHasSubtypes(true);
        this.bID = block;
        this.saplingBlock = saplingBlock;
    }

    @Override
    public IIcon getIconFromDamage(int i) {
        return saplingBlock.getIcon(0, i);
    }

    @Override
    public String getUnlocalizedName (ItemStack itemstack) {
        int meta = itemstack.getItemDamage();
        EnumSaplingType saplingType = saplingBlock.getSaplingType(meta);
        return (new StringBuilder()).append(unlocalizedName).append(".").append(saplingType).toString();
    }

    public abstract void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4);

    @Override
    public boolean onItemUse (ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10)
    {
        Block blockID = world.getBlock(x, y, z);

        if (blockID == Blocks.snow && (world.getBlockMetadata(x, y, z) & 7) < 1)
        {
            side = 1;
        }
        else if (blockID != Blocks.vine && blockID != Blocks.tallgrass && blockID != Blocks.deadbush && (blockID == null || !blockID.isReplaceable(world, x, y, z)))
        {
            if (side == 0)
            {
                --y;
            }

            if (side == 1)
            {
                ++y;
            }

            if (side == 2)
            {
                --z;
            }

            if (side == 3)
            {
                ++z;
            }

            if (side == 4)
            {
                --x;
            }

            if (side == 5)
            {
                ++x;
            }
        }

        if (stack.getItemDamage() == 5)
        {
            Block block = world.getBlock(x, y + 1, z);
            if (block == null || world.isAirBlock(x, y + 1, z))
                return false;
        }
        else
        {
            Block block = world.getBlock(x, y - 1, z);
            if (block == null || world.isAirBlock(x, y - 1, z))
                return false;
        }

        if (stack.stackSize == 0)
        {
            return false;
        }
        else if (!player.canPlayerEdit(x, y, z, side, stack))
        {
            return false;
        }
        else if (y == 255 && this.bID.getMaterial().isSolid())
        {
            return false;
        }
        else if (world.canPlaceEntityOnSide(this.bID, x, y, z, false, side, player, stack))
        {
            Block block = this.bID;
            int j1 = this.getMetadata(stack.getItemDamage());
            int k1 = this.bID.onBlockPlaced(world, x, y, z, side, par8, par9, par10, j1);

            if (placeBlockAt(stack, player, world, x, y, z, side, par8, par9, par10, k1))
            {
                world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, block.stepSound.getBreakSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
                --stack.stackSize;
            }

            return true;
        }
        else
        {
            return false;
        }
    }
}
