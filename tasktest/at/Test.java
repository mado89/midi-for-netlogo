import org.nlogo.api.*;

public class Test extends DefaultCommand {
  // take one number as input, report a list
  public Syntax getSyntax()
  {
    return Syntax.commandSyntax(	new int[] {Syntax.CommandTaskType()} );
  }
  
  public void perform(Argument[] args, Context ctx) throws ExtensionException, LogoException
	{
    CommandTask cmd;
		
		cmd= args[0].getCommandTask();
		
		cmd.perform(ctx, new Object[0]);
  }
}

