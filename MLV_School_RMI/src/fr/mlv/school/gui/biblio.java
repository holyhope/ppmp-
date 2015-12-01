package fr.mlv.school.gui;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JList;
import javax.swing.JTextField;
import java.awt.Panel;
import java.awt.Toolkit;

import javax.swing.JMenuBar;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.JDesktopPane;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.List;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.ImageIcon;
import java.awt.Label;
import javax.swing.JLayeredPane;
import javax.swing.JRadioButton;
import java.awt.FlowLayout;

public class biblio {

	private JFrame frame;
	private JTable table;
	private JTextField findBook;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					biblio window = new biblio();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public biblio() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		int frameWidth = 450;
		int frameHeight = 300;

		frame = new JFrame();
		//frame.setSize(frameWidth, frameHeight);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds((int) screenSize.getWidth() - frameWidth, 0, frameWidth, frameHeight);


		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.WHITE);
		frame.getContentPane().add(desktopPane, BorderLayout.CENTER);

		Object data[][] = {{"Test","Test","Test","Test","Test","Test"}};
		String headers[]= {"Isbn","Title","Author","Summary","Publisher","Format"};
		table = new JTable(data,headers);
		table.setBackground(Color.WHITE);
		table.setBounds((int) screenSize.getWidth() - frameWidth, 0, frameWidth, frameHeight);
		//desktopPane.add(table);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(6, 193, 438, 79);
		desktopPane.add(scrollPane);

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignOnBaseline(true);
		panel.setBorder(null);
		panel.setBackground(Color.WHITE);
		panel.setBounds(6, 56, 438, 38);

		JPanel panelWelcome = new JPanel();
		panelWelcome.setBackground(Color.LIGHT_GRAY);
		panelWelcome.setBounds(6, 6, 438, 38);
		desktopPane.add(panelWelcome);

		JLabel lblWelcome = new JLabel("New label",SwingConstants.CENTER);
		panelWelcome.add(lblWelcome);
		lblWelcome.setText("Bienvenue dans le biblioteque MLVBook");

		JLabel lblFindBook = new JLabel("Rechercher un livre :");
		panel.add(lblFindBook);

		findBook = new JTextField();
		panel.add(findBook);
		findBook.setColumns(10);

		desktopPane.add(panel);

		Component horizontalStrut = Box.createHorizontalStrut(40);
		panel.add(horizontalStrut);

		//Resize l'image
		ImageIcon iconPanier = new ImageIcon(new ImageIcon("/Users/Vatsana/Desktop/panier_icon.png").getImage().getScaledInstance(40, 30, Image.SCALE_DEFAULT));

		JLabel lblPanier = new JLabel("");
		panel.add(lblPanier);
		lblPanier.setIcon(iconPanier);

		Label lblPanierValue = new Label("Value");
		panel.add(lblPanierValue);
		lblPanierValue.setAlignment(Label.RIGHT);
		
		JRadioButton rdbtnFindByTitle = new JRadioButton("Titre");
		rdbtnFindByTitle.setBounds(6, 123, 141, 17);
		desktopPane.add(rdbtnFindByTitle);
		
		JRadioButton rdbtnfindByAuthor = new JRadioButton("Author");
		rdbtnfindByAuthor.setBounds(6, 146, 141, 17);
		desktopPane.add(rdbtnfindByAuthor);
		
		JRadioButton rdbtnfindByIsbn = new JRadioButton("Isbn");
		rdbtnfindByIsbn.setBounds(6, 165, 141, 17);
		desktopPane.add(rdbtnfindByIsbn);
		
		JLabel lblWhatFind = new JLabel("Criteres de séléction :");
		lblWhatFind.setBounds(6, 95, 141, 16);
		desktopPane.add(lblWhatFind);
	}
}
