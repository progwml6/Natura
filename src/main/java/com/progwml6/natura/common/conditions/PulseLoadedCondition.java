package com.progwml6.natura.common.conditions;

import com.google.gson.JsonObject;
import com.progwml6.natura.Natura;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class PulseLoadedCondition implements ICondition {

  private static final ResourceLocation NAME = new ResourceLocation("natura", "pulse_loaded");
  private final String pulseId;

  public PulseLoadedCondition(String pulseId) {
    this.pulseId = pulseId;
  }

  @Override
  public ResourceLocation getID() {
    return NAME;
  }

  @Override
  public boolean test() {
    return Natura.pulseManager.isPulseLoaded(this.pulseId);
  }

  @Override
  public String toString() {
    return "pulse_loaded(\"" + this.pulseId + "\")";
  }

  public static class Serializer implements IConditionSerializer<PulseLoadedCondition> {

    public static final Serializer INSTANCE = new Serializer();

    @Override
    public void write(JsonObject json, PulseLoadedCondition value) {
      json.addProperty("pulseid", value.pulseId);
    }

    @Override
    public PulseLoadedCondition read(JsonObject json) {
      return new PulseLoadedCondition(JSONUtils.getString(json, "pulseid"));
    }

    @Override
    public ResourceLocation getID() {
      return PulseLoadedCondition.NAME;
    }
  }
}