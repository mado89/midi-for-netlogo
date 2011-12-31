package at.univie.csd.midi;

import java.util.ArrayList;
import java.util.Iterator;

//import javax.sound.midi.InvalidMidiDataException;
//import javax.sound.midi.ShortMessage;

import org.nlogo.api.CommandTask;
import org.nlogo.api.ExtensionException;

//import at.univie.csd.Conversion;
//import at.univie.csd.TwoByte;

/**
 * This is the class for the 'musicians'
 * Every sheet can contain several actions with a time code
 * @author Martin Dobiasch
 */
public class Sheet
{
	private ArrayList<TimedEvent> sEventQueue;
	private long abstime;
	private Iterator<TimedEvent> it;
	private TimedEvent curr;
	public final static long NO_TIMECODE= -1;
	
	public Sheet()
	{
		abstime= 0;
		sEventQueue= new ArrayList<TimedEvent>();
		curr= null;
		it= null;
	}
	
	/*private void addNote(double args[], long tc) throws ExtensionException
	{
		int channel;
		int note;
		double vol;
		int dur;
		ShortMessage msg1, msg2;
		
		msg1= new ShortMessage();
		msg2= new ShortMessage();
		
		try
		{
			channel= (int) args[0] - 1;
			note= (int) args[1];
			vol= args[2];
			dur= (int) args[3];
			
			msg1.setMessage(ShortMessage.NOTE_ON, channel, note, (int) Conversion
					.Rescale(vol, 0, 1, 0, 127));
			msg2.setMessage(ShortMessage.NOTE_OFF, channel, note, 0);
		}
		catch(InvalidMidiDataException e)
		{
			throw new ExtensionException("Invalid Midi data " + e.getMessage());
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new ExtensionException("not enough inputs for note " + e.getMessage());
		}
		
		TimedEvent ev= new TimedEvent();
		ev.abstime= abstime;
		if( tc != NO_TIMECODE )
			ev.abstime+= tc;
		ev.msg= msg1;
		sEventQueue.add(ev);
		
		ev= new TimedEvent();
		ev.abstime= abstime + dur - 1;
		ev.msg= msg2;
		sEventQueue.add(ev);
		
		if( tc == NO_TIMECODE )
			abstime+= dur;
		else
			abstime+= tc;
	}
	
	private void addNoteOn(double args[], long tc) throws ExtensionException
	{
		int channel;
		int note;
		double vol;
		ShortMessage msg1;
		
		msg1= new ShortMessage();
		
		try
		{
			channel= (int) args[0] - 1;
			note= (int) args[1];
			vol= args[2];
			
			msg1.setMessage(ShortMessage.NOTE_ON, channel, note, (int) Conversion
					.Rescale(vol, 0, 1, 0, 127));
		}
		catch(InvalidMidiDataException e)
		{
			throw new ExtensionException("Invalid Midi data " + e.getMessage());
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new ExtensionException("not enough inputs for noteon " + e.getMessage());
		}
		
		TimedEvent ev= new TimedEvent();
		ev.abstime= abstime;
		if( tc != NO_TIMECODE )
			ev.abstime+= tc;
		ev.msg= msg1;
		sEventQueue.add(ev);
		
		if( tc != NO_TIMECODE )
			abstime+= tc;
	}
	
	private void addNoteOff(double args[], long tc) throws ExtensionException
	{
		int channel;
		int note;
		ShortMessage msg1;
		
		msg1= new ShortMessage();
		
		try
		{
			channel= (int) args[0] - 1;
			note= (int) args[1];
			
			msg1.setMessage(ShortMessage.NOTE_OFF, channel, note, 0);
		}
		catch(InvalidMidiDataException e)
		{
			throw new ExtensionException("Invalid Midi data " + e.getMessage());
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new ExtensionException("not enough inputs for noteoff " + e.getMessage());
		}
		
		TimedEvent ev= new TimedEvent();
		ev.abstime= abstime;
		if( tc != NO_TIMECODE )
			ev.abstime+= tc;
		ev.msg= msg1;
		sEventQueue.add(ev);
		
		if( tc != NO_TIMECODE )
			abstime+= tc;
	}
	
	private void addKeypressure(double args[], long tc) throws ExtensionException
	{
		int channel;
		int note;
		double rval;
		ShortMessage msg;
		
		msg= new ShortMessage();
		
		try
		{
			channel= (int) args[0] - 1;
			note= (int) args[1];
			rval= args[2];
			
			msg.setMessage(ShortMessage.CHANNEL_PRESSURE, channel, note, (int) Conversion.Rescale(rval, 0, 1, 0, 127));
		}
		catch(InvalidMidiDataException e)
		{
			throw new ExtensionException("Invalid Midi data " + e.getMessage());
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new ExtensionException("not enough inputs for keypressure " + e.getMessage());
		}
		
		TimedEvent ev= new TimedEvent();
		ev.abstime= abstime;
		if( tc != NO_TIMECODE )
			ev.abstime+= tc;
		ev.msg= msg;
		sEventQueue.add(ev);
		
		if( tc != NO_TIMECODE )
			abstime+= tc;
	}

	private void addChannelPressure(double args[], long tc) throws ExtensionException
	{
		int channel;
		double rval;
		ShortMessage msg;
		
		msg= new ShortMessage();
		
		try
		{
			channel= (int) args[0] - 1;
			rval= args[1];
			
			msg.setMessage(ShortMessage.CHANNEL_PRESSURE, channel, (int) Conversion.Rescale(rval, 0, 1, 0, 127), 0);
		}
		catch(InvalidMidiDataException e)
		{
			throw new ExtensionException("Invalid Midi data " + e.getMessage());
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new ExtensionException("not enough inputs for noteon " + e.getMessage());
		}
		
		TimedEvent ev= new TimedEvent();
		ev.abstime= abstime;
		if( tc != NO_TIMECODE )
			ev.abstime+= tc;
		ev.msg= msg;
		sEventQueue.add(ev);
		
		if( tc != NO_TIMECODE )
			abstime+= tc;
	}
	
	private void addInstrument(double args[], long tc) throws ExtensionException
	{
		int channel;
		int note;
		ShortMessage msg;
		
		msg= new ShortMessage();
		
		try
		{
			channel= (int) args[0] - 1;
			note= (int) args[1];
			
			msg.setMessage(ShortMessage.PROGRAM_CHANGE, channel, note - 1, 0);
		}
		catch(InvalidMidiDataException e)
		{
			throw new ExtensionException("Invalid Midi data " + e.getMessage());
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new ExtensionException("not enough inputs for instrument " + e.getMessage());
		}
		
		TimedEvent ev= new TimedEvent();
		ev.abstime= abstime;
		if( tc != NO_TIMECODE )
			ev.abstime+= tc;
		ev.msg= msg;
		sEventQueue.add(ev);
		
		if( tc != NO_TIMECODE )
			abstime+= tc;
	}
	
	private void addPitchBend(double args[], long tc) throws ExtensionException
	{
		int channel;
		double rval;
		ShortMessage msg;
		TwoByte MyTwobyte = new TwoByte();
		
		msg= new ShortMessage();
		
		try
		{
			channel= (int) args[0] - 1;
			rval= args[1];
			
			MyTwobyte = Conversion.TwoByteMidiRescale(rval, -1, 1);
			
			msg.setMessage(ShortMessage.PITCH_BEND, channel, MyTwobyte.lsb, MyTwobyte.msb);
		}
		catch(InvalidMidiDataException e)
		{
			throw new ExtensionException("Invalid Midi data " + e.getMessage());
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new ExtensionException("not enough inputs for noteon " + e.getMessage());
		}
		
		TimedEvent ev= new TimedEvent();
		ev.abstime= abstime;
		if( tc != NO_TIMECODE )
			ev.abstime+= tc;
		ev.msg= msg;
		sEventQueue.add(ev);
		
		if( tc != NO_TIMECODE )
			abstime+= tc;
	}
	
	private void addController(long tc, int channel, int contr, double ival) throws ExtensionException
	{
		ShortMessage msg;
		
		msg= new ShortMessage();
		
		try
		{
			msg.setMessage(ShortMessage.CONTROL_CHANGE, channel - 1, contr, (int) ival);
		}
		catch(InvalidMidiDataException e)
		{
			throw new ExtensionException("Invalid Midi data " + e.getMessage());
		}
		
		TimedEvent ev= new TimedEvent();
		ev.abstime= abstime;
		if( tc != NO_TIMECODE )
			ev.abstime+= tc;
		ev.msg= msg;
		sEventQueue.add(ev);
		
		if( tc != NO_TIMECODE )
			abstime+= tc;
	}
	
	private void addMasterTuneFine(double args[], long tc) throws ExtensionException, IndexOutOfBoundsException
	{
		int channel;
		int lorpn;
		int hirpn;
		int lodata;
		int hidata;
		int cents;
		TwoByte tb;
		
		channel= (int) args[0];
		cents= (int) args[1];
		tb= Conversion.TwoByteMidiRescale(cents, -100, 100);
		
		lorpn= 1;
		hirpn= 0;
		lodata= tb.lsb;
		hidata= tb.msb;
		
		addRPN(channel, lorpn, hirpn, lodata, hidata, tc);
	}
	
	private void addRPN(int channel, int lorpn, int hirpn, int lodata, int hidata, long tc) throws ExtensionException
	{
		ShortMessage msg1, msg2, msg3, msg4, msg5, msg6;
		
		msg1= new ShortMessage();
		msg2= new ShortMessage();
		msg3= new ShortMessage();
		msg4= new ShortMessage();
		msg5= new ShortMessage();
		msg6= new ShortMessage();
		
		channel--;
		
		try
		{
			msg1.setMessage(ShortMessage.CONTROL_CHANGE, channel, 101, (int) Conversion.Rescale(hirpn, 0, 1, 0, 127));
			msg2.setMessage(ShortMessage.CONTROL_CHANGE, channel, 100, (int) Conversion.Rescale(lorpn, 0, 1, 0, 127));
			msg3.setMessage(ShortMessage.CONTROL_CHANGE, channel,   6, (int) Conversion.Rescale(hidata, 0, 1, 0, 127));
			msg4.setMessage(ShortMessage.CONTROL_CHANGE, channel,  38, (int) Conversion.Rescale(lodata, 0, 1, 0, 127));
			msg5.setMessage(ShortMessage.CONTROL_CHANGE, channel, 101, 127);
			msg6.setMessage(ShortMessage.CONTROL_CHANGE, channel, 100, 127);
		}
		catch(InvalidMidiDataException e)
		{
			throw new ExtensionException("Invalid Midi data " + e.getMessage());
		}
		
		TimedEvent ev= new TimedEvent();
		ev.abstime= abstime;
		if( tc != NO_TIMECODE )
			ev.abstime+= tc;
		ev.msg= msg1;
		sEventQueue.add(ev);
		
		ev= new TimedEvent();
		ev.abstime= abstime;
		if( tc != NO_TIMECODE )
			ev.abstime+= tc;
		ev.msg= msg2;
		sEventQueue.add(ev);
		
		ev= new TimedEvent();
		ev.abstime= abstime;
		if( tc != NO_TIMECODE )
			ev.abstime+= tc;
		ev.msg= msg3;
		sEventQueue.add(ev);
		
		ev= new TimedEvent();
		ev.abstime= abstime;
		if( tc != NO_TIMECODE )
			ev.abstime+= tc;
		ev.msg= msg4;
		sEventQueue.add(ev);
		
		ev= new TimedEvent();
		ev.abstime= abstime;
		if( tc != NO_TIMECODE )
			ev.abstime+= tc;
		ev.msg= msg5;
		sEventQueue.add(ev);
		
		ev= new TimedEvent();
		ev.abstime= abstime;
		if( tc != NO_TIMECODE )
			ev.abstime+= tc;
		ev.msg= msg6;
		sEventQueue.add(ev);
		
		if( tc != NO_TIMECODE )
			abstime+= tc;
	}*/
	
	@Deprecated
	public void addCommand(String command, long tc)
	{
		TimedEvent ev;
		
		ev= new TimedEvent();
		ev.abstime= abstime;
		if( tc != NO_TIMECODE )
			ev.abstime+= tc;
		ev.msg= command;
		sEventQueue.add( ev );
		
		if( tc != NO_TIMECODE )
			abstime+= tc;
	}
	

	public void addCommand(CommandTask command, long tc)
	{
		TimedEvent ev;
		
		ev= new TimedEvent();
		ev.abstime= abstime;
		if( tc != NO_TIMECODE )
			ev.abstime+= tc;
		ev.cmd= command;
		sEventQueue.add( ev );
		
		if( tc != NO_TIMECODE )
			abstime+= tc;
	}
	
	public void addCommand(String command, double[] args, long tc) throws ExtensionException
	{
		/*try
		{
			if( command.equals("note") )
				addNote(args, tc);
			else if( command.equals ("noteon") )
				addNoteOn(args, tc);
			else if( command.equals("noteoff"))
				addNoteOff(args, tc);
			else if(command.equals("keypressure"))
				addKeypressure(args, tc);
			else if(command.equals("controller"))
				addController(tc, (int) args[0], (int) args[1], args[2]);
			else if(command.equals("instrument"))
				addInstrument(args, tc);
			else if(command.equals("channelpressure"))
				addChannelPressure(args, tc);
			else if(command.equals("pitchbend"))
				addPitchBend(args, tc);
			else if(command.equals("modulation"))
				addController(tc, (int) args[0], 1, Conversion.Rescale(args[1], 0, 1, 0, 127) );
			else if(command.equals("volume"))
				addController(tc, (int) args[0], 7, Conversion.Rescale(args[1], 0, 1, 0, 127) );
			else if(command.equals("expression"))
				addController(tc, (int) args[0], 11, Conversion.Rescale(args[1], 0, 1, 0, 127) );
			else if(command.equals("pan"))
				addController(tc, (int) args[0], 10, Conversion.Rescale(args[1], -1, 1, 0, 127));
			else if(command.equals("sustain"))
				addController(tc, (int) args[0], 64, Conversion.Rescale(args[1], 0, 1, 0, 127) );
			else if(command.equals("reverb"))
				addController(tc, (int) args[0], 91, Conversion.Rescale(args[1], 0, 1, 0, 127) );
			else if(command.equals("chorus"))
				addController(tc, (int) args[0], 93, Conversion.Rescale(args[1], 0, 1, 0, 127) );
			else if(command.equals("resetcontrollers"))
				addController(tc, (int) args[0], 121, 0 );
			else if(command.equals("allnotesoff"))
				addController(tc, (int) args[0], 123, 0 );
			else if(command.equals("bankselectmsb"))
				addController(tc, (int) args[0], 32, args[1] );
			else if(command.equals("bankselectlsb"))
				addController(tc, (int) args[0], 0, args[1] );
			else if(command.equals("breath"))
				addController(tc, (int) args[0], 2, Conversion.Rescale(args[1], 0, 1, 0, 127) );
			else if(command.equals("footpedal"))
				addController(tc, (int) args[0], 4, Conversion.Rescale(args[1], 0, 1, 0, 127) );
			else if(command.equals("portamentotime"))
				addController(tc, (int) args[0], 5, args[1] );
			else if(command.equals("pitchbendrange"))
				addRPN((int) args[0], 0, 0, 0, (int) args[1], tc);
			else if(command.equals("mastertunecoarse"))
				addRPN((int) args[0], 2, 0, 0, (int) args[1] + 64, tc);
			else if(command.equals("mastertunefine"))
				addMasterTuneFine(args, tc);
			else
				throw new ExtensionException("Unknown command " + command );
		}
		catch( IndexOutOfBoundsException e )
		{
			throw new ExtensionException( "Not enough inputs to " + command + "\n" + e.getMessage() );
		}*/
	}
	
	public TimedEvent getCurrEvent() throws ExtensionException
	{
		if( curr == null )
		{
			if( it == null )
			{
				it= sEventQueue.iterator();
				curr= it.next();
				return curr;
			}
			else
			{
				if( it.hasNext() )
				{
					curr= it.next();
					return curr;
				}
				return null;
			}
		}
		else
			return curr;
	}
	
	public long getDuration()
	{
		return abstime;
	}
	
	/*public TimedEvent getNextEvent()
	{
		if( it == null )
			it= sEventQueue.iterator();
		
		return it.next();
	}*/

	public void NextEvent()
	{
		if( it != null )
		{
			if( it.hasNext() )
			{
				curr= it.next();
			}
			else
				curr= null;
		}
	}
	
	public void reset()
	{
		it= sEventQueue.iterator();
	}
}
