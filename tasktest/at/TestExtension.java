import org.nlogo.api.*;

public class TestExtension extends DefaultClassManager
{
  public void load(PrimitiveManager primitiveManager)
  {
    primitiveManager.addPrimitive("test", new Test());
  }
}
