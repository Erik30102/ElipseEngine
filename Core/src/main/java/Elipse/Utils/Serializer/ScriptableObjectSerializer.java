package Elipse.Utils.Serializer;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import Elipse.Core.Scripting.ScriptEngine;
import Elipse.Core.Scripting.ScriptableObject;

public class ScriptableObjectSerializer
		implements JsonSerializer<ScriptableObject>, JsonDeserializer<ScriptableObject> {

	@Override
	public ScriptableObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject object = json.getAsJsonObject();
		String type = object.get("type").getAsString();
		JsonObject properties = object.get("properties").getAsJsonObject();

		ScriptableObject result = context.deserialize(properties,
				ScriptEngine.GetInstance().GetScript(type).GetBaseClazz());
		return result;
	}

	@Override
	public JsonElement serialize(ScriptableObject src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject result = new JsonObject();
		result.add("type", new JsonPrimitive(src.getClass().getCanonicalName()));
		result.add("properties", context.serialize(src, src.getClass()));
		return result;
	}

}
