package Elipse.Core.EventSystem.Events;

public class MouseMovedEvent extends Event {

	private double x, y;

	public MouseMovedEvent(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return the x coordinate of the mouse
	 */
	public double getX() {
		return x;
	}

	/**
	 * @return the y coordinate of the mouse
	 */
	public double getY() {
		return y;
	}

	@Override
	public EventType GetEventType() {
		return EventType.MouseMoved;
	}

	@Override
	public String GetName() {
		return "MouseMovedEvent";
	}

}
