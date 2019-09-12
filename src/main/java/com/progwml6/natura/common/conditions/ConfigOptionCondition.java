package com.progwml6.natura.common.conditions;

import com.google.gson.JsonObject;
import com.progwml6.natura.common.Config;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class ConfigOptionCondition implements ICondition {

  private static final ResourceLocation NAME = new ResourceLocation("natura", "config_option_enabled");
  private final String configSetting;

  public ConfigOptionCondition(String configSetting) {
    this.configSetting = configSetting;
  }

  @Override
  public ResourceLocation getID() {
    return NAME;
  }

  @Override
  public boolean test() {
    if ("enableWheatRecipe".equals(this.configSetting)) {
      return Config.ServerConfig.enableWheatRecipe.get();
    }
    return false;
  }

  @Override
  public String toString() {
    return "config_option_enabled(\"" + this.configSetting + "\")";
  }

  public static class Serializer implements IConditionSerializer<ConfigOptionCondition> {

    public static final Serializer INSTANCE = new Serializer();

    @Override
    public void write(JsonObject json, ConfigOptionCondition value) {
      json.addProperty("configSetting", value.configSetting);
    }

    @Override
    public ConfigOptionCondition read(JsonObject json) {
      return new ConfigOptionCondition(JSONUtils.getString(json, "configSetting"));
    }

    @Override
    public ResourceLocation getID() {
      return ConfigOptionCondition.NAME;
    }
  }
}