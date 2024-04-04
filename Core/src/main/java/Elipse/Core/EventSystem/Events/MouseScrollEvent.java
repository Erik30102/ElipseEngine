package Elipse.Core.EventSystem.Events;

public class MouseScrollEvent extends Event {

	private double OffsetX, OffsetY;

	public MouseScrollEvent(double OffsetX, double OffsetY) {
		this.OffsetX = OffsetX;
		this.OffsetY = OffsetY;
	}

	/**
	 * @return distances scrolled in x direction
	 */
	public double GetOffsetX() {
		return OffsetX;
	}

	/**
	 * @return distances scrolled in y direction
	 */
	public double GetOffsetY() {
		return OffsetY;
	}

	@Override
	public EventType GetEventType() {
		return EventType.MouseScrolled;
	}

	@Override
	public String GetName() {
		return "Mouse Scroll Event";
	}

}
