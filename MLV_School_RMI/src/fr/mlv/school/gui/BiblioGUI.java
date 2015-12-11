package fr.mlv.school.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventObject;
import java.util.Vector;
import java.util.function.Consumer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import fr.mlv.school.Book;
import fr.mlv.school.Library;
import fr.mlv.school.User;

public class BiblioGUI {
	private final JFrame						   frame	 = new JFrame();

	private final Vector<Vector<Object>>		   content	 = new Vector<>();

	private final ArrayList<Consumer<WindowEvent>> consumers = new ArrayList<>();

	private final Theme							   theme;
	private final Library						   library;
	private final User							   user;

	/**
	 * Create the application.
	 * 
	 * @param library
	 * 
	 * @param user
	 * @param panierGUI
	 * 
	 * @throws RemoteException
	 */
	private BiblioGUI(Theme theme, Library library, User user) {
		this.theme = theme;
		this.library = library;
		this.user = user;
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @param theme
	 * 
	 * @throws RemoteException
	 */
	public static BiblioGUI construct(Theme theme, Library library, User user) throws RemoteException {
		BiblioGUI biblioGUI = new BiblioGUI(theme, library, user);

		int headerSize = biblioGUI.getHeaderSize();

		// TODO Error occured here
		/*
		 * user.addObserver(new Consumer<Notification>() {
		 * 
		 * @Override public void accept(Notification notification) { try { Book
		 * book = notification.getBook(); int response =
		 * JOptionPane.showConfirmDialog(biblioGUI.frame, book.getTitle() +
		 * " est disponible.\nVoulez-vous le réserver ?"); switch (response) {
		 * case JOptionPane.YES_OPTION: library.getBook(book, user); case
		 * JOptionPane.NO_OPTION: user.consumeNotification(notification); break;
		 * } } catch (RemoteException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(System.err); } } });
		 */

		int frameWidth = 900;
		int frameHeight = 600;

		// Fenetre principale
		biblioGUI.frame.setTitle(library.getName());
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		biblioGUI.frame.setBounds((int) screenSize.getWidth() - frameWidth, 0, frameWidth, frameHeight);
		biblioGUI.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		biblioGUI.frame.setResizable(false);
		biblioGUI.frame.setLocationRelativeTo(null);
		biblioGUI.frame.getContentPane().setLayout(null);
		biblioGUI.frame.setBackground(biblioGUI.theme.background);

		Vector<Object> headers = new Vector<>();
		headers.addElement("Isbn");
		headers.addElement("Titre");
		headers.addElement("Auteur");
		headers.addElement("Éditeur");
		headers.addElement("Emprunt");
		headers.addElement("Achat");

		DefaultTableModel tableModel = new DefaultTableModel(biblioGUI.content, headers);

		// Up

		JPanel header = new JPanel();
		header.setLayout(null);
		header.setBounds(0, 0, frameWidth, headerSize);
		JLabel headerImage = new JLabel("Test");
		ImageIcon imageBiblio = new ImageIcon(
				new ImageIcon(BiblioGUI.class.getResource("/fr/mlv/school/gui/biblio.jpg")).getImage()
						.getScaledInstance(frameWidth, 150, Image.SCALE_DEFAULT));
		headerImage.setIcon(imageBiblio);
		headerImage.setBounds(0, 0, frameWidth, headerSize);

		JLabel headerLabel = new JLabel(library.getName());
		headerLabel.setBounds(50, 20, frameWidth - 100, 30);
		header.add(headerLabel);

		@SuppressWarnings("serial")
		JTextField textField = new JTextField() {
			@Override
			protected void paintComponent(final Graphics pG) {
				super.paintComponent(pG);

				if (getText().length() > 0) {
					return;
				}

				final Graphics2D g = (Graphics2D) pG;
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g.setColor(getDisabledTextColor());
				g.drawString("Rechercher un livre...", 1, pG.getFontMetrics().getMaxAscent() + 6);
			}
		};
		textField.setBounds(50, 70, frameWidth - 250, 30);
		header.add(textField);

		JButton btnFindBook = new JButton("Rechercher");
		btnFindBook.setBounds(frameWidth - 150, 70, 100, 30);
		header.add(btnFindBook);

		JPanel headerFilter = new JPanel();
		headerFilter.setBackground(new Color(biblioGUI.theme.background.getRed(), biblioGUI.theme.background.getGreen(),
				biblioGUI.theme.background.getBlue(), 160));
		headerFilter.setBounds(header.getBounds());
		header.add(headerFilter);
		header.add(headerImage);
		biblioGUI.frame.getContentPane().add(header);

		headerLabel.setForeground(biblioGUI.theme.primary);
		headerLabel.setLabelFor(textField);
		headerLabel.setFont(new Font(null, Font.BOLD, 30));

		theme.applyTo(textField);
		textField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						Book[] books = library.searchByTitle(textField.getText());

						biblioGUI.setBooks(books);
						tableModel.fireTableDataChanged();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

		theme.applyTo(btnFindBook);
		btnFindBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					Book[] books = library.searchByTitle(textField.getText());

					biblioGUI.setBooks(books);
					tableModel.fireTableDataChanged();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace(System.err);
				}
			}
		});

		// Center

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, headerSize, frameWidth, frameHeight - headerSize);
		biblioGUI.frame.getContentPane().add(scrollPane);
		theme.applyTo(scrollPane);

		JTable table = new JTable(tableModel);
		theme.applyTo(table);
		table.setRowSelectionAllowed(true);
		scrollPane.setViewportView(table);

		TableCellEditor nonEditableCellEditor = new TableCellEditor() {
			@Override
			public boolean stopCellEditing() {
				return false;
			}

			@Override
			public boolean shouldSelectCell(EventObject anEvent) {
				return false;
			}

			@Override
			public void removeCellEditorListener(CellEditorListener l) {
			}

			@Override
			public boolean isCellEditable(EventObject anEvent) {
				return false;
			}

			@Override
			public Object getCellEditorValue() {
				return null;
			}

			@Override
			public void cancelCellEditing() {
			}

			@Override
			public void addCellEditorListener(CellEditorListener l) {
			}

			@Override
			public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
					int column) {
				return null;
			}
		};

		TableColumn isbnColumn = table.getColumn("Isbn");
		isbnColumn.setPreferredWidth(50);
		isbnColumn.setCellRenderer(new LabelCellRenderer(theme));
		isbnColumn.setCellEditor(nonEditableCellEditor);
		isbnColumn.setHeaderRenderer(new HeaderCellRenderer(theme));

		TableColumn titleColumn = table.getColumn("Titre");
		titleColumn.setPreferredWidth(200);
		titleColumn.setCellRenderer(new LabelCellRenderer(theme));
		titleColumn.setCellEditor(nonEditableCellEditor);
		titleColumn.setHeaderRenderer(new HeaderCellRenderer(theme));

		TableColumn authorColumn = table.getColumn("Auteur");
		authorColumn.setPreferredWidth(100);
		authorColumn.setCellRenderer(new LabelCellRenderer(theme));
		authorColumn.setCellEditor(nonEditableCellEditor);
		authorColumn.setHeaderRenderer(new HeaderCellRenderer(theme));

		TableColumn publisherColumn = table.getColumn("Éditeur");
		publisherColumn.setPreferredWidth(100);
		publisherColumn.setCellRenderer(new LabelCellRenderer(theme));
		publisherColumn.setCellEditor(nonEditableCellEditor);
		publisherColumn.setHeaderRenderer(new HeaderCellRenderer(theme));

		TableColumn columnBorrow = table.getColumn("Emprunt");
		columnBorrow.setCellRenderer(BorrowButtonRenderer.construct(theme, library));
		columnBorrow.setCellEditor(BorrowButton.construct(user, library, new JCheckBox()));
		columnBorrow.setHeaderRenderer(new HeaderCellRenderer(theme));
		columnBorrow.setPreferredWidth(50);

		TableColumn columnBuy = table.getColumn("Achat");
		columnBuy.setCellRenderer(BuyButtonRenderer.construct(theme, library));
		columnBuy.setCellEditor(BuyButton.construct(user, library, new JCheckBox()));
		columnBuy.setHeaderRenderer(new HeaderCellRenderer(theme));
		columnBuy.setPreferredWidth(50);

		Book books[] = library.getAllBooks();
		biblioGUI.setBooks(books);
		tableModel.fireTableDataChanged();

		// Bottom
		biblioGUI.frame.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				biblioGUI.consumers.parallelStream().forEach(consumer -> consumer.accept(e));
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}
		});

		biblioGUI.frame.setVisible(true);
		textField.grabFocus();

		return biblioGUI;
	}

	public int getWindowWidth() {
		return frame.getWidth();
	}

	public int getHeaderSize() {
		return 150;
	}

	public void close() {
		frame.dispose();
	}

	public void addCloseListener(Consumer<WindowEvent> consumer) {
		consumers.add(consumer);
	}

	public void setBooks(Book[] books) {
		content.removeAllElements();

		Arrays.stream(books).forEach(book -> {
			try {
				Vector<Object> vectorBook = new Vector<>();
				long barCode = book.getBarCode();
				vectorBook.addElement(book.getISBN());
				vectorBook.addElement(book.getTitle());
				vectorBook.addElement(book.getAuthor());
				vectorBook.addElement(book.getPublisher());
				vectorBook.addElement(barCode);
				vectorBook.addElement(barCode);
				content.addElement(vectorBook);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace(System.err);
			}
		});
	}
}
