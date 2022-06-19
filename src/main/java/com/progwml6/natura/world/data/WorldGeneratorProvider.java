package com.progwml6.natura.world.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.JsonOps;
import com.progwml6.natura.Natura;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.stream.StreamSupport;

public class WorldGeneratorProvider implements DataProvider {

  private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

  private final DataGenerator generator;

  public WorldGeneratorProvider(DataGenerator generator) {
    this.generator = generator;
  }

  @Override
  public void run(HashCache pCache) throws IOException {
    Path path = this.generator.getOutputFolder();
    RegistryAccess registryaccess = BuiltinRegistries.ACCESS;
    DynamicOps<JsonElement> dynamicops = RegistryOps.create(JsonOps.INSTANCE, registryaccess);

    StreamSupport.stream(RegistryAccess.knownRegistries().spliterator(), false)
      .filter(r -> registryaccess.ownedRegistry(r.key()).isPresent() && !r.key().equals(Registry.BIOME_REGISTRY))
      .forEach((data) -> dumpRegistryCap(pCache, path, registryaccess, dynamicops, data));
  }

  private static <T> void dumpRegistryCap(HashCache cache, Path path, RegistryAccess access, DynamicOps<JsonElement> ops, RegistryAccess.RegistryData<T> data) {
    Natura.LOG.info("Dumping: {}", data.key());
    dumpRegistry(path, cache, ops, data.key(), access.ownedRegistryOrThrow(data.key()), data.codec());
  }

  private static <E, T extends Registry<E>> void dumpRegistry(Path path, HashCache cache, DynamicOps<JsonElement> ops, ResourceKey<? extends T> key, T registry, Encoder<E> encoder) {
    for (Map.Entry<ResourceKey<E>, E> entry : registry.entrySet()) {
      Natura.LOG.info(entry.getKey().location());
      if (entry.getKey().location().getNamespace().equals(Natura.MOD_ID)) {
        Natura.LOG.info("\t\t{}", entry.getKey().location().getPath());
        Path otherPath = createPath(path, key.location(), entry.getKey().location());
        dumpValue(otherPath, cache, ops, encoder, entry.getValue());
      }
    }

  }

  private static <E> void dumpValue(Path path, HashCache cache, DynamicOps<JsonElement> ops, Encoder<E> encoder, E entry) {
    try {
      Optional<JsonElement> optional = encoder.encodeStart(ops, entry).resultOrPartial((p_206405_) -> {
        Natura.LOG.error("Couldn't serialize element {}: {}", path, p_206405_);
      });
      if (optional.isPresent()) {
        if (optional.get().isJsonObject()) {
          JsonObject object = optional.get().getAsJsonObject();
          if (object.has("generator") && object.get("generator").isJsonObject()) {
            JsonObject generator = object.getAsJsonObject("generator");
            if (generator.has("use_overworld_seed")) {
              generator.remove("use_overworld_seed");
              generator.addProperty("use_overworld_seed", true);
            }
            if (generator.has("wrapped_generator")) {
              JsonObject wrapped_generator = generator.getAsJsonObject("wrapped_generator");
              if (wrapped_generator.has("biome_source"))
                wrapped_generator.getAsJsonObject("biome_source").remove("seed");
            }
          }
        }
        DataProvider.save(GSON, cache, optional.get(), path);
      }
    } catch (IOException ioexception) {
      Natura.LOG.error("Couldn't save element {}", path, ioexception);
    }

  }

  private static Path createPath(Path path, ResourceLocation registry, ResourceLocation entry) {
    return path.resolve("data").resolve(entry.getNamespace()).resolve(registry.getPath()).resolve(entry.getPath() + ".json");
  }

  @Override
  public String getName() {
    return "Natura World Generator";
  }
}
