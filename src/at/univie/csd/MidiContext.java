/**
 * 
 */
package at.univie.csd;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;

import org.nlogo.api.Context;

import at.univie.csd.midi.Conductor;
import at.univie.csd.midi.ConductorThread;

/**
 * @author Martin Dobiasch
 * This class takes care of the synchronized access to the midi device
 * it opens the midi device, and takes care of the Conductor
 */
public class MidiContext
{
	private boolean m_free;
	private boolean m_free_c;
	
	private MidiDevice m_dev;
	private Conductor m_cond; 
	private ConductorThread m_ct;
	
	/**
	 * Constructor
	 * @throws MidiUnavailableException when midi is not available
	 */
	public MidiContext() throws MidiUnavailableException
	{
		m_free= true;
		m_free_c= true;
		
		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
		m_dev = MidiSystem.getMidiDevice(infos[0]);
		
		m_cond= new Conductor();
		
		m_ct= null;
	}

	/**
	 * Waits for the Device to be free, and locks it
	 * @return Midi Device
	 */
	public synchronized MidiDevice getDevice()
	{
		// if would be an error !!!
		while( !m_free )
		{
			try
			{
				wait();
			}
			catch(InterruptedException e)
			{
			}
		}
		m_free= false;
		
		return m_dev;
	}
	
	/**
	 * Unlock the Device
	 */
	public synchronized void releaseDevice()
	{
		m_free= true;
		notify();
	}
	
	/**
	 * Waits for the Conductor to be free, and locks it
	 * @return reference to the Conductor instance
	 */
	public synchronized Conductor getConductor()
	{
		// if would be an error !!!
		while( !m_free_c )
		{
			try
			{
				wait();
			}
			catch(InterruptedException e)
			{
			}
		}
		m_free_c= false;
		return m_cond;
	}
	
	/**
	 * Unlock the Conductor for other threads
	 */
	public synchronized void releaseConductor()
	{
		m_free_c= true;
		notify();
	}
	
	public synchronized void startConductor(Context ctx)
	{
		if( m_ct != null ) //Conductor is running at the moment
		{ //stop it
			m_ct.interrupt();
		}
		m_ct= new ConductorThread(ctx);
		m_ct.start();
	}
	
	public synchronized void stopCondcutor()
	{
		if( m_ct != null )
		{
			m_ct.interrupt();
			m_ct= null;
		}
	}
}
