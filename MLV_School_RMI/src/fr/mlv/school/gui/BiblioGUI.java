package fr.mlv.school.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import fr.mlv.school.BookImpl;
import fr.mlv.school.Library;
import fr.mlv.school.User;

public class BiblioGUI {

	private final JFrame	 frame	   = new JFrame();
	private JTable			 table;
	private final JTextField textField = new JTextField();;
	private Library			 library;
	private User			 user;

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// BiblioGUI window = new BiblioGUI();
	// window.frame.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	/**
	 * Create the application.
	 * 
	 * @param library
	 * 
	 * @param user
	 * 
	 * @throws RemoteException
	 */
	public BiblioGUI(Library library, User user) {
		this.library = library;
		this.user = user;
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws RemoteException
	 */
	public static BiblioGUI construct(Library library, User user) {
		BiblioGUI biblioGUI = new BiblioGUI(library, user);

		int frameWidth = 900;
		int frameHeight = 600;
		Color myColor = new Color(218, 165, 32);

		// Fenetre principale
		biblioGUI.frame.setTitle("Biblioteque MLV");
		// frame.setSize(frameWidth, frameHeight);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		biblioGUI.frame.setBounds((int) screenSize.getWidth() - frameWidth, 0, frameWidth, frameHeight);
		biblioGUI.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		biblioGUI.frame.setResizable(false);
		biblioGUI.frame.setLocationRelativeTo(null);

		// Up
		JPanel panelTop = new JPanel();
		biblioGUI.frame.getContentPane().add(panelTop, BorderLayout.NORTH);

		JLabel lblNewLabel = new JLabel(" ");
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		ImageIcon imageBiblio = new ImageIcon(
				new ImageIcon(BiblioGUI.class.getResource("/fr/mlv/school/gui/biblio.jpg")).getImage()
						.getScaledInstance(biblioGUI.frame.getWidth(), 150, Image.SCALE_DEFAULT));
		panelTop.setLayout(new GridLayout(0, 1, 0, 300));

		lblNewLabel.setIcon(imageBiblio);
		panelTop.add(lblNewLabel);

		// Center
		JSplitPane splitPaneCenter = new JSplitPane();
		splitPaneCenter.setResizeWeight(0.05);
		biblioGUI.frame.getContentPane().add(splitPaneCenter, BorderLayout.CENTER);

		JPanel panelRight = new JPanel();
		splitPaneCenter.setRightComponent(panelRight);
		panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.X_AXIS));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getViewport().setBackground(myColor);
		panelRight.add(scrollPane);

		Object data[][] = { { "Test1", "Test2", "Test2", "Test4", "Test5", "Test6", "Ajouter au panier" } };
		String headers[] = { "Isbn", "Title", "Author", "Summary", "Publisher", "Format", "+" };

		DefaultTableModel model = new DefaultTableModel(data, headers);
		JTable table = new JTable(model);

		ButtonEditor buttonAddPanier = new ButtonEditor(new JCheckBox());
		buttonAddPanier.addTableModel(model);

		table.getColumn("+").setCellRenderer(new ButtonRenderer());
		table.getColumn("+").setCellEditor(buttonAddPanier);
		scrollPane.setViewportView(table);

		JDesktopPane desktopPaneLeft = new JDesktopPane();
		desktopPaneLeft.setBackground(new Color(218, 165, 32));
		splitPaneCenter.setLeftComponent(desktopPaneLeft);
		GridBagLayout gbl_desktopPaneLeft = new GridBagLayout();
		gbl_desktopPaneLeft.columnWidths = new int[] { 183, 0 };
		gbl_desktopPaneLeft.rowHeights = new int[] { 22, 23, 23, 160, 16, 28, 1, 29, 0 };
		gbl_desktopPaneLeft.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_desktopPaneLeft.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		desktopPaneLeft.setLayout(gbl_desktopPaneLeft);

		GridBagConstraints gbc_btnConnexion = new GridBagConstraints();
		gbc_btnConnexion.anchor = GridBagConstraints.NORTH;
		gbc_btnConnexion.insets = new Insets(0, 0, 5, 0);
		gbc_btnConnexion.gridx = 0;
		gbc_btnConnexion.gridy = 1;

		JButton btnPanier = new JButton("Mon panier");
		btnPanier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PanierGUI panierGUI = new PanierGUI();
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

		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 5;
		desktopPaneLeft.add(biblioGUI.textField, gbc_textField);
		biblioGUI.textField.setColumns(10);

		JButton btnFindBook = new JButton("Rechercher");
		btnFindBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				boolean find = false;
				table.setRowSelectionAllowed(true);
				String bookInfo = biblioGUI.textField.getText();
				System.out.println(bookInfo);

				for (int row = 0; row <= table.getRowCount() - 1; row++) {

					for (int col = 0; col <= table.getColumnCount() - 1; col++) {

						if (bookInfo.equals(table.getValueAt(row, col))) {
							find = true;

							System.out.println("trouvé en " + row + " " + col);

							// this will automatically set the view of the
							// scroll in the location of the value
							table.scrollRectToVisible(table.getCellRect(row, 0, true));

							// this will automatically set the focus of the
							// searched/selected row/value
							table.setRowSelectionInterval(row, row);
						}
					}
				}

				if (!find) {
					table.setRowSelectionAllowed(false);
				}
			}
		});

		GridBagConstraints gbc_btnFindBook = new GridBagConstraints();
		gbc_btnFindBook.insets = new Insets(0, 0, 5, 0);
		gbc_btnFindBook.anchor = GridBagConstraints.SOUTH;
		gbc_btnFindBook.gridx = 0;
		gbc_btnFindBook.gridy = 6;
		desktopPaneLeft.add(btnFindBook, gbc_btnFindBook);

		// Bottom
		JPanel panelDown = new JPanel();
		biblioGUI.frame.getContentPane().add(panelDown, BorderLayout.SOUTH);

		return biblioGUI;
	}
}
