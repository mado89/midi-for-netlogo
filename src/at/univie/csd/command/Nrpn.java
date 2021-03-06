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
 * @author Martin
 *
 */
public class Nrpn extends MidiCommand
{
	public Nrpn()
	{
		super();
	}
	
	public Syntax getSyntax()
	{
		return Syntax.commandSyntax(new int[] { Syntax.NumberType(), Syntax.NumberType(),
				Syntax.NumberType(), Syntax.NumberType(), Syntax.NumberType()});
	}
	
	public void perform(Argument[] args, Context ctx) throws ExtensionException,
			LogoException
	{
		int channel;
		int lorpn;
		int hirpn;
		int lodata;
		int hidata;
		
		preAction();
		ShortMessage msg= new ShortMessage();
		try
		{
			channel= args[0].getIntValue();
			lorpn= args[1].getIntValue();
			hirpn= args[2].getIntValue();
			lodata= args[3].getIntValue();
			hidata= args[4].getIntValue();
		}
		catch(LogoException e)
		{
			throw new ExtensionException(e.getMessage());
		}
		
		try
		{
			msg.setMessage(ShortMessage.CONTROL_CHANGE, channel - 1, 99, (int) Conversion.Rescale(hirpn, 0, 1, 0, 127));
			msg.setMessage(ShortMessage.CONTROL_CHANGE, channel - 1, 98, (int) Conversion.Rescale(lorpn, 0, 1, 0, 127));
			msg.setMessage(ShortMessage.CONTROL_CHANGE, channel - 1,  6, (int) Conversion.Rescale(hidata, 0, 1, 0, 127));
			msg.setMessage(ShortMessage.CONTROL_CHANGE, channel - 1, 38, (int) Conversion.Rescale(lodata, 0, 1, 0, 127));
			msg.setMessage(ShortMessage.CONTROL_CHANGE, channel - 1, 99, 127);
			msg.setMessage(ShortMessage.CONTROL_CHANGE, channel - 1, 98, 127);
			
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
