package Elipse.Utils.Serializer.Components;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import Elipse.Core.ECS.BuiltIn.BaseSystem.BaseComponentWrapper;

public class BaseComponentSerializer
		implements JsonDeserializer<BaseComponentWrapper>, JsonSerializer<BaseComponentWrapper> {

	@Override
	public JsonElement serialize(BaseComponentWrapper src, Type typeOfSrc, JsonSerializationContext context) {

		throw new UnsupportedOperationException("Unimplemented method 'serialize'");
	}

	@Override
	public BaseComponentWrapper deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {

		throw new UnsupportedOperationException("Unimplemented method 'deserialize'");
	}

}
