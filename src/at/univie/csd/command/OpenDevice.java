package at.univie.csd.command;

import javax.sound.midi.MidiUnavailableException;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.Syntax;

import at.univie.csd.MidiCommand;
import at.univie.csd.MidiManager;

public class OpenDevice extends MidiCommand
{
	public OpenDevice()
	{
		super();
	}
	
	public Syntax getSyntax()
	{
		return Syntax.commandSyntax(new int[] { Syntax.TYPE_NUMBER });
	}
	
	public void perform(Argument[] args, Context ctx) throws ExtensionException,
			LogoException
	{
		int number;
		
		preAction();
		try
		{
			number= args[0].getIntValue();
			MidiManager.getMidiContext().setDevice(number);
		}
		catch(LogoException e)
		{
			throw new ExtensionException(e.getMessage());
		}
		catch (MidiUnavailableException e)
		{
			throw new ExtensionException(e.getMessage());
		}
		postAction();
	}
}
