/**
 * 
 */
package at.univie.csd.command;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.ShortMessage;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.Syntax;

import at.univie.csd.Conversion;
import at.univie.csd.MidiCommand;

/**
 * @author Martin Dobiasch
 * 
 */
public class Note extends MidiCommand
{
	
	public Note()
	{
	}
	
	public Syntax getSyntax()
	{
		return Syntax.commandSyntax(new int[] { Syntax.NumberType(),
				Syntax.NumberType(), Syntax.NumberType(), Syntax.NumberType() });
	}
	
	public void perform(Argument[] args, Context ctx) throws ExtensionException,
			LogoException
	{
		int channel;
		int note;
		double vol;
		int dur;
		
		preAction();
		ShortMessage msg= new ShortMessage();
		// msg.setMessage(ShortMessage.NOTE_ON,
		try
		{
			channel= args[0].getIntValue();
			note= args[1].getIntValue();
			vol= args[2].getDoubleValue();
			dur= args[3].getIntValue();
		}
		catch(LogoException e)
		{
			throw new ExtensionException(e.getMessage());
		}
		
		try
		{
			msg.setMessage(ShortMessage.NOTE_ON, channel - 1, note, (int) Conversion
					.Rescale(vol, 0, 1, 0, 127));
			m_recv.send(msg, -1);
		}
		catch(InvalidMidiDataException e)
		{
			throw new ExtensionException("Invalid Midi data " + e.getMessage());
		}
		finally
		{
			postAction();
		}
		
		postAction();
		try
		{
			Thread.sleep(dur);
			preAction();
			msg.setMessage(ShortMessage.NOTE_OFF, channel - 1, note, (int) Conversion
					.Rescale(vol, 0, 1, 0, 127));
			m_recv.send(msg, -1);
		}
		catch(InvalidMidiDataException e)
		{
			throw new ExtensionException("Invalid Midi data " + e.getMessage());
		}
		catch(InterruptedException e)
		{
			throw new ExtensionException("Interruped " + e.getMessage());
		}
		finally
		{
			postAction();
		}
		
		postAction();
	}
}
