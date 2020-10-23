package saitMLS.problemDomain.property;

import java.io.Serializable;

import saitMLS.exceptions.property.InvalidLegalDescriptionException;
/**
 * The abstract class Property.
 * Defines some behaviors and traits of Property classes, such as CommercialProperty and ResidentialProperty.
 * @author 729380
 * @version 1
 */
public abstract class Property implements Serializable
{

	/**
	 * A serial version UID: 862010293076845231L
	 */
	private static final long serialVersionUID = 862010293076845231L;
	/**
	 * The address of the property (like 123 Main Street)
	 */
	private String address;
	/**
	 * The asking price of the property.
	 */
	private double askingPrice;
	/**
	 * Any additional comments about the property.
	 */
	private String comments;
	/**
	 * The ID number of the property.
	 */
	private long id;
	/**
	 * A code that describes the property legally. Like [0-9999][A-Z][0-9999][-][0-99]
	 */
	private String legalDescription;
	/**
	 * A two letter description of which quadrant the property is in the city. NE, NW, SW, SE
	 */
	private String quadrant;
	/**
	 * The zone of the city that the property is in.
	 */
	private String zone;
	
	/**
	 * A default constructor. Sets all values to 0 or blank.
	 */
	public Property()
	{
		address = "";
		askingPrice = 0;
		comments = "";
		id = 0;
		legalDescription = "";
		quadrant = "";
		zone = "";
	}
	
	/**
	 * A detailed constructor. Contains all information necessary to create this class.
	 * @param id The Identification Number of the property.
	 * @param legalDescription A code defining the property legally. Like [0-9999][A-Z][0-9999][-][0-99]
	 * @param address The location of the property.
	 * @param quadrant The quadrant of the city of the property: NE, NW, SW, or SE
	 * @param zone The zone in which the property resides.
	 * @param askingPrice The asking price of the property.
	 * @param comments Additional comments about the property.
	 * @throws InvalidLegalDescriptionException In case the legal description does not match this format: [0-9999][A-Z][0-9999][-][0-99]
	 */
	public Property(long id, String legalDescription, String address, 
			String quadrant, String zone, double askingPrice, String comments) throws InvalidLegalDescriptionException
	{
		this.id = id;
		setLegalDescription(legalDescription);
		this.address = address;
		this.quadrant = quadrant;
		this.zone = zone;
		this.askingPrice = askingPrice;
		this.comments = comments;
	}

	/**
	 * A simple getter for address.
	 * @return the address.
	 */
	public String getAddress()
	{
		return address;
	}

	/**
	 * A simple setter for address.
	 * @param address The new address to be set.
	 */
	public void setAddress(String address)
	{
		this.address = address;
	}

	/**
	 * A simple getter for the asking price.
	 * @return the asking price
	 */
	public double getAskingPrice()
	{
		return askingPrice;
	}

	/**
	 * A simple setter for the asking price.
	 * @param askingPrice The new asking price to be set.
	 */
	public void setAskingPrice(double askingPrice)
	{
		this.askingPrice = askingPrice;
	}

	/**
	 * A simple getter for the comments section.
	 * @return The comments section.
	 */
	public String getComments()
	{
		return comments;
	}

	/**
	 * A simple setter for the comments.
	 * @param comments The new comments to be set.
	 */
	public void setComments(String comments)
	{
		this.comments = comments;
	}

	/**
	 * A simple getter for the ID.
	 * @return The ID.
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * A simple setter for the ID.
	 * @param id The new ID to be set.
	 */
	public void setId(long id)
	{
		this.id = id;
	}

	/**
	 * A simple getter for the legal description code.
	 * @return The legal description code.
	 */
	public String getLegalDescription()
	{
		return legalDescription;
	}

	/**
	 * A simple setter for the legal description. Validates the legal description.
	 * @param legalDescription A legal description code like [0-9999][A-Z][0-9999][-][0-99]
	 * @throws InvalidLegalDescriptionException in case the legal description code does not match this format: [0-9999][A-Z][0-9999][-][0-99]
	 */
	public void setLegalDescription(String legalDescription) throws InvalidLegalDescriptionException
	{
		validateLegalDescription(legalDescription);
		this.legalDescription = legalDescription;
	}

	/**
	 * A simple getter for the quadrant.
	 * @return The quadrant of the city in which the property resides.
	 */
	public String getQuadrant()
	{
		return quadrant;
	}

	/**
	 * A simple setter for the quadrant.
	 * @param quadrant The new quadrant for the property.
	 */
	public void setQuadrant(String quadrant)
	{
		this.quadrant = quadrant;
	}

	/**
	 * A simple getter for the zone of the property.
	 * @return The property's zone in the city.
	 */
	public String getZone()
	{
		return zone;
	}
	
	/**
	 * A simple setter for the zone of the property.
	 * @param zone The new zone the property resides in.
	 */
	public void setZone(String zone)
	{
		this.zone = zone;
	}
	
	/**
	 * A helper method that validates the legal description.
	 * Ensures it matches this format: [0-9999][A-Z][0-9999][-][0-99]
	 * @param desc A legal description code.
	 * @throws InvalidLegalDescriptionException in case it does not match this format: [0-9999][A-Z][0-9999][-][0-99]
	 */
	private void validateLegalDescription(String desc) throws InvalidLegalDescriptionException
	{// Example: 8454T3477-90, must match [0-9999][A-Z][0-9999][-][0-99]
//		if (!(desc.matches("[0-9]*[0-9]*[0-9]*[0-9][A-Z][0-9]*[0-9]*[0-9]*[0-9][-][0-9]*[0-9]")))
//			throw new InvalidLegalDescriptionException("Invalid Legal Description.");
		try
		{
		String descParts[] = desc.split("[A-Z||[-]]");
		if (!(Integer.parseInt(descParts[0]) >= 0 && Integer.parseInt(descParts[0]) <= 9999))
			throw new InvalidLegalDescriptionException("Invalid Legal Description.");
		if (!(Integer.parseInt(descParts[1]) >= 0 && Integer.parseInt(descParts[1]) <= 9999))
			throw new InvalidLegalDescriptionException("Invalid Legal Description.");
		if (!(Integer.parseInt(descParts[2]) >= 0 && Integer.parseInt(descParts[2]) <= 99))
			throw new InvalidLegalDescriptionException("Invalid Legal Description.");
		}
		catch (NumberFormatException e)
		{
			throw new InvalidLegalDescriptionException("Invalid Legal Description. Missing A-Z, or has too many.");
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			throw new InvalidLegalDescriptionException("Invalid Legal Description. Missing -, or has too many.");
		}
	}
	
	/**
	 * A simple toString override method.
	 * Includes id, legal description, address, quadrant, zone, asking price, and comments.
	 * @return A string containing information about the property.
	 */
	@Override
	public String toString()
	{
		String propertyString = id + ": " + legalDescription + " "
				+ address + ", " + quadrant + ", " + zone
				+ ", " + askingPrice + ", " + comments;
		return propertyString;
	}
	
	/**
	 * A simple equals override method.
	 * Checks if the property IDs are the same to measure equality.
	 * @param obj An object to be checked if it is the same property as the one being compared against.
	 * @return a boolean true if they're equal, or false if they are not.
	 */
	@Override
	public boolean equals(Object obj)
	{
		boolean equalsFlag = false;
		if (obj instanceof Property)
		{
			Property testProp = (Property) obj;
			if (this.id == testProp.getId())
				equalsFlag = true;
		}
		return equalsFlag;
	}
}
