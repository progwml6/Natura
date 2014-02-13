package mods.natura.client;

import mods.natura.client.entity.FlameSpiderBabyRender;
import mods.natura.client.entity.FlameSpiderRender;
import mods.natura.client.entity.FusewoodArrowRender;
import mods.natura.client.entity.ImpModel;
import mods.natura.client.entity.ImpRender;
import mods.natura.client.entity.NitroCreeperRender;
import mods.natura.common.NProxyCommon;
import mods.natura.entity.BabyHeatscarSpider;
import mods.natura.entity.FusewoodArrow;
import mods.natura.entity.HeatscarSpider;
import mods.natura.entity.ImpEntity;
import mods.natura.entity.NitroCreeper;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class NProxyClient extends NProxyCommon
{
    @Override
    public void registerRenderer ()
    {
        RenderingRegistry.registerBlockHandler(new BerryRender());
        RenderingRegistry.registerBlockHandler(new SaguaroRenderer());
        RenderingRegistry.registerBlockHandler(new CropRender());
        RenderingRegistry.registerBlockHandler(new FenceRender());
        RenderingRegistry.registerBlockHandler(new HopperRender());
        RenderingRegistry.registerBlockHandler(new LeverRender());
        TickRegistry.registerTickHandler(new NCropsTickHandler(), Side.CLIENT);

        RenderingRegistry.registerEntityRenderingHandler(ImpEntity.class, new ImpRender(new ImpModel(), 0f));
        RenderingRegistry.registerEntityRenderingHandler(HeatscarSpider.class, new FlameSpiderRender());
        RenderingRegistry.registerEntityRenderingHandler(NitroCreeper.class, new NitroCreeperRender());
        RenderingRegistry.registerEntityRenderingHandler(FusewoodArrow.class, new FusewoodArrowRender());
        RenderingRegistry.registerEntityRenderingHandler(BabyHeatscarSpider.class, new FlameSpiderBabyRender());

        Minecraft mc = Minecraft.getMinecraft();
        try
        {
            GrassColorizerAlternate.setBlueGrassBiomeColorizer(TextureUtil.readImageData(mc.getResourceManager(), bluegrass));
            GrassColorizerAlternate.setOrangeGrassBiomeColorizer(TextureUtil.readImageData(mc.getResourceManager(), orangegrass));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static final ResourceLocation bluegrass = new ResourceLocation("natura", "textures/misc/bluegrasscolor.png");
    private static final ResourceLocation orangegrass = new ResourceLocation("natura", "textures/misc/orangegrasscolor.png");

    public static void renderStandardInvBlock (RenderBlocks renderblocks, Block block, int meta)
    {
        Tessellator tessellator = Tessellator.instance;
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1F, 0.0F);
        renderblocks.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderblocks.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1F);
        renderblocks.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderblocks.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1F, 0.0F, 0.0F);
        renderblocks.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderblocks.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, meta));
        tessellator.draw();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

    /*public File getMinecraftDir ()
    {
        return Minecraft.getMinecraftDir();
    }*/
}
