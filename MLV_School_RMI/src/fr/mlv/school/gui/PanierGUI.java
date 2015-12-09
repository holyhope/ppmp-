package fr.mlv.school.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class PanierGUI {

	private final JFrame frame = new JFrame();

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
	private PanierGUI() {
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public static PanierGUI construct() {
		PanierGUI panierGUI = new PanierGUI();
		int frameWidth = 450;
		int frameHeight = 300;
		Color myColor = new Color(218, 165, 32);

		panierGUI.frame.setTitle("Mon Panier");
		panierGUI.frame.setBounds(100, 100, 601, 401);
		panierGUI.frame.setLocationRelativeTo(null);
		panierGUI.frame.setVisible(true);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		Object data[][] = { { "Test", "Test", "Test", "Test", "Test", "Test", "Supprimer" } };
		String headers[] = { "Isbn", "Title", "Author", "Summary", "Publisher", "Format", "-" };

		JTable table = new JTable(data, headers);
		table.setBounds(100, 100, frameWidth, frameHeight);
		table.getColumn("-").setCellRenderer(new ButtonRenderer());
		table.getColumn("-").setCellEditor(new ButtonEditor(new JCheckBox()));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(table);
		scrollPane.getViewport().setBackground(myColor);
		panierGUI.frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

		// frame.setResizable(false);

		return panierGUI;
	}
}
