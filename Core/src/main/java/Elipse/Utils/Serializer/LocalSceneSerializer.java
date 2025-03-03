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
import Elipse.Core.ECS.BuiltIn.BaseSystem.BaseComponentWrapper;
import Elipse.Core.ECS.BuiltIn.BaseSystem.BaseSystem;
import Elipse.Core.ECS.BuiltIn.Physics.BoxColliderComponent;
import Elipse.Core.ECS.BuiltIn.Physics.PhysicsSystem;
import Elipse.Core.ECS.BuiltIn.Physics.RidgedBodyComponent;
import Elipse.Core.ECS.BuiltIn.RenderSystem.CameraComponent;
import Elipse.Core.ECS.BuiltIn.RenderSystem.PointLightComponent;
import Elipse.Core.ECS.BuiltIn.RenderSystem.SpriteRenderComponent;
import Elipse.Core.ECS.BuiltIn.RenderSystem.TilemapComponent;
import Elipse.Utils.Serializer.Components.BaseComponentSerializer;
import Elipse.Utils.Serializer.Components.BoxColliderComponentSerializer;
import Elipse.Utils.Serializer.Components.CameraComponentSerializer;
import Elipse.Utils.Serializer.Components.PointLightComponentSerializer;
import Elipse.Utils.Serializer.Components.RidgetbodyComponentSerializer;
import Elipse.Utils.Serializer.Components.SpriteRenderComponentSerializer;
import Elipse.Utils.Serializer.Components.TilemapComponentSerializer;

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
				if (component instanceof BaseComponentWrapper) {
					JsonObject componentResult = new BaseComponentSerializer().serialize((BaseComponentWrapper) component,
							BaseComponentWrapper.class,
							context).getAsJsonObject();

					componentResult.add("compType", new JsonPrimitive("Base"));
					componentArray.add(componentResult);
				} else if (component instanceof SpriteRenderComponent) {
					JsonObject componentResult = new SpriteRenderComponentSerializer()
							.serialize((SpriteRenderComponent) component,
									SpriteRenderComponent.class,
									context)
							.getAsJsonObject();

					componentResult.add("compType", new JsonPrimitive("Sprite"));
					componentArray.add(componentResult);
				} else if (component instanceof CameraComponent) {
					JsonObject componentResult = new CameraComponentSerializer()
							.serialize((CameraComponent) component,
									CameraComponent.class,
									context)
							.getAsJsonObject();

					componentResult.add("compType", new JsonPrimitive("Camera"));
					componentArray.add(componentResult);
				} else if (component instanceof BoxColliderComponent) {
					JsonObject componentResult = new BoxColliderComponentSerializer()
							.serialize((BoxColliderComponent) component,
									BoxColliderComponent.class,
									context)
							.getAsJsonObject();

					componentResult.add("compType", new JsonPrimitive("BoxCollider"));
					componentArray.add(componentResult);
				} else if (component instanceof RidgedBodyComponent) {
					JsonObject componentResult = new RidgetbodyComponentSerializer()
							.serialize((RidgedBodyComponent) component,
									RidgedBodyComponent.class,
									context)
							.getAsJsonObject();

					componentResult.add("compType", new JsonPrimitive("RidgetBody"));
					componentArray.add(componentResult);
				} else if (component instanceof PointLightComponent) {
					JsonObject componentResult = new PointLightComponentSerializer()
							.serialize((PointLightComponent) component,
									PointLightComponent.class,
									context)
							.getAsJsonObject();

					componentResult.add("compType", new JsonPrimitive("PointLight"));
					componentArray.add(componentResult);
				} else if (component instanceof TilemapComponent) {
					JsonObject componentResult = new TilemapComponentSerializer()
							.serialize((TilemapComponent) component,
									TilemapComponent.class,
									context)
							.getAsJsonObject();

					componentResult.add("compType", new JsonPrimitive("Tilemap"));
					componentArray.add(componentResult);
				}
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
		scene.AddSystem(new BaseSystem(), new PhysicsSystem());

		for (JsonElement entityElement : entitiesArray) {
			JsonObject entityObject = entityElement.getAsJsonObject();
			String name = entityObject.get("Name").getAsString();
			Transform transform = context.deserialize(entityObject.get("transform"), Transform.class);

			JsonArray componentArray = entityObject.getAsJsonArray("Components");

			Entity entity = scene.Create(name);
			entity.setTransform(transform);

			for (JsonElement componentElement : componentArray) {
				JsonObject componentObject = componentElement.getAsJsonObject();

				Component comp;

				switch (componentObject.get("compType").getAsString()) {
					case "Base":
						BaseComponentSerializer base_ser = new BaseComponentSerializer();
						comp = base_ser.deserialize(componentObject, BaseComponentWrapper.class, context);

						entity.AddComponent(comp);
						break;
					case "Sprite":
						SpriteRenderComponentSerializer sprite_ser = new SpriteRenderComponentSerializer();
						comp = sprite_ser.deserialize(componentObject, SpriteRenderComponent.class, context);

						entity.AddComponent(comp);
						break;
					case "Camera":
						CameraComponentSerializer camera_ser = new CameraComponentSerializer();
						comp = camera_ser.deserialize(componentObject, CameraComponent.class, context);

						entity.AddComponent(comp);
						break;
					case "RidgetBody":
						RidgetbodyComponentSerializer rigidbody_ser = new RidgetbodyComponentSerializer();
						comp = rigidbody_ser.deserialize(componentObject, RidgedBodyComponent.class, context);

						entity.AddComponent(comp);
						break;
					case "BoxCollider":
						BoxColliderComponentSerializer boxcollider_ser = new BoxColliderComponentSerializer();
						comp = boxcollider_ser.deserialize(componentObject, BoxColliderComponent.class, context);

						entity.AddComponent(comp);
						break;
					case "PointLight":
						PointLightComponentSerializer pointlight_ser = new PointLightComponentSerializer();
						comp = pointlight_ser.deserialize(componentObject, PointLightComponent.class, context);

						entity.AddComponent(comp);
						break;
					case "Tilemap":
						TilemapComponentSerializer tilemap_ser = new TilemapComponentSerializer();
						comp = tilemap_ser.deserialize(componentObject, TilemapComponent.class, context);

						entity.AddComponent(comp);
						break;
					default:
						Logger.c_error("Unknown Component Type: " + componentObject.get("compType").getAsString());
						break;
				}
			}
		}

		return scene;
	}

}
