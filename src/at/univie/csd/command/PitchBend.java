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
import at.univie.csd.TwoByte;

/**
 * @author Martin Dobiasch
 *
 */
public class PitchBend extends MidiCommand
{
	public PitchBend()
	{
		super();
	}
	
	public Syntax getSyntax()
	{
		return Syntax.commandSyntax(new int[] { Syntax.TYPE_NUMBER,
				Syntax.TYPE_NUMBER });
	}
	
	public void perform(Argument[] args, Context ctx) throws ExtensionException,
			LogoException
	{
		int channel;
		double rval;
		TwoByte MyTwobyte;;
		
		preAction();
		ShortMessage msg= new ShortMessage();
		try
		{
			channel= args[0].getIntValue();
			rval= args[1].getDoubleValue();
		}
		catch(LogoException e)
		{
			throw new ExtensionException(e.getMessage());
		}
		
		MyTwobyte = Conversion.TwoByteMidiRescale(rval, -1, 1);
    
		try
		{
			msg.setMessage(ShortMessage.PITCH_BEND, channel - 1, MyTwobyte.lsb, MyTwobyte.msb);
		}
		catch(InvalidMidiDataException e)
		{
			throw new ExtensionException("Invalid Midi data " + e.getMessage());
		}
		finally
		{
			postAction();
		}
		
		m_recv.send(msg, -1);
		
		postAction();
	}
}
