package mods.natura.blocks.trees;

import java.util.Random;

import mods.natura.common.NaturaTab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;

public class FruitTreeBlock extends BlockLog
{
    public FruitTreeBlock()
    {
        super();
        this.setHardness(1.5F);
        this.setResistance(5F);
        this.setStepSound(Block.soundTypeWood);
        // TODO 1.7 Where the heck did this go? setBurnProperties(this, 5, 20);
        this.setCreativeTab(NaturaTab.tab);
    }

    /*public int getIcon(int side, int metadata)
    {
    	int tex = blockIndexInTexture + (metadata % 4);
    	int orientation = metadata / 4;
    	
    	switch (orientation) //Ends of logs
    	{
    	case 0:
    		if (side == 0 || side == 1)
    			return tex + 16;
    		break;
    	case 1:
    		if (side == 4 || side == 5)
    			return tex + 16;
    		break;
    	case 2:
    		if (side == 2 || side == 3)
    			return tex + 16;
    		break;
    	}
    	
    	return tex;
    }*/

    public int idDropped (int par1, Random par2Random, int par3)
    {
        return Block.getIdFromBlock(this);
    }

    @Override
    public int damageDropped (int meta)
    {
        return meta % 4;
    }

    /*public int getFlammability(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face)
    {
        return metadata % 4 != 2 ? blockFlammability[blockID] : 0;
    }

    public int getFireSpreadSpeed(World world, int x, int y, int z, int metadata, ForgeDirection face)
    {
        return metadata % 4 != 2 ? blockFireSpreadSpeed[blockID] : 0;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
    	for (int i = 0; i < 4; i++)
        par3List.add(new ItemStack(par1, 1, i));
    }*/
}
