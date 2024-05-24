package Sandbox;

import Elipse.Core.Logger;
import Elipse.Core.ECS.Component;

public class TickComponent implements Component {

	public void OnTick() {
		Logger.c_info("tick system is working");
	}

}
