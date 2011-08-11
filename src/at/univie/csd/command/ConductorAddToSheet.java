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
import at.univie.csd.midi.Sheet;


/**
 * @author Martin Dobiasch
 *
 */
public class ConductorAddToSheet extends DefaultCommand
{
	public Syntax getSyntax()
	{
		return Syntax.commandSyntax( new int[] {Syntax.NumberType(), Syntax.NumberType(),
				Syntax.NumberType()} ) ;
	}
	
	public void perform(Argument[] args, Context ctx) throws ExtensionException,
	LogoException
	{
		Sheet[] sheets;
		int sheet;
		String cmd;
		//double[] cargs;
		int dur;
		// int i;
		// LogoList list;
		
		try
		{
			sheet= args[0].getIntValue() - 1;
			cmd= args[2].getString();
			dur= args[1].getIntValue();
			/*list= args[2].getList();
			
			cargs= new double[list.size() + 1];
			//cargs[0]= sheet;
			i= 0;
			while( ! list.isEmpty())
			{
				cargs[i]= (Double) list.first();
				list= list.butFirst();
				i++;
			}*/
		}
		catch(LogoException e)
		{
			throw new ExtensionException(e.getMessage());
		}
		
		Conductor c= MidiManager.getMidiContext().getConductor();
		sheets= c.getSheets();
		
		if( sheets[sheet] == null )
			sheets[sheet]= new Sheet();
		
		//sheets[sheet].addCommand(cmd, cargs, Sheet.NO_TIMECODE);
		sheets[sheet].addCommand(cmd, dur);
		
		MidiManager.getMidiContext().releaseConductor();
		
		// throw new ExtensionException( cmd );
	}
}
