package at.univie.csd;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultCommand;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;

public class Cmd extends DefaultCommand
{

	public void perform(Argument[] args, Context ctx) throws ExtensionException,
			LogoException
	{
		System.out.println("Cmd executed");
	}
	
}
