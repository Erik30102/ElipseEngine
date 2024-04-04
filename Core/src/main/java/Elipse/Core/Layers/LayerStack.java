package Elipse.Core.Layers;

import java.util.ArrayList;
import java.util.List;

import Elipse.Core.EventSystem.Events.Event;

public class LayerStack {
	private List<Layer> layers = new ArrayList<Layer>();

	private boolean ImGui = false;
	private ImGuiLayer ImLayer;

	public LayerStack(boolean ImGui) {
		this.ImGui = ImGui;

		if (ImGui) {
			ImLayer = new ImGuiLayer();
			ImLayer.OnAttach();
		}
	}

	public void addLayer(Layer layer) {
		layers.add(layer);
	}

	public void OnUpdate(double dt) {
		for (Layer layer : this.layers) {
			layer.OnUpdate(dt);
		}
	}

	public void OnAttach() {
		for (Layer layer : this.layers) {
			layer.OnAttach();
		}
	}

	public void OnImguiRender() {
		if (!this.ImGui)
			return;

		ImLayer.Begin();

		for (Layer layer : this.layers) {
			layer.OnImguiRender();
		}

		ImLayer.End();
	}

	public void OnEvent(Event e) {
		if (this.ImGui)
			ImLayer.OnEvent(e);

		for (Layer layer : this.layers) {
			layer.OnEvent(e);
			if (e.handled)
				break;
		}
	}
}
