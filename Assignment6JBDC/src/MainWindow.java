
import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
/**
 * MainWindow - 	The central class of the SaitMLS project. 
 * 					This class initializes the GUI, creates and adds topic selection functionality,
 * 					and serves to bring together the .closeBroker() functionalities of the other classes.
 * @author 729380
 * @version 1
 */
public class MainWindow extends JFrame
{
	/**
	 * The main panel of the GUI. This is below the topic bar.
	 */
	private Container mainPane; //Main pane containing everything else
	/**
	 * Serial Version = 1L
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * A list of JPanels to be swapped upon clicking the topic buttons.
	 */
	private ArrayList<JPanel> panelList = new ArrayList<JPanel>();
	/**
	 * The "Main" window button, representing the current GUI.
	 */
	private JButton btnMain;
	/**
	 * The "Client" window button, representing the client GUI.
	 */
	private JButton btnClient;
	/**
	 * The "Residential" button, representing the residential property GUI.
	 */
	private JButton btnResidential;
	/**
	 * The "Commercial" button. representing the commercial property GUI.
	 */
	private JButton btnCommercial;
	/**
	 * The object for the commercial window.
	 */
	CommercialWindow commWind = null;
	/**
	 * The object for the residential window.
	 */
	ResidentialWindow resWind = null;
	/**
	 * The object for the customer (or client) window.
	 */
	CustomerWindow custWind = null;
	
	/**
	 * This constructor initializes the windows and adds them to an ArrayList.
	 */
	public MainWindow() 
	{
		custWind = new CustomerWindow();
		resWind = new ResidentialWindow();
		commWind = new CommercialWindow();
		
		panelList.add(createPanel());
		panelList.add(custWind.createPanel());
		panelList.add(resWind.createPanel());
		panelList.add(commWind.createPanel());
	}
	/**
	 * This method creates the GUI, sets its bounds and properties, and adds the menu panel at the top of the GUI.
	 */
	public void createMenuAndWindow()
	{
		this.setBounds(100,100,900,600);
		this.setTitle("Client and Property Management Application");
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter()
		{
			/**
			 * A method that runs when the window closes. Saves RAFs, closes them, and terminates program.
			 */
			@Override
			public void windowClosing(WindowEvent e)
			{
				custWind.closeWindow();
				resWind.closeWindow();
				commWind.closeWindow();
				System.exit(0);
			}
		});
		
		JPanel menuPanel = new JPanel();
		menuPanel.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(192, 192, 192), new Color(128, 128, 128), null, null));
		mainPane = this.getContentPane();
		menuPanel.setLayout(new GridLayout(0, 4, 0, 0));
		
		btnMain = new JButton("Main");
		btnMain.addActionListener(new ButtonListener());
		menuPanel.add(btnMain);
		
		btnClient = new JButton("Client");
		btnClient.addActionListener(new ButtonListener());
		menuPanel.add(btnClient);
		
		btnResidential = new JButton("Residential");
		btnResidential.addActionListener(new ButtonListener());
		menuPanel.add(btnResidential);
		
		btnCommercial = new JButton("Commercial");
		btnCommercial.addActionListener(new ButtonListener());
		menuPanel.add(btnCommercial);
		
		mainPane.add(menuPanel,BorderLayout.NORTH);
		mainPane.add(panelList.get(0),BorderLayout.CENTER);
		
		this.setVisible(true);
	}
	
	/**
	 * This method creates the panel for the Main page. This includes the title and the center text.
	 * @return A JPanel object to be added to an ArrayList, and used when swapping panels in the GUI.
	 */
	private JPanel createPanel()
	{
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel welcomeLabel = new JLabel("<html>Welcome to the Client and<br> Property Management Application</html>");
		welcomeLabel.setBackground(Color.LIGHT_GRAY);
		welcomeLabel.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 30));
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(welcomeLabel, BorderLayout.NORTH);
		
		JLabel frontLabel = new JLabel("<html><h2>Please select a topic from the menu above</h2></html>");
		frontLabel.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 11));
		frontLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(frontLabel, BorderLayout.CENTER);
		
		return contentPanel;
	}
	
	/**
	 * Inner class ButtonListener - This class controls all of the ActionListener events that occur when pressing
	 * 								or selecting GUI elements.
	 * @author 729380
	 *
	 */
	private class ButtonListener implements ActionListener
	{
		//Attributes
		/**
		 * The current panel being represented by the GUI.
		 */
		private JPanel tp;
		/**
		 * Interface function that controls everything that occurs when GUI elements are clicked.
		 * For example, Main goes to Main, Client goes to Client, and so on for the rest of topic bar.
		 */
		@Override
		public void actionPerformed(ActionEvent e)
		{
			for(int i = 0; i < panelList.size(); i++)
			{
				tp = panelList.get(i);
				mainPane.remove(tp);
				tp.setVisible(false);
			}
			
			if(e.getSource() == btnMain)
			{
				tp = panelList.get(0);
				mainPane.add(tp,BorderLayout.CENTER);
				tp.setVisible(true);
				
			}
			
			if(e.getSource() == btnClient)
			{
				tp = panelList.get(1);
				mainPane.add(tp,BorderLayout.CENTER);
				tp.setVisible(true);
				
			}
			if(e.getSource() == btnResidential)
			{
				tp = panelList.get(2);
				mainPane.add(tp,BorderLayout.CENTER);
				tp.setVisible(true);
				
			}
			if(e.getSource() == btnCommercial)
			{
				tp = panelList.get(3);
				mainPane.add(tp,BorderLayout.CENTER);
				tp.setVisible(true);
				
			}
		}
		
	}
}
