package saitMLS.persistence.property;


import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import saitMLS.exceptions.property.InvalidLegalDescriptionException;
import saitMLS.exceptions.property.InvalidNumberOfBathroomsException;
import saitMLS.persistence.Broker;
import saitMLS.problemDomain.property.CommercialProperty;
import saitMLS.problemDomain.property.ResidentialProperty;
import utilities.CredentialStore;
import utilities.SLLNode;

public class ResidentialPropertyBroker implements Broker, Serializable
{
	/**
	 * Serial Version: -8818624026248998700L
	 */
	private static final long serialVersionUID = -8818624026248998700L;
	/**
	 * ResidentialPropertyBroker object, singleton. Belongs to class. Returned when using getBroker method.
	 */
	private static ResidentialPropertyBroker resBroker;
	/**
	 * A number representing the nextID in the list. (highest id + 1)
	 */
	private long nextId;
	/**
	 * An oracle Java Database Connection object holding the actual connection to the database.
	 */
	private Connection conn;
	/**
	 * An oracle Java Database Connection object holding a statement to be used and changed.
	 */
	private Statement stmt;
	/**
	 * An oracle Java Database Connection object holding the results of select statements.
	 */
	private ResultSet rs;
	
	private ResidentialPropertyBroker()
	{	//Connects to DB using JBDC.
		try {
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			String url = "jdbc:oracle:thin:@" + CredentialStore.HOSTNAME +":" + CredentialStore.PORT;
			conn = DriverManager.getConnection(url, CredentialStore.USER, CredentialStore.PASSWORD);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		findHighestCurrentId();
	}
	
	/**
	 * Finds the highest current ID and sets it to the global field highId.
	 * Note that this value goes up by one each time property is added, so this is only used once during initialization.
	 */
	private void findHighestCurrentId()
	{
		try {
			stmt = conn.createStatement();
			String query = "SELECT max(id) FROM residentialProperty";
			rs = stmt.executeQuery(query);
			
			rs.next();
			nextId = rs.getLong(1) + 1L;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The public access method for the ResidentialPropertyBroker constructor.
	 * Uses singleton structure.
	 * @return The ResidentialPropertyBroker instance. Creates a new one if one does not already exist.
	 */
	public static ResidentialPropertyBroker getBroker()
	{
		if(resBroker == null)
		{ //If resBroker is null, then it creates a new ResidentialPropertyBroker.
		  //Else it returns the same ResidentialPropertyBroker that is available globally in this class.
			resBroker = new ResidentialPropertyBroker();
		}
		return resBroker;
	}
	
	/**
	 * A method that closes the broker, releasing its resources.
	 */
	@Override
	public void closeBroker()
	{	//Changes dont need to be commited. JDBC auto-commits by default.
		try
		{
			conn.close();
			stmt.close();
			rs.close();
		} catch (SQLException e)
		{
			System.err.println(e.getMessage());
		}
	}

	/**
	 * A method that makes changes to residential property persistent or adds new residential properties to the list.
	 * If the ID is 0, it will create a new object and assign it the next available ID.
	 * If the ID is already taken, it will modify the existing object to match the one provided.
	 * 
	 * Additions are entered into this table:
	 * 	id				    NUMBER(4)		CONSTRAINT property_PK PRIMARY KEY,
		legalDescription	VARCHAR2(12)	NOT NULL,
		address		        VARCHAR2(50)	NOT NULL,
		quadrant	        VARCHAR2(2) 	NOT NULL CONSTRAINT property_quadrant CHECK (quadrant IN('NW','NE','SE','SW')),
		zone 		        CHAR(2) 		NOT NULL CONSTRAINT propertyZone_CHECK CHECK (zone IN('R1', 'R2','R3','R4')),
		askingPrice 	    NUMBER(12,2) 	NOT NULL,
		comments 		    VARCHAR2(50) 	NOT NULL,
    	area                NUMBER(10)      NOT NULL,
    	bathroom           	NUMBER(3,1)    	NOT NULL,
    	bedroom            	NUMBER(1)      	NOT NULL,
    	garage              CHAR(1)         NOT NULL CONSTRAINT garage_CHECK CHECK (garage IN('N','D','A'))
	 * 
	 * @param obj An object to be added to the list. Must be a ResidentialProperty object for this broker.
	 * @return A true if it is sucessfully saved, or false if not.
	 */
	@Override
	public boolean persist(Object obj)
	{	//Inserts into residentialproperty
		//Must be below maximum sizes especially in this one.
		ResidentialProperty resProp = (ResidentialProperty) obj;
		if (resProp.getId() == 0) 	//new property added
			return addProp(resProp);
		else 						//old property updated
			return updateProp(resProp);
	}
	
	/**
	 * A helper method for persist. Adds properties when the ID is 0.
	 * @param commProp The residential property provided to persist method.
	 * @return A true if the object is added, or a false if it is not.
	 */
	private boolean addProp(ResidentialProperty resProp)
	{
		resProp.setId(nextId++);
		try 
		{
			String query = "INSERT INTO residentialProperty "
					+ "(id, legalDescription, address, quadrant, zone, askingPrice, comments, area, bathroom, bedroom, garage) "
					+ "values (?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement pStat = conn.prepareStatement(query);
			
			pStat.setLong(1, resProp.getId());
			pStat.setString(2, resProp.getLegalDescription());
			pStat.setString(3, resProp.getAddress());
			pStat.setString(4, resProp.getQuadrant());
			pStat.setString(5, resProp.getZone());
			pStat.setDouble(6, resProp.getAskingPrice());
			pStat.setString(7, resProp.getComments());
			pStat.setDouble(8, resProp.getArea());
			pStat.setDouble(9, resProp.getBathrooms());
			pStat.setInt(10, resProp.getBedrooms());
			pStat.setString(11, Character.toString(resProp.getGarage()));
			
			pStat.executeUpdate();
		} catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(null, e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * A helper method for persist. Modifies properties when the ID is not 0.
	 * @param commProp The residential property provided to persist method.
	 * @return A true if the object is overwritten, or a false if it is not.
	 */
	private boolean updateProp(ResidentialProperty resProp)
	{
		try 
		{
			String query = "UPDATE residentialProperty "
					+ "set legalDescription = ?, address = ?, quadrant = ?, "
					+ "zone = ?, askingPrice = ?, comments = ?, area = ?, "
					+ "bathroom = ?, bedroom = ?, garage = ? "
					+ "where id = ?";
			PreparedStatement pStat = conn.prepareStatement(query);
			
			pStat.setString(1, resProp.getLegalDescription());
			pStat.setString(2, resProp.getAddress());
			pStat.setString(3, resProp.getQuadrant());
			pStat.setString(4, resProp.getZone());
			pStat.setDouble(5, resProp.getAskingPrice());
			pStat.setString(6, resProp.getComments());
			pStat.setDouble(7, resProp.getArea());
			pStat.setDouble(8, resProp.getBathrooms());
			pStat.setInt(9, resProp.getBedrooms());
			pStat.setString(10, Character.toString(resProp.getGarage()));
			pStat.setLong(11, resProp.getId());
			
			pStat.executeUpdate();
		} catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(null, e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * A method to remove the object provided.
	 * @param obj the object to be removed
	 * @return A true if it is removed, or a false if it is not removed.
	 */
	@Override
	public boolean remove(Object obj)
	{
		//Deletes from using id
		ResidentialProperty resProp = (ResidentialProperty) obj;
		try 
		{
			String query = "DELETE residentialProperty "
					+ "where id = ?";
			PreparedStatement pStat = conn.prepareStatement(query);
			
			pStat.setLong(1, resProp.getId());
			
			pStat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * A search function.
	 * Searches the propertyList based on the type parameter for the item parameter.
	 * @param item the search term to be found.
	 * @param type the type of search done. Can be "id", "legal description", "quadrant", "price"
	 * @return A list of all items that match the search.
	 */
	@Override
	public List search(String item, String type)
	{
		//could be "legal description", "id", "quadrant" or "price".
		//Copied from CommercialPropertyBroker
		ArrayList<ResidentialProperty> searchResults = new ArrayList<ResidentialProperty>();
		
		if (type.equals("legal description"))
		{
			searchLegal(searchResults, item);
		}
		else if (type.equals("id"))
		{	
			if (item.matches("\\d+"))
				searchId(searchResults, item);
			else
				JOptionPane.showMessageDialog(null, "Please enter a number for searching ID");
		}
		else if (type.equals("quadrant"))
		{
			searchQuadrant(searchResults, item);
		}	
		else if (type.equals("price"))
		{
			if (item.matches("\\d+"))
				searchPrice(searchResults, item);
			else
				JOptionPane.showMessageDialog(null, "Please enter a number for searching ID");
		}
		return searchResults;
	}
	
	/**
	 * A helper function that searches for legal descriptions.
	 * @param searchResults The results of the search in a list. Contained in parent method "search".
	 * @param item The term being searched for in the propertyList.
	 */
	private void searchLegal(ArrayList<ResidentialProperty> searchResults, String item)
	{
		try {
			String query = "SELECT * FROM residentialProperty "
					+ "where legalDescription = ? "
					+ "order by id";
			PreparedStatement pStat = conn.prepareStatement(query);		
			pStat.setString(1, item);
	
			rs = pStat.executeQuery();
						
			while (rs.next()) {
				ResidentialProperty addedProperty = new ResidentialProperty();
				addedProperty.setId(rs.getLong(1));
				addedProperty.setLegalDescription(rs.getString(2));
				addedProperty.setAddress(rs.getString(3));
				addedProperty.setQuadrant(rs.getString(4));
				addedProperty.setZone(rs.getString(5));
				addedProperty.setAskingPrice(rs.getDouble(6));
				addedProperty.setComments(rs.getString(7));
				addedProperty.setArea(rs.getDouble(8));
				addedProperty.setBathrooms(rs.getDouble(9));
				addedProperty.setBedrooms(rs.getInt(10));
				addedProperty.setGarage(rs.getString(11).charAt(0));
				
				searchResults.add(addedProperty);
			}
			
		}
		catch (SQLException e) {
			System.err.println(e.getMessage());
		} catch (InvalidLegalDescriptionException e)
		{
			System.err.println(e.getMessage());
		} catch (InvalidNumberOfBathroomsException e)
		{
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * A helper function that searches for property IDs.
	 * @param searchResults The results of the search in a list. Contained in parent method "search".
	 * @param item The term being searched for in the propertyList.
	 */
	private void searchId(ArrayList<ResidentialProperty> searchResults, String item)
	{
		try {
			String query = "SELECT * FROM residentialProperty "
					+ "where id = ? "
					+ "order by id";
			PreparedStatement pStat = conn.prepareStatement(query);		
			pStat.setLong(1, Long.parseLong(item));
	
			rs = pStat.executeQuery();
						
			while (rs.next()) {
				ResidentialProperty addedProperty = new ResidentialProperty();
				addedProperty.setId(rs.getLong(1));
				addedProperty.setLegalDescription(rs.getString(2));
				addedProperty.setAddress(rs.getString(3));
				addedProperty.setQuadrant(rs.getString(4));
				addedProperty.setZone(rs.getString(5));
				addedProperty.setAskingPrice(rs.getDouble(6));
				addedProperty.setComments(rs.getString(7));
				addedProperty.setArea(rs.getDouble(8));
				addedProperty.setBathrooms(rs.getDouble(9));
				addedProperty.setBedrooms(rs.getInt(10));
				addedProperty.setGarage(rs.getString(11).charAt(0));
				
				searchResults.add(addedProperty);
			}
			
		}
		catch (SQLException e) {
			System.err.println(e.getMessage());
		} catch (InvalidLegalDescriptionException e)
		{
			System.err.println(e.getMessage());
		} catch (InvalidNumberOfBathroomsException e)
		{
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * A helper function that searches for quadrants.
	 * @param searchResults The results of the search in a list. Contained in parent method "search".
	 * @param item The term being searched for in the propertyList.
	 */
	private void searchQuadrant(ArrayList<ResidentialProperty> searchResults, String item)
	{
		try {
			String query = "SELECT * FROM residentialProperty "
					+ "where quadrant = ? "
					+ "order by id";
			PreparedStatement pStat = conn.prepareStatement(query);		
			pStat.setString(1, item);
	
			rs = pStat.executeQuery();
						
			while (rs.next()) {
				ResidentialProperty addedProperty = new ResidentialProperty();
				addedProperty.setId(rs.getLong(1));
				addedProperty.setLegalDescription(rs.getString(2));
				addedProperty.setAddress(rs.getString(3));
				addedProperty.setQuadrant(rs.getString(4));
				addedProperty.setZone(rs.getString(5));
				addedProperty.setAskingPrice(rs.getDouble(6));
				addedProperty.setComments(rs.getString(7));
				addedProperty.setArea(rs.getDouble(8));
				addedProperty.setBathrooms(rs.getDouble(9));
				addedProperty.setBedrooms(rs.getInt(10));
				addedProperty.setGarage(rs.getString(11).charAt(0));
				
				searchResults.add(addedProperty);
			}
			
		}
		catch (SQLException e) {
			System.err.println(e.getMessage());
		} catch (InvalidLegalDescriptionException e)
		{
			System.err.println(e.getMessage());
		} catch (InvalidNumberOfBathroomsException e)
		{
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * A helper function that searches for prices equal to or below the search item..
	 * @param searchResults The results of the search in a list. Contained in parent method "search".
	 * @param item The term being searched for in the propertyList.
	 */
	private void searchPrice(ArrayList<ResidentialProperty> searchResults, String item)
	{
		try {
			String query = "SELECT * FROM residentialProperty "
					+ "where askingPrice <= ? "
					+ "order by id";
			PreparedStatement pStat = conn.prepareStatement(query);		
			pStat.setDouble(1, Double.parseDouble(item));
	
			rs = pStat.executeQuery();
						
			while (rs.next()) {
				ResidentialProperty addedProperty = new ResidentialProperty();
				addedProperty.setId(rs.getLong(1));
				addedProperty.setLegalDescription(rs.getString(2));
				addedProperty.setAddress(rs.getString(3));
				addedProperty.setQuadrant(rs.getString(4));
				addedProperty.setZone(rs.getString(5));
				addedProperty.setAskingPrice(rs.getDouble(6));
				addedProperty.setComments(rs.getString(7));
				addedProperty.setArea(rs.getDouble(8));
				addedProperty.setBathrooms(rs.getDouble(9));
				addedProperty.setBedrooms(rs.getInt(10));
				addedProperty.setGarage(rs.getString(11).charAt(0));
				
				searchResults.add(addedProperty);
			}
			
		}
		catch (SQLException e) {
			System.err.println(e.getMessage());
		} catch (InvalidLegalDescriptionException e)
		{
			System.err.println(e.getMessage());
		} catch (InvalidNumberOfBathroomsException e)
		{
			System.err.println(e.getMessage());
		}
	}

}
