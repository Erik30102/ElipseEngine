package Elipse.Utils.Serializer;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import Elipse.Core.Assets.Asset;
import Elipse.Core.Assets.AssetMetaData;

public class AssetBankSerializer implements JsonDeserializer<Map<String, AssetMetaData>> {

	@Override
	public Map<String, AssetMetaData> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		Map<String, AssetMetaData> map = new HashMap<>();

		json.getAsJsonObject().entrySet().forEach(d -> {
			UUID assetId = UUID.fromString(d.getKey());
			JsonObject assetData = d.getValue().getAsJsonObject();

			AssetMetaData assetMetaData = new AssetMetaData(Asset.GetAssetTypeFromString(assetData.get("type").getAsString()),
					assetData.get("path").getAsString());

			map.put(assetId.toString(), assetMetaData);
		});

		return map;
	}

}
