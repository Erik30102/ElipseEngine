package Elipse.Utils.Serializer.Components;

import java.lang.reflect.Type;
import java.util.UUID;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import Elipse.Core.ECS.BuiltIn.RenderSystem.TilemapComponent;
import Elipse.Core.Project.Project;

public class TilemapComponentSerializer
		implements JsonSerializer<TilemapComponent>, JsonDeserializer<TilemapComponent> {

	@Override
	public TilemapComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {

		JsonObject jsonObject = json.getAsJsonObject();

		UUID assetId = context.deserialize(jsonObject.get("assetId"), UUID.class);
		TilemapComponent t = new TilemapComponent();
		t.setTilemap(Project.GetActive().GetAssetManager().GetAsset(assetId));
		return t;
	}

	@Override
	public JsonElement serialize(TilemapComponent src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject jsonObject = new JsonObject();
		String AssetID = "NF";

		UUID uuid = src.getTilemap().GetAssetHandel();
		if (uuid != null)
			AssetID = uuid.toString();

		jsonObject.add("assetId", new JsonPrimitive(AssetID));
		return jsonObject;
	}

}
