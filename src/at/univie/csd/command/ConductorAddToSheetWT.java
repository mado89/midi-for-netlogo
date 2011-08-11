package at.univie.csd.command;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultCommand;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.LogoList;
import org.nlogo.api.Syntax;

import at.univie.csd.MidiManager;
import at.univie.csd.midi.Conductor;
import at.univie.csd.midi.Sheet;

public class ConductorAddToSheetWT extends DefaultCommand
{
	public Syntax getSyntax()
	{
		return Syntax.commandSyntax( new int[] {Syntax.NumberType(), Syntax.ListType() } ) ;
	}
	
	public void perform(Argument[] args, Context ctx) throws ExtensionException,
	LogoException
	{
		Sheet[] sheets;
		int sheet;
		LogoList list;
		LogoList cmd; //command
		String cmds; //command string
		String cmdn = null;
		long tc;
		
		try
		{
			sheet= args[0].getIntValue() - 1;
			list= args[1].getList();
			
			Conductor c= MidiManager.getMidiContext().getConductor();
			sheets= c.getSheets();
			
			if( sheets[sheet] == null )
				sheets[sheet]= new Sheet();
			
			while( ! list.isEmpty())
			{
				cmd= (LogoList) list.first();//get next Command
				
				tc= ((Double) cmd.first()).longValue(); //get Timecode
				cmd= cmd.butFirst(); //go to command to add
				
				//cmds= (String) ((LogoList)cmd.first()).first(); //get command to add
				cmds= (String) cmd.first(); //get command to add
				
				sheets[sheet].addCommand(cmds, tc);
				
				list= list.butFirst();
			}
			
			
		}
		catch(LogoException e)
		{
			throw new ExtensionException(cmdn + e.getMessage());
		}
		
		MidiManager.getMidiContext().releaseConductor();
	}
}
