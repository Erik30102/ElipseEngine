package Elipse.Core.EventSystem.Events;

public class MouseButtonPressedEvent extends Event {
	private int mouseCode;

	public MouseButtonPressedEvent(int mouseCode) {
		this.mouseCode = mouseCode;
	}

	/**
	 * @return Key Code for the given mouse key pressed
	 */
	public int GetMouseCode() {
		return this.mouseCode;
	}

	@Override
	public EventType GetEventType() {
		return EventType.MouseButtonPressed;
	}

	@Override
	public String GetName() {
		return "MouseButtonPressedEvent";
	}

}
