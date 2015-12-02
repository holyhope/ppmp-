package fr.mlv.school.gui;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;

import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;

import java.awt.Component;

import javax.naming.ldap.Rdn;
import javax.swing.Box;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JDesktopPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class BiblioGUI {

	private JFrame frame;
	private JTable table;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BiblioGUI window = new BiblioGUI();
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
	public BiblioGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		int frameWidth = 450;
		int frameHeight = 300;

		//Fenetre principale
		frame = new JFrame();
		//frame.setSize(frameWidth, frameHeight);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds((int) screenSize.getWidth() - frameWidth, 0, (int)screenSize.getWidth(), (int)screenSize.getHeight());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Up
		JPanel panelTop = new JPanel();
		frame.getContentPane().add(panelTop, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel(" ");
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		ImageIcon imageBiblio = new ImageIcon(new ImageIcon(BiblioGUI.class.getResource("/fr/mlv/school/gui/biblio.jpg")).getImage().getScaledInstance(frame.getWidth(), 150, Image.SCALE_DEFAULT));
		panelTop.setLayout(new GridLayout(0, 1, 0, 300));
		
		lblNewLabel.setIcon(imageBiblio);
		panelTop.add(lblNewLabel);
		
	
		//Center
		JSplitPane splitPaneCenter = new JSplitPane();
		splitPaneCenter.setResizeWeight(0.2);
		frame.getContentPane().add(splitPaneCenter, BorderLayout.CENTER);
		
		JPanel panelRight = new JPanel();
		splitPaneCenter.setRightComponent(panelRight);
		panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(new Color(218, 165, 32));
		panelRight.add(scrollPane);
		
		Object data[][] = {{"Test","Test","Test","Test","Test","Test",""}};
		String headers[]= {"Isbn","Title","Author","Summary","Publisher","Format","Ajouter"};
		table = new JTable(data,headers);
		//table.getColumn("Ajouter").setCellEditor(new ButtonEditor(new JCheckBox()));
		scrollPane.setViewportView(table);
		
		JDesktopPane desktopPaneLeft = new JDesktopPane();
		desktopPaneLeft.setBackground(new Color(218, 165, 32));
		splitPaneCenter.setLeftComponent(desktopPaneLeft);
		
		textField = new JTextField();
		textField.setBounds(6, 34, 134, 28);
		desktopPaneLeft.add(textField);
		textField.setColumns(10);
		
		JLabel lblFindLivre = new JLabel("Rechercher un livre :");
		lblFindLivre.setBounds(6, 6, 183, 16);
		desktopPaneLeft.add(lblFindLivre);
		
		ButtonGroup group = new ButtonGroup();
		
		JPanel panel = new JPanel();
		panel.setForeground(new Color(218, 165, 32));
		panel.setBackground(new Color(218, 165, 32));
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.setBounds(6, 74, 134, 104);
		desktopPaneLeft.add(panel);
		
		JRadioButton rdbtnTitle = new JRadioButton("Title");
		rdbtnTitle.setSelected(true);
		rdbtnTitle.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(rdbtnTitle);
		group.add(rdbtnTitle);
		
		JRadioButton rdbtnAuthor = new JRadioButton("Author");
		rdbtnAuthor.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(rdbtnAuthor);
		group.add(rdbtnAuthor);
		
		JRadioButton rdbtnIsbn = new JRadioButton("Isbn");
		rdbtnIsbn.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(rdbtnIsbn);
		group.add(rdbtnIsbn);
		
		JButton btnFindBook = new JButton("Rechercher");
		btnFindBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Button appuyé");
			}
		});
		btnFindBook.setBounds(6, 190, 117, 29);
		desktopPaneLeft.add(btnFindBook);
		
		//Bottom
		JPanel panelDown = new JPanel();
		frame.getContentPane().add(panelDown, BorderLayout.SOUTH);
	}
}
