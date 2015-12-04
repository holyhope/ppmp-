package fr.mlv.school.gui;

import java.awt.EventQueue;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JDesktopPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JSplitPane;
import javax.swing.JPanel;

public class PanierGUI {

	private JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PanierGUI window = new PanierGUI();
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
	public PanierGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		int frameWidth = 450;
		int frameHeight = 300;
		Color myColor = new Color(218,165,32);
		
		frame = new JFrame();
		frame.setTitle("Mon Panier");
		frame.setBounds(100, 100, 601, 401);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();	
		
		Object data[][] = {{"Test","Test","Test","Test","Test","Test","Supprimer"}};
		String headers[]= {"Isbn","Title","Author","Summary","Publisher","Format","-"};
		
		table = new JTable(data,headers);
		table.setBounds(100, 100, frameWidth, frameHeight);
		table.getColumn("-").setCellRenderer(new ButtonRenderer());
		table.getColumn("-").setCellEditor(
		    new ButtonEditor(new JCheckBox()));
	
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(table);
		scrollPane.getViewport().setBackground(myColor);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		//frame.setResizable(false);
	}
}
