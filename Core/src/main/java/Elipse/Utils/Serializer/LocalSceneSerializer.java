package Elipse.Utils.Serializer;

import java.lang.reflect.Type;

import java.util.List;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import Elipse.Core.ECS.Entity;
import Elipse.Core.Logger;
import Elipse.Core.ECS.Component;
import Elipse.Core.ECS.Scene;
import Elipse.Core.ECS.Transform;

public class LocalSceneSerializer implements JsonDeserializer<Scene>, JsonSerializer<Scene> {

	@Override
	public JsonElement serialize(Scene src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject result = new JsonObject();
		JsonArray entities = new JsonArray();
		for (Entry<Entity, List<Component>> entity : src.GetEntities().entrySet()) {

			JsonObject entityObject = new JsonObject();
			entityObject.add("Name", new JsonPrimitive(entity.getKey().GetName()));
			entityObject.add("transform", context.serialize(entity.getKey().transform));

			JsonArray componentArray = new JsonArray();

			for (Component component : entity.getValue()) {
				JsonObject componentResult = new JsonObject();
				result.add("type", new JsonPrimitive(component.getClass().getCanonicalName()));
				result.add("properties", context.serialize(component, src.getClass()));
				componentArray.add(componentResult);
			}

			entityObject.add("Components", componentArray);

			entities.add(entityObject);
		}

		result.add("entities", entities);
		return result;
	}

	@Override
	public Scene deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject sceneObject = json.getAsJsonObject();
		JsonArray entitiesArray = sceneObject.getAsJsonArray("entities");

		Scene scene = new Scene();

		for (JsonElement entityElement : entitiesArray) {
			JsonObject entityObject = entityElement.getAsJsonObject();
			String name = entityObject.get("Name").getAsString();
			Transform transform = context.deserialize(entityObject.get("transform"), Transform.class);

			JsonArray componentArray = entityObject.getAsJsonArray("Components");

			Entity entity = scene.Create(name);
			entity.setTransform(transform);

			for (JsonElement componentElement : componentArray) {
				JsonObject componentObject = componentElement.getAsJsonObject();
				String type = componentObject.get("type").getAsString();
				Component component;
				try {
					component = context.deserialize(componentObject.get("properties"), Class.forName(type));
					scene.AddComponent(component, entity);
				} catch (ClassNotFoundException e) {
					Logger.c_error("Error while trying to deserialize a Scene");

					e.printStackTrace();
					return null;
				}
			}
		}

		return scene;
	}

}
