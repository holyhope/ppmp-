package fr.mlv.school.gui;

import java.awt.EventQueue;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
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
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JDesktopPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.table.TableCellRenderer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.SpringLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JSeparator;

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
		
		int frameWidth = 900;
		int frameHeight = 600;
		Color myColor = new Color(218,165,32);

		//Fenetre principale
		frame = new JFrame();
		frame.setTitle("Biblioteque MLV");
		//frame.setSize(frameWidth, frameHeight);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds((int) screenSize.getWidth() - frameWidth, 0, frameWidth, frameHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
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
		splitPaneCenter.setResizeWeight(0.05);
		frame.getContentPane().add(splitPaneCenter, BorderLayout.CENTER);
		
		JPanel panelRight = new JPanel();
		splitPaneCenter.setRightComponent(panelRight);
		panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getViewport().setBackground(myColor);
		panelRight.add(scrollPane);
	
		Object data[][] = {{"Test","Test","Test","Test","Test","Test","Ajouter au panier"}};
		String headers[]= {"Isbn","Title","Author","Summary","Publisher","Format","+"};
		table = new JTable(data,headers);
		 table.getColumn("+").setCellRenderer(new ButtonRenderer());
		    table.getColumn("+").setCellEditor(
		        new ButtonEditor(new JCheckBox()));
		scrollPane.setViewportView(table);
		
		JDesktopPane desktopPaneLeft = new JDesktopPane();
		desktopPaneLeft.setBackground(new Color(218, 165, 32));
		splitPaneCenter.setLeftComponent(desktopPaneLeft);
		GridBagLayout gbl_desktopPaneLeft = new GridBagLayout();
		gbl_desktopPaneLeft.columnWidths = new int[]{183, 0};
		gbl_desktopPaneLeft.rowHeights = new int[]{22, 23, 23, 160, 16, 28, 1, 29, 0};
		gbl_desktopPaneLeft.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_desktopPaneLeft.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		desktopPaneLeft.setLayout(gbl_desktopPaneLeft);
		
		JButton btnConnexion = new JButton("Connexion");
		btnConnexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ConnexionGUI connexionGUI = new ConnexionGUI();
			}
		});
		GridBagConstraints gbc_btnConnexion = new GridBagConstraints();
		gbc_btnConnexion.anchor = GridBagConstraints.NORTH;
		gbc_btnConnexion.insets = new Insets(0, 0, 5, 0);
		gbc_btnConnexion.gridx = 0;
		gbc_btnConnexion.gridy = 1;
		desktopPaneLeft.add(btnConnexion, gbc_btnConnexion);
		
		JButton btnPanier = new JButton("Mon panier");
		btnPanier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PanierGUI panniierGUI = new PanierGUI();
			}
		});
		GridBagConstraints gbc_btnPanier = new GridBagConstraints();
		gbc_btnPanier.anchor = GridBagConstraints.NORTH;
		gbc_btnPanier.insets = new Insets(0, 0, 5, 0);
		gbc_btnPanier.gridx = 0;
		gbc_btnPanier.gridy = 2;
		desktopPaneLeft.add(btnPanier, gbc_btnPanier);
		
		JLabel lblFindLivre = new JLabel("Rechercher un livre :");
		GridBagConstraints gbc_lblFindLivre = new GridBagConstraints();
		gbc_lblFindLivre.fill = GridBagConstraints.VERTICAL;
		gbc_lblFindLivre.insets = new Insets(0, 0, 5, 0);
		gbc_lblFindLivre.gridx = 0;
		gbc_lblFindLivre.gridy = 4;
		desktopPaneLeft.add(lblFindLivre, gbc_lblFindLivre);
		
		ButtonGroup group = new ButtonGroup();
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 5;
		desktopPaneLeft.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setForeground(new Color(218, 165, 32));
		panel.setBackground(new Color(218, 165, 32));
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.VERTICAL;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 6;
		desktopPaneLeft.add(panel, gbc_panel);
		
		JRadioButton rdbtnTitle = new JRadioButton("Title");
		rdbtnTitle.setSelected(true);
		rdbtnTitle.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(rdbtnTitle);
		group.add(rdbtnTitle);
		
		JRadioButton rdbtnIsbn = new JRadioButton("Isbn");
		rdbtnIsbn.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(rdbtnIsbn);
		group.add(rdbtnIsbn);
		
		JRadioButton rdbtnAuthor = new JRadioButton("Author");
		rdbtnAuthor.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(rdbtnAuthor);
		group.add(rdbtnAuthor);
		
		JButton btnFindBook = new JButton("Rechercher");
		btnFindBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		GridBagConstraints gbc_btnFindBook = new GridBagConstraints();
		gbc_btnFindBook.anchor = GridBagConstraints.SOUTH;
		gbc_btnFindBook.gridx = 0;
		gbc_btnFindBook.gridy = 7;
		desktopPaneLeft.add(btnFindBook, gbc_btnFindBook);
		
		//Bottom
		JPanel panelDown = new JPanel();
		frame.getContentPane().add(panelDown, BorderLayout.SOUTH);
	}
	
	private static final class JGradientButton extends JButton{
	    private JGradientButton(String text){
	        super(text);
	        setContentAreaFilled(false);
	    }

	    @Override
	    protected void paintComponent(Graphics g){
	        Graphics2D g2 = (Graphics2D)g.create();
	        g2.setPaint(new GradientPaint(
	                new Point(0, 0), 
	                getBackground(), 
	                new Point(0, getHeight()/3), 
	                Color.YELLOW));
	        g2.fillRect(0, 0, getWidth(), getHeight()/3);
	        g2.setPaint(new GradientPaint(
	                new Point(0, getHeight()/3), 
	                Color.YELLOW, 
	                new Point(0, getHeight()), 
	                getBackground()));
	        g2.fillRect(0, getHeight()/3, getWidth(), getHeight());
	        g2.dispose();

	        super.paintComponent(g);
	    }
	}
}

