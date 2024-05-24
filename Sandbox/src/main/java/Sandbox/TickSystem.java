package Sandbox;

import java.util.List;

import Elipse.Core.ECS.Component;
import Elipse.Core.ECS.ECSSystem;
import Elipse.Core.ECS.Entity;
import Elipse.Utils.Pair;

public class TickSystem extends ECSSystem {

	private float tickLength = 2f;
	private float time = 0;

	@Override
	public void OnStart() {
		time = 0;
	}

	@Override
	public void OnRuntimeStep(float dt) {
		time += dt;

		if (time >= tickLength) {
			time = 0;

			List<Pair<Entity, Component>> components = this.scene.GetComponents(TickComponent.class);

			if (components == null)
				return;

			for (Pair<Entity, Component> pair : components) {
				TickComponent tick = (TickComponent) pair.getValue();
				tick.OnTick();
			}
		}
	}

	/**
	 * Sets the duration between a tick and the next one
	 * 
	 * @param tickLength the duration between a tick and the next one in
	 *                   seconds
	 */
	public void SetTickLength(float tickLength) {
		this.tickLength = tickLength;
	}

	/**
	 * Gets the duration between a tick and the next one
	 * 
	 * @return the current tick length
	 */
	public float GetTickLength() {
		return tickLength;
	}

}
