package Elipse.Utils.Serializer;

import java.util.UUID;
import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import Elipse.Core.Project.Project;
import Elipse.Renderer.Batching.Spritesheet;

public class LocalSpritesheetSerializer implements JsonSerializer<Spritesheet>, JsonDeserializer<Spritesheet> {

	@Override
	public Spritesheet deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();

		int spriteWidth = jsonObject.get("spriteWidth").getAsInt();
		int spriteHeight = jsonObject.get("spriteHeight").getAsInt();
		String textureID = jsonObject.get("TextureID").getAsString();
		return new Spritesheet(Project.GetActive().GetAssetManager().GetAsset(UUID.fromString(textureID)), spriteWidth,
				spriteHeight);
	}

	@Override
	public JsonElement serialize(Spritesheet src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject result = new JsonObject();
		result.add("spriteWidth", new JsonPrimitive(src.getSpriteWidth()));
		result.add("spriteHeight", new JsonPrimitive(src.getSpriteHeight()));

		result.add("TextureID", new JsonPrimitive(src.getTexture().GetAssetHandel().toString()));
		return result;
	}

}
