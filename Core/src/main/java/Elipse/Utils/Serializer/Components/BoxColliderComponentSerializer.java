package Elipse.Utils.Serializer.Components;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import Elipse.Core.ECS.BuiltIn.Physics.BoxColliderComponent;

public class BoxColliderComponentSerializer
		implements JsonDeserializer<BoxColliderComponent>, JsonSerializer<BoxColliderComponent> {

	@Override
	public JsonElement serialize(BoxColliderComponent src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject jsonObject = new JsonObject();

		return jsonObject;
	}

	@Override
	public BoxColliderComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		BoxColliderComponent comp = new BoxColliderComponent();

		return comp;
	}

}
