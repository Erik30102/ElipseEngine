package Elipse.Utils.Serializer.Components;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import Elipse.Core.ECS.BuiltIn.Physics.RidgedBodyComponent;

public class RidgetbodyComponentSerializer
		implements JsonDeserializer<RidgedBodyComponent>, JsonSerializer<RidgedBodyComponent> {

	@Override
	public JsonElement serialize(RidgedBodyComponent src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("isRotationLocked", src.IsRotationLocked());

		return jsonObject;
	}

	@Override
	public RidgedBodyComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();

		boolean isRotationLocked = jsonObject.get("isRotationLocked").getAsBoolean();

		RidgedBodyComponent comp = new RidgedBodyComponent();
		comp.SetIsRotationLocked(isRotationLocked);
		return comp;
	}

}
