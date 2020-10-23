package mainGUIWindowFrames;

import java.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import saitMLS.exceptions.clientale.InvalidPhoneNumberException;
import saitMLS.exceptions.clientale.InvalidPostalCodeException;
import saitMLS.persistence.clientale.ClientBroker;
import saitMLS.problemDomain.clientale.Client;

import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.util.*;

public class ClientWindow  {
	private JLabel copyLabel;
	private JButton saveButton;
	private JButton clearButton;
	private JButton deleteButton;
	private JTextField text;
	private JTextField idTF;
	private JTextField firstNameTF;
	private JTextField lastNameTF;
	private JTextField addressTF;
	private JTextField postalCodeTF;
	private JTextField phoneNumberTF;
	private JComboBox<String> clientTypeList;
	private JRadioButton idButton;
	private AbstractButton lNameButton;
	private JRadioButton typeButton;
	
	private String[] typeString = {" ", "C", "R"};
	private DefaultListModel<String> listBox;
	private JList<String> listData;
	private JScrollPane listScrollPane;
	private JButton clearSearchButton;
	private JButton searchButton;
	private String search = "";
	private DefaultListModel<Client> list;
	private JPanel popUpPanel;
	private JLabel popUp;


	public ClientWindow() {
		
	}

	public JPanel createClientWindow() {
		JPanel customerPanel = new JPanel();
		customerPanel.setLayout(new BorderLayout());
		

		JLabel customerL = new JLabel("Client Management Screen", SwingConstants.CENTER);
		customerL.setForeground(Color.red);
		customerL.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 48));
		customerPanel.add(customerL, BorderLayout.NORTH);
		
		JPanel leftPanel = createleftPanel();
		JPanel rightPanel = createRightPanel();
		
		leftPanel.setPreferredSize(new Dimension(675, 250));
		rightPanel.setPreferredSize(new Dimension(675, 250));
		customerPanel.add(leftPanel, BorderLayout.WEST);
		customerPanel.add(rightPanel, BorderLayout.EAST);

		return customerPanel;
	}
	
	private JPanel createRightPanel() {
		JPanel CenterPanel = new JPanel();
		JPanel southPanel = new JPanel();
		CenterPanel.setLayout(new BorderLayout());
		
		popUpPanel = new JPanel();
		popUp = new JLabel();
		popUpPanel.add(popUp);
		
		copyLabel = new JLabel("Client Information");
		copyLabel.setFont(new Font("Times New Roman",Font.BOLD, 20));
		CenterPanel.add(copyLabel, BorderLayout.NORTH);
		CenterPanel.setBorder(LineBorder.createBlackLineBorder() );
		southPanel = createLowerRight();
		
		
		
		CenterPanel.add(southPanel, BorderLayout.SOUTH);
			deleteButton = new JButton("add");
			text = new JTextField("start",10);
		
			  JPanel rightCenterPanel = new JPanel();
			  rightCenterPanel.setBackground(Color.CYAN);
			  rightCenterPanel.setLayout(new GridLayout(0, 2));
			  
			  JLabel idLabel = new JLabel("Client ID: ", SwingConstants.CENTER);
			  rightCenterPanel.add(idLabel);
			  idTF = new JTextField();
			  idTF.addActionListener(new buttonListener());
			  rightCenterPanel.add(idTF);
			  
			  JLabel firstNameLabel = new JLabel("First Name: ", SwingConstants.CENTER);
			  rightCenterPanel.add(firstNameLabel);
			  firstNameTF = new JTextField();
			  firstNameTF.addActionListener(new buttonListener());
			  rightCenterPanel.add(firstNameTF);
			  
			  JLabel lastNameLabel = new JLabel("Last Name: ", SwingConstants.CENTER);
			  rightCenterPanel.add(lastNameLabel);
			  lastNameTF = new JTextField();
			  lastNameTF.addActionListener(new buttonListener());
			  rightCenterPanel.add(lastNameTF);
			  
			  JLabel addressLabel = new JLabel("Address: ", SwingConstants.CENTER);
			  rightCenterPanel.add(addressLabel);
			  addressTF = new JTextField();
			  addressTF.addActionListener(new buttonListener());
			  rightCenterPanel.add(addressTF);
			  
			  JLabel postalCodeLabel = new JLabel("Postal Code: ", SwingConstants.CENTER);
			  rightCenterPanel.add(postalCodeLabel);
			  postalCodeTF = new JTextField();
			  postalCodeTF.addActionListener(new buttonListener());
			  rightCenterPanel.add(postalCodeTF);
			  
			  JLabel phoneNumberLabel = new JLabel("Phone Number: ", SwingConstants.CENTER);
			  rightCenterPanel.add(phoneNumberLabel);
			  phoneNumberTF = new JTextField();
			  phoneNumberTF.addActionListener(new buttonListener());
			  rightCenterPanel.add(phoneNumberTF);
			  
			  JLabel clientTypeLabel = new JLabel("Client Type: ", SwingConstants.CENTER);
			  rightCenterPanel.add(clientTypeLabel);
			  clientTypeList = new JComboBox<String>(typeString);
			  clientTypeList.setMaximumRowCount(3);
			  clientTypeList.setSize(50, 50);
			  clientTypeList.setSelectedIndex(0);
			  clientTypeList.addActionListener(new buttonListener());
			  rightCenterPanel.add(clientTypeList);
			  
			  CenterPanel.add(rightCenterPanel, BorderLayout.CENTER);
			  
			  
        return CenterPanel;
	}

	
	private JPanel createleftPanel() {
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		
		leftPanel.setBorder(LineBorder.createBlackLineBorder() );
		leftPanel.setBackground(Color.blue);
        
        JPanel northPanel = createleftNorthPanel();
        northPanel.setPreferredSize(new Dimension(300, 400));
        northPanel.setBorder(LineBorder.createBlackLineBorder() );
        leftPanel.add(northPanel, BorderLayout.NORTH);
		
		JPanel southPanel = createleftSouthPanel();
        southPanel.setPreferredSize(new Dimension(300, 250));
        southPanel.setBorder(LineBorder.createBlackLineBorder() );
        leftPanel.add(southPanel, BorderLayout.SOUTH);
        

        
        
		
        return leftPanel;
	}
	
	private JPanel createLeftCenterPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, 0));
		panel.setBackground(Color.cyan);
		
		
		JPanel center = new JPanel();
		JLabel label = new JLabel("Select type of search to be performed: ");
		center.setBackground(Color.CYAN);
		center.add(label);
		center.setLayout(new GridLayout(0, 1));
		String id = "Client ID";
		String lName = "Last Name";
		String type = "Client Type";
		idButton = new JRadioButton(id);
		idButton.setMnemonic(KeyEvent.VK_D);
		idButton.setActionCommand(id);
		idButton.addActionListener(new buttonListener());
		idButton.setBackground(Color.CYAN);
		//idButton.setSelected(true);
		   
		lNameButton = new JRadioButton(lName);
		lNameButton.setMnemonic(KeyEvent.VK_N);
		lNameButton.setActionCommand(lName);
		lNameButton.addActionListener(new buttonListener());
		lNameButton.setBackground(Color.CYAN);
		     
		typeButton = new JRadioButton(type);
		typeButton.setMnemonic(KeyEvent.VK_T);
		typeButton.setActionCommand(type);
		typeButton.addActionListener(new buttonListener());
		typeButton.setBackground(Color.CYAN);
		    
		ButtonGroup group = new ButtonGroup();
		group.add(idButton);
		group.add(lNameButton);
		group.add(typeButton);
		     
		center.add(idButton);
		center.add(lNameButton);
		center.add(typeButton);
		
//		
//		JRadioButton radio = new JRadioButton("Client ID");
//		
//		
//		panel.add(label, new BoxLayout(label, 0));
		panel.add(center);
		
		JLabel label1 = new JLabel("Enter Search Parameter Below: ");
		center.add(label1);
		return panel;
	}

	private JPanel createleftNorthPanel() {
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BorderLayout());
		northPanel.setBackground(Color.white);
		
		copyLabel = new JLabel("Search Client");
		copyLabel.setFont(new Font("Times New Roman",Font.BOLD, 20));
		northPanel.add(copyLabel, BorderLayout.NORTH);
		
        JPanel panel = createTopLeftSouth();
		northPanel.add(panel, BorderLayout.SOUTH);
		
        JPanel centerPanel = createLeftCenterPanel();
        northPanel.add(centerPanel, BorderLayout.CENTER);
		
		return northPanel;
	}
	
	private JPanel createleftSouthPanel() {
		JPanel southPanel = new JPanel();
		JPanel lowerWestPanel = new JPanel();
		  list = new DefaultListModel<>();
		  listBox = new DefaultListModel<String>();
		  listData = new JList<String>(listBox);
		  String lineLength = "1234567890123456789012345678901234567890";
		  listData.setPrototypeCellValue(lineLength);
		  listData.setVisibleRowCount(10);
		  listData.addListSelectionListener(new ListSelectionListener() {
		   
		   @Override
		   public void valueChanged(ListSelectionEvent s) {
			   if(listData.getSelectedIndex() >= 0){
			     Client currentClient = (Client) list.get(listData.getSelectedIndex());
			     
			     idTF.setText(Long.toString(currentClient.getId()));
			     firstNameTF.setText(currentClient.getFirstName());
			     lastNameTF.setText(currentClient.getLastName());
			     addressTF.setText(currentClient.getAddress());
			     postalCodeTF.setText(currentClient.getPostalCode());
			     phoneNumberTF.setText(currentClient.getPhoneNumber());
			     
			     
			    }
		   }
		  });
		  listScrollPane = new JScrollPane(listData);
		  lowerWestPanel.add(listScrollPane);
		  southPanel.add(lowerWestPanel);
		  
		 
		
		
		
        
        return southPanel;
	}
//	
	private JPanel createLowerRight() {
		JPanel lowerRight = new JPanel();
		saveButton = new JButton("Save");
		clearButton = new JButton("Clear");
		deleteButton = new JButton("Delete");
		lowerRight.setLayout(new FlowLayout());
		
		saveButton.addActionListener(new buttonListener());
		clearButton.addActionListener(new buttonListener());
		deleteButton.addActionListener(new buttonListener());
		
		
		
		lowerRight.setBackground(Color.pink);
		lowerRight.add(saveButton, FlowLayout.LEFT);
		lowerRight.add(deleteButton, FlowLayout.CENTER);
		lowerRight.add(clearButton, FlowLayout.RIGHT);
//		
//        
        return lowerRight;
	}
	
	private JPanel createTopLeftSouth() {
		
		JPanel Panel = new JPanel();
		
		text = new JTextField(10);
		searchButton = new JButton("Search");
		clearSearchButton = new JButton("Clear Search");
		
		searchButton.addActionListener(new buttonListener());
		clearSearchButton.addActionListener(new buttonListener());
		
		Panel.setBackground(Color.pink);
		
		Panel.add(text, FlowLayout.LEFT);
		Panel.add(clearSearchButton, FlowLayout.CENTER);
		Panel.add(searchButton, FlowLayout.RIGHT);
		
		return Panel;
//		
		
	}
	private class buttonListener implements ActionListener {
		Client client;
		ClientBroker broker = ClientBroker.getBroker();
		
		private java.util.List clientList;
		
		
		public void actionPerformed(ActionEvent e){
			
			
			if (e.getSource() == idButton){
				search = "id";
//				listData = (JList<String>) broker.search(text.getText(), "id");
				
				
			}
	    
			else if (e.getSource() == lNameButton){
				search = "name";
//				ArrayList<Client> search = (ArrayList<Client>) broker.search(text.getText(), "name");
//				clientList = search;
			}
	    
			else if (e.getSource() == typeButton){
				search = "type";
//				ArrayList<Client> search = (ArrayList<Client>) broker.search(text.getText(), "type");
//				clientList = search;
			}
						
			if (e.getSource() == saveButton) {
				
				 boolean action;
				 char clientType = ' ';
				 if(clientTypeList.equals("C")) {
				    clientType = 'C';
				 }else if(clientTypeList.equals("R")) {
				    clientType = 'R';
				 }
				 try{
				    client = new Client(Long.parseLong(idTF.getText()), firstNameTF.getText(), 
				       lastNameTF.getText(), addressTF.getText(), postalCodeTF.getText(), phoneNumberTF.getText(), clientType);
				    action = broker.persist(client);
				    if (action){ 
				      popUp.setForeground(Color.GREEN);
				      popUp.setText("Client has been added.");
				     }
				    else{
				      popUp.setForeground(Color.RED);
				      popUp.setText("Client has not been added.");
				     }
				    } catch (NumberFormatException err){
				     System.out.println("Invalid number format.");
				    } catch (InvalidPhoneNumberException err){
				     JOptionPane.showMessageDialog(null, "The phone number is invalid.\nType in format ###-###-####");
				    } catch (InvalidPostalCodeException err){
				     JOptionPane.showMessageDialog(null, "The postal code is invalid.\nType in format 4M4 5G5");
				    }
	    
			}
	 
			else if (e.getSource() == deleteButton){
				
				Client removeClient = (Client) list.getElementAt(listData.getSelectedIndex());
			    boolean action = broker.remove(removeClient);
			    
			    if (action)
			    { 
			     popUp.setForeground(Color.GREEN);
			     popUp.setText("Client has been deleted.");
			    }
			    else
			    {
			     popUp.setForeground(Color.RED);
			     popUp.setText("Client has not been deleted.");
			    }
			    
			    text.setText("");
			    idTF.setText("0");
			    firstNameTF.setText("");
			    lastNameTF.setText("");
			    addressTF.setText("");
			    postalCodeTF.setText("");
			    phoneNumberTF.setText("");
			    
			    listBox.remove(listData.getSelectedIndex());
	    
			}
	   
			else if (e.getSource() == clearButton){
				
				text.setText("");
				idTF.setText("0");
				firstNameTF.setText("");
				lastNameTF.setText("");
				addressTF.setText("");
				postalCodeTF.setText("");
				phoneNumberTF.setText("");
				
			}
	   
			else if (e.getSource() == searchButton){
				
				list.clear();
			    listBox.clear();
			    clientList = new ArrayList<Client>();
			    clientList = broker.search(text.getText(), search);
			    for(int i = 0; i < clientList.size(); i++)
			    {
			     list.addElement((Client) clientList.get(i));
			     listBox.addElement(((Client) clientList.get(i)).getId() + " - " + ((Client) clientList.get(i)).getLastName() + ", "+ ((Client) clientList.get(i)).getFirstName() + " - " + ((Client) clientList.get(i)).getClientType());
			    }
			}
	    
	   
	   
	   else if (e.getSource() == clearSearchButton)
	   {	
		   list.clear();
		   listBox.clear();
		   text.setText("");
		   idTF.setText("0");
		   firstNameTF.setText("");
		   lastNameTF.setText("");
		   addressTF.setText("");
		   postalCodeTF.setText("");
		   phoneNumberTF.setText("");
	    
	   }
	

	  }
}
}

