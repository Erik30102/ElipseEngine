package Elipse.Core.EventSystem;

import Elipse.Core.EventSystem.Events.Event;

/**
 * Interface for registering a class as a event listener
 */
public interface IEventListener {
    /**
     * the function every event is send to if registerd in the event system
     * 
     * @param event Abstract class of event get event type with
     * 
     *              <pre>
     *              event.GetEventType();
     *              </pre>
     */
    public void OnEvent(Event event);
}
