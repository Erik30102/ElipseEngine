package Elipse.Utils.Serializer.Components;

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

import Elipse.Core.ECS.BuiltIn.RenderSystem.SpriteRenderComponent;
import Elipse.Core.Project.Project;

public class SpriteRenderComponentSerializer
		implements JsonDeserializer<SpriteRenderComponent>, JsonSerializer<SpriteRenderComponent> {

	@Override
	public JsonElement serialize(SpriteRenderComponent src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject result = new JsonObject();
		result.add("texAssetId", new JsonPrimitive(src.getTexture().GetAssetHandel().toString()));
		return result;
	}

	@Override
	public SpriteRenderComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();
		UUID texAssetId = UUID.fromString(jsonObject.get("texAssetId").getAsString());
		SpriteRenderComponent comp = new SpriteRenderComponent();

		comp.SetTexture(Project.GetActive().GetAssetManager().GetAsset(texAssetId));

		return comp;
	}

}
