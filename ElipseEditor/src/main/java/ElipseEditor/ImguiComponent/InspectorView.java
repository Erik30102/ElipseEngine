package ElipseEditor.ImguiComponent;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.joml.Vector3f;

import Elipse.Core.Logger;
import Elipse.Core.ECS.Component;
import Elipse.Core.ECS.Entity;
import Elipse.Core.ECS.Scene;
import Elipse.Core.ECS.BuiltIn.BaseSystem.BaseComponent;
import Elipse.Core.ECS.BuiltIn.BaseSystem.BaseComponentWrapper;
import Elipse.Core.ECS.BuiltIn.RenderSystem.SpriteRenderComponent;
import Elipse.Renderer.Opengl.Texture.Texture2D;
import ElipseEditor.EditorLayer;
import imgui.ImGui;

public class InspectorView {

	private Entity entity;
	private Scene scene;

	public InspectorView(Scene scene) {
		this.scene = scene;
	}

	public void OnImgui() {
		if (entity != null) {
			ImGui.begin("Inspector");

			if (ImGui.collapsingHeader("Entity info")) {
				ImGui.columns(2);

				ImGui.text("Entity name:");
				ImGui.nextColumn();

				ImGui.text(entity.GetName());
			}

			for (Component component : scene.GetComponents(entity)) {
				if (component.getClass() == BaseComponentWrapper.class) {
					BaseComponent comp = ((BaseComponentWrapper) component).GetComponent();
					if (comp == null)
						continue;

					if (ImGui.collapsingHeader(comp.getClass().getSimpleName())) {
						HandleCustomComponentSlider(comp);
					}

					continue;
				}

				if (ImGui.collapsingHeader(component.getClass().getSimpleName())) {
					if (component.getClass() == SpriteRenderComponent.class) {

					}
				}
			}

			if (ImGui.button("add Component"))
				ImGui.openPopup("Add Component");

			if (ImGui.beginPopup("Add Component")) {
				for (Class<? extends Component> component : EditorLayer.GetEditor().GetComponents()) {
					if (ImGui.selectable(component.getSimpleName())) {
						try {
							scene.AddComponent(component.getConstructor().newInstance(), entity);
						} catch (Exception e) {
							Logger.c_error(
									"Failed while trying to add new Component of type: " + component.getSimpleName() + " To Entity");
							e.printStackTrace();
						}
					}
				}

				ImGui.separator();

				for (Class<? extends BaseComponent> comp : EditorLayer.GetEditor().GetScriptEngine().GetComponents()) {
					if (ImGui.selectable(comp.getSimpleName())) {
						try {
							scene.AddComponent(new BaseComponentWrapper(comp.getConstructor().newInstance()), entity);
						} catch (Exception e) {
							Logger.c_error(
									"Failed while trying to add new Base Behavior Component of type: " + comp.getSimpleName()
											+ " To Entity");
							e.printStackTrace();
						}
					}
				}

				ImGui.endPopup();
			}

			ImGui.end();
		}
	}

	/**
	 * Craetes a gird of ImGui Sliders for the given Component based on the fields
	 * set to public in the code
	 * 
	 * @param component The Component to have its fields displayed
	 */
	private void HandleCustomComponentSlider(BaseComponent component) {
		ImGui.columns(2);

		try {
			Field[] fields = component.getClass().getDeclaredFields();
			for (Field field : fields) {
				if (!Modifier.isPrivate(field.getModifiers())) {
					Class type = field.getType();
					Object value = field.get(component);
					String name = field.getName();

					if (type == int.class) {
						ImGui.text(name);
						ImGui.nextColumn();

						int val = (int) value;
						int[] imInt = { val };

						if (ImGui.dragInt("##" + name, imInt)) {
							field.set(component, imInt[0]);
						}

						ImGui.nextColumn();
					} else if (type == float.class) {
						ImGui.text(name);
						ImGui.nextColumn();

						float val = (float) value;
						float[] imfloat = { val };

						if (ImGui.dragFloat("##" + name, imfloat)) {
							field.set(component, imfloat[0]);
						}

						ImGui.nextColumn();
					} else if (type == Vector3f.class) {
						ImGui.text(name);
						ImGui.nextColumn();

						Vector3f val = (Vector3f) value;
						float[] f = { val.x, val.y, val.z };
						if (ImGui.dragFloat3("##v" + name, f)) {
							val.x = f[0];
							val.y = f[1];
							val.z = f[2];
							field.set(component, val);
						}
						ImGui.nextColumn();
					} else if (type == boolean.class) {
						ImGui.text(name);
						ImGui.nextColumn();

						boolean val = (boolean) value;
						if (ImGui.checkbox("##" + name, val)) {
							field.set(component, !val);
						}
						ImGui.nextColumn();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ImGui.columns(1);

		ImGui.columns(1);
	}

	public void SetScene(Scene scene) {
		this.scene = scene;
	}

	public void SetEntity(Entity entity) {
		this.entity = entity;
	}
}
