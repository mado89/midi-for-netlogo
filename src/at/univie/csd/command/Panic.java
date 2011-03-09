package at.univie.csd.command;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.ShortMessage;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.Syntax;

import at.univie.csd.MidiCommand;

public class Panic extends MidiCommand
{
	public Panic()
	{
		super();
	}
	
	public Syntax getSyntax()
	{
		return Syntax.commandSyntax(new int[] { });
	}
	
	public void perform(Argument[] args, Context ctx) throws ExtensionException,
			LogoException
	{
		int channel;
		
		preAction();
		ShortMessage msg= new ShortMessage();

		try
		{
			for(channel= 0; channel < 16; channel++)
			{
				msg.setMessage(ShortMessage.CONTROL_CHANGE, channel, 121, 0);
				m_recv.send(msg, -1);
				msg.setMessage(ShortMessage.CONTROL_CHANGE, channel, 123,0);
				m_recv.send(msg, -1);
			}
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
	}
}
