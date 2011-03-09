/**
 * 
 */
package at.univie.csd.command;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultCommand;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.Syntax;

import at.univie.csd.MidiManager;
import at.univie.csd.midi.Conductor;

/**
 * @author Martin Dobiasch
 *
 */
public class ConductorPlaymodeEndless extends DefaultCommand
{
	public Syntax getSyntax()
	{
		return Syntax.commandSyntax( new int[] { } ) ;
	}
	
	public void perform(Argument[] args, Context ctx) throws ExtensionException,
	LogoException
	{
		Conductor c= MidiManager.getMidiContext().getConductor();
		
		c.setPlayMode( Conductor.ENDLESS );
		
		MidiManager.getMidiContext().releaseConductor();
	}
}
