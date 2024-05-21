package Sandbox;

import Elipse.Core.ECS.Component;

public abstract class TickComponent implements Component {

	/**
	 * Gets called every tick the duration of a tick is set in the Tick System
	 */
	public abstract void OnTick();

}
