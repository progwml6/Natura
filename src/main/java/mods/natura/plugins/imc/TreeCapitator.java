package mods.natura.plugins.imc;

import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import mods.natura.common.NContent;
import mods.natura.plugins.ICompatPlugin;

public class TreeCapitator implements ICompatPlugin
{

    @Override
    public String getModId ()
    {
        return "TreeCapitator";
    }

    @Override
    public void preInit ()
    {
    }

    @Override
    public void init ()
    {
        NBTTagCompound tpModCfg = new NBTTagCompound();
        tpModCfg.setString("modID", "Natura");
        tpModCfg.setString("axeIDList", String.format("%d; %d; %d; %d; %d", getUniqueName(NContent.ghostwoodAxe), getUniqueName(NContent.bloodwoodAxe), getUniqueName(NContent.darkwoodAxe), getUniqueName(NContent.fusewoodAxe), getUniqueName(NContent.netherquartzAxe)));
        tpModCfg.setBoolean("useShiftedItemID", false);

        NBTTagList treeList = new NBTTagList();

        // amaranth
        NBTTagCompound treeDef = new NBTTagCompound();
        treeDef.setString("treeName", "amaranth");
        treeDef.setString("logs", String.format("%d,2; %d,6; %d,10", getUniqueName(NContent.rareTree), getUniqueName(NContent.rareTree), getUniqueName(NContent.rareTree)));
        treeDef.setString("leaves", String.format("%d,2; %d,10", getUniqueName(NContent.rareLeaves), getUniqueName(NContent.rareLeaves)));
        treeList.appendTag(treeDef);
        // bloodwood
        treeDef = new NBTTagCompound();
        treeDef.setString("treeName", "bloodwood");
        treeDef.setString("logs", String.format("%d", getUniqueName(NContent.bloodwood)));
        treeDef.setString("leaves", String.format("%d,2; %d,10", getUniqueName(NContent.floraLeavesNoColor), getUniqueName(NContent.floraLeavesNoColor)));
        treeList.appendTag(treeDef);
        // darkwood
        treeDef = new NBTTagCompound();
        treeDef.setString("treeName", "darkwood");
        treeDef.setString("logs", String.format("%d,0; %d,4; %d,8", getUniqueName(NContent.darkTree), getUniqueName(NContent.darkTree), getUniqueName(NContent.darkTree)));
        treeDef.setString("leaves", String.format("%d", getUniqueName(NContent.darkLeaves)));
        treeList.appendTag(treeDef);
        // eucalyptus
        treeDef = new NBTTagCompound();
        treeDef.setString("treeName", "eucalyptus");
        treeDef.setString("logs", String.format("%d,0; %d,4; %d,8", getUniqueName(NContent.tree), getUniqueName(NContent.tree), getUniqueName(NContent.tree)));
        treeDef.setString("leaves", String.format("%d,1; %d,9", getUniqueName(NContent.floraLeaves), getUniqueName(NContent.floraLeaves)));
        treeList.appendTag(treeDef);
        // ghostwood
        treeDef = new NBTTagCompound();
        treeDef.setString("treeName", "ghostwood");
        treeDef.setString("logs", String.format("%d,2; %d,6; %d,10", getUniqueName(NContent.tree), getUniqueName(NContent.tree), getUniqueName(NContent.tree)));
        treeDef.setString("leaves", String.format("%d,1; %d,9", getUniqueName(NContent.floraLeavesNoColor), getUniqueName(NContent.floraLeavesNoColor)));
        treeList.appendTag(treeDef);
        // hopseed
        treeDef = new NBTTagCompound();
        treeDef.setString("treeName", "hopseed");
        treeDef.setString("logs", String.format("%d,3; %d,7; %d,11", getUniqueName(NContent.tree), getUniqueName(NContent.tree), getUniqueName(NContent.tree)));
        treeDef.setString("leaves", String.format("%d,2; %d,10", getUniqueName(NContent.floraLeaves), getUniqueName(NContent.floraLeaves)));
        treeList.appendTag(treeDef);
        // maple
        treeDef = new NBTTagCompound();
        treeDef.setString("treeName", "maple");
        treeDef.setString("logs", String.format("%d,0; %d,4; %d,8", getUniqueName(NContent.rareTree), getUniqueName(NContent.rareTree), getUniqueName(NContent.rareTree)));
        treeDef.setString("leaves", String.format("%d,0; %d,8", getUniqueName(NContent.rareLeaves), getUniqueName(NContent.rareLeaves)));
        treeList.appendTag(treeDef);
        // redwood
        treeDef = new NBTTagCompound();
        treeDef.setString("treeName", "redwood");
        treeDef.setString("logs", String.format("%d", getUniqueName(NContent.redwood)));
        treeDef.setString("leaves", String.format("%d,0; %d,8", getUniqueName(NContent.floraLeaves), getUniqueName(NContent.floraLeaves)));
        treeList.appendTag(treeDef);
        // sakura
        treeDef = new NBTTagCompound();
        treeDef.setString("treeName", "sakura");
        treeDef.setString("logs", String.format("%d,1; %d,5; %d,9", getUniqueName(NContent.tree), getUniqueName(NContent.tree), getUniqueName(NContent.tree)));
        treeDef.setString("leaves", String.format("%d,0; %d,8", getUniqueName(NContent.floraLeavesNoColor), getUniqueName(NContent.floraLeavesNoColor)));
        treeList.appendTag(treeDef);
        // siverbell
        treeDef = new NBTTagCompound();
        treeDef.setString("treeName", "siverbell");
        treeDef.setString("logs", String.format("%d,1; %d,5; %d,9", getUniqueName(NContent.rareTree), getUniqueName(NContent.rareTree), getUniqueName(NContent.rareTree)));
        treeDef.setString("leaves", String.format("%d,1; %d,9", getUniqueName(NContent.rareLeaves), getUniqueName(NContent.rareLeaves)));
        treeList.appendTag(treeDef);
        // tigerwood
        treeDef = new NBTTagCompound();
        treeDef.setString("treeName", "tigerwood");
        treeDef.setString("logs", String.format("%d,3; %d,7; %d,11", getUniqueName(NContent.rareTree), getUniqueName(NContent.rareTree), getUniqueName(NContent.rareTree)));
        treeDef.setString("leaves", String.format("%d,3; %d,11", getUniqueName(NContent.rareLeaves), getUniqueName(NContent.rareLeaves)));
        treeList.appendTag(treeDef);
        // willow
        treeDef = new NBTTagCompound();
        treeDef.setString("treeName", "willow");
        treeDef.setString("logs", String.format("%d", getUniqueName(NContent.willow)));
        treeDef.setString("leaves", String.format("%d,3; %d,7; %d,11; %d,15", getUniqueName(NContent.floraLeavesNoColor), getUniqueName(NContent.floraLeavesNoColor),
                getUniqueName(NContent.floraLeavesNoColor), getUniqueName(NContent.floraLeavesNoColor)));
        treeDef.setInteger("maxHorLeafBreakDist", 5);
        treeList.appendTag(treeDef);

        tpModCfg.setTag("trees", treeList);

        FMLInterModComms.sendMessage("TreeCapitator", "ThirdPartyModConfig", tpModCfg);

    }

    @Override
    public void postInit ()
    {
    }

    public static String getUniqueName (Block block)
    {
        return GameData.blockRegistry.getNameForObject(block);
    }

    public static String getUniqueName (Item item)
    {
        return GameData.itemRegistry.getNameForObject(item);
    }
}
