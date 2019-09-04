package com.progwml6.natura.common;

import net.minecraft.client.Minecraft;

public class ClientProxy extends ServerProxy {

  @Override
  public void preInit() {

  }

  @Override
  public void init() {

  }

  @Override
  public void postInit() {

  }

  @Override
  public boolean fancyGraphicsEnabled() {
    return Minecraft.getInstance().gameSettings.fancyGraphics;
  }
}
