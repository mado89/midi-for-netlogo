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
}

