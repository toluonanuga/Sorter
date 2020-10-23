package saitMLS.problemDomain.property;

import java.io.Serializable;

import saitMLS.exceptions.property.InvalidLegalDescriptionException;
import saitMLS.exceptions.property.InvalidNumberOfBathroomsException;

public class ResidentialProperty extends Property implements Serializable
{

	/**
	 * Serial Version: -8167338590320088039L
	 */
	private static final long serialVersionUID = -8167338590320088039L;
	/**
	 * The area of the house in square feet.
	 */
	private double area;
	/**
	 * The number of bathrooms in the house. Must be either .0 or .5 (half baths).
	 */
	private double bathrooms;
	/**
	 * The number of bedrooms in the house.
	 */
	private int bedrooms;
	/**
	 * The garage type of the property. a for attached, d for detached, n for no garage.
	 */
	private char garage;
	
	/**
	 * A default constructor that sets all of the values to 0 or blank.
	 * "No garage" is the default garage type.
	 */
	public ResidentialProperty()
	{
		super();
		area = 0;
		bathrooms = 0;
		bedrooms = 0;
		garage = 'n';
	}
	
	/**
	 * A full constructor that makes use of its parent's constructor.
	 * Includes all parameters needed to define a full ResidentialProperty class.
	 * @param id The identification number of the property.
	 * @param legalDescription The legal description code of the property.
	 * @param address The address of the property.
	 * @param quadrant The quadrant the property resides in: NE, NW, SW, or SE
	 * @param zone The zone in which the property resides in.
	 * @param askingPrice The asking price for the property.
	 * @param comments Additional comments about the property.
	 * @param area The area in square feet of the property.
	 * @param bathrooms The number of bathrooms on the property (can be half bathrooms, as .5)
	 * @param bedrooms The number of bedrooms on the property.
	 * @param garage The type of garage (a for attached, d for detached, n for no garage)
	 * @throws InvalidLegalDescriptionException In case the legal description does not match this format: [0-9999][A-Z][0-9999][-][0-99]
	 * @throws InvalidNumberOfBathroomsException In case the number of bathrooms does not end in .0 or .5
	 */
	public ResidentialProperty(long id, String legalDescription, String address, 
			String quadrant, String zone, double askingPrice, String comments, 
			double area, double bathrooms, int bedrooms, char garage) 
					throws InvalidLegalDescriptionException, InvalidNumberOfBathroomsException
	{
		super(id, legalDescription, address, quadrant, zone, askingPrice, comments);
		this.area = area;
		setBathrooms(bathrooms);
		this.bedrooms = bedrooms;
		this.garage = garage;
	}

	/**
	 * A simple getter for the area.
	 * @return The area of the property in square feet.
	 */
	public double getArea()
	{
		return area;
	}

	/**
	 * A simple setter for the area.
	 * @param area The new area of the property.
	 */
	public void setArea(double area)
	{
		this.area = area;
	}

	/**
	 * A simple getter for the number of bathrooms.
	 * @return The number of bathrooms.
	 */
	public double getBathrooms()
	{
		return bathrooms;
	}

	/**
	 * A simple setter for the number of bathrooms.
	 * @param bathrooms The new number of bathrooms. Must end in .0 or .5
	 * @throws InvalidNumberOfBathroomsException In case the number of bathrooms does not end in .0 or .5
	 */
	public void setBathrooms(double bathrooms) throws InvalidNumberOfBathroomsException
	{
		validateNumOfBathrooms(bathrooms);
		this.bathrooms = bathrooms;
	}

	/**
	 * A helper method that validates the number of bathrooms
	 * @param bathrooms2 The number of bathrooms used in the "setBathrooms" method.
	 * @throws InvalidNumberOfBathroomsException In case the number of bathrooms does not end in .0 or .5
	 */
	private void validateNumOfBathrooms(double bathrooms2) throws InvalidNumberOfBathroomsException
	{
		if (!(bathrooms2 % 0.5 == 0))	
		{
			throw new InvalidNumberOfBathroomsException("Number of Bathrooms must end in .0 or .5");
		}
	}

	/**
	 * A simple getter for the number of bedrooms.
	 * @return The number of bedrooms.
	 */
	public int getBedrooms()
	{
		return bedrooms;
	}

	/**
	 * A simple setter for the number of bedrooms.
	 * @param bedrooms The new number of bedrooms.
	 */
	public void setBedrooms(int bedrooms)
	{
		this.bedrooms = bedrooms;
	}

	/**
	 * A getter for the garage type.
	 * @return The garage type (a for attached, d for detached, n for no garage)
	 */
	public char getGarage()
	{
		return garage;
	}

	/**
	 * A simple setter for the garage type.
	 * @param garage The new garage type (a for attached, d for detached, n for no garage)
	 */
	public void setGarage(char garage)
	{
		this.garage = garage;
	}
	
	/**
	 * A simple toString override that provides details about the property.
	 * This includes: id, legal description, address, quadrant, zone, asking price, comments,
	 * area, bathrooms, bedrooms, garage type.
	 * @return A string containing the information about the property.
	 */
	@Override
	public String toString()
	{
		//Parent's toString: id + ": " + legalDescription + " "
		//		+ address + ", " + quadrant + ", " + zone
		//		+ ", " + askingPrice + ", " + comments;
		String commProp;
		commProp = super.toString() + ", " + area + ", " + bathrooms + ", " + bedrooms + ", " + garage;
		return commProp;
	}
	
}
