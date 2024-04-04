package Elipse.Utils.Serializer;

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

public class UUIDSerializer implements JsonDeserializer<UUID>, JsonSerializer<UUID> {

	@Override
	public JsonElement serialize(UUID src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject result = new JsonObject();
		result.add("uuidString", new JsonPrimitive(src.toString()));
		return result;
	}

	@Override
	public UUID deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();
		String uuidString = jsonObject.get("uuidString").getAsString();

		return UUID.fromString(uuidString);
	}

}
