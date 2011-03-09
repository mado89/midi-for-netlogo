/**
 * 
 */
package at.univie.csd.midi;

/**
 * @author Martin Dobiasch
 * Datatype for Events
 */
public class TimedEvent
{
	/**
	 * Timecode of the event
	 */
	public long   abstime;
	/**
	 * Action to be performed
	 */
	public String msg;
}
