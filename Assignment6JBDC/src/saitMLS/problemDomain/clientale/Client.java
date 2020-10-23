package saitMLS.problemDomain.clientale;

import saitMLS.exceptions.clientale.InvalidPhoneNumberException;
import saitMLS.exceptions.clientale.InvalidPostalCodeException;

/**
 * A Client object containing information about a Client and its activity.
 * Exceptions include InvalidPhoneNumberException and InvalidPostalCodeException.
 * @author 729380
 * @version 1
 */
public class Client
{
	/**
	 * A field that denotes whether or not an object is to be included in search function results.
	 */
	private boolean active;
	/**
	 * The address of this client.
	 */
	private String address;
	/**
	 * The type of this client (R for residential, C for commercial).
	 */
	private char clientType;
	/**
	 * This client's firstname.
	 */
	private String firstName;
	/**
	 * This client's identification number.
	 */
	private long id;
	/**
	 * This client's last name (or surname).
	 */
	private String lastName;
	/**
	 * This client's phone number (in this format: ###-###-####).
	 */
	private String phoneNumber;
	/**
	 * This client's postal code (assumed Canadian, in this format: A1A 1A1).
	 */
	private String postalCode;
	/**
	 * A serialization number.
	 */
	private static long serialVersionUID = 1L;
	/**
	 * The size of each client object in the random access file. Currently 105 bytes.
	 */
	public static int SIZE = 130;
	
	/**
	 * The default Client constructor. Creates an empty Client object that is inactive.
	 */
	public Client()
	{
		active = false;
		address = "";
		clientType = 'C';
		id = 0L;
		lastName = "";
		phoneNumber = "";
		postalCode = "";		
	}
	
	/**
	 * Creates a Client object. The postalCode and phoneNumber must be verified. clientType is C or R.
	 * All objects created by this are active by default.
	 * @param id the Client ID as a long.
	 * @param firstName the Client's first name.
	 * @param lastName the Client's last name or surname.
	 * @param address the Client's address.
	 * @param postalCode the Client's postal code.
	 * @param phoneNumber the Client's phone number.
	 * @param clientType the Client's client type. C for commercial, R for residential.
	 * @throws InvalidPhoneNumberException in case the phone number does not meet this format: ###-###-####.
	 * @throws InvalidPostalCodeException in case the postal code does not meet this format: A1A 1A1.
	 */
	public Client(long id, String firstName, String lastName, String address, String postalCode, 
			String phoneNumber, char clientType) throws InvalidPhoneNumberException, InvalidPostalCodeException
	{
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		setPostalCode(postalCode);
		setPhoneNumber(phoneNumber);
		this.clientType = clientType;
		active = true;
	}
	
	/**
	 * A constructor that uses a single line to construct a Client object.
	 * @param line A single line containing all client information separated by ; Order is ID, firstname, lastname, address, postalcode, phonenumber, clienttype
	 * @throws InvalidPhoneNumberException in case the phone number does not meet this format: ###-###-####.
	 * @throws InvalidPostalCodeException in case the postal code does not meet this format: A1A 1A1.
	 */
	public Client(String line) throws InvalidPostalCodeException, InvalidPhoneNumberException
	{
		String parts[] = line.split(";");
		
		this.firstName = parts[0];
		this.lastName = parts[1];
		this.address = parts[2];
		setPostalCode(parts[3]);
		setPhoneNumber(parts[4]);
		this.clientType = parts[5].charAt(0);
		active = true;
	}

	/**
	 * Checks if the client is active or not.
	 * @return true if active == true, false if active == false
	 */
	public boolean isActive()
	{
		return active;
	}
	/**
	 * Sets active as true or false.
	 * @param active the state to set active to.
	 */
	public void setActive(boolean active)
	{
		this.active = active;
	}
	/**
	 * A simple get method to return the address of a client.
	 * @return the client's address.
	 */
	public String getAddress()
	{
		return address;
	}
	/**
	 * A simple set method to set the client's address.
	 * @param address the client's new address.
	 */
	public void setAddress(String address)
	{
		this.address = address;
	}
	/**
	 * A simple get method to return the client's type. C for commercial, R for residential.
	 * @return A char of the client's type. C for commercial, R for residential.
	 */
	public char getClientType()
	{
		return clientType;
	}
	/**
	 * A simple method to set that client's type. C for commercial, R for residential.
	 * @param clientType the client's type. C for commercial, R for residential.
	 */
	public void setClientType(char clientType)
	{
		this.clientType = clientType;
	}
	/**
	 * A simple method to get the client's first name.
	 * @return the client's first name.
	 */
	public String getFirstName()
	{
		return firstName;
	}
	/**
	 * A simple method to set the client's first name.
	 * @param firstName the client's updated first name.
	 */
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	/**
	 * A simple method to get the client's ID.
	 * @return the client's ID.
	 */
	public long getId()
	{
		return id;
	}
	/**
	 * A simple method to set the client's ID. Note that changing the client's ID and using the persist method in ClientBroker may cause it to overwrite another client.
	 * @param id the client's new ID.
	 */
	public void setId(long id)
	{
		this.id = id;
	}
	/**
	 * A simple method to return the client's last name.
	 * @return the client's last name.
	 */
	public String getLastName()
	{
		return lastName;
	}
	/**
	 * A simple method to set the client's last name.
	 * @param lastName the client's updated last name.
	 */
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	/**
	 * A simple method to get the client's phone number in this format ###-###-####.
	 * @return The client's phone number in this format ###-###-####.
	 */
	public String getPhoneNumber()
	{
		return phoneNumber;
	}
	/**
	 * A simple method to set the client's phone number in this format ###-###-####.
	 * @param phoneNumber The client's updated phone number in this format ###-###-####.
	 * @throws InvalidPhoneNumberException In the case that the phone number does not match this format: ###-###-####.
	 */
	public void setPhoneNumber(String phoneNumber) throws InvalidPhoneNumberException
	{
		validatePhoneNumber(phoneNumber);
		this.phoneNumber = phoneNumber;
	}
	/**
	 * A simple method to get the client's postal code.
	 * @return The client's postal code.
	 */
	public String getPostalCode()
	{
		return postalCode;
	}
	/**
	 * A simple method to set the client's postal code in this format: A1A 1A1.
	 * @param postalCode The client's updated postal code in this format: A1A 1A1.
	 * @throws InvalidPostalCodeException In case the postal code does not match this format: A1A 1A1.
	 */
	public void setPostalCode(String postalCode) throws InvalidPostalCodeException
	{
		validatePostalCode(postalCode);
		this.postalCode = postalCode;
	}
	/**
	 * A simple method to get the Client class object size based on the ClientBroker class.
	 * I'm not sure why the javadoc wanted this class to contain the size considering the Broker writes it.
	 * @return An integer representing the number of bytes in a Client object written to a RAF file using the ClientBroker class.
	 */
	public static int getSIZE()
	{
		return SIZE;
	}
	
	/**
	 * An equals override method that checks if an object equals this one. Checks specifically for a matching ID.
	 * @param obj An object that is supposed to be a Client object to be tested for equality.
	 * @return True if the ID's match and false if they do not or if obj is not an instance of Client.
	 */
	@Override
	public boolean equals(Object obj)
	{
		boolean equalsFlag = false;
		if (obj instanceof Client)
		{
			Client testClient = (Client) obj;
			if (this.id == testClient.getId())
				equalsFlag = true;
		}
		return equalsFlag;
	}
	
	/**
	 * A toString override that outputs all Client information.
	 * @return A client containing the information of this client neatly presented in a line.
	 */
	@Override
	public String toString()
	{
		String clientString = id + ": " + firstName + " "
				+ lastName + ", " + address + ", " + postalCode
				+ ", " + phoneNumber + ", " + clientType + ".";
		return clientString;
	}
	
	/**
	 * A helper method used when creating a new Client object that tests the postal code for format.
	 * @param pc A postal code to be tested.
	 * @throws InvalidPostalCodeException in case the postal code does not match this format: A1A 1A1.
	 */
	private void validatePostalCode(String pc) throws InvalidPostalCodeException
	{
		pc = pc.trim();
		if(!(pc.length() == 7) || !(Character.isAlphabetic(pc.charAt(0)) && Character.isDigit(pc.charAt(1)) 
				&& Character.isAlphabetic(pc.charAt(2)) && Character.isWhitespace(pc.charAt(3))
				&& Character.isDigit(pc.charAt(4)) && Character.isAlphabetic(pc.charAt(5))
				&& Character.isDigit(pc.charAt(6))))
			throw new InvalidPostalCodeException("The postal code is invalid. Try in the format of A1A 1A1");
	}
	
	/**
	 * A helper method used when creating a new Client object that tests the phone number for format.
	 * @param pn A phone number to be tested.
	 * @throws InvalidPhoneNumberException in case the phone number does not match this format: ###-###-####.
	 */
	private void validatePhoneNumber(String pn) throws InvalidPhoneNumberException
	{
		pn = pn.trim();
		if (!(pn.matches("^\\d{3}-\\d{3}-\\d{4}$")))
			throw new InvalidPhoneNumberException("The phone number is invalid. Try in the format of ###-###-####");
	}
	
}
