package ElipseEditor;

import Elipse.Core.Application;

public class Editor extends Application {

	public Editor() {
		super(900, 600, "Elipse Editor", true);
	}

	public static void main(String[] args) {
		// TODO: while true for restarting when script jar changes
		new Editor().Run();
	}

	@Override
	public void OnInitialize() {
		this.PushLayer(new EditorLayer());
	}
}
