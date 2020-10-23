package saitMLS.persistence.property;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import saitMLS.exceptions.property.InvalidLegalDescriptionException;
import saitMLS.persistence.Broker;
import saitMLS.problemDomain.property.CommercialProperty;
import utilities.SLL;
import utilities.SLLNode;

public class CommercialPropertyBroker implements Broker, Serializable
{
	/**
	 * CommercialPropertyBroker object, singleton. Belongs to class. Returned when using getBroker method.
	 */
	private static CommercialPropertyBroker commBroker;
	/**
	 * A number representing the nextID in the list. (highest id + 1)
	 */
	private long nextId;
	/**
	 * The location of the comprop.txt file for use in creating the list.
	 */
	private static final String INPUT_FILE = "res/comprop.txt";
	/**
	 * The location of the comprop.ser file, to be used in saving the list.
	 */
	private static final String SERIALIZED_FILE = "res/comprop.ser";
	/**
	 * A single linked list containing the list of properties.
	 */
	private SLL propertyList;
	/**
	 * A serial version UID: -3200671299620990519L
	 */
	private static final long serialVersionUID = -3200671299620990519L;
	
	/**
	 * A private constructor that creates the propertyList from a text or serial object file.
	 * Can only be accessed through the getBroker() method.
	 * @throws IOException in case the file is not found or has another general IO error.
	 */
	private CommercialPropertyBroker() throws IOException
	{	
		File file = new File(SERIALIZED_FILE);
		propertyList = new SLL();
		if(!file.exists())
		{ //doesn't exist, read from txt and create ser
			long incrementedID = 1; //Starts at 1 because there is no "0" for ID.
			File textFile = new File(INPUT_FILE);
			
			try (Scanner in = new Scanner(textFile)) {
				while (in.hasNextLine()) {
					CommercialProperty commProp;
					try
					{//FORMAT OF PROPERTY IN FILE
					//8454T3477-90;17 26th St.;NE;I1;819842.41;Large parking area;M;5
						String lineParts[];
						lineParts = in.nextLine().split(";");
						//A total of 8 parts
						String legalDescription = lineParts[0];
						String address = lineParts[1];
						String quadrant = lineParts[2]; 
						String zone = lineParts[3];
						double askingPrice = Double.parseDouble(lineParts[4]);
						String comments = lineParts[5];
						String type = lineParts[6];
						int noFloors = Integer.parseInt(lineParts[7]);
						
						commProp = new CommercialProperty(incrementedID++, legalDescription, address,
								quadrant, zone, askingPrice, comments, type, noFloors);
						
						propertyList.addLast(commProp);
					} catch (InvalidLegalDescriptionException e)
					{
						System.err.println("Error writing CommercialProperty due to invalid legal description.");
					}
				}
			} catch (FileNotFoundException e) {
				System.err.println("File not found.");
			}
		}
		else
		{ //does exist, read ser and pass it to propertyList
			try
			{
				FileInputStream fileIn = new FileInputStream(file);
				ObjectInputStream objectIn = new ObjectInputStream(fileIn);
				
				propertyList = (SLL) objectIn.readObject();
				objectIn.close();
			}
			catch (IOException e)
			{
				System.err.println(e.getMessage());
			} 
			catch (ClassNotFoundException e)
			{
				System.err.println(e.getMessage());
			}
		}
		findHighestCurrentId();
	}
	
	/**
	 * The public access method for the CommercialPropertyBroker constructor.
	 * Uses singleton structure.
	 * @return The CommercialPropertyBroker instance. Creates a new one if one does not already exist.
	 */
	public static CommercialPropertyBroker getBroker()
	{
		if(commBroker == null)
		{ //If commBroker is null, then it creates a new CommercialPropertyBroker.
		  //Else it returns the same CommercialPropertyBroker that is available globally in this class.
			try
			{
				commBroker = new CommercialPropertyBroker();
			} catch (FileNotFoundException e)
			{
				System.err.println("File not found.");
			} catch (IOException e)
			{
				System.err.println(e.getMessage());
			}
		}
		return commBroker;
	}
	
	/**
	 * A method that closes the broker, releasing its resources and saving the propertyList to SERIALIZED_FILE.
	 */
	@Override
	public void closeBroker()
	{//Must save propertyList to ser file
		FileOutputStream fileOut;
		try
		{
			fileOut = new FileOutputStream(SERIALIZED_FILE);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			
			objectOut.writeObject(propertyList);
			objectOut.close();
		} catch (FileNotFoundException e)
		{
			System.err.println("File not found. Cannot save.");
		} catch (IOException e)
		{
			System.err.println("General IOException");
		}
	}

	/**
	 * A method that makes changes to commercial property persistent or adds new commercial properties to the list.
	 * If the ID is 0, it will create a new object and assign it the next available ID.
	 * If the ID is already taken, it will modify the existing object to match the one provided.
	 * @param obj An object to be added to the list. Must be a CommercialProperty object for this broker.
	 * @return A true if it is sucessfully saved, or false if not.
	 */
	@Override
	public boolean persist(Object obj)
	{
		CommercialProperty commProp = (CommercialProperty) obj;
		if (commProp.getId() == 0) 	//new property added
			return addProp(commProp);
		else 						//old property updated
			return updateProp(commProp);
	}

	/**
	 * A helper method for persist. Adds properties when the ID is 0.
	 * @param commProp The commercial property provided to persist method.
	 * @return A true if the object is added, or a false if it is not.
	 */
	private boolean addProp(CommercialProperty commProp)
	{
		commProp.setId(nextId++);
		propertyList.addLast(commProp);
		return true;
	}

	/**
	 * A helper method for persist. Modifies properties when the ID is not 0.
	 * @param commProp The commercial property provided to persist method.
	 * @return A true if the object is overwritten, or a false if it is not.
	 */
	private boolean updateProp(CommercialProperty commProp)
	{
		long changingId = commProp.getId();
		
		SLLNode testedPropertyNode = propertyList.getRoot();
		
		for (int i = 0; i < propertyList.size(); i++)
		{
			CommercialProperty testedElement = (CommercialProperty) testedPropertyNode.getElement();
			if (testedElement.getId() == changingId)
			{
				propertyList.set(commProp, i);
				break; //required so that it doesn't try to getNext from a non-existant node.
			}
			testedPropertyNode = testedPropertyNode.getNext();
		}
		return true;
	}

	/**
	 * A method to remove the object provided.
	 * Searches the propertyList based on the ID and removes that ID.
	 * @param obj the object to be removed
	 * @return A true if it is removed, or a false if it is not removed.
	 */
	@Override
	public boolean remove(Object obj)
	{
		CommercialProperty commProp = (CommercialProperty) obj;
		long removingId = commProp.getId();
		
		SLLNode testedPropertyNode = propertyList.getRoot();
		
		for (int i = 0; i < propertyList.size(); i++)
		{
			CommercialProperty testedElement = (CommercialProperty) testedPropertyNode.getElement();
			if (testedElement.getId() == removingId)
			{
				propertyList.remove(i);
				break; //required so that it doesn't try to getNext from a non-existant node.
			}
			testedPropertyNode = testedPropertyNode.getNext();
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
		ArrayList<CommercialProperty> searchResults = new ArrayList<CommercialProperty>();
		
		if (type.equals("legal description"))
			searchLegal(searchResults, item);
		else if (type.equals("id"))
			searchId(searchResults, item);
		else if (type.equals("quadrant"))
			searchQuadrant(searchResults, item);
		else if (type.equals("price"))
			searchPrice(searchResults, item);
		
		return searchResults;
	}
	
	/**
	 * A helper function that searches for legal descriptions.
	 * @param searchResults The results of the search in a list. Contained in parent method "search".
	 * @param item The term being searched for in the propertyList.
	 */
	private void searchLegal(ArrayList<CommercialProperty> searchResults, String item)
	{
		SLLNode testedPropertyNode = propertyList.getRoot();
		
		for (int i = 0; i < propertyList.size(); i++)
		{
			CommercialProperty testedElement = (CommercialProperty) testedPropertyNode.getElement();
			if (testedElement.getLegalDescription().equals(item))
			{
				searchResults.add(testedElement);
			}
			testedPropertyNode = testedPropertyNode.getNext();
		}
	}
	
	/**
	 * A helper function that searches for property IDs.
	 * @param searchResults The results of the search in a list. Contained in parent method "search".
	 * @param item The term being searched for in the propertyList.
	 */
	private void searchId(ArrayList<CommercialProperty> searchResults, String item)
	{
		SLLNode testedPropertyNode = propertyList.getRoot();
		
		for (int i = 0; i < propertyList.size(); i++)
		{
			CommercialProperty testedElement = (CommercialProperty) testedPropertyNode.getElement();
			if (testedElement.getId() == Long.parseLong(item))
			{
				searchResults.add(testedElement);
			}
			testedPropertyNode = testedPropertyNode.getNext();
		}
	}
	
	/**
	 * A helper function that searches for quadrants.
	 * @param searchResults The results of the search in a list. Contained in parent method "search".
	 * @param item The term being searched for in the propertyList.
	 */
	private void searchQuadrant(ArrayList<CommercialProperty> searchResults, String item)
	{
		SLLNode testedPropertyNode = propertyList.getRoot();
		
		for (int i = 0; i < propertyList.size(); i++)
		{
			CommercialProperty testedElement = (CommercialProperty) testedPropertyNode.getElement();
			if (testedElement.getQuadrant().equals(item))
			{
				searchResults.add(testedElement);
			}
			testedPropertyNode = testedPropertyNode.getNext();
		}
	}
	
	/**
	 * A helper function that searches for prices equal to or below the search item..
	 * @param searchResults The results of the search in a list. Contained in parent method "search".
	 * @param item The term being searched for in the propertyList.
	 */
	private void searchPrice(ArrayList<CommercialProperty> searchResults, String item)
	{
		SLLNode testedPropertyNode = propertyList.getRoot();
		
		for (int i = 0; i < propertyList.size(); i++)
		{
			CommercialProperty testedElement = (CommercialProperty) testedPropertyNode.getElement();
			if (testedElement.getAskingPrice() <= Double.parseDouble(item))
			{
				searchResults.add(testedElement);
			}
			testedPropertyNode = testedPropertyNode.getNext();
		}
	}




	//=========================HELPER METHODS==========================
	/**
	 * Finds the highest current ID and sets it to the global field highId.
	 * Note that this value goes up by one each time property is added, so this is only used once during initialization.
	 */
	private void findHighestCurrentId()
	{
		CommercialProperty lastProperty = (CommercialProperty) propertyList.getLast();
		long lastid = lastProperty.getId();
		
		nextId = lastid + 1;
	}

}
