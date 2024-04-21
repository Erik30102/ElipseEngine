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

import Elipse.Core.ECS.BuiltIn.RenderSystem.CameraComponent;

public class CameraComponentSerializer implements JsonSerializer<CameraComponent>, JsonDeserializer<CameraComponent> {

	@Override
	public CameraComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();
		float zoom = jsonObject.get("zoom").getAsFloat();
		boolean isActive = jsonObject.get("isActive").getAsBoolean();

		CameraComponent camera = new CameraComponent();
		camera.SetZoom(zoom);
		camera.SetActive(isActive);

		return camera;
	}

	@Override
	public JsonElement serialize(CameraComponent src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject result = new JsonObject();
		result.add("zoom", new JsonPrimitive(src.GetZoom()));
		result.add("isActive", new JsonPrimitive(src.isActive()));

		return result;
	}

}
