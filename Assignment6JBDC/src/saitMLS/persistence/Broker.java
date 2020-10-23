package saitMLS.persistence;

import java.util.List;

/**
 * The Broker interface is implemented by ClientBroker, ResidentialPropertyBroker, and CommercialPropertyBroker.
 * The implemented methods work with a RAF to manage data.
 * 
 * @author 729380
 *
 */
public interface Broker
{
	/**
	 * A method that saves the broker and releases resources allocated.
	 */
	public void closeBroker();
	
	/**
	 * A method that saves or modifies data in the broker.
	 * @param obj An object of what will be saved to the RAF.
	 * @return true if saved, false if not saved
	 */
	public boolean persist(Object obj);
	
	/**
	 * A method that removes data from the broker.
	 * @param obj An object that will be removed from the RAF.
	 * @return true if removed, false if not removed.
	 */
	public boolean remove(Object obj);
	
	/**
	 * A method that searches the objects in the Broker. 
	 * @param item The search keyword. Examples are a name, ID, or other valid type.
	 * @param type The type of search being performed.
	 * @return A list containing all objects matching the search.
	 */
	public List search(String item, String type);
}
