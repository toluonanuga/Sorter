package saitMLS.problemDomain.property;

import java.io.Serializable;

import saitMLS.exceptions.property.InvalidLegalDescriptionException;

/**
 * A simple class that defines the attributes of a Commercial Property.
 * This class extends Property and it's methods.
 * @author 729380
 * @version April 9th, 2017
 */
public class CommercialProperty extends Property implements Serializable
{

	/**
	 * A serial version UID: 114655995578013413L
	 */
	private static final long serialVersionUID = 114655995578013413L;
	/**
	 * The number of floors in the property.
	 */
	private int noFloors;
	/**
	 * The type of property. M and O are suggested types (for manufacturing and office)
	 */
	private String type; //m for manufacturing, o for office
	
	/**
	 * A default constructor that sets all attributes to 0 or blank.
	 */
	public CommercialProperty()
	{
		super();
		noFloors = 0;
		type = "";
	}
	
	/**
	 * A full constructor that makes use of its parent's constructor.
	 * Includes all parameters needed to define a full CommercialProperty class.
	 * @param id The identification number of the property.
	 * @param legalDescription The legal description code of the property.
	 * @param address The address of the property.
	 * @param quadrant The quadrant the property resides in: NE, NW, SW, or SE
	 * @param zone The zone in which the property resides in.
	 * @param askingPrice The asking price for the property.
	 * @param comments Additional comments about the property.
	 * @param type The property's type. M or O.
	 * @param noFloors The number of floors on the property.
	 * @throws InvalidLegalDescriptionException In case the legal description does not match this format: [0-9999][A-Z][0-9999][-][0-99]
	 */
	public CommercialProperty(long id, String legalDescription, String address, 
			String quadrant, String zone, double askingPrice, String comments, 
			String type, int noFloors) throws InvalidLegalDescriptionException
	{
		super(id, legalDescription, address, quadrant, zone, askingPrice, comments);
		this.noFloors = noFloors;
		this.type = type;
	}

	/**
	 * A simple getter method for the number of floors.
	 * @return the number of floors on the property.
	 */
	public int getNoFloors()
	{
		return noFloors;
	}

	/**
	 * A simple setter method to define the number of floors.
	 * @param noFloors The new number of floors on the property.
	 */
	public void setNoFloors(int noFloors)
	{
		this.noFloors = noFloors;
	}

	/**
	 * A simple getter method for the type of property.
	 * @return The type of property (M or O).
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * A simple setter method to define the type of property.
	 * @param type The new type of property.
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	/**
	 * A simple toString override that provides details about the property.
	 * This includes: id, legal description, address, quadrant, zone, asking price, comments, type, and no of floors.
	 * @return A string containing the information about the property.
	 */
	@Override
	public String toString()
	{
		//Parent's toString: id + ": " + legalDescription + " "
		//		+ address + ", " + quadrant + ", " + zone
		//		+ ", " + askingPrice + ", " + comments;
		String commProp;
		commProp = super.toString() + ", " + type + ", " + noFloors + ".";
		return commProp;
	}
	
	

}
