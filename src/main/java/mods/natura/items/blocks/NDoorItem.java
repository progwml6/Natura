package mods.natura.items.blocks;

import java.util.List;

import mods.natura.common.NContent;
import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.StatCollector;

public class NDoorItem extends Item
{
    public IIcon[] icons;
    public String[] textureNames = new String[] { "redwood", "eucalyptus", "hopseed", "sakura", "ghostwood", "bloodwood", "redwoodbark" };

    public NDoorItem()
    {
        super();
        maxStackSize = 64;
        setCreativeTab(NaturaTab.tab);
        setHasSubtypes(true);
    }

    public static final String unlocalizedNames[] = { "redwood", "eucalyptus", "hopseed", "sakura", "ghost", "blood", "redwoodBark" };

    public String getUnlocalizedName (ItemStack itemstack)
    {
        return (new StringBuilder()).append(unlocalizedNames[itemstack.getItemDamage()]).append("NDoor").toString();
    }

    @Override
    public boolean onItemUse (ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float clickX, float clickY, float clickZ)
    {
        if (side != 1)
        {
            return false;
        }
        y++;

        Block block;
        switch (itemstack.getItemDamage())
        {
        case 0:
            block = NContent.redwoodDoor;
            break;
        case 1:
            block = NContent.eucalyptusDoor;
            break;
        case 2:
            block = NContent.hopseedDoor;
            break;
        case 3:
            block = NContent.sakuraDoor;
            break;
        case 4:
            block = NContent.ghostDoor;
            break;
        case 5:
            block = NContent.bloodDoor;
            break;
        case 6:
            block = NContent.redwoodBarkDoor;
            break;
        default:
            block = Blocks.wooden_door;
            break;
        }
        if (!player.canPlayerEdit(x, y, z, side, itemstack) || !player.canPlayerEdit(x, y + 1, z, side, itemstack))
        {
            return false;
        }
        if (!block.canPlaceBlockAt(world, x, y, z))
        {
            return false;
        }
        else
        {
            int rotate = MathHelper.floor_double((double) (((player.rotationYaw + 180F) * 4F) / 360F) - 0.5D) & 3;
            placeDoorBlock(world, x, y, z, rotate, block);
            itemstack.stackSize--;
            return true;
        }
    }

    public static void placeDoorBlock (World world, int x, int y, int z, int rotate, Block block)
    {
        byte var6 = 0;
        byte var7 = 0;

        if (rotate == 0)
        {
            var7 = 1;
        }

        if (rotate == 1)
        {
            var6 = -1;
        }

        if (rotate == 2)
        {
            var7 = -1;
        }

        if (rotate == 3)
        {
            var6 = 1;
        }

        int var8 = (world.isBlockNormalCube(x - var6, y, z - var7) ? 1 : 0) + (world.isBlockNormalCube(x - var6, y + 1, z - var7) ? 1 : 0);
        int var9 = (world.isBlockNormalCube(x + var6, y, z + var7) ? 1 : 0) + (world.isBlockNormalCube(x + var6, y + 1, z + var7) ? 1 : 0);
        boolean var10 = world.getBlock(x - var6, y, z - var7) == block || world.getBlock(x - var6, y + 1, z - var7) == block;
        boolean var11 = world.getBlock(x + var6, y, z + var7) == block || world.getBlock(x + var6, y + 1, z + var7) == block;
        boolean var12 = false;

        if (var10 && !var11)
        {
            var12 = true;
        }
        else if (var9 > var8)
        {
            var12 = true;
        }

        world.setBlock(x, y, z, block, rotate, 2);
        world.setBlock(x, y + 1, z, block, 8 | (var12 ? 1 : 0), 2);
        world.notifyBlocksOfNeighborChange(x, y, z, block);
        world.notifyBlocksOfNeighborChange(x, y + 1, z, block);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIconFromDamage (int meta)
    {
        return icons[meta];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons (IIconRegister iconRegister)
    {
        this.icons = new IIcon[textureNames.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = iconRegister.registerIcon("natura:" + textureNames[i] + "_door_item");
        }
    }

    public void getSubItems (Item id, CreativeTabs tab, List list)
    {
        for (int i = 0; i < unlocalizedNames.length; i++)
            list.add(new ItemStack(id, 1, i));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        switch (stack.getItemDamage())
        {
        case 0:
            list.add(StatCollector.translateToLocal("tooltip.tree4"));
            break;
        case 1:
            list.add(StatCollector.translateToLocal("tooltip.tree1"));
            break;
        case 2:
            list.add(StatCollector.translateToLocal("tooltip.tree6"));
            break;
        case 3:
            list.add(StatCollector.translateToLocal("tooltip.tree2"));
            break;
        case 4:
            list.add(StatCollector.translateToLocal("tooltip.tree3"));
            break;
        case 5:
            list.add(StatCollector.translateToLocal("tooltip.firedoor"));
            break;
        case 6:
            list.add(StatCollector.translateToLocal("tooltip.barkdoor"));
            break;
        }
    }
}
