package ElipseEditor;

import Elipse.Core.Application;

public class Editor extends Application {

	private static boolean ShouldBeRunning = true;

	public Editor() {
		super(900, 600, "Elipse Editor", true);
	}

	public static void main(String[] args) {
		while (ShouldBeRunning) {
			new Editor().Run();
		}
	}

	@Override
	public void OnInitialize() {
		this.PushLayer(new EditorLayer());
	}

	public static void SetShouldBeRunning(boolean shouldBeRunning) {
		ShouldBeRunning = shouldBeRunning;
	}
}
