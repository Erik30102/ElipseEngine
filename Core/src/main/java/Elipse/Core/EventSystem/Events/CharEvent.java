package Elipse.Core.EventSystem.Events;

public class CharEvent extends Event {

	private int keycode;

	public CharEvent(int keycode) {
		this.keycode = keycode;
	}

	/**
	 * @return Key Code for the given KeyPressed Event in EL.KEY_XX
	 */
	public int GetKeyCode() {
		return this.keycode;
	}

	@Override
	public EventType GetEventType() {
		return EventType.CharEvent;
	}

	@Override
	public String GetName() {
		return "CharEvent";
	}

	@Override
	public String toString() {
		return "CharEvent for KeyCode: " + keycode;
	}
}
