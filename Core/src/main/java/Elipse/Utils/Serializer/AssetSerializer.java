package Elipse.Utils.Serializer;

import java.lang.reflect.Type;
import java.util.UUID;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import Elipse.Core.Assets.Asset;
import Elipse.Core.Project.Project;

public class AssetSerializer implements JsonDeserializer<Asset>, JsonSerializer<Asset> {

	@Override
	public JsonElement serialize(Asset src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject result = new JsonObject();
		result.add("assetId", context.serialize(src.GetAssetHandel()));

		return result;
	}

	@Override
	public Asset deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject assetObj = json.getAsJsonObject();

		UUID assetId = context.deserialize(assetObj.get("assetId"), UUID.class);

		return Project.GetActive().GetAssetManager().GetAsset(assetId);
	}
}
