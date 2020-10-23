	package mainGUIWindowFrames;
	
	import java.awt.BorderLayout;
	import java.awt.Color;
	import java.awt.Dimension;
	import java.awt.FlowLayout;
	import java.awt.Font;
	import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.awt.event.KeyEvent;
	import java.io.FileNotFoundException;
	import java.util.ArrayList;
	import java.util.Scanner;
	
	import javax.swing.BorderFactory;
	import javax.swing.ButtonGroup;
	import javax.swing.DefaultListModel;
	import javax.swing.JButton;
	import javax.swing.JComboBox;
	import javax.swing.JFrame;
	import javax.swing.JLabel;
	import javax.swing.JList;
	import javax.swing.JPanel;
	import javax.swing.JRadioButton;
	import javax.swing.JScrollPane;
	import javax.swing.JTextField;
	import javax.swing.JMenuBar;
	import javax.swing.JMenu;
	import javax.swing.JMenuItem;
	import javax.swing.SwingConstants;
	import javax.swing.event.ListSelectionEvent;
	import javax.swing.event.ListSelectionListener;
	import javax.swing.JOptionPane;

import saitMLS.persistence.property.ResidentialPropertyBroker;
import saitMLS.problemDomain.property.*;
	
	
	public class ResidentialWindow {
		
		 private DefaultListModel<String> listBox;
		 private JList<String> listData;
		 private JScrollPane listScrollPane;
		 JButton saveButton;
		 JButton deleteButton;
		 JButton clearButton;
		 JButton searchButton;
		 JButton clearSearchButton;
		 JRadioButton resIDButton;
		 JRadioButton resDescButton;
		 JRadioButton cityButton;
		 JRadioButton priceButton;
		 JTextField resIDTF;
		 JTextField propLegalDescTF;
		 JTextField propAddressTF;
		 JTextField propAskPriceTF;
		 JTextField buildingSFTF;
		 JTextField numBathroomTF;
		 JTextField numBedroomTF;
		 JTextField commentTF;
		 JTextField searchData;
		 JComboBox cityQuadList;
		 JComboBox propZoningList;
		 JComboBox garageTypeList;
		 String[] cityQuadString = {"", "NE", "NW", "SE", "SW"};
		 String[] propZoningString = {"", "R1", "R2", "R3", "R4"};
		 String[] garageTypeString = {"", "A", "D", "N"};
		 ResidentialPropertyBroker rpBroker;
		 ResidentialProperty rp;


		 public ResidentialWindow()
		 {
		  
		 }
		 


		 public JPanel createResidentialWindow()
		 {
		  JPanel residentialWindowPanel = new JPanel();
		  residentialWindowPanel.setBackground(Color.CYAN);
		  residentialWindowPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		  residentialWindowPanel.setLayout(new GridLayout(0, 1));
		  residentialWindowPanel.add(this.windowPanel());
		  
		  return residentialWindowPanel;
		 }


		 public JPanel windowPanel()
		 {
		  JPanel windowPanel = new JPanel();
		  windowPanel.setBackground(Color.CYAN);
		  windowPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		  windowPanel.add(this.createNorthPanel());
		  windowPanel.add(this.centerPanel());
		  
		  return windowPanel;
		 }


		 public JPanel createNorthPanel()
		 {
		  JPanel northPanel = new JPanel();
		  northPanel.setBackground(Color.CYAN);
		  northPanel.add(this.northPanel());
		  
		  return northPanel;
		 }


		 public JPanel northPanel()
		 {
		  JPanel nPanel = new JPanel();
		  nPanel.setBackground(Color.CYAN);
		  JLabel nHeader = new JLabel("Residential Property Management Screen");
		  nHeader.setForeground(Color.BLUE);
		  Font nFont = new Font("TimesRoman", Font.BOLD, 30);
		  nHeader.setFont(nFont);
		  nPanel.add(nHeader);
		  
		  return nPanel;
		 }


		 public JPanel centerPanel()
		 {
		  JPanel centerPanel = new JPanel();
		  centerPanel.setBackground(Color.CYAN);
		  centerPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		  centerPanel.add(this.createCenterPanel());
		  
		  return centerPanel;
		 }


		 public JPanel createCenterPanel()
		 {
		  JPanel cPanel = new JPanel();
		  cPanel.setBackground(Color.CYAN);
		  cPanel.setBorder(BorderFactory.createEtchedBorder());
		  cPanel.setLayout(new GridLayout(1, 0));
		  cPanel.add(this.createLeftPanel());
		  cPanel.add(this.createRightPanel());
		  return cPanel;
		 }


		 public JPanel createRightPanel()
		 {
		  JPanel rightPanel = new JPanel();
		  rightPanel.setBackground(Color.CYAN);
		  rightPanel.setBorder(BorderFactory.createEtchedBorder());
		  rightPanel.setLayout(new GridLayout(0, 1));
		  rightPanel.add(this.createNorthEastPanel());
		  rightPanel.add(this.createSouthEastPanel());
		  return rightPanel;
		 }


		 public JPanel createNorthEastPanel()
		 {
		  JPanel northEastPanel = new JPanel(new BorderLayout());
		  JPanel headerPanel = new JPanel();
		  headerPanel.setBackground(Color.CYAN);
		  JLabel eHeader = new JLabel("Residential Property Information");
		  eHeader.setForeground(Color.MAGENTA);
		  Font eFont = new Font("TimesRoman", Font.BOLD, 14);
		  eHeader.setFont(eFont);
		  headerPanel.add(eHeader);
		  northEastPanel.add(headerPanel, BorderLayout.NORTH);
		  northEastPanel.add(this.createCenterEastPanel());
		  
		  return northEastPanel;
		 }


		 public JPanel createCenterEastPanel(){
			 
		  JPanel panel = new JPanel();
		  JPanel centerEastPanel = new JPanel();
		  centerEastPanel.setBackground(Color.CYAN);
		  centerEastPanel.setLayout(new GridLayout(0, 2));
		  
		  JLabel resIDLabel = new JLabel("Residential Property ID: ", SwingConstants.RIGHT);
		  resIDLabel.setForeground(Color.RED);
		  centerEastPanel.add(resIDLabel);
		  resIDTF = new JTextField();
		  centerEastPanel.add(resIDTF);
		  
		  JLabel propLegalDescLabel = new JLabel("Property Legal Description: ", SwingConstants.RIGHT);
		  propLegalDescLabel.setForeground(Color.RED);
		  centerEastPanel.add(propLegalDescLabel);
		  propLegalDescTF = new JTextField();
		  centerEastPanel.add(propLegalDescTF);
		  
		  JLabel propAddressLabel = new JLabel("Property Address: ", SwingConstants.RIGHT);
		  propAddressLabel.setForeground(Color.RED);
		  centerEastPanel.add(propAddressLabel);
		  propAddressTF = new JTextField();
		  centerEastPanel.add(propAddressTF);
		  
		  JLabel cityQuadLabel = new JLabel("City Quadrant: ", SwingConstants.RIGHT);
		  cityQuadLabel.setForeground(Color.RED);
		  centerEastPanel.add(cityQuadLabel);
		  cityQuadList = new JComboBox(cityQuadString);
		  cityQuadList.setMaximumRowCount(5);
		  cityQuadList.setSelectedIndex(0);
		  centerEastPanel.add(cityQuadList);
		  
		  JLabel propZoningLabel = new JLabel("Zoning of Property: ", SwingConstants.RIGHT);
		  propZoningLabel.setForeground(Color.RED);
		  centerEastPanel.add(propZoningLabel);
		  propZoningList = new JComboBox(propZoningString);
		  propZoningList.setMaximumRowCount(5);
		  propZoningList.setSelectedIndex(0);
		  centerEastPanel.add(propZoningList);
		  
		  JLabel propAskPriceLabel = new JLabel("Property Asking Price: ", SwingConstants.RIGHT);
		  propAskPriceLabel.setForeground(Color.RED);
		  centerEastPanel.add(propAskPriceLabel);
		  propAskPriceTF = new JTextField();
		  centerEastPanel.add(propAskPriceTF);
		  
		  JLabel buildingSFLabel = new JLabel("Building Square Footage: ", SwingConstants.RIGHT);
		  buildingSFLabel.setForeground(Color.RED);
		  centerEastPanel.add(buildingSFLabel);
		  buildingSFTF = new JTextField();
		  centerEastPanel.add(buildingSFTF);
		  
		  JLabel numBathroomLabel = new JLabel("No. of Bathrooms: ", SwingConstants.RIGHT);
		  numBathroomLabel.setForeground(Color.RED);
		  centerEastPanel.add(numBathroomLabel);
		  numBathroomTF = new JTextField();
		  centerEastPanel.add(numBathroomTF);
		  
		  JLabel numBedroomLabel = new JLabel("No. of Bedrooms: ", SwingConstants.RIGHT);
		  numBedroomLabel.setForeground(Color.RED);
		  centerEastPanel.add(numBedroomLabel);
		  numBedroomTF = new JTextField();
		  centerEastPanel.add(numBedroomTF);
		  
		  JLabel garageTypeLabel = new JLabel("Garage Type: ", SwingConstants.RIGHT);
		  garageTypeLabel.setForeground(Color.RED);
		  centerEastPanel.add(garageTypeLabel);
		  garageTypeList = new JComboBox(garageTypeString);
		  garageTypeList.setMaximumRowCount(4);
		  garageTypeList.setSelectedIndex(0);
		  centerEastPanel.add(garageTypeList);
		  
		  JLabel commentLabel = new JLabel("Comments about Property: ", SwingConstants.RIGHT);
		  commentLabel.setForeground(Color.RED);
		  centerEastPanel.add(commentLabel);
		  commentTF = new JTextField();
		  centerEastPanel.add(commentTF);
		  
		  panel.add(centerEastPanel, BorderLayout.CENTER);
		  
		  return panel;
		 }


		 public JPanel createSouthEastPanel(){
		  JPanel panel = new JPanel();
		  JPanel southEastPanel = new JPanel();
		  southEastPanel.setBackground(Color.CYAN);
		  saveButton = new JButton("Save");
		  saveButton.setSize(100, 50);
		  saveButton.addActionListener(new buttonListener());
		  southEastPanel.add(saveButton);
		  deleteButton = new JButton("Delete");
		  deleteButton.setSize(100, 50);
		  deleteButton.addActionListener(new buttonListener());
		  southEastPanel.add(deleteButton);
		  clearButton = new JButton("Clear");
		  clearButton.setSize(100, 50);
		  clearButton.addActionListener(new buttonListener());
		  southEastPanel.add(clearButton);
		  
		  panel.add(southEastPanel, BorderLayout.SOUTH);
		  
		  return panel;
		 }


		 public JPanel createLeftPanel(){
		  JPanel leftPanel = new JPanel();
		  leftPanel.setBackground(Color.CYAN);
		  leftPanel.setLayout(new GridLayout(0, 1));
		  leftPanel.add(this.createUpperWestPanel());
		  leftPanel.add(this.createLowerWestPanel());
		  
		  return leftPanel;
		 }


		 public JPanel createUpperWestPanel()
		 {
		  JPanel upperWestPanel = new JPanel();
		  upperWestPanel.setBackground(Color.CYAN);
		  upperWestPanel.setBorder(BorderFactory.createEtchedBorder());
		  upperWestPanel.setLayout(new GridLayout(0, 1));
		  upperWestPanel.add(this.createNorthWestPanel());
		  
		  return upperWestPanel;
		 }


		 public JPanel createNorthWestPanel()
		 {
		  JPanel northWestPanel = new JPanel();
		  northWestPanel.setLayout(new GridLayout(0, 1));
		  JPanel north = this.north();
		  JPanel center = this.center();
		  JPanel south = this.south();
		  northWestPanel.add(north, BorderLayout.NORTH);
		  northWestPanel.add(center, BorderLayout.CENTER);
		  northWestPanel.add(south, BorderLayout.SOUTH);
		  
		  return northWestPanel;
		 }


		 public JPanel north(){
		  JPanel panel = new JPanel();
		  JPanel northPanel = new JPanel(new BorderLayout());
		  northPanel.setBackground(Color.CYAN);
		  JPanel north = new JPanel();
		  north.setBackground(Color.CYAN);
		  JLabel northHeader = new JLabel("Search Residential Property");
		  northHeader.setForeground(Color.MAGENTA);
		  Font wFont = new Font("TimesRoman", Font.BOLD, 14);
		  northHeader.setFont(wFont);
		  north.add(northHeader);
		  JLabel select = new JLabel("Select type of search to be performed:");
		  select.setForeground(Color.RED);
		  Font selectFont = new Font("TimesRoman", Font.PLAIN, 14);
		  select.setFont(selectFont);
		  northPanel.add(north, BorderLayout.NORTH);
		  northPanel.add(select);
		  panel.add(northPanel, BorderLayout.NORTH);
		  
		  return panel;
		 }


		 public JPanel center(){
		  JPanel panel = new JPanel();
		  JPanel centerPanel = new JPanel(new BorderLayout());
		  centerPanel.setBackground(Color.CYAN);
		  JPanel center = new JPanel();
		  center.setBackground(Color.CYAN);
		  center.setLayout(new GridLayout(0, 1));
		  String resID = "Residential Property ID";
		  String resDescription = "Residential Property Legal Description";
		  String city = "Quadrant of City";
		  String price = "Residential Property Price";
		  resIDButton = new JRadioButton(resID);
		  resIDButton.setMnemonic(KeyEvent.VK_R);
		  resIDButton.setActionCommand(resID);
		  resIDButton.setBackground(Color.CYAN);
		  //idButton.setSelected(true);
		  
		  resDescButton = new JRadioButton(resDescription);
		  resDescButton.setMnemonic(KeyEvent.VK_P);
		  resDescButton.setActionCommand(resDescription);
		  resDescButton.setBackground(Color.CYAN);
		     
		  cityButton = new JRadioButton(city);
		  cityButton.setMnemonic(KeyEvent.VK_Q);
		  cityButton.setActionCommand(city);
		  cityButton.setBackground(Color.CYAN);
		     
		  priceButton = new JRadioButton(price);
		  priceButton.setMnemonic(KeyEvent.VK_E);
		  priceButton.setActionCommand(price);
		  priceButton.setBackground(Color.CYAN);
		     
		     ButtonGroup group = new ButtonGroup();
		     group.add(resIDButton);
		     group.add(resDescButton);
		     group.add(cityButton);
		     group.add(priceButton);
		     
		     center.add(resIDButton);
		     center.add(resDescButton);
		     center.add(cityButton);
		     center.add(priceButton);
		     JLabel enter = new JLabel("Enter the search parameter below:");
		  enter.setForeground(Color.RED);
		  Font enterFont = new Font("TimesRoman", Font.PLAIN, 14);
		  enter.setFont(enterFont);
		  center.add(enter);
		  
		     centerPanel.add(center, BorderLayout.LINE_START);
		     panel.add(centerPanel, BorderLayout.CENTER);
		     
		     return panel;
		 }


		 public JPanel south(){
		  JPanel panel = new JPanel();
		  JPanel south = new JPanel();
		  south.setBackground(Color.CYAN);
		  searchData = new JTextField(15);
		  south.add(searchData);
		  
		  searchButton = new JButton("Search");
		  searchButton.setSize(100, 50);
		  searchButton.addActionListener(new buttonListener());
		  south.add(searchButton);
		  
		  clearSearchButton = new JButton("Clear Search");
		  clearSearchButton.setSize(150, 50);
		  clearSearchButton.addActionListener(new buttonListener());
		  south.add(clearSearchButton);
		  
		  panel.add(south, BorderLayout.SOUTH);
		  
		  return panel;
		 }


		 public JPanel createLowerWestPanel(){
			 
		  JPanel panel = new JPanel();
		  JPanel lowerWestPanel = new JPanel();
		  lowerWestPanel.setBackground(Color.CYAN);
		  lowerWestPanel.setBorder(BorderFactory.createEtchedBorder());
		  listBox = new DefaultListModel<String>();
		  listData = new JList<String>(listBox);
		  String lineLength = "1234567890123456789012345678901234567890";
		  listData.setPrototypeCellValue(lineLength);
		  listData.setVisibleRowCount(10);
		  listData.addListSelectionListener(new ListSelectionListener() {
		   
		   @Override
		   public void valueChanged(ListSelectionEvent arg0) {
		    
		   }
		  });
		  listScrollPane = new JScrollPane(listData);
		  lowerWestPanel.add(listScrollPane);
		  panel.add(lowerWestPanel, BorderLayout.NORTH);
		  
		  return panel;
		 }
		 


		 private class buttonListener implements ActionListener
		 {
		  private DefaultListModel<String> displayList;
		private ResidentialPropertyBroker resBroker;
		private String searchType;

		public void actionPerformed(ActionEvent e)
		  {
		   if (e.getSource() == saveButton)
		   {
		    rpBroker.persist(rp);
		   }
		   
		   else if (e.getSource() == deleteButton)
		   {
		    rpBroker.remove(rp);
		   }
		   
		   else if (e.getSource() == clearButton){
		    
		   }
		   
		   else if (e.getSource() == searchButton){
			     
		    
		   }
		   
		   else if (e.getSource() == clearSearchButton){
		    if (resIDButton.isSelected())
		    {
		     resIDButton.setSelected(false);
		    }
		    
		    else if (resDescButton.isSelected())
		    {
		     resDescButton.setSelected(false);
		    }
		    
		    else if (cityButton.isSelected())
		    {
		     cityButton.setSelected(false);
		    }
		    
		    else if (priceButton.isSelected())
		    {
		     priceButton.setSelected(false);
		    }
		    
		   }
		   
		   else if (e.getSource() == resIDButton){
		    
		   }
		   
		   else if (e.getSource() == resDescButton){
		    
		   }
		   
		   else if (e.getSource() == cityButton){
		    
		   }
		   
		   else if (e.getSource() == priceButton){
		    
		   }
		  }
		 }
	}
