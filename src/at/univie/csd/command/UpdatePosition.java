package at.univie.csd.command;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.ShortMessage;

import org.nlogo.agent.Turtle;
import org.nlogo.agent.World;
import org.nlogo.api.Agent;
import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.Syntax;

import at.univie.csd.Conversion;
import at.univie.csd.MidiCommand;

public class UpdatePosition extends MidiCommand
{
	public UpdatePosition()
	{
		super();
	}
	
	public Syntax getSyntax()
	{
		return Syntax.commandSyntax();
	}
	
	public void perform(Argument[] args, Context ctx) throws ExtensionException,
			LogoException
	{
		int channel;
		double tpan;
		int pan;
		//double tvol;
		//int vol;
		double texp;
		int exp;
		Agent agent;
		World world;
		Turtle t;
		double px, py;
		
		// MidiManager.debug("UpdatePosition start");
		
		agent= ctx.getAgent();
		
		channel= (int) agent.id();
		
		world= (World) agent.world();
		t= world.getTurtle(channel);
		
		//Debugging
		/*throw new ExtensionException("Update Position: \n" +
		"x: " + t.xcor() + "\n" +
		"y: " + t.ycor() + "\n" +
		"xmin: " + world.minPxcor() + "\n" +
		"xmax: " + world.maxPxcor() + "\n" +
		"ymin: " + world.minPycor() + "\n" +
		"ymax: " + world.maxPycor() );*/
		
		px= t.xcor();
		py= t.ycor();
		
		// for(int i= 0; i < t.getVariableCount(); i++)
		//try {
			//MidiManager.debug( "Update: " + t.getVariable(i) );
		//} catch (AgentException e1) {
		//	MidiManager.debug( "Exception: " + e1.getMessage());
		//	e1.printStackTrace();
		//}
		
		if( px > 0 )
			tpan= world.maxPxcor() / px;
		else if( px < 0 )
			tpan= ( world.minPxcor() / px ) * -1;
		else
			tpan= 0;
		
		/*if( py > 0 )
			tvol= world.maxPycor() / py;
		else if( py < 0 )
			tvol= ( world.minPycor() / py );
		else
			tvol= 1;*/
		
		if( py > 0 )
			texp= ( world.maxPycor() / py ) / 2 + 0.5;
		else if( py < 0 )
			texp= 0.5 - ( ( world.minPycor() / py ) / 2 );
		else
			texp= 0.5;
		
		pan= (int) Conversion.Rescale(tpan, -1, 1, 0, 127);
		//vol= (int) Conversion.Rescale(tvol, 0, 1, 0, 127);
		exp= (int) Conversion.Rescale(texp, 0, 1, 0, 127);
		
		ShortMessage msg1= new ShortMessage();
		ShortMessage msg2= new ShortMessage();
		
		try
		{
			msg1.setMessage(ShortMessage.CONTROL_CHANGE, channel, 10, pan);
			//msg2.setMessage(ShortMessage.CONTROL_CHANGE, channel, 7, vol);
			msg2.setMessage(ShortMessage.CONTROL_CHANGE, channel, 11, exp);
			
			// MidiManager.debug("UpdatePosition pre preAction");
			preAction();
			// MidiManager.debug("UpdatePosition post preAction");
			
			m_recv.send(msg1, -1);
			m_recv.send(msg2, -1);
			
			// MidiManager.debug("UpdatePosition pre postAction");
			postAction();
			// MidiManager.debug("UpdatePosition post postAction");
			
		}
		catch(InvalidMidiDataException e)
		{
			throw new ExtensionException("Invalid Midi data " + e.getMessage());
		}
		finally
		{
			postAction();
		}
		
		// MidiManager.debug("UpdatePosition done");
	}
}
