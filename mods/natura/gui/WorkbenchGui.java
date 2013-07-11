package mods.natura.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

public class WorkbenchGui extends GuiContainer
{
    public WorkbenchGui(InventoryPlayer inventoryplayer, World world)
    {
        super(new WorkbenchContainer(inventoryplayer, world));
    }

    public void onGuiClosed ()
    {
        super.onGuiClosed();
    }

    @Override
    protected void drawGuiContainerForegroundLayer (int par1, int par2)
    {
        fontRenderer.drawString("Crafting", 28, 6, 0x404040);
        fontRenderer.drawString("Inventory", 8, (ySize - 96) + 2, 0x404040);
    }

    protected void drawGuiContainerBackgroundLayer (float f, int i, int j)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture("/gui/crafting.png");
        int l = (width - xSize) / 2;
        int i1 = (height - ySize) / 2;
        drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
    }
}
