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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import java.util.function.Consumer;

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
import javax.swing.table.TableColumn;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import fr.mlv.school.Book;
import fr.mlv.school.Library;
import fr.mlv.school.User;


public class BiblioGUI {
	private final JFrame						   frame	 = new JFrame();
	private final JTextField					   textField = new JTextField();;
	private final Library						   library;
	private final User							   user;

	private final ArrayList<Consumer<WindowEvent>> consumers = new ArrayList<>();
	private final PanierGUI						   panierGUI;

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
	public BiblioGUI(Library library, User user, PanierGUI panierGUI) {
		this.library = library;
		this.user = user;
		this.panierGUI = panierGUI;
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws RemoteException
	 */
	public static BiblioGUI construct(Library library, User user) throws RemoteException {
		Vector<Vector<Object>> data = new Vector<>();
		Vector<Object> headers = new Vector<>();
		headers.addElement("Isbn");
		headers.addElement("Title");
		headers.addElement("Author");
		headers.addElement("Publisher");
		headers.addElement("");

		DefaultTableModel tableModel = new DefaultTableModel(data, headers);
		JTable table = new JTable(tableModel);
		table.setRowSelectionAllowed(false);

		PanierGUI panierGUI = PanierGUI.construct();
		BiblioGUI biblioGUI = new BiblioGUI(library, user, panierGUI);

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
		Color myColor = new Color(218, 165, 32);

		// Fenetre principale
		try {
			biblioGUI.frame.setTitle(library.getName());
		} catch (RemoteException e1) {
			e1.printStackTrace(System.err);
			biblioGUI.frame.setTitle("Bibliothèque inconnue");
		}
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

		BorrowButton borrowButton = BorrowButton.construct(user, library, new JCheckBox());

		TableColumn columnKart = table.getColumn(headers.lastElement());
		columnKart.setCellRenderer(new ButtonRenderer());
		columnKart.setCellEditor(borrowButton);
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

		Book books[] = library.getAllBooks();
		biblioGUI.setBooks(data, tableModel, books);
		JButton btnFindBook = new JButton("Rechercher");
		btnFindBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					Book[] books = library.searchByTitle(biblioGUI.textField.getText());

					biblioGUI.setBooks(data, tableModel, books);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace(System.err);
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
				biblioGUI.panierGUI.close();
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

		return biblioGUI;
	}

	public void close() {
		frame.dispose();
	}

	public void addCloseListener(Consumer<WindowEvent> consumer) {
		consumers.add(consumer);
	}

	public void setBooks(Vector<Vector<Object>> data, DefaultTableModel tableModel, Book[] books) {
		data.removeAllElements();

		Arrays.stream(books).forEach(book -> {
			try {
				Vector<Object> vectorBook = new Vector<>();
				long isbn = book.getISBN();
				vectorBook.addElement(isbn);
				vectorBook.addElement(book.getTitle());
				vectorBook.addElement(book.getAuthor());
				vectorBook.addElement(book.getPublisher());
				vectorBook.addElement(new CellValue(library, book));
				data.addElement(vectorBook);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace(System.err);
			}
		});

		tableModel.fireTableDataChanged();
	}

	public void callBankServiceDepositeMoney(String idAccount,String firstname,String lastname,String currency,String amount) throws IOException{
		URL url = new URL("http://localhost:8080/BankService/services/BankManager?method=depositMoney&id="+idAccount+
				"&firstname="+firstname+"&lastname="+lastname+"&currency="+currency+"&amount="+amount);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
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
	
	public void callBankServiceRemoveMoney(String idAccount,String firstname,String lastname,String currency,String amount) throws IOException{
		URL url = new URL("http://localhost:8080/BankService/services/BankManager?method=removeMoney&id="+idAccount+
				"&firstname="+firstname+"&lastname="+lastname+"&currency="+currency+"&amount="+amount);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
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
	
	public void callBankServicePrintBalance(String idAccount,String currency) throws IOException{
		URL url = new URL("http://localhost:8080/BankService/services/BankManager?method=printBalance&id="+idAccount+"&currency="+currency);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
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
