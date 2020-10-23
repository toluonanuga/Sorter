

import javax.swing.*;
import javax.swing.event.*;

import saitMLS.exceptions.clientale.InvalidPhoneNumberException;
import saitMLS.exceptions.clientale.InvalidPostalCodeException;
import saitMLS.persistence.clientale.ClientBroker;
import saitMLS.problemDomain.clientale.Client;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
/**
 * CustomerWindow class -	This class creates a panel to be used in the window and sets its behaviors.
 * Changed some behaviors:
 * - Now sets ID to default 0 when opening
 * - Now has an output for failure or success of save/delete
 * 
 * @author 729380
 * @version 2
 */

public class CustomerWindow extends JFrame
{
	/**
	 * The search type when searching for Clients (id, last name, and client type)
	 */
	private String searchType = "";
	/**
	 * Serial Version = 1L
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * A list model containing all of the clients found in the search function.
	 */
	private DefaultListModel<Client> list;
	/**
	 * A list model containing strings representing all of the clients found in the search function, display in the listArea.
	 */
	private DefaultListModel<String> displayList;
	/**
	 * A JList object, shown in the bottom left of the GUI, showing clients found in the search.
	 */
	private JList<String> listArea;
	/**
	 * A scroll pane object, allowing the listArea to scroll down if the search results in more than 13 entries.
	 */
	private JScrollPane listScrollPane;
	/**
	 * A group of radio buttons representing search types.
	 */
	private final ButtonGroup searchButtons = new ButtonGroup();
	/**
	 * The search input field, giving a string to the search function.
	 */
	private JTextField txtSearchHere;
	/**
	 * A group of buttons representing the type of client (Residential or Commercial)
	 */
	private final ButtonGroup clientTypeButtons = new ButtonGroup();
	/**
	 * A field containing a client's ID.
	 */
	private JTextField clientIDField;
	/**
	 * A field containing a client's first name.
	 */
	private JTextField firstNameField;
	/**
	 * A field containing a client's last name.
	 */
	private JTextField lastNameField;
	/**
	 * A field containing a client's address.
	 */
	private JTextField addressField;
	/**
	 * A field containing a client's postal code.
	 */
	private JTextField postalCodeField;
	/**
	 * A field containing a client's phone number.
	 */
	private JTextField phoneNumField;
	/**
	 * A radio button for selecting a search for a client's ID.
	 */
	private JRadioButton rdbtnClientId;
	/**
	 * A radio button for selecting a search for a client's last name.
	 */
	private JRadioButton rdbtnLastName;
	/**
	 * A radio button for selecting a search for a client's type (Commercial or Residential).
	 */
	private JRadioButton rdbtnClientType;
	/**
	 * A button for activating the search function.
	 */
	private JButton btnSearch;
	/**
	 * A button for activating the clear search function. Also clears all other fields.
	 */
	private JButton btnClearSearch;
	/**
	 * A label that displays some information for the user (whether or not a client was added, saved, or deleted).
	 */
	private JLabel outputLabel;
	/**
	 * A radio button representing that a client's type is Commercial.
	 */
	private JRadioButton rdbtnCommercial;
	/**
	 * A radio button representing that a client's type is Residential.
	 */
	private JRadioButton rdbtnResidential;
	/**
	 * A button for activating the save function. Saves a customer's information, or makes a new one if ID = 0.
	 */
	private JButton btnSave;
	/**
	 * A button for activating the delete function. Deletes currently selected client.
	 */
	private JButton btnDelete;
	/**
	 * A broker object linking this to the backend.
	 */
	private ClientBroker cBroker = null;
	
	/**
	 * The constructor only initializes cBroker.
	 */
	public CustomerWindow() 
	{
		cBroker = ClientBroker.getBroker();
	}
	
	/**
	 * The central method of CustomerWindow creates all of the panels, buttons, lists, fields, and labels.
	 * The method also contains an ListSelectionListener for when the listArea is changed.
	 * @return A JPanel object to be added to an ArrayList in the MainWindow class. Used to display the GUI of Client.
	 */
	public JPanel createPanel()
	{
		JPanel contentPanel = new JPanel();
		
		JLabel welcomeLabel = new JLabel("Client Management Screen");
		welcomeLabel.setBackground(Color.LIGHT_GRAY);
		welcomeLabel.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 30));
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(welcomeLabel, BorderLayout.NORTH);
		
		JPanel belowTitlePanel = new JPanel();
		contentPanel.add(belowTitlePanel, BorderLayout.CENTER);
		belowTitlePanel.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel searchAndListPanel = new JPanel();
		belowTitlePanel.add(searchAndListPanel);
		searchAndListPanel.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel searchOptionsPanel = new JPanel();
		searchAndListPanel.add(searchOptionsPanel);
		searchOptionsPanel.setLayout(new GridLayout(5, 1, 0, 0));
		
		JLabel lblSearchClients = new JLabel("Search Clients");
		lblSearchClients.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSearchClients.setHorizontalAlignment(SwingConstants.CENTER);
		searchOptionsPanel.add(lblSearchClients);
		
		JLabel lblSelectTypeOf = new JLabel("  Select type of search to be performed");
		searchOptionsPanel.add(lblSelectTypeOf);
		
		JPanel radioButtonsPanel = new JPanel();
		searchOptionsPanel.add(radioButtonsPanel);
		
		rdbtnClientId = new JRadioButton("Client ID");
		searchButtons.add(rdbtnClientId);
		rdbtnClientId.addActionListener(new ButtonListener());
		radioButtonsPanel.add(rdbtnClientId);
		
		rdbtnLastName = new JRadioButton("Last Name");
		searchButtons.add(rdbtnLastName);
		rdbtnLastName.addActionListener(new ButtonListener());
		radioButtonsPanel.add(rdbtnLastName);
		
		rdbtnClientType = new JRadioButton("Client Type");
		searchButtons.add(rdbtnClientType);
		rdbtnClientType.addActionListener(new ButtonListener());
		radioButtonsPanel.add(rdbtnClientType);
		
		JLabel lblEnterTheSearch = new JLabel("  Enter the search parameter below");
		searchOptionsPanel.add(lblEnterTheSearch);
		
		JPanel parameterAndButtonPanel = new JPanel();
		searchOptionsPanel.add(parameterAndButtonPanel);
		parameterAndButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		txtSearchHere = new JTextField();
		txtSearchHere.setToolTipText("Enter the search parameter here");
		parameterAndButtonPanel.add(txtSearchHere);
		txtSearchHere.setColumns(20);
		
		btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ButtonListener());
		parameterAndButtonPanel.add(btnSearch);
		
		btnClearSearch = new JButton("Clear Search");
		btnClearSearch.addActionListener(new ButtonListener());
		parameterAndButtonPanel.add(btnClearSearch);
		
		JPanel listPanel = new JPanel();
		searchAndListPanel.add(listPanel);
		
		//to add to list, use list.addElement, most complicated part
		list = new DefaultListModel<>();
		displayList = new DefaultListModel<>();		
		listArea = new JList<String>(displayList);	//to receive from list, use displayList.getSelectedIndex() and listModel.get(i)
		String lineWidth = "1234567890123456789012345678901234567890"; //valueChanged action event is for these lists
		listArea.setPrototypeCellValue(lineWidth);
		listArea.setVisibleRowCount(13);
		listScrollPane = new JScrollPane(listArea);
		listPanel.add(listScrollPane);
		listArea.addListSelectionListener(new ListSelectionListener()
		{
			/**
			 * On the selection being changed (selection index), this method takes the client's information
			 * and sends it to the fields and inputs on the right side of the GUI.
			 * @param event Not used. Not sure what it's for.
			 */
			@Override
			public void valueChanged(ListSelectionEvent event)
			{
				if(listArea.getSelectedIndex() >= 0)
				{
					Client currentClient = list.get(listArea.getSelectedIndex());
					//Place values in fields
					clientIDField.setText(Long.toString(currentClient.getId()));
					firstNameField.setText(currentClient.getFirstName());
					lastNameField.setText(currentClient.getLastName());
					addressField.setText(currentClient.getAddress());
					postalCodeField.setText(currentClient.getPostalCode());
					phoneNumField.setText(currentClient.getPhoneNumber());
					//Set button to correct value
					switch(currentClient.getClientType())
					{
					case 'C':
						rdbtnCommercial.setSelected(true);
						break;
					case 'R':
						rdbtnResidential.setSelected(true);
						break;
					}
				}
			}
		});
		
		JPanel clientInfoPanel = new JPanel();
		belowTitlePanel.add(clientInfoPanel);
		clientInfoPanel.setLayout(new GridLayout(10, 1, 0, 0));
		
		JLabel lblClientInformation = new JLabel("Client Information");
		lblClientInformation.setHorizontalAlignment(SwingConstants.CENTER);
		lblClientInformation.setFont(new Font("Tahoma", Font.BOLD, 11));
		clientInfoPanel.add(lblClientInformation);
		
		JPanel clientIDPanel = new JPanel();
		clientInfoPanel.add(clientIDPanel);
		clientIDPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblClientId = new JLabel("Client ID:");
		clientIDPanel.add(lblClientId);
		
		clientIDField = new JTextField("0");
		clientIDPanel.add(clientIDField);
		clientIDField.setEditable(false);
		clientIDField.setColumns(10);
		
		JPanel firstNamePanel = new JPanel();
		clientInfoPanel.add(firstNamePanel);
		firstNamePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblFirstName = new JLabel("First Name:");
		firstNamePanel.add(lblFirstName);
		
		firstNameField = new JTextField();
		firstNamePanel.add(firstNameField);
		firstNameField.setColumns(20);
		
		JPanel lastNamePanel = new JPanel();
		clientInfoPanel.add(lastNamePanel);
		lastNamePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblLastName = new JLabel("Last Name:");
		lastNamePanel.add(lblLastName);
		
		lastNameField = new JTextField();
		lastNamePanel.add(lastNameField);
		lastNameField.setColumns(20);
		
		JPanel addressPanel = new JPanel();
		clientInfoPanel.add(addressPanel);
		addressPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblAddress = new JLabel("Address:");
		addressPanel.add(lblAddress);
		
		addressField = new JTextField();
		addressPanel.add(addressField);
		addressField.setColumns(20);
		
		JPanel postalCodePanel = new JPanel();
		clientInfoPanel.add(postalCodePanel);
		postalCodePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblPostalCode = new JLabel("Postal Code:");
		postalCodePanel.add(lblPostalCode);
		
		postalCodeField = new JTextField();
		postalCodePanel.add(postalCodeField);
		postalCodeField.setColumns(7);
		
		JPanel phoneNumPanel = new JPanel();
		clientInfoPanel.add(phoneNumPanel);
		phoneNumPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblPhoneNumber = new JLabel("Phone Number:");
		phoneNumPanel.add(lblPhoneNumber);
		
		phoneNumField = new JTextField();
		phoneNumPanel.add(phoneNumField);
		phoneNumField.setColumns(12);
		
		JPanel clientTypePanel = new JPanel();
		clientInfoPanel.add(clientTypePanel);
		clientTypePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblClientType = new JLabel("Client Type:");
		clientTypePanel.add(lblClientType);
		
		rdbtnCommercial = new JRadioButton("Commercial");
		clientTypeButtons.add(rdbtnCommercial);
		rdbtnCommercial.addActionListener(new ButtonListener());
		clientTypePanel.add(rdbtnCommercial);
		
		rdbtnResidential = new JRadioButton("Residential");
		clientTypeButtons.add(rdbtnResidential);
		rdbtnResidential.addActionListener(new ButtonListener());
		clientTypePanel.add(rdbtnResidential);
		
		JPanel saveDeleteClosePanel = new JPanel();
		clientInfoPanel.add(saveDeleteClosePanel);
		saveDeleteClosePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnSave = new JButton("Save to Selected");
		btnSave.addActionListener(new ButtonListener());
		saveDeleteClosePanel.add(btnSave);
		
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ButtonListener());
		saveDeleteClosePanel.add(btnDelete);
		
		JPanel outputPanel = new JPanel();
		outputLabel = new JLabel();
		outputPanel.add(outputLabel);
		clientInfoPanel.add(outputPanel);
		
		return contentPanel;
	}
	
	/**
	 * A simple method containing only cBroker.closeBroker(), to save and close the broker.
	 */
	public void closeWindow()
	{
		cBroker.closeBroker();
	}
	
	/**
	 * Inner class ButtonListener - This class controls all of the ActionListener events that occur when pressing
	 * 								or selecting GUI elements.
	 * @author 729380
	 *
	 */
	private class ButtonListener implements ActionListener
	{
		/**
		 * Controls all actions. All buttons are here, including:
		 * searchType controls, Save, Delete, Clear Search, and Search.
		 * @param e event variable, not used.
		 */
		@Override
		public void actionPerformed(ActionEvent e)
		{
			//Radio buttons
			if(e.getSource() == rdbtnClientId)
			{
				searchType = "id";
			}
			if(e.getSource() == rdbtnClientType)
			{
				searchType = "type";
			}
			if(e.getSource() == rdbtnLastName)
			{
				searchType = "name";
			}
			
			if(e.getSource() == btnSearch)
			{
				list.clear();
				displayList.clear();
				List<Client> clients = cBroker.search(txtSearchHere.getText(), searchType);
				for(int i = 0; i < clients.size(); i++)
				{
					list.addElement(clients.get(i));
					displayList.addElement(clients.get(i).getId() + " - " + clients.get(i).getLastName() + ", "
							+ clients.get(i).getFirstName() + " - " + clients.get(i).getClientType());
				}
			}
			
			if(e.getSource() == btnClearSearch)
			{
				list.clear();
				displayList.clear();
				txtSearchHere.setText("");
				clientIDField.setText("0");
				firstNameField.setText("");
				lastNameField.setText("");
				addressField.setText("");
				postalCodeField.setText("");
				phoneNumField.setText("");
			}
			
			if(e.getSource() == btnSave)
			{
				boolean testFlag;
				char clientType;
				if(rdbtnCommercial.isSelected())
					clientType = 'C';
				else
					clientType = 'R';
				
				try
				{
					Client addedClient = new Client(Long.parseLong(clientIDField.getText()), firstNameField.getText(), 
							lastNameField.getText(), addressField.getText(), postalCodeField.getText(), phoneNumField.getText(), clientType);
					testFlag = cBroker.persist(addedClient);
					if (testFlag)
					{	
						outputLabel.setForeground(Color.GREEN);
						outputLabel.setText("Client has been added.");
					}
					else
					{
						outputLabel.setForeground(Color.RED);
						outputLabel.setText("Client has not been added.");
					}
				} catch (NumberFormatException e1)
				{
					System.out.println("Invalid number format.");
				} catch (InvalidPhoneNumberException e1)
				{
					JOptionPane.showMessageDialog(null, "The phone number is invalid.\nTry the format ###-###-####");
				} catch (InvalidPostalCodeException e1)
				{
					JOptionPane.showMessageDialog(null, "The postal code is invalid.\nTry the format A1A 1A1");
				}
			}
			if(e.getSource() == btnDelete)
			{
				Client removingClient = list.getElementAt(listArea.getSelectedIndex());
				boolean testFlag = cBroker.remove(removingClient);
				
				if (testFlag)
				{	
					outputLabel.setForeground(Color.GREEN);
					outputLabel.setText("Client has been deleted.");
				}
				else
				{
					outputLabel.setForeground(Color.RED);
					outputLabel.setText("Client has not been deleted.");
				}
				
				txtSearchHere.setText("");
				clientIDField.setText("0");
				firstNameField.setText("");
				lastNameField.setText("");
				addressField.setText("");
				postalCodeField.setText("");
				phoneNumField.setText("");
				
				displayList.remove(listArea.getSelectedIndex());
			}
		}
	}
}
