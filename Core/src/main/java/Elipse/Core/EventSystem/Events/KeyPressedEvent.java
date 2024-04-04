package Elipse.Core.EventSystem.Events;

public class KeyPressedEvent extends Event {
    private int keyCode;
    private boolean reapeat;

    public KeyPressedEvent(int keyCode, boolean reapeat) {
        this.keyCode = keyCode;
        this.reapeat = reapeat;
    }

    /**
     * @return Key Code for the given keyboard key pressed
     */
    public int GetKeyCode() {
        return this.keyCode;
    }

    /**
     * @return Event Type
     */
    @Override
    public EventType GetEventType() {
        return EventType.KeyPressed;
    }

    /**
     * @return Name of the Event !!DEBUG ONLY!!
     */
    @Override
    public String GetName() {
        return "KeyPressed Event";
    }

    public boolean isKeyRepeat() {
        return this.reapeat;
    }

    @Override
    public String toString() {
        return "KeyPressedEvent for KeyCode: " + keyCode;
    }
}
