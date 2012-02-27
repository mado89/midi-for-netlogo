/**
 * 
 */
package at.univie.csd.midi;

import java.util.ArrayList;

import org.nlogo.api.Context;
import org.nlogo.api.ExtensionException;

import at.univie.csd.MidiManager;

/**
 * @author Martin Dobiasch
 *
 */
public class ConductorThread extends Thread
{
	private Context m_ctx;
	private final Object[] EMPTY_ARRAY= new Object[0];
	
	public ConductorThread(Context ctx)
	{
		super();
		m_ctx= ctx;
		// MidiManager.debug("CT init");
		
		this.setPriority(MIN_PRIORITY);
	}
	
	public void run()
	{
		Conductor c;
		try
		{
			// MidiManager.debug("CT1");
			while( true )
			{
				// MidiManager.debug("CT2");
				c= MidiManager.getMidiContext().getConductor();
				TimedEvent ev;
				ArrayList<TimedEvent> l;
				int i= 0;
				
				// MidiManager.debug("CT3");
				l= c.getDueEvents();
				// MidiManager.debug("CT4");
				
				// MidiManager.debug("Conductor conduct release Conductor " + l.size());
				MidiManager.getMidiContext().releaseConductor();
				
				// MidiManager.debug("Conductor conduct start");
				
				for(i= 0; i < l.size(); i++)
				{
					ev= l.get(i);
					//throw new ExtensionException( ev.msg );
					
					//Runs a Command, and not waits for the command to terminate
					// m_ctx.runCommand(ev.msg, false);
					ev.cmd.perform(m_ctx, EMPTY_ARRAY);
					// ctx.runCommand(ev.msg, true);
					
					//m_recv.send(ev.msg, ev.abstime);
				}
				
				// MidiManager.debug("Conductor conduct end");
				
				Thread.sleep(100);
				Thread.yield();
			}
		}
		catch( ExtensionException e)
		{
			//TODO
			/* MidiManager.debug("ExtensionException in CT");
			MidiManager.debug(e.getMessage()); */
		}
		catch (InterruptedException e)
		{
			//TODO
			/* MidiManager.debug("InterruptedException in CT");
			MidiManager.debug(e.getMessage()); */
		}
	}
}
