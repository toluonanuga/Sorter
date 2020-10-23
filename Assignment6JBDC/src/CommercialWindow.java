
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import saitMLS.exceptions.property.InvalidLegalDescriptionException;
import saitMLS.persistence.property.CommercialPropertyBroker;
import saitMLS.problemDomain.property.CommercialProperty;

/**
 * 
 * CommercialWindow class - This class creates a panel to be used in the window
 * and sets its behaviors.
 * 
 * Changed some behaviors:
 * 
 * - Now sets ID to default 0 when opening
 * 
 * - Now has an output for failure or success of save/delete
 * 
 * 
 * 
 * @author 729380
 * 
 * @version 2
 * 
 * 
 * 
 */

public class CommercialWindow extends JFrame

{

	/**
	 * 
	 * A list model containing all of the properties found in the search function.
	 * 
	 */

	private DefaultListModel<CommercialProperty> list;

	/**
	 * 
	 * A list model containing strings representing all of the properties found in
	 * the search function, display in the listArea.
	 * 
	 */

	private DefaultListModel<String> displayList;

	/**
	 * 
	 * The search type when searching for properties (id, legal description, and
	 * quadrant of city, price)
	 * 
	 */

	private String searchType = "";

	/**
	 * 
	 * Serial Version = 1L
	 * 
	 */

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * A JList object, shown in the bottom left of the GUI, showing properties found
	 * in the search.
	 * 
	 */

	private JList<String> listArea; // Convert to client list later, or other lists

	/**
	 * 
	 * A scroll pane object, allowing the listArea to scroll down if the search
	 * results in more than 13 entries.
	 * 
	 */

	private JScrollPane listScrollPane;

	/**
	 * 
	 * A group of radio buttons representing search types.
	 * 
	 */

	private final ButtonGroup searchButtons = new ButtonGroup();

	/**
	 * 
	 * The search input field, giving a string to the search function.
	 * 
	 */

	private JTextField txtSearchHere;

	/**
	 * 
	 * A field containing a commercial property's ID.
	 * 
	 */

	private JTextField commPropIDField;

	/**
	 * 
	 * A field containing the property's legal description.
	 * 
	 */

	private JTextField legalDescField;

	/**
	 * 
	 * A field containing the property's address.
	 * 
	 */

	private JTextField addressField;

	/**
	 * 
	 * A string array containing all quadrants of the city, used in the quadrant
	 * combo box.
	 * 
	 */

	private String[] quadrants = { "NE", "SE", "SW", "NW" };

	/**
	 * 
	 * A string array containing all of the property zones in the city, used in the
	 * zoning property box.
	 * 
	 */

	private String[] propertyZones = { "R1", "R2", "R3", "R4", "I1", "I2", "I3", "I4" };

	/**
	 * 
	 * A string array containing all of the property types (O for office, M for
	 * manufacturing), used in the property types combo box.
	 * 
	 */

	private String[] propertyTypes = { "O", "M" };

	/**
	 * 
	 * A field containing the property's asking price.
	 * 
	 */

	private JTextField askingPriceField;

	/**
	 * 
	 * A field containing additional comments about the property.
	 * 
	 */

	private JTextField commentsField;

	/**
	 * 
	 * A field containing the number of floors a property has.
	 * 
	 */

	private JTextField noOfFloorsField;

	/**
	 * 
	 * A radio button for searching by commercial property ID.
	 * 
	 */

	private JRadioButton rdbtnCommPropID;

	/**
	 * 
	 * A radio button for searching by legal description.
	 * 
	 */

	private JRadioButton rdbtnLegalDesc;

	/**
	 * 
	 * A radio button for searching by quadrant of the city.
	 * 
	 */

	private JRadioButton rdbtnQuadOfCity;

	/**
	 * 
	 * A radio button for searching by the price of the property.
	 * 
	 */

	private JRadioButton rdbtnPrice;

	/**
	 * 
	 * A button that calls the search function.
	 * 
	 */

	private JButton btnSearch;

	/**
	 * 
	 * A button that calls the clear search function.
	 * 
	 */

	private JButton btnClearSearch;

	/**
	 * 
	 * A combobox containing a string array of quadrants (NE, SE, SW, NW) clockwise.
	 * 
	 */

	private JComboBox cityQuadrantCombo;

	/**
	 * 
	 * A combobox containing a string array of zones (R1, R2, R3, R4, I1, I2, I3,
	 * I4)
	 * 
	 */

	private JComboBox zoningCombo;

	/**
	 * 
	 * A combobox containing a string array of property types(O, M)
	 * 
	 */

	private JComboBox propTypeCombo;

	/**
	 * 
	 * A button that calls the save function.
	 * 
	 */

	private JButton btnSave;

	/**
	 * 
	 * A button that calls the delete function.
	 * 
	 */

	private JButton btnDelete;

	/**
	 * 
	 * A broker object linking this class to the backend.
	 * 
	 */

	private CommercialPropertyBroker commBroker = null;

	/**
	 * 
	 * A label for displaying output (like "Save successful".
	 * 
	 */

	private JLabel outputLabel;

	/**
	 * 
	 * A constructor containing only the .getBroker() method for commBroker
	 * 
	 */

	public CommercialWindow()

	{

		commBroker = CommercialPropertyBroker.getBroker();

	}

	/**
	 * 
	 * The central method of CommercialWindow creates all of the panels, buttons,
	 * lists, fields, and labels.
	 * 
	 * The method also contains an ListSelectionListener for when the listArea is
	 * changed.
	 * 
	 * @return A JPanel object to be added to an ArrayList in the MainWindow class.
	 *         Used to display the GUI of Commercial.
	 * 
	 */

	public JPanel createPanel()

	{

		JPanel contentPanel = new JPanel();

		JLabel welcomeLabel = new JLabel("Commercial Property Management Screen");

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

		JLabel lblSearchCommProp = new JLabel("Search Commercial Property");

		lblSearchCommProp.setFont(new Font("Tahoma", Font.BOLD, 11));

		lblSearchCommProp.setHorizontalAlignment(SwingConstants.CENTER);

		searchOptionsPanel.add(lblSearchCommProp);

		JLabel lblSelectTypeOf = new JLabel("  Select type of search to be performed");

		searchOptionsPanel.add(lblSelectTypeOf);

		JPanel radioButtonsPanel = new JPanel();

		searchOptionsPanel.add(radioButtonsPanel);

		rdbtnCommPropID = new JRadioButton("Property ID");

		searchButtons.add(rdbtnCommPropID);

		rdbtnCommPropID.addActionListener(new ButtonListener());

		radioButtonsPanel.add(rdbtnCommPropID);

		rdbtnLegalDesc = new JRadioButton("Legal Description");

		searchButtons.add(rdbtnLegalDesc);

		rdbtnLegalDesc.addActionListener(new ButtonListener());

		radioButtonsPanel.add(rdbtnLegalDesc);

		rdbtnQuadOfCity = new JRadioButton("Quadrant of City");

		searchButtons.add(rdbtnQuadOfCity);

		rdbtnQuadOfCity.addActionListener(new ButtonListener());

		radioButtonsPanel.add(rdbtnQuadOfCity);

		rdbtnPrice = new JRadioButton("Price");

		searchButtons.add(rdbtnPrice);

		rdbtnPrice.addActionListener(new ButtonListener());

		radioButtonsPanel.add(rdbtnPrice);

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

		list = new DefaultListModel<>();

		displayList = new DefaultListModel<>();

		listArea = new JList<String>(displayList); // to receive from list, use displayList.getSelectedIndex() and
													// listModel.get(i)

		String lineWidth = "1234567890123456789012345678901234567890"; // valueChanged action event is for these lists

		listArea.setPrototypeCellValue(lineWidth);

		listArea.setVisibleRowCount(13);

		listScrollPane = new JScrollPane(listArea);

		listPanel.add(listScrollPane);

		listArea.addListSelectionListener(new ListSelectionListener()

		{

			/**
			 * 
			 * On the selection being changed (selection index), this method takes the
			 * property's information
			 * 
			 * and sends it to the fields and inputs on the right side of the GUI.
			 * 
			 * @param event
			 *            Not used. Not sure what it's for.
			 * 
			 */

			@Override

			public void valueChanged(ListSelectionEvent event)

			{

				if (listArea.getSelectedIndex() >= 0)

				{

					CommercialProperty currentCommercialProp = list.get(listArea.getSelectedIndex());

					// Place values in fields

					commPropIDField.setText(Long.toString(currentCommercialProp.getId()));

					legalDescField.setText(currentCommercialProp.getLegalDescription());

					addressField.setText(currentCommercialProp.getAddress());

					askingPriceField.setText(Double.toString(currentCommercialProp.getAskingPrice()));

					noOfFloorsField.setText(Integer.toString(currentCommercialProp.getNoFloors()));

					commentsField.setText(currentCommercialProp.getComments());

					// Set comboboxes to correct value

					switch (currentCommercialProp.getQuadrant())

					{

					case "NE":

						cityQuadrantCombo.setSelectedIndex(0);

						break;

					case "SE":

						cityQuadrantCombo.setSelectedIndex(1);

						break;

					case "SW":

						cityQuadrantCombo.setSelectedIndex(2);

						break;

					case "NW":

						cityQuadrantCombo.setSelectedIndex(3);

						break;

					}

					switch (currentCommercialProp.getZone())

					{

					case "R1":

						zoningCombo.setSelectedIndex(0);

						break;

					case "R2":

						zoningCombo.setSelectedIndex(1);

						break;

					case "R3":

						zoningCombo.setSelectedIndex(2);

						break;

					case "R4":

						zoningCombo.setSelectedIndex(3);

						break;

					case "I1":

						zoningCombo.setSelectedIndex(4);

						break;

					case "I2":

						zoningCombo.setSelectedIndex(5);

						break;

					case "I3":

						zoningCombo.setSelectedIndex(6);

						break;

					case "I4":

						zoningCombo.setSelectedIndex(7);

						break;

					}

					switch (currentCommercialProp.getType())

					{

					case "O":

						propTypeCombo.setSelectedIndex(0);

						break;

					case "M":

						propTypeCombo.setSelectedIndex(1);

						break;

					}

				}

			}

		});

		JPanel commPropInfoPanel = new JPanel();

		belowTitlePanel.add(commPropInfoPanel);

		commPropInfoPanel.setLayout(new GridLayout(12, 1, 0, 0));

		JLabel lblCommResInfo = new JLabel("Commercial Property Information");

		lblCommResInfo.setHorizontalAlignment(SwingConstants.CENTER);

		lblCommResInfo.setFont(new Font("Tahoma", Font.BOLD, 11));

		commPropInfoPanel.add(lblCommResInfo);

		JPanel commPropIDPanel = new JPanel();

		commPropInfoPanel.add(commPropIDPanel);

		commPropIDPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblCommPropertyId = new JLabel("Commercial Property ID:");

		commPropIDPanel.add(lblCommPropertyId);

		commPropIDField = new JTextField("0");

		commPropIDField.setEditable(false);

		commPropIDPanel.add(commPropIDField);

		commPropIDField.setColumns(10);

		JPanel legalDescPanel = new JPanel();

		commPropInfoPanel.add(legalDescPanel);

		legalDescPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblLegalDescription = new JLabel("Legal Description:");

		legalDescPanel.add(lblLegalDescription);

		legalDescField = new JTextField();

		legalDescPanel.add(legalDescField);

		legalDescField.setColumns(10);

		JPanel propertyAddressPanel = new JPanel();

		commPropInfoPanel.add(propertyAddressPanel);

		propertyAddressPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblPropertyAddress = new JLabel("Property Address:");

		propertyAddressPanel.add(lblPropertyAddress);

		addressField = new JTextField();

		propertyAddressPanel.add(addressField);

		addressField.setColumns(10);

		JPanel cityQuadrantPanel = new JPanel();

		commPropInfoPanel.add(cityQuadrantPanel);

		cityQuadrantPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblCityQuadrant = new JLabel("City Quadrant:");

		cityQuadrantPanel.add(lblCityQuadrant);

		cityQuadrantCombo = new JComboBox(quadrants);

		cityQuadrantCombo.setMaximumRowCount(4);

		cityQuadrantPanel.add(cityQuadrantCombo);

		JPanel zoningOfPropertyPanel = new JPanel();

		commPropInfoPanel.add(zoningOfPropertyPanel);

		zoningOfPropertyPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblZoningOfProperty = new JLabel("Zoning of Property:");

		zoningOfPropertyPanel.add(lblZoningOfProperty);

		zoningCombo = new JComboBox(propertyZones);

		zoningOfPropertyPanel.add(zoningCombo);

		JPanel askingPricePanel = new JPanel();

		commPropInfoPanel.add(askingPricePanel);

		askingPricePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblPropertyAskingPrice = new JLabel("Property Asking Price:");

		askingPricePanel.add(lblPropertyAskingPrice);

		askingPriceField = new JTextField();

		askingPricePanel.add(askingPriceField);

		askingPriceField.setColumns(10);

		JPanel propertyTypePanel = new JPanel();

		commPropInfoPanel.add(propertyTypePanel);

		propertyTypePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblPropertyType = new JLabel("Property Type:");

		propertyTypePanel.add(lblPropertyType);

		propTypeCombo = new JComboBox(propertyTypes);

		propertyTypePanel.add(propTypeCombo);

		JPanel noOfFloorsPanel = new JPanel();

		commPropInfoPanel.add(noOfFloorsPanel);

		noOfFloorsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNumberOfFloors = new JLabel("Number of Floors:");

		noOfFloorsPanel.add(lblNumberOfFloors);

		noOfFloorsField = new JTextField();

		noOfFloorsPanel.add(noOfFloorsField);

		noOfFloorsField.setColumns(10);

		JPanel propertyCommentsPanel = new JPanel();

		commPropInfoPanel.add(propertyCommentsPanel);

		JLabel lblCommentsAboutProperty = new JLabel("Comments about Property:");

		propertyCommentsPanel.add(lblCommentsAboutProperty);

		commentsField = new JTextField();

		propertyCommentsPanel.add(commentsField);

		commentsField.setColumns(25);

		JPanel saveDeleteClosePanel = new JPanel();

		commPropInfoPanel.add(saveDeleteClosePanel);

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

		commPropInfoPanel.add(outputPanel);

		return contentPanel;

	}

	/**
	 * 
	 * A simple method containing only cBroker.closeBroker(), to save and close the
	 * broker.
	 * 
	 */

	public void closeWindow()

	{

		commBroker.closeBroker();

	}

	/**
	 * 
	 * Inner class ButtonListener - This class controls all of the ActionListener
	 * events that occur when pressing
	 * 
	 * or selecting GUI elements.
	 * 
	 * @author 729380
	 *
	 * 
	 * 
	 */

	private class ButtonListener implements ActionListener

	{

		/**
		 * 
		 * Controls all actions. All buttons are here, including:
		 * 
		 * searchType controls, Save, Delete, Clear Search, and Search.
		 * 
		 * @param e
		 *            event variable, not used.
		 * 
		 */

		@Override

		public void actionPerformed(ActionEvent e)

		{

			// Radio buttons

			if (e.getSource() == rdbtnLegalDesc)

			{

				searchType = "legal description";

			}

			if (e.getSource() == rdbtnPrice)

			{

				searchType = "price";

			}

			if (e.getSource() == rdbtnQuadOfCity)

			{

				searchType = "quadrant";

			}

			if (e.getSource() == rdbtnCommPropID)

			{

				searchType = "id";

			}

			if (e.getSource() == btnSearch)

			{

				list.clear();

				displayList.clear();

				List<CommercialProperty> properties = commBroker.search(txtSearchHere.getText(), searchType);

				for (int i = 0; i < properties.size(); i++)

				{

					list.addElement(properties.get(i));

					displayList.addElement(
							properties.get(i).getId() + " - " + properties.get(i).getLegalDescription() + ", "

									+ properties.get(i).getQuadrant() + " - " + properties.get(i).getAskingPrice());

				}

			}

			if (e.getSource() == btnClearSearch)

			{

				list.clear();

				displayList.clear();

				txtSearchHere.setText("");

				commPropIDField.setText("0");

				legalDescField.setText("");

				addressField.setText("");

				askingPriceField.setText("");

				noOfFloorsField.setText("");

				commentsField.setText("");

				cityQuadrantCombo.setSelectedIndex(0);

				propTypeCombo.setSelectedIndex(0);

				zoningCombo.setSelectedIndex(0);

			}

			if (e.getSource() == btnSave)

			{

				boolean testFlag;

				String zone = (String) zoningCombo.getSelectedItem();

				String quadrant = (String) cityQuadrantCombo.getSelectedItem();

				String propType = (String) propTypeCombo.getSelectedItem();

				try

				{

					CommercialProperty addedCommProp = new CommercialProperty(Long.parseLong(commPropIDField.getText()),

							legalDescField.getText(), addressField.getText(), quadrant, zone,

							Double.parseDouble(askingPriceField.getText()), commentsField.getText(),

							propType, Integer.parseInt(noOfFloorsField.getText()));

					testFlag = commBroker.persist(addedCommProp);

					if (testFlag)

					{

						outputLabel.setForeground(Color.GREEN);

						outputLabel.setText("Property has been added.");

					}

					else

					{

						outputLabel.setForeground(Color.RED);

						outputLabel.setText("Property has not been added.");

					}

				} catch (NumberFormatException e1)

				{

					JOptionPane.showMessageDialog(null, "Error in a number field.\n" + e1.getMessage());

				} catch (InvalidLegalDescriptionException e1)

				{

					JOptionPane.showMessageDialog(null,
							"The legal field is invalid.\nPlease look up the format of a legal description.");

				}

			}

			if (e.getSource() == btnDelete)

			{

				CommercialProperty removingCommProp = list.getElementAt(listArea.getSelectedIndex());

				boolean testFlag = commBroker.remove(removingCommProp);

				if (testFlag)

				{

					outputLabel.setForeground(Color.GREEN);

					outputLabel.setText("Property has been deleted.");

				}

				else

				{

					outputLabel.setForeground(Color.RED);

					outputLabel.setText("Property has not been deleted.");

				}

				commPropIDField.setText("0");

				legalDescField.setText("");

				addressField.setText("");

				askingPriceField.setText("");

				noOfFloorsField.setText("");

				commentsField.setText("");

				cityQuadrantCombo.setSelectedIndex(0);

				propTypeCombo.setSelectedIndex(0);

				zoningCombo.setSelectedIndex(0);

				displayList.remove(listArea.getSelectedIndex());

			}

		}

	}

}
