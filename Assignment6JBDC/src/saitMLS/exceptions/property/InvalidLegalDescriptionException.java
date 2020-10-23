package saitMLS.exceptions.property;
/**
 * Exception case: When the legal description does not follow this pattern:
 * [0-9999][A-Z][0-9999][-][0-99]
 * 
 * @author 729380
 */
public class InvalidLegalDescriptionException extends Exception
{
	/**
	 * Serial version 782501726797480077L
	 */
	private static final long serialVersionUID = 782501726797480077L;
	
	public InvalidLegalDescriptionException(String message)
	{
		super(message);
	}

}
