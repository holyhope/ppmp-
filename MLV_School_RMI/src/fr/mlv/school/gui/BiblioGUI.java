package fr.mlv.school.gui;

import java.awt.Color;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import fr.mlv.school.Book;
import fr.mlv.school.Library;
import fr.mlv.school.User;
import fr.mlv.school.gui.table.BorrowButton;
import fr.mlv.school.gui.table.BorrowButtonRenderer;
import fr.mlv.school.gui.table.BuyButton;
import fr.mlv.school.gui.table.BuyButtonRenderer;
import fr.mlv.school.gui.table.CellRenderer;
import fr.mlv.school.gui.table.HeaderCellRenderer;

public class BiblioGUI implements WindowListener {
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

		biblioGUI.defineHeader(tableModel);

		biblioGUI.defineTable(tableModel);

		Book books[] = library.getAllBooks();
		biblioGUI.setBooks(books);
		tableModel.fireTableDataChanged();

		biblioGUI.frame.addWindowListener(biblioGUI);

		biblioGUI.frame.setVisible(true);

		return biblioGUI;
	}

	private void defineHeader(DefaultTableModel tableModel) throws RemoteException {
		JPanel header = new JPanel();
		header.setLayout(null);
		int frameWidth = frame.getWidth();
		int headerSize = getHeaderSize();
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
		headerFilter.setBackground(
				new Color(theme.background.getRed(), theme.background.getGreen(), theme.background.getBlue(), 160));
		headerFilter.setBounds(header.getBounds());
		header.add(headerFilter);
		header.add(headerImage);
		frame.getContentPane().add(header);

		headerLabel.setForeground(theme.primary);
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

						setBooks(books);
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

					setBooks(books);
					tableModel.fireTableDataChanged();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace(System.err);
				}
			}
		});

		textField.grabFocus();
	}

	private void defineTable(DefaultTableModel tableModel) {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, getHeaderSize(), frame.getWidth(), frame.getHeight() - getHeaderSize());
		frame.getContentPane().add(scrollPane);
		theme.applyTo(scrollPane);

		JTable table = new JTable(tableModel);
		theme.applyTo(table);
		table.setRowSelectionAllowed(true);
		scrollPane.setViewportView(table);

		BookCard bookCard = BookCard.construct(theme);

		TableColumn isbnColumn = table.getColumn("Isbn");
		isbnColumn.setPreferredWidth(50);
		Function<Book, String> functionIsbn = b -> {
			try {
				return Long.toString(b.getISBN());
			} catch (Exception e) {
				e.printStackTrace(System.err);
				return "";
			}
		};
		isbnColumn.setCellRenderer(CellRenderer.construct(theme, bookCard, functionIsbn));
		isbnColumn.setCellEditor(CellEditor.construct(theme, bookCard, functionIsbn));
		isbnColumn.setHeaderRenderer(new HeaderCellRenderer(theme));

		TableColumn titleColumn = table.getColumn("Titre");
		titleColumn.setPreferredWidth(200);
		Function<Book, String> functionTitle = b -> {
			try {
				return b.getTitle();
			} catch (Exception e) {
				e.printStackTrace(System.err);
				return "";
			}
		};
		titleColumn.setCellRenderer(CellRenderer.construct(theme, bookCard, functionTitle));
		isbnColumn.setCellEditor(CellEditor.construct(theme, bookCard, functionTitle));
		titleColumn.setHeaderRenderer(new HeaderCellRenderer(theme));

		TableColumn authorColumn = table.getColumn("Auteur");
		authorColumn.setPreferredWidth(100);
		Function<Book, String> functionAuthor = b -> {
			try {
				return b.getAuthor();
			} catch (Exception e) {
				e.printStackTrace(System.err);
				return "";
			}
		};
		authorColumn.setCellRenderer(CellRenderer.construct(theme, bookCard, functionAuthor));
		isbnColumn.setCellEditor(CellEditor.construct(theme, bookCard, functionAuthor));
		authorColumn.setHeaderRenderer(new HeaderCellRenderer(theme));

		TableColumn publisherColumn = table.getColumn("Éditeur");
		publisherColumn.setPreferredWidth(100);
		Function<Book, String> functionPublisher = b -> {
			try {
				return b.getPublisher();
			} catch (Exception e) {
				e.printStackTrace(System.err);
				return "";
			}
		};
		publisherColumn.setCellRenderer(CellRenderer.construct(theme, bookCard, functionPublisher));
		isbnColumn.setCellEditor(CellEditor.construct(theme, bookCard, functionPublisher));
		publisherColumn.setHeaderRenderer(new HeaderCellRenderer(theme));

		TableColumn columnBorrow = table.getColumn("Emprunt");
		columnBorrow.setCellRenderer(BorrowButtonRenderer.construct(theme, library));
		columnBorrow.setCellEditor(BorrowButton.construct(user, library, new JCheckBox()));
		columnBorrow.setHeaderRenderer(new HeaderCellRenderer(theme));
		columnBorrow.setPreferredWidth(50);

		TableColumn columnBuy = table.getColumn("Achat");
		columnBuy.setCellRenderer(BuyButtonRenderer.construct(theme, library));
		columnBuy.setCellEditor(BuyButton.construct(user, new JCheckBox()));
		columnBuy.setHeaderRenderer(new HeaderCellRenderer(theme));
		columnBuy.setPreferredWidth(50);
	}

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
		consumers.parallelStream().forEach(consumer -> consumer.accept(e));
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
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
			Vector<Object> vectorBook = new Vector<>();
			vectorBook.addElement(book);
			vectorBook.addElement(book);
			vectorBook.addElement(book);
			vectorBook.addElement(book);
			vectorBook.addElement(book);
			vectorBook.addElement(book);
			content.addElement(vectorBook);
		});
	}

	public void callBankServiceDepositeMoney(String idAccount, String firstname, String lastname, String currency,
			String amount) throws IOException {
		URL url = new URL("http://localhost:8080/BankService/services/BankManager?method=depositMoney&id=" + idAccount
				+ "&firstname=" + firstname + "&lastname=" + lastname + "&currency=" + currency + "&amount=" + amount);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();

		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String str;
		StringBuffer stringBuffer = new StringBuffer();
		while ((str = bufferReader.readLine()) != null) {
			stringBuffer.append(str);
			stringBuffer.append("\n");
		}
		System.out.println(stringBuffer.toString());
	}

	public void callBankServiceRemoveMoney(String idAccount, String firstname, String lastname, String currency,
			String amount) throws IOException {
		URL url = new URL("http://localhost:8080/BankService/services/BankManager?method=removeMoney&id=" + idAccount
				+ "&firstname=" + firstname + "&lastname=" + lastname + "&currency=" + currency + "&amount=" + amount);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();

		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String str;
		StringBuffer stringBuffer = new StringBuffer();
		while ((str = bufferReader.readLine()) != null) {
			stringBuffer.append(str);
			stringBuffer.append("\n");
		}
		System.out.println(stringBuffer.toString());
	}

	public void callBankServicePrintBalance(String idAccount, String currency) throws IOException {
		URL url = new URL("http://localhost:8080/BankService/services/BankManager?method=printBalance&id=" + idAccount
				+ "&currency=" + currency);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();

		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String str;
		StringBuffer stringBuffer = new StringBuffer();

		while ((str = bufferReader.readLine()) != null) {
			stringBuffer.append(str);
			stringBuffer.append("\n");
		}
		System.out.println(stringBuffer.toString());
	}
}
