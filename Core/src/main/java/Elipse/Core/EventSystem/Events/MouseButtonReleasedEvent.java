package Elipse.Core.EventSystem.Events;

public class MouseButtonReleasedEvent extends Event {
	private int mouseCode;

	public MouseButtonReleasedEvent(int mouseCode) {
		this.mouseCode = mouseCode;
	}

	/**
	 * @return Key Code for the given mouse key released
	 */
	public int GetMouseCode() {
		return this.mouseCode;
	}

	@Override
	public EventType GetEventType() {
		return EventType.MouseButtonReleased;
	}

	@Override
	public String GetName() {
		return "MouseButtonReleasedEvent";
	}

}
