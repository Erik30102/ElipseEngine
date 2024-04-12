package ElipseRuntime;

import Elipse.Core.Application;

public class Runtime extends Application {
	public static void main(String[] args) {
		new Runtime().Run();
	}

	public Runtime() {
		super(900, 640, "Elipse Runtime", false);
	}

	@Override
	public void OnInitialize() {
		this.PushLayer(new RuntimeLayer());
	}
}
