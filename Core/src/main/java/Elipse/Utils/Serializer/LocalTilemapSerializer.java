package Elipse.Utils.Serializer;

import java.lang.reflect.Type;
import java.util.UUID;

import org.jbox2d.dynamics.Profile;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import Elipse.Core.Project.Project;
import Elipse.Core.Tilemap.Tilemap;

public class LocalTilemapSerializer implements JsonDeserializer<Tilemap>, JsonSerializer<Tilemap> {

	@Override
	public JsonElement serialize(Tilemap src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject result = new JsonObject();

		result.add("width", new JsonPrimitive(src.GetWidth()));
		result.add("height", new JsonPrimitive(src.GetHeight()));
		result.add("SpritesheetID", new JsonPrimitive(src.GetSpritesheet().GetAssetHandel().toString()));

		result.add("tiles", context.serialize(src.GetTiles(), int[].class));

		return result;
	}

	@Override
	public Tilemap deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {

		JsonObject jsonObject = json.getAsJsonObject();
		int width = jsonObject.get("width").getAsInt();
		int height = jsonObject.get("height").getAsInt();
		String textureID = jsonObject.get("SpritesheetID").getAsString();
		int[] tiles = context.deserialize(jsonObject.get("tiles"), int[].class);

		return new Tilemap(Project.GetActive().GetAssetManager().GetAsset(UUID.fromString(textureID)), width, height,
				tiles);
	}

}
