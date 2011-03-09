/**
 * 
 */
package at.univie.csd.midi;

import java.util.ArrayList;

import org.nlogo.api.ExtensionException;


/**
 * @author Martin Dobiasch
 * 
 */
public class Conductor
{
	public static final int NORMAL=  1;
	public static final int ENDLESS= 2;
	
	private Sheet[] sheets;
	private long[]  offs;
	private long starttime;
	private int playmode;
	
	public Conductor()
	{
		sheets= new Sheet[16];
		offs= new long[16];
		
		playmode= NORMAL;
	}
	
	/**
	 * Provide access to the sheets of the conductor
	 * @return sheets
	 */
	public Sheet[] getSheets()
	{
		return sheets;
	}
	
	/**
	 * Starts the conducting
	 */
	public synchronized void start()
	{
		starttime= System.currentTimeMillis();

		for(int i= 0; i < 16; i++)
			offs[i]= 0;
	}
	
	/**
	 * How much time has elapsed since start() had been performed
	 * @return elapsed time since start
	 */
	public synchronized long elapsedTime()
	{
		if( starttime == 0 )
			return 0;
		return System.currentTimeMillis() - starttime;
	}

	/**
	 * Sets everything to start
	 * starttime, and sheets are reset to start
	 */
	public synchronized void resettime()
	{
		starttime= 0;
		
		for(int i= 0; i < 16; i++)
			offs[i]= 0;
	}

	/**
	 * Remove all sheets
	 */
	public void clearSheets()
	{
		for(int i= 0; i < 16; i++)
		{
			sheets[i]= null;
			offs[i]= 0;
		}
	}

	/**
	 * Set all sheets to start
	 */
	public void resetSheets()
	{
		int i;
		
		for(i= 0; i < 16; i++)
		{
			if( sheets[i] != null )
			{
				sheets[i].reset();
				offs[i]= 0;;
				
			}
		}
	}

	/**
	 * Set Playmode
	 * @param mode PlayMode
	 */
	public void setPlayMode(int mode)
	{
		playmode= mode;
	}

	/**
	 * Returns all events which are due.
	 * Use this function after start() has been performed
	 * @return due events in their order of sheets and occurence
	 * @throws ExtensionException 
	 */
	public ArrayList<TimedEvent> getDueEvents() throws ExtensionException
	{
		long currTime;
		int i;
		ArrayList<TimedEvent> l= new ArrayList<TimedEvent>();
		TimedEvent ev;
		
		if( this.elapsedTime() == 0 )
		{
			this.start();
			currTime= 0;
		}
		else
			currTime= this.elapsedTime();
		
		for(i= 0; i < 16; i++)
		{
			if( sheets[i] != null )
			{
				ev= sheets[i].getCurrEvent();
				if( ev != null )
				{
						//throw new ExtensionException(ev.abstime + " " + currTime );
						if( ( offs[i] + ev.abstime ) <= currTime )
						{
							//send
							l.add(ev);
							sheets[i].NextEvent();
						}
				}
				else if( ( this.playmode & ENDLESS ) != 0)
				{
					sheets[i].reset();
					offs[i]+= sheets[i].getDuration();
				}
			}
		}
		return l;
	}
	
}
