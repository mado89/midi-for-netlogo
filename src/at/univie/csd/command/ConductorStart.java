package at.univie.csd.command;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultCommand;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.Syntax;

import at.univie.csd.MidiContext;
import at.univie.csd.MidiManager;

public class ConductorStart extends DefaultCommand/*MidiCommand*/
{
	public Syntax getSyntax()
	{
		return Syntax.commandSyntax( new int[] {} ) ;
	}
	
	public void perform(Argument[] args, Context ctx) throws ExtensionException,
	LogoException
	{
		MidiContext mctx;
		
		MidiManager.debug("conductor.start start");
		
		mctx= MidiManager.getMidiContext();
		
		mctx.getConductor().start();
		mctx.releaseConductor();
		
		mctx.startConductor(ctx);
		
		MidiManager.debug("conductor.start end");
	}
}
