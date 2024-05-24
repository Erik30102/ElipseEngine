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

import Elipse.Core.ECS.BuiltIn.RenderSystem.PointLightComponent;
import Elipse.Core.Maths.Color;

public class PointLightComponentSerializer
		implements JsonSerializer<PointLightComponent>, JsonDeserializer<PointLightComponent> {

	@Override
	public PointLightComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();
		float strength = jsonObject.get("Strength").getAsFloat();
		Color color = context.deserialize(jsonObject.get("Color"), Color.class);

		PointLightComponent p = new PointLightComponent();
		p.SetColor(color);
		p.SetIntensity(strength);

		return p;
	}

	@Override
	public JsonElement serialize(PointLightComponent src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject result = new JsonObject();
		result.add("Strength", new JsonPrimitive(src.GetIntensity()));
		result.add("Color", context.serialize(src.GetColor()));

		return result;
	}

}
