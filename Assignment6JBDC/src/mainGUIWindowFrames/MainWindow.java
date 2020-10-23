
package mainGUIWindowFrames;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MainWindow extends JFrame {
	private Container mainPane;
	private JButton mainButton;
	private JButton clientButton;
	private JButton commercialButton;
	private JButton residentialButton;
	private JButton gameButton;
	private JPanel[] panelList;

	public MainWindow() {
		panelList = new JPanel[4];

		// fill panel list with JPanels associated with each button
		JPanel tempPanel = createMainPanel();
		panelList[0] = tempPanel;
		CommercialWindow cw = new CommercialWindow();
		panelList[2] = cw.createCommercialWindow();
		ClientWindow cm = new ClientWindow();
		panelList[1] = cm.createClientWindow();
		ResidentialWindow rw = new ResidentialWindow();
		panelList[3] = rw.createResidentialWindow();
	}

	public void createWindow() {
		this.setBounds(100, 100, 800, 600);
		this.setTitle("Sample Window!");
		this.setLocationRelativeTo(null); // will place the frame in the center of the screen!
		
		mainPane = this.getContentPane();
		mainPane.setLayout(new BorderLayout(5, 5));

		// Panel for the buttons located in the north section
		JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
		Border buttonEdge = BorderFactory.createRaisedBevelBorder();

		// button for main window
		mainButton = new JButton("Main");
		mainButton.setBorder(buttonEdge);
		mainButton.addActionListener(new ButtonListener());
		buttonPanel.add(mainButton);

		// button for customer window
		clientButton = new JButton("Client");
		clientButton.setBorder(buttonEdge);
		clientButton.addActionListener(new ButtonListener());
		buttonPanel.add(clientButton);

		// button for commercial window
		commercialButton = new JButton("Commercial");
		commercialButton.setBorder(buttonEdge);
		commercialButton.addActionListener(new ButtonListener());
		buttonPanel.add(commercialButton);

		mainPane.add(buttonPanel, BorderLayout.NORTH);
		mainPane.add(panelList[0], BorderLayout.CENTER);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		//Button for Residential Window
		residentialButton = new JButton("Residential");
		residentialButton.setBorder(buttonEdge);
		residentialButton.addActionListener(new ButtonListener());
		buttonPanel.add(residentialButton);

		mainPane.add(buttonPanel, BorderLayout.NORTH);
		mainPane.add(panelList[0], BorderLayout.CENTER);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	private JPanel createMainPanel() {
		JPanel mainPanel = new JPanel();

		JLabel mainL = new JLabel("The Main Screen", SwingConstants.CENTER);
		mainL.setForeground(Color.blue);
		mainL.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 48));
		mainPanel.add(mainL);

		return mainPanel;
	}

	/***************************** INNER CLASSES *****************************/

	private class ButtonListener implements ActionListener {
		// Attributes
		private JPanel tp;

		@Override
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < panelList.length; i++) {
				tp = panelList[i];
				mainPane.remove(tp);
				tp.setVisible(false);
			}

			if (e.getSource() == mainButton) {
				tp = panelList[0];
				mainPane.add(tp, BorderLayout.CENTER);
				tp.setVisible(true);
			}

			else if (e.getSource() == clientButton) {
				tp = panelList[1];
				mainPane.add(tp, BorderLayout.CENTER);
				tp.setVisible(true);

			} else if (e.getSource() == commercialButton) {
				tp = panelList[2];
				mainPane.add(tp, BorderLayout.CENTER);
				tp.setVisible(true);

			}else if (e.getSource() == residentialButton) {
				tp = panelList[3];
				mainPane.add(tp, BorderLayout.CENTER);
				tp.setVisible(true);
			
		}

	}
	}
}
