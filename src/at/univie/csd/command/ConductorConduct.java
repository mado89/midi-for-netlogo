package at.univie.csd.command;

import java.util.ArrayList;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultCommand;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.Syntax;

import at.univie.csd.MidiManager;
import at.univie.csd.midi.Conductor;
import at.univie.csd.midi.TimedEvent;

public class ConductorConduct extends DefaultCommand/*MidiCommand*/
{
	private class WorkerThread extends Thread
	{
		private Context ctx;
		private ArrayList<TimedEvent> events;
		
		public WorkerThread(Context ctx, ArrayList<TimedEvent> events)
		{
			this.ctx= ctx;
			this.events= events;
		}
		
		public void run()
		{
			TimedEvent ev;
			for(int i= 0; i < events.size(); i++)
			{
				ev= events.get(i);
				//throw new ExtensionException( ev.msg );
				
				try
				{
					//Runs a Command, and not waits for the command to terminate
					ctx.runCommand(ev.msg, false);
				}
				catch (ExtensionException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public Syntax getSyntax()
	{
		return Syntax.commandSyntax( new int[] {} ) ;
	}
	
	public void perform(Argument[] args, Context ctx) throws ExtensionException,
	LogoException
	{
		Conductor c= MidiManager.getMidiContext().getConductor();
		// TimedEvent ev;
		ArrayList<TimedEvent> l;
		// int i= 0;
		
		l= c.getDueEvents();
		
		MidiManager.getMidiContext().releaseConductor();
		
		new WorkerThread(ctx,l).run();
		// As we are not 'doing' commands we don't need to get the 
		//   MidiContext here
		// preAction();
		// MidiManager.debug("Conductor conduct start");
		/*
		for(i= 0; i < l.size(); i++)
		{
			ev= l.get(i);
			//throw new ExtensionException( ev.msg );
			
			//Runs a Command, and not waits for the command to terminate
			ctx.runCommand(ev.msg, false);
			// ctx.runCommand(ev.msg, true);
			
			//m_recv.send(ev.msg, ev.abstime);
		}
		*/
		// postAction();
		// MidiManager.debug("Conductor conduct end");
		// MidiManager.getMidiContext().releaseConductor();
		// MidiManager.debug("Conductor conduct MidiContext released");
	}
}