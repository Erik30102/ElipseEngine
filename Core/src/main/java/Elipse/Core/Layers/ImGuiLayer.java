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
import imgui.ImGuiStyle;
import imgui.extension.imguizmo.ImGuizmo;
import imgui.flag.ImGuiBackendFlags;
import imgui.flag.ImGuiCol;
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

		ImGuiStyle style = ImGui.getStyle();

		style.setAlpha(1.0f);
		style.setDisabledAlpha(0.5f);
		style.setWindowPadding(12.0f, 12.0f);
		style.setWindowRounding(0.0f);
		style.setWindowBorderSize(0.0f);
		style.setWindowMinSize(20.0f, 32.0f);
		style.setWindowTitleAlign(0.0f, 0.5f);
		style.setChildRounding(0.0f);
		style.setChildBorderSize(1.0f);
		style.setPopupRounding(0.0f);
		style.setPopupBorderSize(1.0f);
		style.setFramePadding(4.0f, 2.0f);
		style.setFrameRounding(1.0f);
		style.setFrameBorderSize(0.0f);
		style.setItemSpacing(8.0f, 5.0f);
		style.setItemInnerSpacing(6.0f, 3.700000047683716f);
		style.setCellPadding(4.199999809265137f, 2.0f);
		style.setIndentSpacing(20.0f);
		style.setColumnsMinSpacing(5.800000190734863f);
		style.setScrollbarSize(10.30000019073486f);
		style.setScrollbarRounding(20.0f);
		style.setGrabMinSize(10.89999961853027f);
		style.setGrabRounding(6.5f);
		style.setTabRounding(1.0f);
		style.setTabBorderSize(0.0f);
		style.setTabMinWidthForCloseButton(0.0f);
		style.setButtonTextAlign(0.5f, 0.5f);
		style.setSelectableTextAlign(0.0f, 0.0f);

		style.setColor(ImGuiCol.Text, 0.949f, 0.956f, 0.976f, 1.0f);
		style.setColor(ImGuiCol.TextDisabled, 0.357f, 0.42f, 0.467f, 1.0f);
		style.setColor(ImGuiCol.WindowBg, 0.109f, 0.149f, 0.169f, 1.0f);
		style.setColor(ImGuiCol.ChildBg, 0.149f, 0.176f, 0.22f, 1.0f);
		style.setColor(ImGuiCol.PopupBg, 0.078f, 0.078f, 0.078f, 0.94f);
		style.setColor(ImGuiCol.Border, 0.078f, 0.098f, 0.117f, 1.0f);
		style.setColor(ImGuiCol.BorderShadow, 0.0f, 0.0f, 0.0f, 0.0f);
		style.setColor(ImGuiCol.FrameBg, 0.2f, 0.247f, 0.286f, 1.0f);
		style.setColor(ImGuiCol.FrameBgHovered, 0.117f, 0.2f, 0.278f, 1.0f);
		style.setColor(ImGuiCol.FrameBgActive, 0.086f, 0.118f, 0.137f, 1.0f);
		style.setColor(ImGuiCol.TitleBg, 0.086f, 0.118f, 0.137f, 0.65f);
		style.setColor(ImGuiCol.TitleBgActive, 0.078f, 0.098f, 0.117f, 1.0f);
		style.setColor(ImGuiCol.TitleBgCollapsed, 0.0f, 0.0f, 0.0f, 0.51f);
		style.setColor(ImGuiCol.MenuBarBg, 0.149f, 0.176f, 0.22f, 1.0f);
		style.setColor(ImGuiCol.ScrollbarBg, 0.019f, 0.019f, 0.019f, 0.39f);
		style.setColor(ImGuiCol.ScrollbarGrab, 0.2f, 0.247f, 0.286f, 1.0f);
		style.setColor(ImGuiCol.ScrollbarGrabHovered, 0.176f, 0.22f, 0.247f, 1.0f);
		style.setColor(ImGuiCol.ScrollbarGrabActive, 0.086f, 0.208f, 0.31f, 1.0f);
		style.setColor(ImGuiCol.CheckMark, 0.278f, 0.557f, 1.0f, 1.0f);
		style.setColor(ImGuiCol.SliderGrab, 0.278f, 0.557f, 1.0f, 1.0f);
		style.setColor(ImGuiCol.SliderGrabActive, 0.369f, 0.608f, 1.0f, 1.0f);
		style.setColor(ImGuiCol.Button, 0.2f, 0.247f, 0.286f, 1.0f);
		style.setColor(ImGuiCol.ButtonHovered, 0.278f, 0.557f, 1.0f, 1.0f);
		style.setColor(ImGuiCol.ButtonActive, 0.058f, 0.529f, 0.976f, 1.0f);
		style.setColor(ImGuiCol.Header, 0.2f, 0.247f, 0.286f, 0.55f);
		style.setColor(ImGuiCol.HeaderHovered, 0.259f, 0.588f, 0.976f, 0.8f);
		style.setColor(ImGuiCol.HeaderActive, 0.259f, 0.588f, 0.976f, 1.0f);
		style.setColor(ImGuiCol.Separator, 0.2f, 0.247f, 0.286f, 1.0f);
		style.setColor(ImGuiCol.SeparatorHovered, 0.098f, 0.4f, 0.749f, 0.78f);
		style.setColor(ImGuiCol.SeparatorActive, 0.098f, 0.4f, 0.749f, 1.0f);
		style.setColor(ImGuiCol.ResizeGrip, 0.259f, 0.588f, 0.976f, 0.25f);
		style.setColor(ImGuiCol.ResizeGripHovered, 0.259f, 0.588f, 0.976f, 0.67f);
		style.setColor(ImGuiCol.ResizeGripActive, 0.259f, 0.588f, 0.976f, 0.95f);
		style.setColor(ImGuiCol.Tab, 0.109f, 0.149f, 0.169f, 1.0f);
		style.setColor(ImGuiCol.TabHovered, 0.259f, 0.588f, 0.976f, 0.8f);
		style.setColor(ImGuiCol.TabActive, 0.2f, 0.247f, 0.286f, 1.0f);
		style.setColor(ImGuiCol.TabUnfocused, 0.109f, 0.149f, 0.169f, 1.0f);
		style.setColor(ImGuiCol.TabUnfocusedActive, 0.109f, 0.149f, 0.169f, 1.0f);
		style.setColor(ImGuiCol.PlotLines, 0.607f, 0.607f, 0.607f, 1.0f);
		style.setColor(ImGuiCol.PlotLinesHovered, 1.0f, 0.427f, 0.349f, 1.0f);
		style.setColor(ImGuiCol.PlotHistogram, 0.898f, 0.698f, 0.0f, 1.0f);
		style.setColor(ImGuiCol.PlotHistogramHovered, 1.0f, 0.6f, 0.0f, 1.0f);
		style.setColor(ImGuiCol.TableHeaderBg, 0.188f, 0.188f, 0.2f, 1.0f);
		style.setColor(ImGuiCol.TableBorderStrong, 0.31f, 0.31f, 0.349f, 1.0f);
		style.setColor(ImGuiCol.TableBorderLight, 0.227f, 0.227f, 0.247f, 1.0f);
		style.setColor(ImGuiCol.TableRowBg, 0.0f, 0.0f, 0.0f, 1f);

	}

}
