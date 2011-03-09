/**
 * 
 */
package at.univie.csd;

/**
 * @author Martin Dobiasch (java portation), Erich Neuwirth
 * Helperclass for Conversion Tasks
 */
public class Conversion
{
	/**
	 * Rescales a Number with given Lower and Upper Bound
	 * @param x The Number to be rescaled
	 * @param inmin Lower-Bound of the new number
	 * @param inmax Upper-Bound of the new number
	 * @return rescaled number
	 */
	public static TwoByte TwoByteMidiRescale(double x, int inmin, int inmax)
	{
		long medval;
		TwoByte ret= new TwoByte();
		
		medval= Rescale(x, inmin, inmax, 0, 16383);
		ret.msb= (byte) (medval / (int) 128);
		ret.lsb= (byte) (medval % 128);
		
		return ret;
	}
	
	/**
	 * Rescales a Number with given Lower and Upper Bound of the input
	 *  and maxima for the output
	 * @param x The Number to be rescaled
	 * @param inmin Lower-Bound of the new number
	 * @param inmax Upper-Bound of the new number
	 * @param outmin Minimum of the Output
	 * @param outmax Maximum of the Output
	 * @return rescaled number
	 */
	public static long Rescale(double x, int inmin, int inmax, int outmin, int outmax)
	{
		double result;
		
		result = ((x - inmin) / (inmax - inmin)) * (outmax - outmin) + outmin;
		
		if( result > outmax )
			result = outmax;
		if( result < outmin )
			result = outmin;
		
		return (long)result;
	}
	
	/**
	 * Makes a String out the input
	 * 
	 * Example: MakeString(1, "ist toll") reports "1\tist toll"
	 * @param InValues Values to be converted to String
	 * @return InValues separated by "\t"
	 */
	public static String MakeString(Object ...InValues)
	{
		int i;
		String ret= "";
		
		ret= InValues[0].toString();
		for(i= 1; i < InValues.length; i++)
		{
			ret+= "\t" + InValues[i].toString();
		}
		
		return ret;
		/* Original Code
		Dim i As Integer
		On Error GoTo CatchError
		i = LBound(InValues)
		MakeString = InValues(i)
		Do While i < UBound(InValues)
		    i = i + 1
		    MakeString = MakeString & vbTab & CStr(InValues(i))
		    i = i + 1
		    If i > UBound(InValues) Then Exit Do
		    MakeString = MakeString & CStr(InValues(i))
		Loop
		Exit Function
		CatchError:
		MakeString = ""
		 */
	}
	
	/**
	 * 
	 * @param inString
	 * @return inString if not empty, else "0"
	 */
	public static String ZeroPad(String inString)
	{
		if( inString.length() > 1 )
			return inString;
		else
			return "0" + inString;
	}
	
	/**
	 * RGB used a lot of times
	 */
	public static long RGB(int r, int g, int b)
	{
		return (long)( r << 16 | g << 8 | b);
	}
	
	/*public static byte[] splitLong(long l)
	{
		byte bytes[]= new byte[4];
		//int bytes[]= new int[4];
		
		Debug.showMessage( Long.toHexString(l) );
		
		bytes[0]= (byte) (( l & 0xFF000000) >> 32);
		bytes[1]= (byte) (( l & 0xFF0000) >> 16);
		bytes[2]= (byte) ((l & 0xFF00) >> 8);
		bytes[3]= (byte) (l & 0xFF);
		
		return bytes;
	}
	
	public static String GetChannelPitchTimeVolume(TimedSymbolicEvent ev)
	{
		return String.format("00", ev.channel) +
		String.format("00", Long.parseLong(ev.EventParams[0])) +
		String.format("000000000", ev.AbsTime) +
		String.format("000)", 1000 * Long.parseLong( ev.EventParams[1]) );
	}*/
}

