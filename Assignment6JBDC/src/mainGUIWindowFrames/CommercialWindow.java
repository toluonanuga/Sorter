	package mainGUIWindowFrames;
	
	import java.awt.BorderLayout;
	import java.awt.Color;
	import java.awt.Dimension;
	import java.awt.FlowLayout;
	import java.awt.Font;
	import java.awt.GridLayout;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.awt.event.KeyEvent;
	
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
	
	import saitMLS.problemDomain.property.*;
	
	
	public class CommercialWindow{
		
	 private DefaultListModel<String> listBox;
	 private JList<String> listData;
	 private JScrollPane listScrollPane;
	 private JButton saveButton;
	 private JButton deleteButton;
	 private JButton clearButton;
	 private JButton searchButton;
	 private JButton clearSearchButton;
	 private JRadioButton comIDButton;
	 private JRadioButton comDescButton;
	 private JRadioButton cityButton;
	 private JRadioButton priceButton;
	 private JTextField comIDTF;
	 private JTextField propLegalDescTF;
	 private JTextField propAddressTF;
	 private JTextField propAskPriceTF;
	 private JTextField numFloorTF;
	 private JTextField commentTF;
	 private JTextField searchData;
	 private JComboBox cityQuadList;
	 private JComboBox propZoningList;
	 private JComboBox propTypeList;
	 private String[] cityQuadString = {"", "NE", "NW", "SE", "SW"};
	 private String[] propZoningString = {"", "I1", "I2", "I3", "I4"};
	 private String[] propTypeString = {"", "M", "O"};
	 
	 
	 public CommercialWindow(){
	  
	 }
	 
	 public JPanel createCommercialWindow(){
		 
		 JPanel commercialWindowPanel = new JPanel();
		 commercialWindowPanel.setBackground(Color.CYAN);
		 commercialWindowPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		 commercialWindowPanel.setLayout(new GridLayout(0, 1));
		 commercialWindowPanel.add(this.windowPanel());
	  
		 return commercialWindowPanel;
	 }
	 
	 public JPanel windowPanel(){
		 
		  JPanel windowPanel = new JPanel();
		  windowPanel.setBackground(Color.CYAN);
		  windowPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		  windowPanel.add(this.createNorthPanel());
		  windowPanel.add(this.centerPanel());
		  
		  return windowPanel;
		 }
	 
	 public JPanel createNorthPanel(){
		 
	  JPanel northPanel = new JPanel();
	  northPanel.setBackground(Color.CYAN);
	  northPanel.add(this.northPanel());
	  
	  return northPanel;
	 }


	 public JPanel northPanel()
	 {
	  JPanel nPanel = new JPanel();
	  nPanel.setBackground(Color.CYAN);
	  JLabel nHeader = new JLabel("Commercial Property Management Screen");
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
	  JLabel eHeader = new JLabel("Commercial Property Information");
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
	  
	  JLabel comIDLabel = new JLabel("Commercial Property ID: ", SwingConstants.RIGHT);
	  comIDLabel.setForeground(Color.RED);
	  centerEastPanel.add(comIDLabel);
	  comIDTF = new JTextField();
	  centerEastPanel.add(comIDTF);
	  
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
	  
	  JLabel propTypeLabel = new JLabel("Property Type: ", SwingConstants.RIGHT);
	  propTypeLabel.setForeground(Color.RED);
	  centerEastPanel.add(propTypeLabel);
	  propTypeList = new JComboBox(propTypeString);
	  propTypeList.setMaximumRowCount(3);
	  propTypeList.setSelectedIndex(0);
	  centerEastPanel.add(propTypeList);
	  
	  JLabel numFloorLabel = new JLabel("No. of Floors: ", SwingConstants.RIGHT);
	  numFloorLabel.setForeground(Color.RED);
	  centerEastPanel.add(numFloorLabel);
	  numFloorTF = new JTextField();
	  centerEastPanel.add(numFloorTF);
	  
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
	
	 public JPanel createLeftPanel()
	 {
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
	  JLabel northHeader = new JLabel("Search Commercial Property");
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
	 
	 public JPanel center() {
      JPanel panel = new JPanel();
	  JPanel centerPanel = new JPanel(new BorderLayout());
	  centerPanel.setBackground(Color.CYAN);
	  JPanel center = new JPanel();
	  center.setBackground(Color.CYAN);
	  center.setLayout(new GridLayout(0, 1));
	  String comID = "Commercial Property ID";
	  String comDescription = "Commercial Property Legal Description";
	  String city = "Quadrant of City";
	  String price = "Commercial Property Price";
	  comIDButton = new JRadioButton(comID);
	  comIDButton.setMnemonic(KeyEvent.VK_C);
	  comIDButton.setActionCommand(comID);
	  comIDButton.setBackground(Color.CYAN);
	  //idButton.setSelected(true);
	  
	  comDescButton = new JRadioButton(comDescription);
	  comDescButton.setMnemonic(KeyEvent.VK_P);
	  comDescButton.setActionCommand(comDescription);
	     comDescButton.setBackground(Color.CYAN);
	     
	     cityButton = new JRadioButton(city);
	     cityButton.setMnemonic(KeyEvent.VK_Q);
	     cityButton.setActionCommand(city);
	     cityButton.setBackground(Color.CYAN);
	     
	     priceButton = new JRadioButton(price);
	     priceButton.setMnemonic(KeyEvent.VK_E);
	     priceButton.setActionCommand(price);
	     priceButton.setBackground(Color.CYAN);
	     
	     ButtonGroup group = new ButtonGroup();
	     group.add(comIDButton);
	     group.add(comDescButton);
	     group.add(cityButton);
	     group.add(priceButton);
	     
	     center.add(comIDButton);
	     center.add(comDescButton);
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
	 
	 public JPanel createLowerWestPanel() {
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
	  public void actionPerformed(ActionEvent e)
	  {
	   if (e.getSource() == saveButton){
		   
	    
	   }
	   
	   else if (e.getSource() == deleteButton)
	   {
	    
	   }
	   
	   else if (e.getSource() == clearButton)
	   {
	    
	   }
	   
	   else if (e.getSource() == searchButton)
	   {
	    
	    
	    if (comIDButton.isSelected())
	    {
	     
	    }
	    
	    else if (comDescButton.isSelected())
	    {
	     
	    }
	    
	    else if (cityButton.isSelected())
	    {
	     
	    }
	    
	    else if (priceButton.isSelected())
	    {
	     
	    }
	   }
	   
	   else if (e.getSource() == clearSearchButton)
	   {
	    if (comIDButton.isSelected())
	    {
	     comIDButton.setSelected(false);
	    }
	    
	    else if (comDescButton.isSelected())
	    {
	     comDescButton.setSelected(false);
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
	   
	   else if (e.getSource() == comIDButton)
	   {
	    
	   }
	   
	   else if (e.getSource() == comDescButton)
	   {
	    
	   }
	   
	   else if (e.getSource() == cityButton)
	   {
	    
	   }
	   
	   else if (e.getSource() == priceButton)
	   {
	    
	   }
	  }
	 }
	}
