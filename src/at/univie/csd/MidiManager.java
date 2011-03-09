/**
 * 
 */
package at.univie.csd;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.sound.midi.MidiUnavailableException;

import org.nlogo.api.DefaultClassManager;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.PrimitiveManager;

import at.univie.csd.command.*;

/**
 * @author Martin Dobiasch
 * Adds the Commands to NetLogo
 */
public class MidiManager extends DefaultClassManager
{
	private static MidiContext ctx;
	private static BufferedWriter out;
	
	public MidiManager() throws ExtensionException
	{
		try
		{
			ctx= new MidiContext();
		}
		catch(MidiUnavailableException e)
		{
			throw new ExtensionException("Midi not available");
		}
		openfile();
	}
	
	private void openfile()
	{
		File file= new File("midiextensionlog.txt");
		try
		{
			out= new BufferedWriter(new FileWriter(file));
		}
		catch (IOException e)
		{
			//Debug.showMessage("const");
		}
	}
	
	public static void debug(String s)
	{
		try
		{
			out.write(s + "\r\n");
			out.flush();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static MidiContext getMidiContext()
	{
		return ctx;
	}
	
	public void load(PrimitiveManager primitiveManager) throws ExtensionException
	{
		primitiveManager.addPrimitive("noteon", new NoteOn());
		primitiveManager.addPrimitive("noteoff", new NoteOff());
		primitiveManager.addPrimitive("note", new Note());
		primitiveManager.addPrimitive("instrument", new Instrument());
		primitiveManager.addPrimitive("pitch.bend", new PitchBend());
		primitiveManager.addPrimitive("controller", new Controller());
		primitiveManager.addPrimitive("key.pressure", new KeyPressure());
		primitiveManager.addPrimitive("channel.pressure", new ChannelPressure());
		primitiveManager.addPrimitive("volume", new Volume());
		primitiveManager.addPrimitive("expression", new Expression());
		primitiveManager.addPrimitive("modulation", new Modulation());
		primitiveManager.addPrimitive("pan", new Pan());
		primitiveManager.addPrimitive("sustain", new Sustain());
		primitiveManager.addPrimitive("reverb", new Reverb());
		primitiveManager.addPrimitive("chorus", new Chorus());
		primitiveManager.addPrimitive("portamento.time", new PortamentoTime());
		primitiveManager.addPrimitive("portamento", new Portamento());
		primitiveManager.addPrimitive("portamento.from", new PortamentoFrom());
		primitiveManager.addPrimitive("rpn", new Rpn());
		primitiveManager.addPrimitive("nrpn", new Nrpn());
		primitiveManager.addPrimitive("reset.controllers", new ResetControllers());
		primitiveManager.addPrimitive("all.notes.off", new AllNotesOff());
		primitiveManager.addPrimitive("pitch.sens", new PitchSens());
		primitiveManager.addPrimitive("mastertune.coarse", new MastertuneCoarse());
		primitiveManager.addPrimitive("mastertune.fine", new MastertuneFine());
		primitiveManager.addPrimitive("panic", new Panic());
		
		primitiveManager.addPrimitive("updateposition", new UpdatePosition());
		
		primitiveManager.addPrimitive("conductor.clear.sheets", new ConductorClearSheets());
		primitiveManager.addPrimitive("conductor.add.to.sheet", new ConductorAddToSheet());
		primitiveManager.addPrimitive("conductor.add.to.sheet.list", new ConductorAddToSheetWT());
		primitiveManager.addPrimitive("conductor.restart", new ConductorReset());
		primitiveManager.addPrimitive("conductor.conduct", new ConductorConduct());
		primitiveManager.addPrimitive("conductor.setplaymode.endless", new ConductorPlaymodeEndless() );
		primitiveManager.addPrimitive("conductor.setplaymode.normal", new ConductorPlaymodeNormal() );
		primitiveManager.addPrimitive("conductor.start", new ConductorStart() );
		primitiveManager.addPrimitive("conductor.stop", new ConductorStop() );
	}
}

