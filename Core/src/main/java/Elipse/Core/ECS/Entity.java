package Elipse.Core.ECS;

public class Entity {

	private String name;
	public Transform transform;

	private transient Scene scene;

	final void setScene(Scene scene) {
		this.scene = scene;
	}

	public Entity(String name) {
		this.name = name;
		transform = new Transform();
	}

	public void AddComponent(Component component) {
		scene.AddComponent(component, this);
	}

	public <T extends Component> boolean HasComponent(Class<T> clazz) {
		return scene.GetComponent(this, clazz) != null;
	}

	public String GetName() {
		return this.name;
	}

	public void setTransform(Transform transform2) {
		this.transform = transform2;
	}

	public void SetName(String newname) {
		this.name = newname;
	}

	public Scene GetScene() {
		return this.scene;
	}

	public <T extends Component> T GetComponent(Class<T> clazz) {
		return scene.GetComponent(this, clazz);
	}

}
