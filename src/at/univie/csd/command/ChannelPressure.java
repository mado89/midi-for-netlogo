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

public class ChannelPressure extends MidiCommand
{
	public ChannelPressure()
	{
		super();
	}
	
	public Syntax getSyntax()
	{
		return Syntax.commandSyntax(new int[] { Syntax.NumberType(), Syntax.NumberType(),
				Syntax.NumberType() });
	}
	
	public void perform(Argument[] args, Context ctx) throws ExtensionException,
			LogoException
	{
		int channel;
		double rval;
		
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
		
		try
		{
			msg.setMessage(ShortMessage.CHANNEL_PRESSURE, channel - 1, (int) Conversion.Rescale(rval, 0, 1, 0, 127), 0);
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
