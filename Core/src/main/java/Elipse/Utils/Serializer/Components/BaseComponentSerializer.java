package Elipse.Utils.Serializer.Components;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import Elipse.Core.Logger;
import Elipse.Core.ECS.BuiltIn.BaseSystem.BaseComponent;
import Elipse.Core.ECS.BuiltIn.BaseSystem.BaseComponentWrapper;
import Elipse.Core.Scripting.ScriptEngine;

public class BaseComponentSerializer
		implements JsonDeserializer<BaseComponentWrapper>, JsonSerializer<BaseComponentWrapper> {

	@Override
	public JsonElement serialize(BaseComponentWrapper src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject result = new JsonObject();
		result.add("type", new JsonPrimitive(src.GetComponent().getClass().getCanonicalName()));
		result.add("properties", context.serialize(src, src.getClass()));

		return result;
	}

	@Override
	public BaseComponentWrapper deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {

		JsonObject jsonObject = json.getAsJsonObject();
		String type = jsonObject.get("type").getAsString();
		JsonObject properties = jsonObject.get("properties").getAsJsonObject();
		try {
			BaseComponent baseComp = context.deserialize(properties,
					ScriptEngine.GetInstance().GetClassLoader().loadClass(type));
			return new BaseComponentWrapper(baseComp);
		} catch (ClassNotFoundException e) {
			Logger.c_error("failed trying to create class from json of type: " + type);
			e.printStackTrace();
		}

		return null;
	}

}
