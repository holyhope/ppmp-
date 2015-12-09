package fr.mlv.school.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.rmi.RemoteException;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import fr.mlv.school.Book;

public class PanierGUI {

	private final JFrame				 frame	 = new JFrame();

	private final Vector<Vector<Object>> content = new Vector<>();

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
		panierGUI.frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		Vector<String> headers = new Vector<>();
		headers.addElement("Isbn");
		headers.addElement("Title");
		headers.addElement("Author");
		headers.addElement("Publisher");
		headers.addElement("Date");
		headers.addElement("");

		JTable table = new JTable(panierGUI.content, headers);
		table.setBounds(100, 100, frameWidth, frameHeight);
		TableColumn columnKart = table.getColumn(headers.lastElement());
		columnKart.setCellRenderer(new ButtonRenderer());
		columnKart.setCellEditor(new ButtonEditor(new JCheckBox()));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(table);
		scrollPane.getViewport().setBackground(myColor);
		panierGUI.frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

		// frame.setResizable(false);

		return panierGUI;
	}

	public void setContent(Vector<Book> books) throws RemoteException {
		Vector<Vector<Object>> newContent = new Vector<>(books.size());

		for (Book book : books) {
			Vector<Object> line = new Vector<>();
			line.addElement(book.getISBN());
			line.addElement(book.getTitle());
			line.addElement(book.getAuthor());
			line.addElement(book.getPublisher());
			line.addElement(book.getDate());
			newContent.addElement(line);
		}

		content.removeAllElements();
		content.addAll(newContent);
	}

	public void show() {
		frame.setVisible(true);
	}

	public void close() {
		frame.dispose();
	}
}
