package at.univie.csd;
/**
 * @author Martin Dobiasch 
 */

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Synthesizer;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultCommand;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;

/**
 * @author Martin Dobiasch
 * Class for Commands for NetLogo using Midi
 * every Command before accessing the Midi-Device has to call preAction before
 * sending Commands to the device. After all Commands have been sent to the 
 * device, postAction has to be called to unlock the Midi-Device again
 */
public class MidiCommand extends DefaultCommand
{
	private static MidiDevice m_dev;
	private static Synthesizer m_synth;
	protected static Receiver m_recv;
	
	
	public MidiCommand()
	{
		
	}
	
	/**
	 * not used, has to be overwritten by of subclasses
	 */
	public void perform(Argument[] args, Context ctx) throws ExtensionException,
			LogoException
	{
		
	}
	
	/**
	 * Wait for the MidiContext, and lock it
	 * @throws ExtensionException raised when Midi is unavailable
	 */
	protected void preAction() throws ExtensionException
	{
		try
		{
			m_dev= MidiManager.getMidiContext().getDevice();
			if( !m_dev.isOpen() )
				m_dev.open();
			if( m_synth == null )
				m_synth = MidiSystem.getSynthesizer();
			if( !m_synth.isOpen() )
				m_synth.open();
			if( m_recv == null )
				m_recv = m_synth.getReceiver();
		}
		catch( MidiUnavailableException e)
		{
			throw new ExtensionException("Midi not available " + e.getMessage());
		}
		finally
		{
			MidiManager.getMidiContext().releaseDevice();
		}
	}
	
	/**
	 * Release the MidiContext
	 */
	protected void postAction()
	{
		//m_recv.close();
		//m_synth.close();
		//m_dev.close();
		MidiManager.getMidiContext().releaseDevice();
	}
}
