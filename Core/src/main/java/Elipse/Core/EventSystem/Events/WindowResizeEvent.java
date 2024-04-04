package Elipse.Core.EventSystem.Events;

public class WindowResizeEvent extends Event {

	private int width, height;

	public WindowResizeEvent(int width, int height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * @return The width of the window
	 */
	public int GetWidth() {
		return this.width;
	}

	/**
	 * @return The height of the window
	 */
	public int GetHeight() {
		return this.height;
	}

	@Override
	public EventType GetEventType() {
		return EventType.WindowResize;
	}

	@Override
	public String GetName() {
		return "WindowResize";
	}

	@Override
	public String toString() {
		return "WindowResizeEvent: " + width + ":" + height;
	}
}
