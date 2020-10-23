package saitMLS.persistence.clientale;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import saitMLS.problemDomain.clientale.*;
import saitMLS.exceptions.clientale.InvalidPhoneNumberException;
import saitMLS.exceptions.clientale.InvalidPostalCodeException;
import saitMLS.persistence.Broker;
/**
 * The ClientBroker class handles the random access file containing Clients and manages its data.
 * The class is instantiated using .getBroker() method which returns a ClientBroker object.
 * @author 729380
 * @version 1
 */
public class ClientBroker implements Broker
{
	/**
	 * ClientBroker object, singleton. Belongs to class. Returned when using getBroker method.
	 */
	private static ClientBroker clientBroker;
	/**
	 * A number representing the highest ID currently in the array.
	 */
	private long highId;
	/**
	 * The location of the clients.txt file for use in creating the RAF file.
	 */
	private static final String INPUT_FILE = "res/clients.txt";
	/**
	 * The location of the RAF file to be created if necessary.
	 */
	private static final String RANDOM_FILE = "res/clients.bin";
	/**
	 * A random access file containing the clients read from the text file.
	 */
	private RandomAccessFile raf;
	
	/**
	 * The ClientBroker constructor. Publically accessed through getBroker method.
	 * @throws FileNotFoundException in the case that the file is not found.
	 */
	private ClientBroker() throws FileNotFoundException
	{	//Creates the RAF
		File file = new File(RANDOM_FILE);
		if (!file.exists())
		{
			raf = new RandomAccessFile(file, "rw"); //creates new RAF with read write privelages
			createCustomerBinaryFile();				//generates binary file from text file
		}else //already exists, makes the file a RAF
		{
			raf = new RandomAccessFile(file, "rw");
		}
		findHighestCurrentId(); //Gets the highest current ID and sets it as the global field.
	}
	
	/**
	 * The publically accessed instantiation method of the ClientBroker class.
	 * @return a ClientBroker object. Always returns the same object (is singleton).
	 */
	public static ClientBroker getBroker()
	{
		if(clientBroker == null)
		{ //If clientBroker is null, then it creates a new ClientBroker.
		  //Else it returns the same ClientBroker that is available globally in this class.
			try
			{
				clientBroker = new ClientBroker();
			} catch (FileNotFoundException e)
			{
				System.err.println("File not found.");
			}
		}
		return clientBroker;
	}
	
	/**
	 * A method that creates the clients.bin file if it does not already exist.
	 * Uses the clients.txt file to do this. Relies on the private writeClient class.
	 */
	private void createCustomerBinaryFile()
	{
		//Only runs if file doesnt exist. otherwise it reads the last file
		//Creates from text file
		int incrementedID = 1; //Starts at 1 because there is no "0" for ID.
		File file = new File(INPUT_FILE);
		try (Scanner in = new Scanner(file)) {
			while (in.hasNextLine()) {
				Client client;
				try
				{
					client = new Client(in.nextLine());
					client.setId(incrementedID++);
					writeClient(client);
				} catch (InvalidPostalCodeException e)
				{
					System.err.println("Error writing client due to invalid postal code.");
				} catch (InvalidPhoneNumberException e)
				{
					System.err.println("Error writing client due to invalid phone number.");
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("File not found.");
		}
	}
	
	/**
	 * A method that writes Client objects to a random access file.
	 * @param obj a Client object that is to be written to the RAF.
	 */
	private void writeClient(Client obj)
	{ //Writes to position you're at in RAF
		try {
			//Total 130 bytes
			raf.writeBoolean(obj.isActive()); // 1 Byte
			raf.writeLong(obj.getId()); // 8 Bytes
			raf.writeUTF(String.format("%20s", obj.getFirstName())); // 22 Bytes
			raf.writeUTF(String.format("%20s", obj.getLastName())); // 22 Bytes
			raf.writeUTF(String.format("%50s", obj.getAddress())); // 52 Bytes
			raf.writeUTF(String.format("%7s", obj.getPostalCode())); // 9 Bytes
			raf.writeUTF(String.format("%12s", obj.getPhoneNumber())); // 14 Bytes
			raf.writeChar(obj.getClientType()); // 2 Bytes
		} catch (IOException e) {
			//In case the file is not found or there is a directory problem. Basic IOException issues.
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * A method to close the random access file. This saves the information to the binary file.
	 * It also releases any allocated resources associated with this file.
	 */
	@Override
	public void closeBroker()
	{
		try
		{
			raf.seek(0);
			raf.close();
		} catch (IOException e)
		{
			System.err.println(e.getMessage());
		}
		
	}

	/**
	 * A method that saves any changes made to a Client object to the random access file or creates
	 * a new object if the object begins with an ID that equals 0
	 * @param obj a Client object to be updated or added.
	 * @return a true if the RAF is updated (or added), a false if it is unable to do so.
	 */
	@Override
	public boolean persist(Object obj)
	{
		//if object's ID is 0, add to maxID. if object's ID is #, save to #
		//raf automatically overwrites, not inserts
		Client client = (Client) obj;
		if (client.getId() == 0) 	//new client
			return addClient(client);
		else 						//old client
			return updateClient(client);
	}
	
	/**
	 * A method that adds a new client if the client given has an ID of 0. Changes its ID to the next highest ID.
	 * @param client a Client object with an ID of 0 to be added to the end of the random access file
	 * @return A true if the client is successfully added, or false if it is not added (due to IOException).
	 */
	private boolean addClient(Client client)
	{
		try {
			client.setId(++highId); //Adds one to the highest ID (before using)
			raf.seek(raf.length()); //Goes to the end
			writeClient(client); //Writes another RAF object
			return true;
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}
	
	/**
	 * A method that changes an existing client if the ID matches another client.
	 * @param client A previously existing client object (or an object with an existing ID).
	 * @return A true if the client is overwritten or a false if the client cannot be overwritten.
	 */
	private boolean updateClient(Client client)
	{
		try {
			long offsetPosition = searchClientOffset(client.getId()); //Sets the position to the client's position in RAF
			if (offsetPosition >= 0) { //only runs if offset is greater than 0 (not -1, which searchClientOffset can return)
				raf.seek(offsetPosition); //Uses the position, seeks to where the client is
				writeClient(client); //Writes the client
				return true;
			}else{
				return false; //When ID not found
			}

		} catch (IOException e) {
			System.err.println(e.getMessage());
			return false; //When error occurs.
		}
	}
	
	/**
	 * A method that logically deletes an object from the RAF by setting the first field (active) to false.
	 * The search method then checks if it is false and will not return inactive objects.
	 * @param obj A client object input from the program that is to be logically deleted from the RAF and excluded from searches.
	 * @return a true if active == false or a false if active cannot be set to false or another error occurs. 
	 */
	@Override
	public boolean remove(Object obj)
	{
		//set object's active value to false, overwrite boolean.
		Client client = (Client) obj; //Sets input object to a Client object
		boolean flag = false;
		try {
			long offSet = searchClientOffset(client.getId());
			if (offSet >= 0) {
				raf.seek(offSet);
				raf.writeBoolean(false);
				flag = true;
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return flag;
		}
		return flag;
	}
	
	/**
	 * A helper method that finds a Client object in the RAF based on its ID.
	 * This method is very important and is used often in other methods of this class that deal with the RAF.
	 * @param clientid A long variable containing a client's ID.
	 * @return A long variable that is the position of a client object in the RAF. A positive value if the object is found or -1 if it is not found (or an error occurs).
	 */
	private long searchClientOffset(long clientid) {
		//Gets the offset of the Client object that you're using so that we can overwrite parts of it
		try {
			raf.seek(0);
			for (long i = 0; i < raf.length(); i += Client.SIZE) {
				Client client = readClient();
				if (client.getId() == clientid && client.isActive()) { //Checks if client is active and has the same ID
					return i;
				}
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return -1; //returns negative one if client ID isnt found
	}
	
	/**
	 * From the current position of the random access file it reads the next 105 bytes and creates a Client object from them.
	 * These fields include: active, id, firstName, lastName. address, postalCode, phoneNumber, and clientType.
	 * @return A client object that is read from the random access file at the current position.
	 * @throws IOException in case the file is not found or another IO issue occurs.
	 */
	private Client readClient() throws IOException
	{
		//Reads the client at the current point in the RAF
		Client client = new Client();
		
		client.setActive(raf.readBoolean());
		client.setId(raf.readLong());
		client.setFirstName(raf.readUTF().trim());//Reads and trims the formatted firstName string.
		client.setLastName(raf.readUTF().trim());//Reads and trims the formatted lastName string.
		client.setAddress(raf.readUTF().trim());//Reads and trims the formatted address string.
		try
		{
			client.setPostalCode(raf.readUTF()); //Does not need to be trimmed, as it is required to be 7 in length.
		} catch (InvalidPostalCodeException e)
		{
			System.err.println("Invalid Postal Code");
		}
		try
		{
			client.setPhoneNumber(raf.readUTF()); //Does not need to be trimmed, as it is required to be 12 in length
		} catch (InvalidPhoneNumberException e)
		{
			System.err.println("Invalid Phone Number");
		}
		client.setClientType(raf.readChar());
		
		return client;
	}
	
	/**
	 * Finds the highest current ID and sets it to the global field highId.
	 * Note that this value goes up by one each time addClient is run, so this is only used once during initialization.
	 */
	private void findHighestCurrentId()
	{
		long positionOfLastItem;
		try {
			positionOfLastItem = raf.length() - Client.SIZE;
			raf.seek(positionOfLastItem); //Goes to last object
			Client client = readClient(); //Reads last object
			highId = client.getId(); //Takes ID of last object, this is the highest ID.
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * A search function that returns active objects that match the search item and type.
	 * @param item The "item" being searched for. The user input.
	 * @param type The type of item being searched for. In this case: "id", "name" for lastname, or "type".
	 * @return A List containing all of the results of the search.
	 */
	@Override
	public List search(String item, String type)
	{	//Searches by name, type, and ID
		ArrayList<Client> searchResults = new ArrayList<Client>();
		
		if (type.equals("id"))
			searchId(searchResults, item);
		else if (type.equals("name"))
			searchName(searchResults, item);
		else if (type.equals("type"))
			searchType(searchResults, item);
		
		return searchResults;
	}
	
	/**
	 * A helper method for the search function that searches specifically for last names. Adds to the ArrayList parameter.
	 * @param al An ArrayList to be added to and returned in the search(item, type) function.
	 * @param item The last name being searched for in the search function.
	 */
	private void searchName(ArrayList<Client> al, String item)
	{
		try
		{
			Client testedObject;
			raf.seek(0);
			long numObjects = raf.length() / Client.SIZE;
			for (int i = 0; i < numObjects; i++)
			{
				testedObject = readClient();
				if(testedObject.isActive())
				{
					if (testedObject.getLastName().equals(item))
					{
						al.add(testedObject);
					}
				}
			}
		} catch (IOException e)
		{
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * A helper method for the search function that searches specifically for type. Adds to the ArrayList parameter.
	 * @param al An ArrayList to be added to and returned in the search(item, type) function.
	 * @param item The type being searched for in the search function. This can be either 'C' or 'R'.
	 */
	private void searchType(ArrayList<Client> al, String item)
	{
		try
		{
			Client testedObject;
			raf.seek(0);
			long numObjects = raf.length() / Client.SIZE;
			for (int i = 0; i < numObjects; i++)
			{
				testedObject = readClient();
				if(testedObject.isActive())
				{
					if (testedObject.getClientType() == item.charAt(0))
					{
						al.add(testedObject);
					}
				}
			}
		} catch (IOException e)
		{
			System.err.println(e.getMessage());
		}
	}

	/**
	 * A helper method for the search function that searches specifically for IDs. Adds to the ArrayList parameter.
	 * @param al An ArrayList to be added to and returned in the search(item, type) function.
	 * @param item The id being searched for in the search function.
	 */
	private void searchId(ArrayList<Client> al, String item)
	{
		try
		{
			Client testedObject;
			raf.seek(0);
			long numObjects = raf.length() / Client.SIZE;
			for (int i = 0; i < numObjects; i++)
			{
				testedObject = readClient();
				if(testedObject.isActive())
				{
					if (testedObject.getId() == Long.parseLong(item))
					{
						al.add(testedObject);
					}
				}
			}
		} catch (IOException e)
		{
			System.err.println(e.getMessage());
		}
	}
}
