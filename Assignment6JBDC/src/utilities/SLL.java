package utilities;

import java.io.Serializable;
/**
 * Single Linked List Class. Implements custom "List" interface and serializable.
 * This uses the class "SLLNode" to store elements in a list of connected objects.
 * Each object (except the last) has a reference to the next node in the list.
 * 
 * Thoroughly tested with 100% coverage in SLLTest.
 * @author 729380
 * @version 2, April 9th, 2017
 */
public class SLL implements List, Serializable
{
	/**
	 * A serial version. -2037236127081009218L
	 */
	private static final long serialVersionUID = -2037236127081009218L;
	/**
	 * The first object in the list. Required to access any other object.
	 */
	private SLLNode root;
	/**
	 * The size of the list. Decreased when removing, increased when adding, set to 0 when clearing.
	 */
	private int length;

	/**
	 * A specified constructor that sets length to 0, and root to null.
	 */
	public SLL()
	{
		length = 0;
		root = null;
	}
	/**
	 * Adds the element to the first position in the list. This becomes the root of the list.
	 * If adding to a list with content, this also increments the index of all other elements by 1.
	 * @param element The element you want to add to the beginning of the list
	 * @return a true if sucessfully added
	 */
	@Override
	public boolean addFirst(Object element)
	{
		if (root == null) //Empty list
		{
			root = new SLLNode(element);
			length++;
		}
		else //non empty linkedlist
		{
			SLLNode newNode = new SLLNode(element); //creates a new node
			newNode.setNext(root);	//sets old root to new node's "next"
			root = newNode; //sets new node to root
			length++;
		}
		return true;
	}
	
	/**
	 * A method that adds the element provided to the last position in the list.
	 * Technically this adds to the first position if the list is empty.
	 * @param element The element you want to add to the beginning of the list
	 * @return a true if sucessfully added
	 */
	@Override
	public boolean addLast(Object element)
	{
		if (root == null) {
			return addFirst(element);
		} else {
			return add(element, length);
		}
	}

	/**
	 * A method that adds the element provided to the position provided (starting at 0).
	 * This shifts up the index of all elements after it by 1.
	 * @param element The element you want to add to the beginning of the list
	 * @param position Where you want to add the element into the list at.
	 * @return a true if sucessfully added
	 * @throws IndexOutOfBoundsException if the position < 0 || position > length
	 */
	@Override
	public boolean add(Object element, int position) throws IndexOutOfBoundsException
	{
		if ((position < 0) || (position > length)) {
			throw new IndexOutOfBoundsException();
		}
		else if (position == 0) {
			return addFirst(element);
		} else {
			SLLNode previousNode = findNode(position - 1);
			SLLNode nextNode = previousNode.getNext();
			SLLNode newNode = new SLLNode(element);
			previousNode.setNext(newNode);
			newNode.setNext(nextNode);
			length++;
			return true;
		}
	}

	/**
	 * Removes everything from the list from the beginning to the end,
	 * and removes all references in objects.
	 * This also resets the length of the list.
	 */
	@Override
	public void clear()
	{	//This could likely be replaced by just setting the root's "next" reference to null.
		//This example is much more thorough, however.
		SLLNode currentNode = root;
		SLLNode lastNode;
		for (int i = 0; i < length; i++)
		{
			lastNode = currentNode;
			currentNode = currentNode.getNext();
			lastNode.setElement(null);
			lastNode.setNext(null);
		}
		length = 0;
	}

	/**
	 * Removes the first object from the list and sets the next object to be the first.
	 * This also lowers the index of every list item by one.
	 * @return the removed element, or null if the list is empty.
	 */
	@Override
	public Object removeFirst()
	{
		if (root == null)
			return null;
		else
		{
			SLLNode oldNode = root; //saves root to "old node"
			root = root.getNext();
			length--;
			oldNode.setNext(null);
			return oldNode.getElement(); //returns old node's element
		}
	}

	/**
	 * Removes the last object from the list.
	 * @return the removed element, or null if the list is empty.
	 */
	@Override
	public Object removeLast()
	{
		if (root == null)
			return null;
		else
		{
			if (length > 1) //when you can get the node before oldNode
			{
				SLLNode priorNode = findNode(length - 2); //gets the second last node
				SLLNode oldNode = priorNode.getNext();
				priorNode.setNext(null); //sets the second last node to the last node.
				
				length--;
				return oldNode.getElement(); //returns old node's element
			}
			else //only one node: the root
				return removeFirst();
		}
	}

	/**
	 * Removes the object at the specified index, and decreases
	 * the index by 1 for all objects after it.
	 * @param position the position of the object to be removed
	 * @return The element being removed.
	 * @throws IndexOutOfBoundsException if the position <0 || position >= length
	 */
	@Override
	public Object remove(int position) throws IndexOutOfBoundsException
	{
		if (position == 0) //added this to prevent null pointer exceptions in larger lists
		{
			return removeFirst();
		}
		else
		{
			if(position < 0 || position >= length)
				throw new IndexOutOfBoundsException("Position not in index.");
			else
			{
				SLLNode removingNode = findNode(position);
				if (!(root == removingNode)) //if it's not the first node
				{
					SLLNode priorNode = findNode(position - 1);
					priorNode.setNext(removingNode.getNext()); //changes the node before it's reference
				}
				removingNode.setNext(null);
				length--;
				return removingNode.getElement();
			}
		}
	}

	/**
	 * Gets the element of the first object in the list.
	 * @return the element of the first object in the list.
	 */
	@Override
	public Object getFirst()
	{
		return get(0);
	}

	/**
	 * Gets element of the last object in the list.
	 * @return the element of the last object in the list.
	 */
	@Override
	public Object getLast()
	{
		int indexOfLast = length - 1;
		return get(indexOfLast); 
	}

	/**
	 * Gets the element of the object in the list at the position specified.
	 * @param position the position of the element you want to get.
	 * @return the element at the position you gave.
	 * @throws an IndexOutOfBoundsException if the position < 0 || position >= length
	 */
	@Override
	public Object get(int position) throws IndexOutOfBoundsException
	{
		if (!(length == 0))
		{
			return findNode(position).getElement();
		}
		else
			return null;
	}

	/**
	 * A method to set the element at a position to a new element.
	 * @param element The element you want to use to replace the old element.
	 * @param position The index of the element you wish to replace.
	 * @return The old element from that position
	 * @throws an IndexOutOfBoundsException if position < 0 || position >= length
	 */
	@Override
	public Object set(Object element, int position) throws IndexOutOfBoundsException
	{
		Object oldElement; //creates an Object to store the old element in it.
		SLLNode changingNode = findNode(position); //Gets the node we're changing.
		oldElement = changingNode.getElement(); //puts old element into oldElement
		changingNode.setElement(element);
		return oldElement;
	}

	/**
	 * Checks if the element provided is contained within the list.
	 * @param element The element to be checked for.
	 * @return True if the element is contained, false if it is not.
	 */
	@Override
	public boolean contains(Object element)
	{
		int index = indexOf(element);
		if (index == -1)
			return false;
		else
			return true;
	}

	/**
	 * Finds the element's position within the list.
	 * @param element The element to be searched for.
	 * @return an integer of the element's position, or -1 if it is not found.
	 */
	@Override
	public int indexOf(Object element)
	{
		SLLNode currentNode = root;
		for(int i = 0; i < length; i++)
		{
			if(currentNode.getElement().equals(element))
			{
				return i;
			}
			currentNode = currentNode.getNext();
		}
		return -1;
	}

	/**
	 * A simple getLength method, effectively.
	 * @return The length of the list as an integer.
	 */
	@Override
	public int size()
	{
//		boolean endFlag = false;
//		SLLNode currentNode = root; //index of 0
//		int index = 0;
//		while (!endFlag)
//		{
//			if (currentNode.getNext() == null)
//				endFlag = true;
//			else
//			{
//				currentNode = currentNode.getNext();
//				index++;
//			}
//		}
//		length = index + 1;
		return length; //will only be 0 if cleared or never added to
	}

	/**
	 * Checks if the list is empty (by checking if the root exists).
	 * @return a true if it is empty, or a false if it has contents.
	 */
	@Override
	public boolean isEmpty()
	{
		if (root == null)
		{
			return true;
		}
		else
			return false;
	}
	
	/**
	 * A simple method to return the root of the SLL.
	 * Needed to allow other programs to run through without using get(1), get(2), and so on,
	 * because those run through the entire list each time.
	 * @return the root SLLNode.
	 */
	public SLLNode getRoot()
	{
		return root;
	}
	
	/**
	 * Finds a node within the list using its position and returns the node.
	 * @param pos The position of the node.
	 * @return An SLLNode containing an element and a reference to the next node.
	 */
	private SLLNode findNode(int pos)
	{
		if (pos<0 || pos>=length)
			throw new IndexOutOfBoundsException("Index is out of bounds.");
		SLLNode currentNode = root;
		for(int i = 0; i < pos; i++)
		{
			currentNode = currentNode.getNext();
		}
		return currentNode;
	}
	
	/**
	 * A replacement toString method.
	 * Simply outputs the list's contents like [element1, element2, element3...] and so on.
	 * @return A string containing all of the list's contents.
	 */
	@Override
	public String toString() {
		if (length == 0)
			return "[]";
		String output = "";
		output += "[";
		SLLNode currentNode = root;
		for (int i = 0; i < length; i++) {
			output += currentNode.getElement();
			currentNode = currentNode.getNext();
			if (i == length - 1) {
				output += "]";
			} else {
				output += ",";
			}
		}
		return output;
	}
}
