package utilities;

import java.io.Serializable;

/*
 * So there's no previous element. That's in a double linked list.
 * This is a single linked list, so it only points to the next element.
 */
/**
 * A node to be used in a Single Linked List class.
 * Contains an element (any Object), and a reference to the next SLLNode in the list.
 * @author 729380
 * @version April 8th, 2017
 */
public class SLLNode implements Serializable
{
	/**
	 * A serial version. -2629294310166298929L
	 */
	private static final long serialVersionUID = -2629294310166298929L;
	/**
	 * An object contained within this node.
	 */
	private Object element;
	/**
	 * The next object in the list. null by default.
	 */
	private SLLNode next;
	
	/**
	 * A constructor for the node. Only requires an element.
	 * Is given a "next" object in the SLL Class.
	 * @param element The object to be stored in the node.
	 */
	public SLLNode(Object element)
	{
		this.element = element;
	}

	/**
	 * A simple method returning the object contained in the node.
	 * @return The object contained in the node. Usually needs to be casted.
	 */
	public Object getElement()
	{
		return element;
	}

	/**
	 * A simple method to change the element of the node.
	 * @param element The new element to be set in the node.
	 */
	public void setElement(Object element)
	{
		this.element = element;
	}

	/**
	 * A simple method to get the next node in the list.
	 * @return The next node in the list.
	 */
	public SLLNode getNext()
	{
		return next;
	}

	/**
	 * A simple method to change the next node in the list.
	 * @param next The node to become the next node in the list after this node.
	 */
	public void setNext(SLLNode next)
	{
		this.next = next;
	}
	
}
