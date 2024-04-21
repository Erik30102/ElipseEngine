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
		String texAssetId = "INTERNAL";

		UUID uid = src.getTexture().GetAssetHandel();
		if (uid != null) {
			texAssetId = uid.toString();
		}

		result.add("texAssetId", new JsonPrimitive(texAssetId));
		return result;
	}

	@Override
	public SpriteRenderComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();

		String uuid = jsonObject.get("texAssetId").getAsString();

		if (!uuid.equals("INTERNAL")) {
			UUID texAssetId = UUID.fromString(uuid);
			SpriteRenderComponent comp = new SpriteRenderComponent();

			comp.SetTexture(Project.GetActive().GetAssetManager().GetAsset(texAssetId));
			return comp;
		} else {
			return new SpriteRenderComponent();
		}
	}

}
