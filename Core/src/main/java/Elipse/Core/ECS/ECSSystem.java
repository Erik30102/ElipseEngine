package Elipse.Core.ECS;

public abstract class ECSSystem {

	protected Scene scene;

	final void setScene(Scene scene) {
		this.scene = scene;
	}

	public void OnStart() {
	}

	public abstract void step(float dt);

	public void Dispose() {
	}
}
