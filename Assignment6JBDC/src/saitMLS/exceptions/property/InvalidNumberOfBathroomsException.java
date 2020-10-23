package saitMLS.exceptions.property;
/**
 * Exception case: When the number of bathrooms does not end in either .0 or .5
 * 
 * @author 729380
 */
public class InvalidNumberOfBathroomsException extends Exception
{
	/**
	 * Serial version 782501726797480077L
	 */
	private static final long serialVersionUID = 782501726797480077L;

	public InvalidNumberOfBathroomsException(String message)
	{
		super(message);
	}
}


