package Elipse.Core.Layers;

import org.lwjgl.glfw.GLFW;

import Elipse.Core.Application;
import Elipse.Core.EventSystem.Events.CharEvent;
import Elipse.Core.EventSystem.Events.Event;
import Elipse.Core.EventSystem.Events.KeyPressedEvent;
import Elipse.Core.EventSystem.Events.KeyReleasedEvent;
import Elipse.Core.EventSystem.Events.MouseButtonPressedEvent;
import Elipse.Core.EventSystem.Events.MouseMovedEvent;
import Elipse.Core.EventSystem.Events.MouseScrollEvent;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.extension.imguizmo.ImGuizmo;
import imgui.flag.ImGuiBackendFlags;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiKey;
import imgui.flag.ImGuiMouseCursor;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.type.ImBoolean;

public class ImGuiLayer extends Layer {

	@Override
	public void OnEvent(Event event) {
		ImGuiIO io = ImGui.getIO();

		switch (event.GetEventType()) {
			case KeyPressed:
				io.setKeysDown(((KeyPressedEvent) event).GetKeyCode(), true);
				break;
			case KeyReleased:
				io.setKeysDown(((KeyReleasedEvent) event).GetKeyCode(), false);
				break;
			case CharEvent:
				io.addInputCharacter(((CharEvent) event).GetKeyCode());
				break;
			case MouseMoved:
				io.setMousePos((float) ((MouseMovedEvent) event).getX(), (float) ((MouseMovedEvent) event).getY());
				break;
			case MouseButtonPressed:
				final boolean[] mouseDown = new boolean[5];

				int button = ((MouseButtonPressedEvent) event).GetMouseCode();

				mouseDown[0] = button == GLFW.GLFW_MOUSE_BUTTON_1;
				mouseDown[1] = button == GLFW.GLFW_MOUSE_BUTTON_2;
				mouseDown[2] = button == GLFW.GLFW_MOUSE_BUTTON_3;
				mouseDown[3] = button == GLFW.GLFW_MOUSE_BUTTON_4;
				mouseDown[4] = button == GLFW.GLFW_MOUSE_BUTTON_5;

				io.setMouseDown(mouseDown);

				if (!io.getWantCaptureMouse() && mouseDown[1]) {
					ImGui.setWindowFocus(null);
				}
				break;
			case MouseButtonReleased:
				final boolean[] _mouseDown = new boolean[5];

				_mouseDown[0] = false;
				_mouseDown[1] = false;
				_mouseDown[2] = false;
				_mouseDown[3] = false;
				_mouseDown[4] = false;

				io.setMouseDown(_mouseDown);
				break;
			case MouseScrolled:
				io.setMouseWheel(io.getMouseWheel() + (float) ((MouseScrollEvent) event).GetOffsetX());
				io.setMouseWheelH(io.getMouseWheelH() + (float) ((MouseScrollEvent) event).GetOffsetY());
				break;
			default:
				break;
		}
	}

	private void setupDockspace() {
		int windowFlags = ImGuiWindowFlags.MenuBar | ImGuiWindowFlags.NoDocking;

		ImGui.setNextWindowPos(0.0f, 0.0f, ImGuiCond.Always);
		ImGui.setNextWindowSize(Application.getApplication().GetWindow().GetWidth(),
				Application.getApplication().GetWindow().GetHeight());
		ImGui.pushStyleVar(ImGuiStyleVar.WindowRounding, 0.0f);
		ImGui.pushStyleVar(ImGuiStyleVar.WindowBorderSize, 0.0f);
		windowFlags |= ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoCollapse |
				ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoMove |
				ImGuiWindowFlags.NoBringToFrontOnFocus | ImGuiWindowFlags.NoNavFocus;

		ImGui.begin("Dockspace Demo", new ImBoolean(true), windowFlags);
		ImGui.popStyleVar(2);

		ImGui.dockSpace(ImGui.getID("Dockspace"));
	}

	private float m_time = 0;

	@Override
	public void OnUpdate(double dt) {

	}

	/**
	 * Begin of the ImGui Renderer put all the imgui commands in here
	 */
	public void Begin() {
		ImGuiIO io = ImGui.getIO();
		Application app = Application.getApplication();
		io.setDisplaySize(app.GetWindow().GetWidth(), app.GetWindow().GetHeight());

		float time = (float) GLFW.glfwGetTime();

		io.setDeltaTime(time - m_time);
		m_time = time;

		ImGui.newFrame();
		ImGuizmo.beginFrame();
		setupDockspace();
	}

	/**
	 * End of ImGui Renderer
	 */
	public void End() {
		ImGui.end();
		ImGui.render();

		imGuiGl3.renderDrawData(ImGui.getDrawData());
	}

	private final long[] mouseCursors = new long[ImGuiMouseCursor.COUNT];
	private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();

	@Override
	public void OnAttach() {
		ImGui.createContext();

		final ImGuiIO io = ImGui.getIO();

		io.setIniFilename("imgui.ini");
		io.setConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);
		io.setConfigFlags(ImGuiConfigFlags.DockingEnable);
		io.setBackendFlags(ImGuiBackendFlags.HasMouseCursors);
		io.setBackendPlatformName("imgui_java_impl_glfw");

		final int[] keyMap = new int[ImGuiKey.COUNT];
		keyMap[ImGuiKey.Tab] = GLFW.GLFW_KEY_TAB;
		keyMap[ImGuiKey.LeftArrow] = GLFW.GLFW_KEY_LEFT;
		keyMap[ImGuiKey.RightArrow] = GLFW.GLFW_KEY_RIGHT;
		keyMap[ImGuiKey.UpArrow] = GLFW.GLFW_KEY_UP;
		keyMap[ImGuiKey.DownArrow] = GLFW.GLFW_KEY_DOWN;
		keyMap[ImGuiKey.PageUp] = GLFW.GLFW_KEY_PAGE_UP;
		keyMap[ImGuiKey.PageDown] = GLFW.GLFW_KEY_PAGE_DOWN;
		keyMap[ImGuiKey.Home] = GLFW.GLFW_KEY_HOME;
		keyMap[ImGuiKey.End] = GLFW.GLFW_KEY_END;
		keyMap[ImGuiKey.Insert] = GLFW.GLFW_KEY_INSERT;
		keyMap[ImGuiKey.Delete] = GLFW.GLFW_KEY_DELETE;
		keyMap[ImGuiKey.Backspace] = GLFW.GLFW_KEY_BACKSPACE;
		keyMap[ImGuiKey.Space] = GLFW.GLFW_KEY_SPACE;
		keyMap[ImGuiKey.Enter] = GLFW.GLFW_KEY_ENTER;
		keyMap[ImGuiKey.Escape] = GLFW.GLFW_KEY_ESCAPE;
		keyMap[ImGuiKey.KeyPadEnter] = GLFW.GLFW_KEY_KP_ENTER;
		keyMap[ImGuiKey.A] = GLFW.GLFW_KEY_A;
		keyMap[ImGuiKey.C] = GLFW.GLFW_KEY_C;
		keyMap[ImGuiKey.V] = GLFW.GLFW_KEY_V;
		keyMap[ImGuiKey.X] = GLFW.GLFW_KEY_X;
		keyMap[ImGuiKey.Y] = GLFW.GLFW_KEY_Y;
		keyMap[ImGuiKey.Z] = GLFW.GLFW_KEY_Z;
		io.setKeyMap(keyMap);

		mouseCursors[ImGuiMouseCursor.Arrow] = GLFW.glfwCreateStandardCursor(GLFW.GLFW_ARROW_CURSOR);
		mouseCursors[ImGuiMouseCursor.TextInput] = GLFW.glfwCreateStandardCursor(GLFW.GLFW_IBEAM_CURSOR);
		mouseCursors[ImGuiMouseCursor.ResizeAll] = GLFW.glfwCreateStandardCursor(GLFW.GLFW_ARROW_CURSOR);
		mouseCursors[ImGuiMouseCursor.ResizeNS] = GLFW.glfwCreateStandardCursor(GLFW.GLFW_VRESIZE_CURSOR);
		mouseCursors[ImGuiMouseCursor.ResizeEW] = GLFW.glfwCreateStandardCursor(GLFW.GLFW_HRESIZE_CURSOR);
		mouseCursors[ImGuiMouseCursor.ResizeNESW] = GLFW.glfwCreateStandardCursor(GLFW.GLFW_ARROW_CURSOR);
		mouseCursors[ImGuiMouseCursor.ResizeNWSE] = GLFW.glfwCreateStandardCursor(GLFW.GLFW_ARROW_CURSOR);
		mouseCursors[ImGuiMouseCursor.Hand] = GLFW.glfwCreateStandardCursor(GLFW.GLFW_HAND_CURSOR);
		mouseCursors[ImGuiMouseCursor.NotAllowed] = GLFW.glfwCreateStandardCursor(GLFW.GLFW_ARROW_CURSOR);

		imGuiGl3.init("#version 330 core");
	}

}
